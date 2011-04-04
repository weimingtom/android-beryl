package org.beryl.diagnostics;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

public class Log implements ILoggerWriter {

	private static final String StringRepresentation_Null = "[Null]";
	
	private String tag = "Log";
	private final ILoggerWriter logDelegate;

	public Log(ILoggerWriter delegate) {
		this.logDelegate = delegate;
	}

	public Log(ILoggerWriter delegate, String tag) {
		this.logDelegate = delegate;
		this.tag = tag;
	}
	
	public void setTag(String tag) {
		this.tag = tag;
	}
	
	public void d(String tag, String msg) {
		logDelegate.d(tag, msg);
	}

	public void e(String tag, String msg) {
		logDelegate.e(tag, msg);
	}

	public void i(String tag, String msg) {
		logDelegate.i(tag, msg);
	}

	public void w(String tag, String msg) {
		logDelegate.w(tag, msg);
	}
	
	public void d(String msg) {
		logDelegate.d(tag, msg);
	}

	public void e(Exception e) {
		logDelegate.e(tag, e);
	}

	public void e(String msg) {
		logDelegate.e(tag, msg);
	}

	public void i(String msg) {
		logDelegate.i(tag, msg);
	}

	public void w(String msg) {
		logDelegate.w(tag, msg);
	}
	
	public void e(String tag, Exception e) {
		logDelegate.e(tag, e);
	}

	/** Prints out all the details of the Intent object. */
	public void d(Intent intent) {
		d("Action", intent.getAction());

		if (intent.getComponent() != null) {
			d("Component", intent.getComponent().toString());
		} else {
			d("Component", null);
		}

		d("DataString", intent.getDataString());
		d("Package", intent.getPackage());
		d("Scheme", intent.getScheme());
		d("Package", intent.getPackage());
		final Bundle extras = intent.getExtras();
		Object obj;

		if (extras == null) {
			d("Extras", "[empty]");
		} else {
			for (String key : extras.keySet()) {
				obj = extras.get(key).toString();
				if (obj == null) {
					obj = StringRepresentation_Null;
				}

				d("Extra", "key= " + key + " data= " + obj.toString());
			}
		}
	}

	/** Prints all the members of a class. */
	public void inspectClass(Class<?> clazz) {
		final String name = clazz.getSimpleName();
		final String packagename = clazz.getPackage().getName();
		d(name, "Package Name= " + packagename);

		final Class<?> baseclazz = clazz.getSuperclass();

		if (baseclazz != null) {
			d(name, "Base Class= " + baseclazz.getName());
		}
		Annotation[] annotations = clazz.getDeclaredAnnotations();
		for (Annotation annotation : annotations) {
			d(name, annotation.toString());
		}

		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			d(name, field.toString());
		}

		Method[] methods = clazz.getDeclaredMethods();
		for (Method method : methods) {
			d(name, method.toString());
		}
	}

	/**
	 * Reads all the data from the cursor and reports it to the log.
	 * 
	 * @param cur
	 */
	public void inspectQueryResult(final Cursor cur) {
		if (cur.moveToFirst()) {
			do {
				inspectCursorData(cur);
			} while (cur.moveToNext());
		}
	}

	/**
	 * Reports the current cursor item's data to the log. The cursor location is
	 * not affected by this call.
	 * 
	 * @param cur
	 */
	public void inspectCursorData(final Cursor cur) {
		String[] column_names = cur.getColumnNames();
		for (int i = 0; i < column_names.length; i++) {
			d("[" + Integer.toString(cur.getPosition()) + "] "
					+ column_names[i], cur.getString(i));
		}
	}
}
