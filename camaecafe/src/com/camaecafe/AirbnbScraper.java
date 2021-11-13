package com.camaecafe;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class AirbnbScraper {
	private static final Logger LOG = Logger.getLogger(AirbnbScraper.class.getName());

	private static final String AIRBNB_USER_URL = "https://www.airbnb.com/users/show/";
	private static final String PROFILE_STRING = "Profile";
	private static final String LOCATION_START = "Lives in ";
	private static final String LANG_START = "Speaks ";
	private static final String LANG_ATTR = "lang";
	
	public AirbnbScraper() {}
	
	/*
	 * Returns an array with two results: the raw location of the user and a
	 * list of the user's languages.
	 */
	public Object[] scrapeUserInfo(Long userId) throws IOException {
		String userUrl = AIRBNB_USER_URL + userId;
		Document doc = Jsoup.connect(userUrl).get();
		Elements elements = doc.select("span._1ax9t0a");
		
		if (doc.title().contains(PROFILE_STRING)) {
			Object[] result = new Object[2];
			
			for (Element userAttrElem : elements) {
				String text = userAttrElem.text();
	
				if (text.startsWith(LOCATION_START)) {
					result[0] = text.substring(LOCATION_START.length());
				} else if(text.startsWith(LANG_START)) {
					Elements langElements = userAttrElem.select("span");
					StringBuilder builder = new StringBuilder();
					
					for (Element langElem : langElements) {
						String langCode = langElem.attr(LANG_ATTR);
						
						if (builder.length() > 0) {
							builder.append(",");
						}
						builder.append(langCode);
					}
					result[1] = builder.toString();
				}
			}
			return result;
		}
		return null;
	}
}
