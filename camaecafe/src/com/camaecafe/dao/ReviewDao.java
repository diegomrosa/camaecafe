package com.camaecafe.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.camaecafe.Lang;
import com.camaecafe.Review;

public class ReviewDao extends Dao {
	private static final String ID = "id";
	private static final String SEND_DATE = "send_date";
	private static final String COMMENTS = "comments";
	private static final String REVIEWER_ID = "reviewer_id";
	
	private static final String INSERT = "INSERT INTO review VALUES (?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE id = id";

	private static final String SELECT_AUTHORS_IDS = "SELECT reviewer_id FROM review WHERE send_date BETWEEN ? AND ?";
	
	private static final String SELECT_REVIEWS_BY_DATE =
			"SELECT id, send_date, comments FROM review WHERE send_date BETWEEN ? AND ?";
	
	private static final String UPDATE_REVIEW_LANG =
			"UPDATE review SET comments_lang_iso = ?, comments_lang_confidence = ? WHERE id = ?";
	
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
	
	public List<Long> readAuthorsIdsBetweenDates(String startDate, String endDate) throws DaoException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Long> authorsIds = new ArrayList<Long>();
		
		try {
			ps = this.getDb().prepareStatement(SELECT_AUTHORS_IDS);
			ps.setString(1, startDate);
			ps.setString(2, endDate);
			rs = ps.executeQuery();
			while (rs.next()) {
				Long authorId = rs.getLong(REVIEWER_ID);

				authorsIds.add(authorId);
			}
		} catch (SQLException exc) {
			throw new DaoException("Error executing SELECT: " + INSERT, exc);
		} finally {
			Db.closeResultSet(rs);
			Db.closeStatement(ps);
		}
		return authorsIds;
	}
	
	public List<Review> readReviewsBetweenDates(String startDate, String endDate) throws DaoException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Review> reviews = new ArrayList<Review>();
		
		try {
			ps = this.getDb().prepareStatement(SELECT_REVIEWS_BY_DATE);
			ps.setString(1, startDate);
			ps.setString(2, endDate);
			rs = ps.executeQuery();
			while (rs.next()) {
				Long id = rs.getLong(ID);
				Date sendDate = rs.getDate(SEND_DATE);
				String comments = rs.getString(COMMENTS);

				reviews.add(new Review(id, sendDate, comments, null, null, null));
			}
		} catch (SQLException exc) {
			throw new DaoException("Error executing SELECT: " + INSERT, exc);
		} finally {
			Db.closeResultSet(rs);
			Db.closeStatement(ps);
		}
		return reviews;
	}
	
	public PreparedStatement createUpdatePs() throws DaoException {
		return this.getDb().prepareStatement(UPDATE_REVIEW_LANG);
	}
	
	public int updateReviewLanguage(PreparedStatement ps, Long reviewId,
			String langIso, String confidence) throws DaoException {
		try {
			ps.clearParameters();
			ps.setString(1, langIso);
			ps.setString(2, confidence);
			ps.setLong(3, reviewId);
			return ps.executeUpdate();
		} catch (SQLException exc) {
			throw new DaoException("Error executing UPDATE: " + UPDATE_REVIEW_LANG, exc);
		}
	}
}
