package com.camaecafe.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import com.camaecafe.User;

public class UserDao extends Dao {
	//private static String SELECT_ALL = "SELECT * FROM airbnb_user";
	private static String INSERT = "INSERT INTO airbnb_user VALUES (?, ?, ?, ?)";
	private static String INSERT_IFNOT = "INSERT INTO airbnb_user VALUES (?, ?, ?, ?) ON DUPLICATE KEY UPDATE id = id";
	private static String UPDATE_AS_HOST = "UPDATE airbnb_user SET is_host = ? WHERE id = ?";
	
//	private static final String IS_HOST = "is_host";
//	private static final String USER_NAME = "user_name";
//	private static final String LOCATION_ID = "location_id";
	
	public UserDao(Db db) {
		super(db);
	}

//	public Map<Long, User> readAll() throws DaoException {
//		Statement stmt = null;
//		ResultSet rs = null;
//		
//		try {
//			stmt = getDb().createStatement();
//			rs = getDb().executeQuery(stmt, SELECT_ALL);
//			Map<Long, User> userMap = new HashMap<Long, User>();
//			
//			while (rs.next()) {
//				Long id = Long.valueOf(rs.getLong(ID));
//				Boolean isHost = Boolean.valueOf(rs.getBoolean(IS_HOST));
//				String name = rs.getString(USER_NAME);
//				Long locationId = Long.valueOf(rs.getLong(LOCATION_ID));
//				Location location = LocationManager.findLocation(locationId);
//
//				userMap.put(id, new User(id, isHost, name, location));
//			}
//			return userMap;
//		} catch (SQLException exc) {
//			throw new DaoException("Error traversing result set.", exc);
//		} finally {
//			Db.closeResultSet(rs);
//			Db.closeStatement(stmt);
//		}
//	}
	
	public int create(User user) throws DaoException {
		PreparedStatement ps = null;
		
		try {
			ps = getDb().prepareStatement(INSERT);
			ps.setLong(1, user.getId().longValue());
			ps.setBoolean(2, user.isHost());
			ps.setString(3, user.getName());
			if (user.getLocation() == null) {
				ps.setNull(4, Types.BIGINT);
			} else {
				ps.setLong(4, user.getLocation().getId().longValue());
			}
			return ps.executeUpdate();
		} catch (SQLException exc) {
			throw new DaoException("Error executing INSERT command: " + INSERT, exc);
		} finally {
			Db.closeStatement(ps);
		}
	}
	
	public int updateUserAsHost(Long userId) throws DaoException {
		PreparedStatement ps = getDb().prepareStatement(UPDATE_AS_HOST);
		
		try {
			ps.setBoolean(1, Boolean.TRUE);
			ps.setLong(2, userId);
			
			return ps.executeUpdate();
		} catch (SQLException exc) {
			throw new DaoException("Error executing UPDATE command: " + UPDATE_AS_HOST, exc);
		} finally {
			Db.closeStatement(ps);
		}
	}
	
	public int insertIfNotExists(User user) throws DaoException {
		PreparedStatement ps = null;
		
		try {
			ps = getDb().prepareStatement(INSERT_IFNOT);
			ps.setLong(1, user.getId().longValue());
			ps.setBoolean(2, user.isHost());
			ps.setString(3, user.getName());
			if (user.getLocation() == null) {
				ps.setNull(4, Types.BIGINT);
			} else {
				ps.setLong(4, user.getLocation().getId().longValue());
			}
			return ps.executeUpdate();
		} catch (SQLException exc) {
			throw new DaoException("Error executing INSERT command: " + INSERT, exc);
		} finally {
			Db.closeStatement(ps);
		}
	}
}
