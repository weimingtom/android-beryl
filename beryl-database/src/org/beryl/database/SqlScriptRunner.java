package org.beryl.database;

import android.database.sqlite.SQLiteDatabase;

/** Runs sql scripts from an SqlScriptReader.
 * The script is run against the database as a single transaction. */
public class SqlScriptRunner {

	private final SQLiteDatabase db;
	private final SqlScriptReader script;
	private Exception transactionException = null;
	
	public SqlScriptRunner(SQLiteDatabase db, SqlScriptReader script) {
		this.db = db;
		this.script = script;
	}
	
	/** Runs the SQL statements against the database.
	 * All exceptions are suppressed.
	 * Use getException() if this method returns false to get the exception.
	 * @return
	 */
	public boolean run() {
		String sqlStatement;
		
		boolean success = false;
		transactionException = null;
		
		try {
			db.beginTransaction();
			
			while((sqlStatement = script.nextStatement()) != null) {
				android.util.Log.w("Script", sqlStatement);
				db.execSQL(sqlStatement);
			}

			db.setTransactionSuccessful();
			success = true;
		} catch(Exception e) {
			success = false;
			transactionException = e;
		} finally {
			db.endTransaction();
		}
		
		
		// Why would this happen?
		if(success == false && transactionException == null) {
			transactionException = new Exception("Sql Script was not successful. But I don't know why.");
		}
		
		return success;
	}

	/** Returns the exception that occured during the run of the script.
	 * If the script was successful the exception will be null.
	 * @return
	 */
	public Exception getException() {
		return transactionException;
	}
}
