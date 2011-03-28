package org.beryl.diagnostics;

public class LogCatLogger implements ILoggerWriter {

	private String tag = "App";
	
	public LogCatLogger() {
		
	}
	public void d(String tag, String msg) {
		android.util.Log.d(tag, msg);
	}

	public void d(String msg) {
		android.util.Log.d(tag, msg);
	}

	public void e(Exception e) {
		android.util.Log.e(tag, "Thrown Exception", e);
	}

	public void e(String tag, String msg) {
		android.util.Log.e(tag, msg);
	}

	public void e(String msg) {
		android.util.Log.e(tag, msg);
	}

	public void i(String tag, String msg) {
		android.util.Log.i(tag, msg);
	}

	public void i(String msg) {
		android.util.Log.i(tag, msg);
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public void w(String tag, String msg) {
		android.util.Log.w(tag, msg);
	}

	public void w(String msg) {
		android.util.Log.w(tag, msg);
	}
}
