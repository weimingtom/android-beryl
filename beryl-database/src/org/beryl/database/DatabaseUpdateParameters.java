package org.beryl.database;

import org.beryl.diagnostics.Log;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseUpdateParameters {

	public final Context context;
	public final SQLiteDatabase db;
	public final Log log;
	
	public DatabaseUpdateParameters(Context context, SQLiteDatabase db, Log log) {
		this.context = context;
		this.db = db;
		this.log = log;
	}
}
