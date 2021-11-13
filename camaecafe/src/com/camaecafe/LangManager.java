package com.camaecafe;

import java.util.List;
import java.util.Map;

import com.camaecafe.dao.DaoException;
import com.camaecafe.dao.Db;
import com.camaecafe.dao.LangDao;

public class LangManager {
	private static Map<Long, Lang> langByIds;
	private static Map<String, Lang> langByIsos;

	private static Map<Long, Lang> getLangMapByIds() {
		if (langByIds == null) {
			loadMaps();
		}
		return langByIds;
	}
	
	private static Map<String, Lang> getLangMapByIsos() {
		if (langByIsos == null) {
			loadMaps();
		}
		return langByIsos;
	}
	
	public static Lang getLangByIso(String iso) {
		return getLangMapByIsos().get(iso);
	}
	
	private static void loadMaps() {
		Db db = null;

		try {
			db = Db.openDbConnection();
			Map[] maps = new LangDao(db).readAll();
			langByIds = maps[0];
			langByIsos = maps[1];
		} catch (DaoException exc) {
			exc.printStackTrace();
		} finally {
			db.close();
		}
	}
}
