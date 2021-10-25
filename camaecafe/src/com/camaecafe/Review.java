package com.camaecafe;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.camaecafe.dataset.DatasetException;

public class Review {
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	
	private Long id;
    private Date sendDate;
    private String comments;
    private Lang commentsLang;
    private User reviewer;
    private Listing listing;
    
	public Review(Long id, Date sendDate, String comments, Lang commentsLang, User reviewer, Listing listing) {
		this.id = id;
		this.sendDate = sendDate;
		this.comments = comments;
		this.commentsLang = commentsLang;
		this.reviewer = reviewer;
		this.listing = listing;
	}
	
	public Review(Long id, String sendDateStr, String comments, User reviewer) throws DatasetException {
		this(id, parseDate(sendDateStr), comments, null, reviewer, null);
	}
	
	private static Date parseDate(String dateStr) throws DatasetException {
		try {
			return DATE_FORMAT.parse(dateStr);
		} catch (ParseException exc) {
			throw new DatasetException("Error parsing review send date: " + dateStr, exc);
		}
	}

	public Long getId() {
		return id;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public String getComments() {
		return comments;
	}

	public Lang getCommentsLang() {
		return commentsLang;
	}

	public User getReviewer() {
		return reviewer;
	}

	public Listing getListing() {
		return listing;
	}
}
