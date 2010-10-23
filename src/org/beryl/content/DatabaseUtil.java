package org.beryl.content;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseUtil
{
	/**
	 * Runs an sql script against a database.
	 * @param context
	 * @param db
	 * @param schema_filepath
	 * @throws IOException
	 */
	public static void RunSqlScriptFromAsset(final Context context, final SQLiteDatabase db, final String schema_filepath) throws IOException
	{
		String sql = GetSqlFromAsset(context, schema_filepath);
		String [] statements = sql.split(";");
		String statement;
		int len = statements.length;
		try
		{
			db.beginTransaction();
			for(int i = 0; i < len; i++)
			{
				statement = statements[i].replace("\n", "").replace("\r", "").trim() + ";";
				if(statement.length() > 1)
				{
					db.execSQL(statement);
				}
			}
			
			db.setTransactionSuccessful();
		}
		catch(Exception e)
		{
			
		}
		finally
		{
			db.endTransaction();
		}
	}
	
	/**
	 * Not implemented.
	 * @param context
	 * @param res_id
	 */
	public static void CreateFromRaw(final Context context, final int res_id)
	{
		
	}
	
	private static String GetSqlFromAsset(final Context context, final String schema_filepath) throws IOException
	{
		String result = null;
		
		final AssetManager manager = context.getAssets();
		InputStream is = manager.open(schema_filepath);
		
		final int filesize = is.available();
		final byte[] filebuffer = new byte[filesize];
		is.read(filebuffer);
		is.close();
		result = new String(filebuffer);
		
		return result;
	}
}
