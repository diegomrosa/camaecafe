package com.camaecafe.dataset;

import java.io.IOException;

public class DatasetException extends IOException {
	private static final long serialVersionUID = 1L;

	public DatasetException(String message) {
		super(message);
	}
	
	public DatasetException(String message, Throwable thr) {
		super(message, thr);
	}
}
