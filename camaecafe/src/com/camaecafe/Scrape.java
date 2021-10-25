package com.camaecafe;

import java.util.Date;

public class Scrape {
	private Long id;
	private Date scrapeDate;
	private Location location;
	
	public Scrape(Long id, Date scrapeDate, Location location) {
		super();
		this.id = id;
		this.scrapeDate = scrapeDate;
		this.location = location;
	}

	public Long getId() {
		return id;
	}

	public Date getScrapeDate() {
		return scrapeDate;
	}

	public Location getLocation() {
		return location;
	}
}
