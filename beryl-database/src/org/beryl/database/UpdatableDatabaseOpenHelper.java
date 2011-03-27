package org.beryl.database;

import org.beryl.diagnostics.Log;
import org.beryl.diagnostics.LogCatLogger;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public abstract class UpdatableDatabaseOpenHelper extends SQLiteOpenHelper {
	private final Context context;
	
	public UpdatableDatabaseOpenHelper(Context context, String name, int version) {
		super(context, name, null, version);
		this.context = context;
	}
	
	public UpdatableDatabaseOpenHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		final DatabaseUpdateParameters params = createUpdateParams(db);
		final DatabaseUpdateRunner updateRunner = new DatabaseUpdateRunner();
		final DatabaseUpdateLoader updateLoader = new DatabaseUpdateLoader(updateRunner, params);
		
		android.os.Debug.waitForDebugger();
		final Log log = params.log;
		
		log.setTag("db_" + getDbName());
		log.i("onCreate: Loading Create Database");
		Class<? extends IDatabaseUpdateScript> createScript = getCreateScript();
		updateLoader.queueScriptFromClass(createScript);
		updateRunner.run();
	}

	private DatabaseUpdateParameters createUpdateParams(SQLiteDatabase db) {
		return new DatabaseUpdateParameters(context, db, getLogger());
	}
	
	private Log getLogger() {
		return new Log(new LogCatLogger());
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		final DatabaseUpdateParameters params = createUpdateParams(db);
		final DatabaseUpdateRunner updateRunner = new DatabaseUpdateRunner();
		final DatabaseUpdateLoader updateLoader = new DatabaseUpdateLoader(updateRunner, params);
		android.os.Debug.waitForDebugger();
		// TODO: Add logic to wipe out the database and recreate it if there's no upgrade path.
		updateLoader.queueScriptFromClassNameTemplate(getUpdateScriptClassPathTemplate(), oldVersion, newVersion);
		updateRunner.run();
	}
	
	protected String createUpdateScriptClassPathTemplate(Class<?> exampleUpdateClass) {
		String template = null;
		
		if(exampleUpdateClass != null) {
			String className = exampleUpdateClass.getName();
			template = className.replace("_2", "_%d");
		}
		
		return template;
	}
	
	public abstract String getDbName();
	protected abstract Class<? extends IDatabaseUpdateScript> getCreateScript();
	protected abstract String getUpdateScriptClassPathTemplate();
}
