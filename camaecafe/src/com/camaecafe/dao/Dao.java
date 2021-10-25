package com.camaecafe.dao;

public class Dao {
	public static String ID = "id";
	
	private Db db;

	public Dao(Db db) {
		this.db = db;
	}
	
	public Db getDb() {
		return this.db;
	}
}
