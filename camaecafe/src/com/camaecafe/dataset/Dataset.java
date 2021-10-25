package com.camaecafe.dataset;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public class Dataset {
	public static final int RIO = 0;
	
	private static final String USER_DIR = System.getProperty("user.dir");
	private static final String DATASET_DIR = USER_DIR + "/dataset";
	
	public static final String BEIJING_LISTINGS_FILE = DATASET_DIR + "/beijing_listings.csv";
	public static final String BEIJING_REVIEWS_FILE = DATASET_DIR + "/beijing_reviews.csv";
	public static final String NEWYORK_LISTINGS_FILE = DATASET_DIR + "/newyork_listings.csv";
	public static final String NEWYORK_REVIEWS_FILE = DATASET_DIR + "/newyork_reviews.csv";
	public static final String PARIS_LISTINGS_FILE = DATASET_DIR + "/paris_listings.csv";
	public static final String PARIS_REVIEWS_FILE = DATASET_DIR + "/paris_reviews.csv";
	public static final String RIO_LISTINGS_FILE = DATASET_DIR + "/rio_listings.csv";
	public static final String RIO_REVIEWS_FILE = DATASET_DIR + "/rio_reviews.csv";
	
	public static class Csv {
		private String filePath;
		private Reader reader;
		
		Csv(String filePath) throws DatasetException {
			this.filePath = filePath;
			try {
				this.reader = new FileReader(this.filePath);
			} catch (FileNotFoundException exc) {
				throw new DatasetException("CSV file not found " + this.filePath, exc);
			}
		}

		public Iterator<CSVRecord> getRecords() throws DatasetException {
			try {
				return CSVFormat.DEFAULT.parse(this.reader).iterator();
			} catch (IOException exc) {
				throw new DatasetException("Error parsing CSV file " + this.filePath, exc);
			}
		}
		
		public void close() {
			if (this.reader != null) {
				try {
					this.reader.close();
				} catch (IOException exc) {
					System.err.println("Error closing file " + this.filePath);
					exc.printStackTrace();
				}
			}
		}
	}
	
	public static Csv loadCsv(String csvFile) throws DatasetException {
		return new Csv(csvFile);
	}
}
