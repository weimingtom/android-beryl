package org.beryl.diagnostics;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

public class Logger
{
	private static final String StringRepresentation_Null = "[Null]";
	public static void d(String tag, String message)
	{
		if(tag == null) { tag = Logger.StringRepresentation_Null; }
		if(message == null) { message = Logger.StringRepresentation_Null; }
		Log.d(tag, message);
	}
	
	public static void e(String tag, String message)
	{
		if(tag == null) { tag = Logger.StringRepresentation_Null; }
		if(message == null) { message = Logger.StringRepresentation_Null; }
		Log.e(tag, message);
	}
	
	public static void e(Exception e)
	{
		Log.e(null, null, e);
	}
	public static void e(String tag, String message, Exception e)
	{
		if(tag == null) { tag = Logger.StringRepresentation_Null; }
		if(message == null) { message = Logger.StringRepresentation_Null; }
		Log.e(tag, message, e);
	}
	
	public static void i(String tag, String message)
	{
		if(tag == null) { tag = Logger.StringRepresentation_Null; }
		if(message == null) { message = Logger.StringRepresentation_Null; }
		Log.i(tag, message);
	}
	
	public static void w(String tag, String message)
	{
		if(tag == null) { tag = Logger.StringRepresentation_Null; }
		if(message == null) { message = Logger.StringRepresentation_Null; }
		Log.w(tag, message);
	}
	
	public static void d(Intent intent)
	{
		Logger.d("Action", intent.getAction());
		
		if(intent.getComponent() != null)
		{
			Logger.d("Component", intent.getComponent().toString());
		}
		else
		{
			Logger.d("Component", null);
		}
		Logger.d("DataString", intent.getDataString());
		Logger.d("Package", intent.getPackage());
		Logger.d("Scheme", intent.getScheme());
		Logger.d("Package", intent.getPackage());
		final Bundle extras = intent.getExtras();
		Object obj;
		for(String key : extras.keySet())
		{
			obj = extras.get(key).toString();
			if(obj == null)
			{
				obj = Logger.StringRepresentation_Null;
			}
			
			Logger.d("Extra", "key= " + key + " data= " + obj.toString());
		}
	}
	
	public static void inspectClass(Class<?> clazz)
	{
		final String name = clazz.getSimpleName();
		final String packagename = clazz.getPackage().getName();
		Logger.d(name, "Package Name= " + packagename);
		
		final Class<?> baseclazz = clazz.getSuperclass();
		
		if(baseclazz != null)
		{
			Logger.d(name, "Base Class= " + baseclazz.getName());
		}
		Annotation[] annotations = clazz.getDeclaredAnnotations();
		for(Annotation annotation : annotations)
		{
			Logger.d(name, annotation.toString());
		}
		
		Field[] fields = clazz.getDeclaredFields();
		for(Field field : fields)
		{
			Logger.d(name, field.toString());
		}
		
		Method[] methods = clazz.getDeclaredMethods();
		for(Method method : methods)
		{
			Logger.d(name, method.toString());
		}
	}
	
	/**
	 * Reads all the data from the cursor and reports it to the log.
	 * @param cur
	 */
	public static void inspectQueryResult(final Cursor cur)
	{
		if(cur.moveToFirst())
    	{
    		do
    		{
    			inspectCursorData(cur);
    		} while(cur.moveToNext());
    	}
	}
	
	/**
	 * Reports the current cursor item's data to the log. The cursor location is not affected by this call.
	 * @param cur
	 */
	public static void inspectCursorData(final Cursor cur)
	{
		String [] column_names = cur.getColumnNames();
		for(int i = 0; i < column_names.length; i++)
		{
			Logger.d("[" + Integer.toString(cur.getPosition()) + "] " + column_names[i], cur.getString(i));
		}
	}
}
