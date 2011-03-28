package org.beryl.diagnostics;

public interface ILoggerWriter {
	
	void setTag(String tag);
	void e(Exception e);
	void e(String tag, String msg);
	void e(String msg);
	void d(String tag, String msg);
	void d(String msg);
	void i(String tag, String msg);
	void i(String msg);
	void w(String tag, String msg);
	void w(String msg);
}
