package org.beryl.diagnostics;

import android.content.Intent;
import android.database.Cursor;

public class Logger {
	
	private static final Log logDelegate = new Log(new LogCatLogWriter());

	public static void d(String tag, String msg) {
		logDelegate.d(tag, msg);
	}

	public static void e(String tag, String msg) {
		logDelegate.e(tag, msg);
	}

	public static void i(String tag, String msg) {
		logDelegate.i(tag, msg);
	}

	public static void w(String tag, String msg) {
		logDelegate.w(tag, msg);
	}
	
	public static void d(String msg) {
		logDelegate.d(msg);
	}

	public static void e(Exception e) {
		logDelegate.e(e);
	}

	public static void e(String msg) {
		logDelegate.e(msg);
	}

	public static void i(String msg) {
		logDelegate.i(msg);
	}

	public static void w(String msg) {
		logDelegate.w(msg);
	}
	
	public static void e(String tag, Exception e) {
		logDelegate.e(tag, e);
	}

	public static void d(Intent intent) {
		logDelegate.d(intent);
	}

	public void inspectClass(Class<?> clazz) {
		logDelegate.inspectClass(clazz);
	}

	public void inspectQueryResult(final Cursor cur) {
		logDelegate.inspectQueryResult(cur);
	}

	public void inspectCursorData(final Cursor cur) {
		logDelegate.inspectCursorData(cur);
	}
}
