package com.camaecafe;

import java.util.List;

import com.camaecafe.dao.DaoException;
import com.camaecafe.dao.Db;
import com.camaecafe.dao.LangDao;

public class LangManager {
	private static List<Lang> cachedLangs;

	public static List<Lang> getLangList() {
		if (cachedLangs == null) {
			Db db = null;

			try {
				db = Db.openDbConnection();
				cachedLangs = new LangDao(db).readAll();
			} catch (DaoException exc) {
				exc.printStackTrace();
			} finally {
				db.close();
			}
		}
		return cachedLangs;
	}
}
