package com.camaecafe.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.camaecafe.Lang;

public class LangDao extends Dao {
	private static final String LANG_6391 = "lang_639_1";
	private static final String LANG_6392T = "lang_639_2T";
	private static final String LANG_6392B = "lang_639_2B";
	private static final String NAME = "lang_name";
	private static final String NATIVE_NAME = "native_name";

	private static final String SELECT_ALL = "SELECT * FROM lang";

	public LangDao(Db db) {
		super(db);
	}

	public List<Lang> readAll() throws DaoException {
		Statement stmt = getDb().createStatement();
		ResultSet rs = null;
		
		try {
			rs = getDb().executeQuery(stmt, SELECT_ALL);
			List<Lang> langList = new ArrayList<Lang>();
			
			while (rs.next()) {
				Long id = Long.valueOf(rs.getLong(ID));
				String lang6391 = rs.getString(LANG_6391);
				String lang6392T = rs.getString(LANG_6392T);
				String lang6392B = rs.getString(LANG_6392B);
				String name = rs.getString(NAME);
				String nativeName = rs.getString(NATIVE_NAME);
				Lang lang = new Lang(id, lang6391, lang6392T, lang6392B, name, nativeName);
				
				langList.add(lang);
			}
			return langList;
		} catch (SQLException exc) {
			throw new DaoException("Error traversing result set.", exc);
		} finally {
			Db.closeStatement(stmt);
		}
	}
}
