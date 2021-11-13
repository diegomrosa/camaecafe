package com.camaecafe;


import java.io.IOException;
import java.sql.PreparedStatement;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.csv.CSVRecord;
import org.apache.tika.language.detect.LanguageConfidence;
import org.apache.tika.language.detect.LanguageResult;

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
	private static final Logger LOG = Logger.getLogger(Main.class.getName());
	
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
		
		//loadCSVandCreateBD();
		//scrapeAirbnbLocations();
		//testLanguageDetection();
		//detectAllReviewLanguages();
		
		System.out.println("===== Finishing Cama&Café =====");
	}
	
	private static void loadCSVandCreateBD() {
		//System.out.println("Starting CSV loading and DB creation: ");
		//loadCityListings("Beijing", Dataset.BEIJING_LISTINGS_FILE);
		//loadCityListings("New York", Dataset.NEWYORK_LISTINGS_FILE);
		//loadCityListings("Paris", Dataset.PARIS_LISTINGS_FILE);
		//loadCityListings("Rio de Janeiro", Dataset.RIO_LISTINGS_FILE);
		//loadCityReviews("Beijing", Dataset.BEIJING_REVIEWS_FILE);
		//loadCityReviews("New York", Dataset.NEWYORK_REVIEWS_FILE);
		//loadCityReviews("Paris", Dataset.PARIS_REVIEWS_FILE);
		//loadCityReviews("Rio de Janeiro", Dataset.RIO_REVIEWS_FILE);
		//System.out.println("Finishing CSV loading and DB creation.");
	}
	
	private static void scrapeAirbnbLocations() {
		long sleepTime = 20;
		String jul2016Start = "2016-07-01";
		String jul2016End = "2016-07-31";
		AirbnbScraper scraper = new AirbnbScraper();
		Db db = null;
		int users = 0;
		//long jeanne = 2444956L;
		//long rodney = 32212L;

		try {
			db = Db.openDbConnection();
			ReviewDao dao = new ReviewDao(db);
			List<Long> authorIds = dao.readAuthorsIdsBetweenDates(jul2016Start, jul2016End);

			System.out.println("Scraping authors: " + authorIds.size());
			for (Long id : authorIds) {
				Object[] result = scraper.scrapeUserInfo(id);

				if (result != null) {
					System.out.println("- User " + id + ": [" + result[0] + "], [" + result[1] + "]");
				}
				users++;
				if (users % 1000 == 0) {
					System.out.println("===== Scraped " + users + " users. =====");
				}
				Thread.sleep(sleepTime);
			}
		} catch (IOException exc) {
			exc.printStackTrace();
		} catch (DaoException exc) {
			exc.printStackTrace();
		} catch (InterruptedException exc) {
			exc.printStackTrace();
		} finally {
			Db.close(db);
		}
	}
	
	private static void testLanguageDetection() {
		try {
			LanguageDetection detect = new LanguageDetection();
			String commentsPt = "Oi, me chamo Diego. Gostei muito do quarto.";
			String commentsEn = "I liked the bedroom. Internet connection was great.";
			String commentsEs = "Hola, mi nombre es Diego. Me gusta mucho el dormitorio";
			long startTime = 0L;
			long endTime = 0L;
			
			startTime = System.currentTimeMillis();
			LanguageResult resultPt = detect.detectLanguage(commentsPt);
			LanguageResult resultEn = detect.detectLanguage(commentsEn);
			LanguageResult resultEs = detect.detectLanguage(commentsEs);
			endTime = System.currentTimeMillis();
			
			System.out.println("Língua detectada: " + resultPt.getLanguage());
			System.out.println("Nível de confiança: " + resultPt.getConfidence());
			System.out.println("Língua detectada: " + resultEn.getLanguage());
			System.out.println("Nível de confiança: " + resultEn.getConfidence());
			System.out.println("Língua detectada: " + resultEs.getLanguage());
			System.out.println("Nível de confiança: " + resultEs.getConfidence());
			System.out.println("Tempo decorrido: " + startTime + " | " + endTime);
		} catch (IOException exc) {
			exc.printStackTrace();
		}
	}
	
	private static void detectAllReviewLanguages() {
		String[][] periods = new String[][] {
			
			// 2016
			//{"2016-07-01", "2016-07-31"},
			{"2016-08-01", "2016-08-31"},
			{"2016-09-01", "2016-09-30"},
			{"2016-10-01", "2016-10-31"},
			{"2016-11-01", "2016-11-30"},
			{"2016-12-01", "2016-12-31"},
			
			// 2017
			{"2017-01-01", "2017-01-31"},
			{"2017-02-01", "2017-02-28"},
			{"2017-03-01", "2017-03-31"},
			{"2017-04-01", "2017-04-30"},
			{"2017-05-01", "2017-05-31"},
			{"2017-06-01", "2017-06-30"},
			{"2017-07-01", "2017-07-31"},
			{"2017-08-01", "2017-08-31"},
			{"2017-09-01", "2017-09-30"},
			{"2017-10-01", "2017-10-31"},
			{"2017-11-01", "2017-11-30"},
			{"2017-12-01", "2017-12-31"},
			
			// 2018
			{"2018-01-01", "2018-01-31"},
			{"2018-02-01", "2018-02-28"},
			{"2018-03-01", "2018-03-31"},
			{"2018-04-01", "2018-04-30"},
			{"2018-05-01", "2018-05-31"},
			{"2018-06-01", "2018-06-30"},
			{"2018-07-01", "2018-07-31"},
			{"2018-08-01", "2018-08-31"},
			{"2018-09-01", "2018-09-30"},
			{"2018-10-01", "2018-10-31"},
			{"2018-11-01", "2018-11-30"},
			{"2018-12-01", "2018-12-31"},
			
			// 2019
			{"2019-01-01", "2019-01-31"},
			{"2019-02-01", "2019-02-28"},
			{"2019-03-01", "2019-03-31"},
			{"2019-04-01", "2019-04-30"},
			{"2019-05-01", "2019-05-31"},
			{"2019-06-01", "2019-06-30"},
			{"2019-07-01", "2019-07-31"},
			{"2019-08-01", "2019-08-31"},
			{"2019-09-01", "2019-09-30"},
			{"2019-10-01", "2019-10-31"},
			{"2019-11-01", "2019-11-30"},
			{"2019-12-01", "2019-12-31"},
			
			// 2020
			{"2020-01-01", "2020-01-31"},
			{"2020-02-01", "2020-02-29"},
			{"2020-03-01", "2020-03-31"},
			{"2020-04-01", "2020-04-30"},
			{"2020-05-01", "2020-05-31"},
			{"2020-06-01", "2020-06-30"},
			{"2020-07-01", "2020-07-31"},
			{"2020-08-01", "2020-08-31"},
			{"2020-09-01", "2020-09-30"},
			{"2020-10-01", "2020-10-31"},
			{"2020-11-01", "2020-11-30"},
			{"2020-12-01", "2020-12-31"},
			
			// 2021
			{"2021-01-01", "2021-01-31"},
			{"2021-02-01", "2021-02-28"},
			{"2021-03-01", "2021-03-31"},
			{"2021-04-01", "2021-04-30"},
			{"2021-05-01", "2021-05-31"},
			{"2021-06-01", "2021-06-30"}
		};
		for (int i = 0; i < periods.length; i++) {
			detectLanguages(periods[i][0], periods[i][1]);
		}
	}
	
	private static void detectLanguages(String start, String end) {
		Db db = null;
		PreparedStatement ps = null;
		LanguageDetection detect = null;

		try {
			db = Db.openDbConnection();
			ReviewDao dao = new ReviewDao(db);
			List<Review> reviews = dao.readReviewsBetweenDates(start, end);
			int revs = 0;

			System.out.println("Detecting languages up to " + end + ": " + reviews.size());
			detect = new LanguageDetection();
			db.enableTransactions();
			ps = dao.createUpdatePs();
			for (Review review : reviews) {
				String[] result = detect.detectLanguageAndConfidence(review.getComments());
				String langIso = result[0];
				String confidence = result[1];

				dao.updateReviewLanguage(ps, review.getId(), langIso, confidence);
				revs++;
				if (revs % 1000 == 0) {
					System.out.println("Inserted " + revs + " languages.");
				}
			}
			db.commit();
			System.out.println("Committed the work. All set.");
		} catch (DaoException exc) {
			exc.printStackTrace();
		} catch (IOException exc) {
			exc.printStackTrace();
		} finally {
			Db.close(db);
		}
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
