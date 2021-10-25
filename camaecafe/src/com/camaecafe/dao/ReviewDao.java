package com.camaecafe.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import com.camaecafe.Review;

public class ReviewDao extends Dao {
	private static String INSERT = "INSERT INTO review VALUES (?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE id = id";

	public ReviewDao(Db db) {
		super(db);
	}

	public int create(Review review, Long listingId) throws DaoException {
		PreparedStatement ps = null;
		
		try {
			ps = getDb().prepareStatement(INSERT);
			ps.setLong(1, review.getId().longValue());
			ps.setDate(2, new java.sql.Date(review.getSendDate().getTime()));
			ps.setString(3, review.getComments());
			if (review.getCommentsLang() == null) {
				ps.setNull(4, Types.BIGINT);
			} else {
				ps.setLong(4, review.getCommentsLang().getId());
			}
			ps.setLong(5, review.getReviewer().getId().longValue());
			ps.setLong(6, listingId);
			return ps.executeUpdate();
		} catch (SQLException exc) {
			throw new DaoException("Error executing INSERT command: " + INSERT, exc);
		} finally {
			Db.closeStatement(ps);
		}
	}
}
