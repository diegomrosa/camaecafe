package com.camaecafe;

import java.io.IOException;

import org.apache.tika.language.detect.LanguageDetector;
import org.apache.tika.language.detect.LanguageResult;

public class LanguageDetection {
	private LanguageDetector detector;

	public LanguageDetection() throws IOException {
		this.detector = LanguageDetector.getDefaultLanguageDetector();
		this.detector.loadModels();
	}
	
	public LanguageResult detectLanguage(String userComments) {
		return detector.detect(userComments);
	}
	
	public String[] detectLanguageAndConfidence(String userComments) {
		LanguageResult result = detectLanguage(userComments);
		
		return new String[] {result.getLanguage(), result.getConfidence().name()};
	}
}
