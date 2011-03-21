package org.beryl.content;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.List;


import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

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
	
	public static boolean TableExists(final SQLiteDatabase db, final String tableName)
	{
		SQLiteStatement stmt = db.compileStatement("SELECT name FROM sqlite_master WHERE name=\"" + tableName + "\"");
		long result = stmt.simpleQueryForLong();
		return result > 0;
	}
	
	public static void CreateTable(final SQLiteDatabase db, final String tableName, List<SchemaField> fields) throws IllegalArgumentException
	{
		if(fields == null || fields.size() <= 0)
			throw new IllegalArgumentException("At least 1 field must be specified for table.");
		
		StringBuilder sb = new StringBuilder();
		sb.append("CREATE TABLE ");
		sb.append(tableName);
		sb.append(" (");
		
		boolean firstField = true;
		
		for(SchemaField field : fields)
		{
			if(!firstField)
			{
				sb.append(", ");
			}
			sb.append(field.toFieldSQL());
			
			firstField = false;
		}

		sb.append(")");
		
		db.execSQL(sb.toString());
	}
	
	public static void DeleteTableContents(final SQLiteDatabase db, final String tableName)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("TRUNCATE TABLE ");
		sb.append(tableName);
		db.execSQL(sb.toString());
	}
	
	public static void DeleteTable(final SQLiteDatabase db, final String tableName)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("DROP TABLE ");
		sb.append(tableName);
		db.execSQL(sb.toString());
	}
	
	public static void AddTableColumn(final SQLiteDatabase db, final String tableName, final String columnName, final String type)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("ALTER TABLE ");
		sb.append(tableName);
		sb.append(" ADD ");
		sb.append(columnName);
		sb.append(" ");
		sb.append(type);
		db.execSQL(sb.toString());
	}
	
	public static void RenameTable(final SQLiteDatabase db, final String oldTableName, final String newTableName)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("ALTER TABLE ");
		sb.append(oldTableName);
		sb.append(" RENAME TO ");
		sb.append(newTableName);
		db.execSQL(sb.toString());
	}

	public static String ToSQLType(Class<?> dataType) {
		return "String";
	}
}
