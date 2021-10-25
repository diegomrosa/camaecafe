package com.camaecafe.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Db {
	static final String DB_URL = "jdbc:mysql://localhost/airbnbscraper";
	static final String USER = "root";
	static final String PASS = "root";
	static final String QUERY = "SELECT * FROM location";

	private Connection conn;
	
	Db(Connection conn) {
		this.conn = conn;
	}
	
	public static Db openDbConnection() throws DaoException {
		Connection c = null;

		try {
			c = DriverManager.getConnection(DB_URL, USER, PASS);
			return new Db(c);
		} catch (SQLException exc) {
			throw new DaoException("Error opening database connection.", exc);
		}
	}

	public Statement createStatement() throws DaoException {
		try {
			return this.conn.createStatement();
		} catch (SQLException exc) {
			throw new DaoException("Error creating statement.", exc);
		}
	}
	
	public PreparedStatement prepareStatement(String sql) throws DaoException {
		try {
			return this.conn.prepareStatement(sql);
		} catch (SQLException exc) {
			throw new DaoException("Error preparing statement for: " + sql, exc);
		}
	}
	
	public PreparedStatement prepareStatementAuto(String sql) throws DaoException {
		try {
			return this.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		} catch (SQLException exc) {
			throw new DaoException("Error preparing statement for: " + sql, exc);
		}
	}
	
	public ResultSet executeQuery(Statement stmt, String query) throws DaoException {		
		try {
			return stmt.executeQuery(query);
		} catch (SQLException exc) {
			throw new DaoException("Error executing query: " + query, exc);
		}
	}
	
	public void enableTransactions() throws DaoException {
		try {
			this.conn.setAutoCommit(false);
		} catch (SQLException exc) {
			throw new DaoException("Error disabling auto commit mode.", exc);
		}
	}
	
	public void commit() throws DaoException {
		try {
			this.conn.commit();
		} catch (SQLException exc) {
			throw new DaoException("Error commiting transaction.", exc);
		}
	}
	
	public void rollback() throws DaoException {
		try {
			this.conn.rollback();
		} catch (SQLException exc) {
			throw new DaoException("Error rolling back transaction.", exc);
		}
	}
	
	public static void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException exc) {
				exc.printStackTrace();
			}
		}
	}
	
	public static void closeStatement(Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException exc) {
				exc.printStackTrace();
			}
		}
	}
	
	public static void close(Db db) {
		if (db != null) {
			db.close();
		}
	}
	
	public void close() {
		if (this.conn != null) {
			try {
				this.conn.close();
			} catch (SQLException exc) {
				exc.printStackTrace();
			}
		}
	}
}
