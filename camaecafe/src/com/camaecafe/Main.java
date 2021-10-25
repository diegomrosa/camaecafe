package com.camaecafe;


import java.text.NumberFormat;
import java.util.Date;
import java.util.Iterator;

import org.apache.commons.csv.CSVRecord;

import com.camaecafe.dao.DaoException;
import com.camaecafe.dao.Db;
import com.camaecafe.dao.ListingDao;
import com.camaecafe.dao.ReviewDao;
import com.camaecafe.dao.UserDao;
import com.camaecafe.dataset.Dataset;
import com.camaecafe.dataset.DatasetException;
import com.camaecafe.dataset.ListingCSV;
import com.camaecafe.dataset.ReviewsCSV;

public class Main {
	private static final int ITEMS_PER_BLOCK = 100;
	private static final int BLOCKS_PER_LINE = 100;
	private static final int ITEMS_PER_LINE = ITEMS_PER_BLOCK * BLOCKS_PER_LINE;
	private static final String BLOCK_STR = ".";
	private static final NumberFormat LINE_FORMAT = NumberFormat.getIntegerInstance();

	public static void main(String[] args) {
		System.out.println("===== Starting Cama&Café =====");
		System.out.println("Working Directory: " + System.getProperty("user.dir"));
		
		LINE_FORMAT.setMinimumIntegerDigits(4);
		LINE_FORMAT.setGroupingUsed(false);
		//loadCityListings("Beijing", Dataset.BEIJING_LISTINGS_FILE);
		//loadCityListings("New York", Dataset.NEWYORK_LISTINGS_FILE);
		//loadCityListings("Paris", Dataset.PARIS_LISTINGS_FILE);
		//loadCityListings("Rio de Janeiro", Dataset.RIO_LISTINGS_FILE);
		//loadCityReviews("Beijing", Dataset.BEIJING_REVIEWS_FILE);
		//loadCityReviews("New York", Dataset.NEWYORK_REVIEWS_FILE);
		//loadCityReviews("Paris", Dataset.PARIS_REVIEWS_FILE);
		//loadCityReviews("Rio de Janeiro", Dataset.RIO_REVIEWS_FILE);
		
		System.out.println("===== Finishing AirbnbScraper =====");
	}
	
	public static void loadCityListings(String cityName, String listingsFilePath) {
		System.out.println();
		System.out.println("*** Loading " + cityName + " listings (" + new Date() + ")");
		Dataset.Csv rioListingsCsv = null;
		Db db = null;
		
		try {
			rioListingsCsv = Dataset.loadCsv(listingsFilePath);
			Iterator<CSVRecord> records = rioListingsCsv.getRecords();
			Location previousLocation = null;
			Location currentLocation = null;
			int line = 1;
			int nItems = 1;
			
			records.next();  // Discards the header line.
			db = Db.openDbConnection();
			db.enableTransactions();
			System.out.print(LINE_FORMAT.format(line) + " ");
			while (records.hasNext()) {
				CSVRecord record = records.next();
				Long id = Long.valueOf(record.get(ListingCSV.id));
				String url = record.get(ListingCSV.listing_url);
				String name = record.get(ListingCSV.name);
			    String description = record.get(ListingCSV.description);
			    Long scrapeId = Long.valueOf(record.get(ListingCSV.scrape_id));
			    Scrape scrape = ScrapeManager.findScrape(scrapeId);
			    Long hostId = Long.valueOf(record.get(ListingCSV.host_id));
			    String hostName = record.get(ListingCSV.host_name);
		    	String rawHostLocation = record.get(ListingCSV.host_location);
		    	User host = null;
			    Listing listing = null;

		    	currentLocation = new Location(rawHostLocation);
		    	if ((previousLocation != null) && previousLocation.isSame(currentLocation)) {
		    		currentLocation = previousLocation;
		    	} else {
		    		currentLocation = LocationManager.findOrCreateLocation(db, currentLocation);
		    	}
		    	host = new User(hostId, Boolean.TRUE, hostName, currentLocation);
		    	new UserDao(db).insertIfNotExists(host);
			    listing = new Listing(id, url, scrape, host, name, description);
				new ListingDao(db).create(listing);
				db.commit();
			    if ((nItems % ITEMS_PER_BLOCK) == 0) {
			    	System.out.print(BLOCK_STR);
			    }
			    if ((nItems % ITEMS_PER_LINE) == 0) {
			    	line++;
			    	System.out.println();
			    	System.out.print(LINE_FORMAT.format(line) + " ");
			    }
				nItems++;
			}
		} catch (DatasetException exc) {
			System.err.println("Error reading dataset files.");
			exc.printStackTrace();
		} catch (DaoException exc) {
			System.err.println("Error accessing database.");
			exc.printStackTrace();
		} finally {
			rioListingsCsv.close();
			Db.close(db);
		}
		System.out.println();
		System.out.println("*** Finished loading " + cityName + " listings  (" + new Date() + ")");
	}
	
	public static void loadCityReviews(String cityName, String cityReviewsFilePath) {
		System.out.println();
		System.out.println("*** Loading " + cityName + " reviews (" + new Date() + ")");
		Dataset.Csv rioReviewsCsv = null;
		Db db = null;

		try {
			rioReviewsCsv = Dataset.loadCsv(cityReviewsFilePath);
			Iterator<CSVRecord> records = rioReviewsCsv.getRecords();
			int line = 1;
			int nItems = 1;

			records.next();  // Discards the header line.
			db = Db.openDbConnection();
			db.enableTransactions();
			System.out.print(LINE_FORMAT.format(line) + " ");
			while (records.hasNext()) {				
				CSVRecord record = records.next();
				Long listingId = Long.valueOf(record.get(ReviewsCSV.listing_id));
				Long id = Long.valueOf(record.get(ReviewsCSV.id));
				String date = record.get(ReviewsCSV.date);
				Long reviewerId = Long.valueOf(record.get(ReviewsCSV.reviewer_id));
				String reviewerName = record.get(ReviewsCSV.reviewer_name);
				String comments = record.get(ReviewsCSV.comments);
				User reviewer = null;
			    Review review = null;

			    reviewer = new User(reviewerId, Boolean.FALSE, reviewerName);
			    new UserDao(db).insertIfNotExists(reviewer);
			    review = new Review(id, date, comments, reviewer);
				new ReviewDao(db).create(review, listingId);
				db.commit();
				if ((nItems % ITEMS_PER_BLOCK) == 0) {
			    	System.out.print(BLOCK_STR);
			    }
			    if ((nItems % ITEMS_PER_LINE) == 0) {
			    	line++;
			    	System.out.println();
			    	System.out.print(LINE_FORMAT.format(line) + " ");
			    }
				nItems++;
			}
		} catch (DatasetException exc) {
			System.err.println("Error reading dataset files.");
			exc.printStackTrace();
		} catch (DaoException exc) {
			System.err.println("Error accessing database.");
			exc.printStackTrace();
		} finally {
			rioReviewsCsv.close();
			Db.close(db);
		}
		System.out.println("\n");
		System.out.println("*** Finished loading " + cityName +  " reviews  (" + new Date() + ")");
	}
}
