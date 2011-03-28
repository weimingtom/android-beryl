package org.beryl.diagnostics;

public class Logger {
	private static final Log logger = new Log(new LogCatLogger());

	public static void d(String tag, String message) {
		logger.d(tag, message);
	}

	public static void e(String tag, String message) {
		logger.d(tag, message);
	}

	public static void e(Exception e) {
		logger.e(e);
	}

	public static void i(String tag, String message) {
		logger.i(tag, message);
	}

	public static void w(String tag, String message) {
		logger.w(tag, message);
	}

}
