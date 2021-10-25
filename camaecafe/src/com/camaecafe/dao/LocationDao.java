package com.camaecafe.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import com.camaecafe.Location;

public class LocationDao extends Dao {
	private static final String SELECT_ALL = "SELECT * FROM location";
	private static final String INSERT = "INSERT INTO location VALUES (?, ?, ?, ?, ?)";

	private static final String RAW_TEXT = "raw_text";
	private static final String CITY = "city";
	private static final String REGION = "region";
	private static final String COUNTRY = "country";
	
	public LocationDao(Db db) {
		super(db);
	}
	
	@SuppressWarnings("rawtypes")
	public Map[] readAll() throws DaoException {
		Statement stmt = getDb().createStatement();
		ResultSet rs = null;
		
		try {
			rs = getDb().executeQuery(stmt, SELECT_ALL);
			Map<Long, Location> idIndexedMap = new HashMap<Long, Location>();
			Map<String, Location> rawIndexedMap = new HashMap<String, Location>();
			Map[] locationLists = new Map[] {idIndexedMap, rawIndexedMap};
			
			while (rs.next()) {
				Long id = Long.valueOf(rs.getLong(ID));
				String rawText = rs.getString(RAW_TEXT);
				String city = rs.getString(CITY);
				String region = rs.getString(REGION);
				String country = rs.getString(COUNTRY);
				Location location = new Location(id, rawText, city, region, country);
				
				idIndexedMap.put(id, location);
				rawIndexedMap.put(rawText, location);
			}
			return locationLists;
		} catch (SQLException exc) {
			throw new DaoException("Error traversing result set.", exc);
		} finally {
			Db.closeStatement(stmt);
		}
	}
	
	public Location create(Location location) throws DaoException {
		PreparedStatement ps = getDb().prepareStatementAuto(INSERT);
		ResultSet rs = null;
		
		try {
			ps.setNull(1, Types.BIGINT);
			ps.setString(2, location.getRawText());
			if (location.getCity() == null) {
				ps.setNull(3, Types.VARCHAR);
			} else {
				ps.setString(3, location.getCity());
			}
			if (location.getRegion() == null) {
				ps.setNull(4, Types.VARCHAR);
			} else {
				ps.setString(4, location.getRegion());
			}
			if (location.getCountry() == null) {
				ps.setNull(5, Types.VARCHAR);
			} else {
				ps.setString(5, location.getCountry());
			}
			int updateCount = ps.executeUpdate();
			if (updateCount >= 1) {
				rs = ps.getGeneratedKeys();
				if (rs.next()) {
					location.setId(Long.valueOf(rs.getLong(1)));
				} else {
					throw new DaoException("No generated ID returned by location INSERT.");
				}
			} else {
				throw new DaoException("Update count <= ZERO after location INSERT.");
			}
			return location;
		} catch (SQLException exc) {
			throw new DaoException("Error executing INSERT command: " + INSERT, exc);
		} finally {
			Db.closeResultSet(rs);
			Db.closeStatement(ps);
		}
	}
}
