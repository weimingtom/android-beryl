package org.beryl.database;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseUtils {
	
	public static InputStream readAsset(final Context context, final String path) throws IOException {
		final AssetManager assets = context.getAssets();
		return assets.open(path, AssetManager.ACCESS_BUFFER);
	}
	
	public static BufferedReader readBufferedAsset(final Context context, final String path) throws IOException {
		final InputStream is = readAsset(context, path);
		final InputStreamReader isr = new InputStreamReader(new BufferedInputStream(is));
		return new BufferedReader(isr);
	}
	
	public static boolean runSqlScriptFromAsset(final Context context,
			final SQLiteDatabase db, final String schemaFilePath) throws IOException {
		SqlScriptReader script = new SqlScriptReader(readAsset(context, schemaFilePath));
		SqlScriptRunner sqlRunner = new SqlScriptRunner(db, script);
		return sqlRunner.run();
	}
}
