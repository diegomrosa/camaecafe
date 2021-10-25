package com.camaecafe.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import com.camaecafe.Listing;

public class ListingDao extends Dao {
	private static String INSERT = "INSERT INTO listing VALUES (?, ?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE id = id";
	
	public ListingDao(Db db) {
		super(db);
	}
	
	public int create(Listing listing) throws DaoException {
		PreparedStatement ps = getDb().prepareStatement(INSERT);
		
		try {
			ps.setLong(1, listing.getId().longValue());
			ps.setString(2, listing.getUrl());
			ps.setLong(3, listing.getScrape().getId());
			ps.setLong(4, listing.getHost().getId());
			ps.setString(5, listing.getName());
			ps.setString(6, listing.getDescription());
			if (listing.getDescriptionLang() == null) {
				ps.setNull(7, Types.BIGINT);
			} else {
				ps.setLong(7, listing.getDescriptionLang().getId());
			}
			return ps.executeUpdate();
		} catch (SQLException exc) {
			throw new DaoException("Error executing INSERT command: " + INSERT, exc);
		} finally {
			Db.closeStatement(ps);
		}
	}
}
