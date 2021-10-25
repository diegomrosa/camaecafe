package com.camaecafe;

import java.util.Map;

import com.camaecafe.dao.DaoException;
import com.camaecafe.dao.Db;
import com.camaecafe.dao.ScrapeDao;

public class ScrapeManager {
	private static Map<Long, Scrape> cachedScrapes;

	public static Map<Long, Scrape> getScrapeMap() {
		if (cachedScrapes == null) {
			Db db = null;

			try {
				db = Db.openDbConnection();
				cachedScrapes = new ScrapeDao(db).readAll();
			} catch (DaoException exc) {
				exc.printStackTrace();
			} finally {
				db.close();
			}
		}
		return cachedScrapes;
	}
	
	public static Scrape findScrape(Long id) {
		return getScrapeMap().get(id);
	}
}
