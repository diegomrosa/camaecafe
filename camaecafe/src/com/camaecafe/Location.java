package com.camaecafe;

import java.util.Arrays;

import com.camaecafe.dataset.DatasetException;

public class Location {
	private static final String SEPARATOR = ", ";
	private static final String STATE_PREFIX = "State of ";
	
	private static final String USA_NAME = "United States";
	private static final String[] USA_STATES = new String[] {
		"Alabama",
		"Alaska",
		"Arizona",
		"Arkansas",
		"California",
		"Colorado",
		"Connecticut",
		"Delaware",
		"Florida",
		"Georgia",
		"Hawaii",
		"Idaho",
		"Illinois",
		"Indiana",
		"Iowa",
		"Kansas",
		"Kentucky",
		"Louisiana",
		"Maine",
		"Maryland",
		"Massachusetts",
		"Michigan",
		"Minnesota",
		"Mississippi",
		"Missouri",
		"Montana",
		"Nebraska",
		"Nevada",
		"New Hampshire",
		"New Jersey",
		"New Mexico",
		"New York",
		"North Carolina",
		"North Dakota",
		"Ohio",
		"Oklahoma",
		"Oregon",
		"Pennsylvania",
		"Rhode Island",
		"South Carolina",
		"South Dakota",
		"Tennessee",
		"Texas",
		"Utah",
		"Vermont",
		"Virginia",
		"Washington",
		"West Virginia",
		"Wisconsin",
		"Wyoming"
	};
	
	private Long id;
	private String rawText;
	private String city;
	private String region;
	private String country;
	
	public Location(Long id) {
		this(id, null, null, null, null);
	}
	
	public Location(Long id, String rawText, String city, String region, String country) {
		this.id = id;
		this.rawText = rawText;
		this.city = city;
		this.region = region;
		this.country = country;
	}
	
	public Location(String rawText) throws DatasetException {
		String[] parts = rawText.split(SEPARATOR);
		
		this.id = null;
		boolean hasAwkwardPart = (parts.length > 4);
		
		for (int i = 0; i < parts.length; i++) {
			parts[i] = cleanUpLocationName(parts[i]);
			if ((parts[i].length() <= 0)
					|| (parts[i].length() > 40)
					|| containsNumbers(parts[i])) {
				hasAwkwardPart = true;
			}
		}
		if (hasAwkwardPart) {
			
			// This location is awkward: store everything in rawText and leave others empty.
			this.city = null;
			this.region = null;
			this.country = null;
		} else if (parts.length == 1) {
			
			// Only one part might indicate only country
			this.city = null;
			this.region = null;
			this.country = parts[0];
		} else if (parts.length == 2) {
			
			// Two parts might be: city and country OR city and USA state
			this.city = parts[0];
			if (isUsaState(parts[1])) {
				this.region = parts[1];
				this.country = USA_NAME;
				this.rawText = this.city + ", " + this.region + ", " + this.country;
			} else {
				this.region = null;
				this.country = parts[1];
			}
		} else if (parts.length == 3) {
			
			// Three parts indicate city, region, and country.
			this.city = parts[0];
			this.region = parts[1];
			this.country = parts[2];
		} else if (parts.length == 4) {
			
			// With 4 parts, maybe the first one is a district or neighborhood!
			this.city = parts[1];
			this.region = parts[2];
			this.country = parts[3];
		} else {
			// For more than 4 parts, I give up! Just put it all on rawText.
			this.country = rawText.trim();
		}
		if (hasAwkwardPart) {
			this.rawText = rawText;
		} else if (this.city == null) {
			this.rawText = this.country;
		} else if (this.region == null) {
			this.rawText = this.city + SEPARATOR + this.country;
		} else {
			this.rawText = this.city + SEPARATOR + this.region + SEPARATOR + this.country;
		}
		this.rawText = this.rawText.toUpperCase();
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getRawText() {
		return rawText;
	}

	public String getCity() {
		return city;
	}

	public String getRegion() {
		return region;
	}

	public String getCountry() {
		return country;
	}
	
	public boolean isSame(Location other) {
		return this.getRawText().equals(other.getRawText());
	}
	
	private static String cleanUpLocationName(String locationName) {
		String cleanedUpName = locationName.trim();
		
		if (cleanedUpName.startsWith(STATE_PREFIX)) {
			cleanedUpName = cleanedUpName.substring(STATE_PREFIX.length());
		}
		return cleanedUpName;
	}
	
	private static boolean isUsaState(String locationName) {
		return Arrays.binarySearch(USA_STATES, locationName) >= 0;
	}
	
	private static boolean containsNumbers(String str) {
		return str.matches(".*\\d.*");
	}
}
