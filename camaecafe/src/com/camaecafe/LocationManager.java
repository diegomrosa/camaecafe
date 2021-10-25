package com.camaecafe;

import java.util.Map;

import com.camaecafe.dao.DaoException;
import com.camaecafe.dao.Db;
import com.camaecafe.dao.LocationDao;
import com.camaecafe.dataset.DatasetException;

public class LocationManager {
	private static Map<Long, Location> idIndexedCache;
	private static Map<String, Location> rawIndexedCache;

	public static Map<Long, Location> getIdIndexedMap() {
		if (idIndexedCache == null) {
			loadCache();
		}
		return idIndexedCache;
	}
	
	public static Map<String, Location> getRawIndexedMap() {
		if (rawIndexedCache == null) {
			loadCache();
		}
		return rawIndexedCache;
	}
	
	@SuppressWarnings({"unchecked", "rawtypes"})
	private static void loadCache() {
		Db db = null;

		try {
			db = Db.openDbConnection();
			Map[] maps = new LocationDao(db).readAll();
			idIndexedCache = (Map<Long, Location>) maps[0];
			rawIndexedCache = (Map<String, Location>) maps[1];
		} catch (DaoException exc) {
			exc.printStackTrace();
		} finally {
			db.close();
		}
	}
	
	public static Location findLocation(Long id) {
		return getIdIndexedMap().get(id);
	}
	
	public static Location findLocation(String rawText) {
		return getRawIndexedMap().get(rawText);
	}
	
	public static Location findOrCreateLocation(Db db, Location addedLocation) throws DatasetException, DaoException {
		Location newLocation = findLocation(addedLocation.getRawText());
		
		if (newLocation == null) {
			newLocation = new LocationDao(db).create(addedLocation);
			getIdIndexedMap().put(newLocation.getId(), newLocation);
			getRawIndexedMap().put(newLocation.getRawText(), newLocation);
		}
		return newLocation;
	}
}
