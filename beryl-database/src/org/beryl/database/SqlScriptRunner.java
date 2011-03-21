package org.beryl.database;

import android.database.sqlite.SQLiteDatabase;

public class SqlScriptRunner {

	private final SQLiteDatabase db;
	private final SqlScriptReader script;
	public SqlScriptRunner(SQLiteDatabase db, SqlScriptReader script) {
		this.db = db;
		this.script = script;
	}
	public boolean run() {
		String sqlStatement;
		
		boolean success = false;
		try {
			db.beginTransaction();
			
			while((sqlStatement = script.nextStatement()) != null) {
				db.execSQL(sqlStatement);
			}

			db.setTransactionSuccessful();
			success = true;
		} catch(Exception e) {
			success = false;
		} finally {
			db.endTransaction();
		}
		
		return success;
	}

}
