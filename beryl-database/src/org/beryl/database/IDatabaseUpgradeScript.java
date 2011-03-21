package org.beryl.database;

import android.database.sqlite.SQLiteDatabase;

public interface IDatabaseUpgradeScript {

	void onBeforeSchemaPatch(SQLiteDatabase db);
	void onAfterSchemaPatch(SQLiteDatabase db);
	String getSchemaPatchAssetPath();
}
