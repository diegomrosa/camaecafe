package com.camaecafe;

public class Listing {
	private Long id;
    private String url;
    private Scrape scrape;
    private User host;
    private String name;
    private String description;
    private Lang descriptionLang;
    
	public Listing() {}
	
	public Listing(Long id, String url, Scrape scrape, User host, String name, String description) {
		this.id = id;
		this.url = url;
		this.scrape = scrape;
		this.host = host;
		this.name = name;
		this.description = description;
		this.descriptionLang = null;
	}

	public Long getId() {
		return id;
	}

	public String getUrl() {
		return url;
	}

	public Scrape getScrape() {
		return scrape;
	}

	public User getHost() {
		return host;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Lang getDescriptionLang() {
		return descriptionLang;
	}

	public void setDescriptionLang(Lang descriptionLang) {
		this.descriptionLang = descriptionLang;
	}
}
