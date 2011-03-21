package org.beryl.database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public abstract class UpgradableDatabaseOpenHelper extends SQLiteOpenHelper {

	
	public UpgradableDatabaseOpenHelper(Context context, String name) {
		super(context, name, null, getVersion());
	}
	
	public UpgradableDatabaseOpenHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}
	
	public UpgradableDatabaseOpenHelper(Context context, String name, CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
		super(context, name, factory, version, errorHandler);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}
	
	// TODO: This is really flaky
	protected Object getUpgradeClassInstance(int fromVersion, int toVersion) {
		String upgradeClassPath = getUpgradeClassName(fromVersion, toVersion);
		Object instance = null;
		
		try {
			Class<IDatabaseUpgradeScript> upgraderClass = (Class<IDatabaseUpgradeScript>)Class.forName(upgradeClassPath);
			
			instance = upgraderClass.newInstance();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return instance;
	}
	
	protected String getUpgradeClassName(int fromVersion, int toVersion) {
		String baseClassName = getClass().getName();
		StringBuilder sb = new StringBuilder();
		sb.append(baseClassName);
		sb.append("_");
		sb.append(fromVersion);
		sb.append("_");
		sb.append(toVersion);
		return sb.toString();
	}
	protected abstract String getDbName();
	protected abstract int getVersion();
	protected abstract String getAssetBaseDir();
}
