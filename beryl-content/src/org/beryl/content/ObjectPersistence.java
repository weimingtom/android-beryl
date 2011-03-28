package org.beryl.content;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;


import android.database.sqlite.SQLiteDatabase;

public class ObjectPersistence
{
	private final SQLiteDatabase _db;
	public ObjectPersistence(SQLiteDatabase db)
	{
		_db = db;
	}
	
	public boolean SynchronizeSchema(final Class<?> schemaRoot) throws NoSuchFieldException
	{
		int i;
		Class<?>[] primaryTables = schemaRoot.getDeclaredClasses();
		//Field[] fields = schemaRoot.getFields();
		
		int numTables = primaryTables.length;
		for(i = 0; i < numTables; i++)
		{
			SynchronizeTable(primaryTables[i], null);
		}
		
		return false;
	}
	
	
	private void SynchronizeTable(Class<?> tableSchema, Class<?> parentTable) throws NoSuchFieldException
	{
		int i;
		final Field[] fields = tableSchema.getDeclaredFields();
		final Class<?>[] childTables = tableSchema.getDeclaredClasses();
		Field cField;
		Class<?> cClass;
		final String tableName = tableSchema.getSimpleName();
		
		int numFields = fields.length;
		
		if(! tableExists(tableName)) {
			createTable(tableName, fields);
		}
		else
		{
			for(i = 0; i < numFields; i++) {
				cField = fields[i];
				if(fieldExists(cField.getName())) {
					
				}
			}
		}

		int numChildren = childTables.length;
		for(i = 0; i < numChildren; i++) {
			SynchronizeTable(childTables[i], tableSchema);
		}
	}

	
	private boolean fieldExists(String name) {
		// TODO Auto-generated method stub
		return false;
	}

	private void createTable(String tableName, Field[] fields) {
		
		ArrayList<SchemaField> sFields = new ArrayList<SchemaField>();
		for(Field field : fields)
		{
			sFields.add(new SchemaField(field.getName(), DatabaseUtil.ToSQLType(field.getClass())));
		}
		DatabaseUtil.CreateTable(_db, tableName, sFields);
	}

	private boolean tableExists(String tableName) {
		return DatabaseUtil.TableExists(_db, tableName);
	}

	public static boolean SynchronizeSchema(final SQLiteDatabase db, final Class<?> schemaRoot) throws IOException, NoSuchFieldException
	{
		final ObjectPersistence op = new ObjectPersistence(db);
		return op.SynchronizeSchema(schemaRoot);
	}
	
	
}
