package com.camaecafe.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.camaecafe.Location;
import com.camaecafe.LocationManager;
import com.camaecafe.Scrape;

public class ScrapeDao extends Dao {
	private static final String SELECT_ALL = "SELECT * FROM scrape";

	private static final String SCRAPE_DATE = "scrape_date";
	private static final String LOCATION_ID = "location_id";
	
	public ScrapeDao(Db db) {
		super(db);
	}

	public Map<Long, Scrape> readAll() throws DaoException {
		Statement stmt = getDb().createStatement();
		ResultSet rs = null;
		
		try {
			rs = getDb().executeQuery(stmt, SELECT_ALL);
			Map<Long, Scrape> scrapeMap = new HashMap<Long, Scrape>();
			
			while (rs.next()) {
				Long id = Long.valueOf(rs.getLong(ID));
				Date date = new Date(rs.getDate(SCRAPE_DATE).getTime());
				Long locationId = Long.valueOf(rs.getLong(LOCATION_ID));
				Location location = LocationManager.findLocation(locationId);

				scrapeMap.put(id, new Scrape(id, date, location));
			}
			return scrapeMap;
		} catch (SQLException exc) {
			throw new DaoException("Error traversing result set.", exc);
		} finally {
			Db.closeStatement(stmt);
		}
	}
}
