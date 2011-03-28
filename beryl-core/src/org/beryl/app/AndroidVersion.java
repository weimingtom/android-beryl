package org.beryl.app;

/** Version independent class to get the Android version that is currently running on the device. */
public class AndroidVersion {
	private static final int _ANDROID_SDK_VERSION;
	
	static {
		int android_sdk = 3; // 3 is Android 1.5 (Cupcake) which is the earliest Android SDK available.
		try {
			android_sdk = Integer.parseInt(android.os.Build.VERSION.SDK);
		}
		catch (Exception e) { }
		finally {}
		
		_ANDROID_SDK_VERSION = android_sdk;
	}
	
	/** Gets the SDK Level available to the device. */
	public static int getSdkVersion() {
		return _ANDROID_SDK_VERSION;
	}
	
	/** Returns true if running on Android 1.6 or higher. */
	public static boolean isDonutOrHigher() {
		return _ANDROID_SDK_VERSION >= android.os.Build.VERSION_CODES.DONUT;
	}
	
	/** Returns true if running on Android 2.1 or higher. */
	public static boolean isEclairOrHigher() {
		return _ANDROID_SDK_VERSION >= android.os.Build.VERSION_CODES.ECLAIR;
	}
	
	/** Returns true if running on Android 2.2 or higher. */
	public static boolean isFroyoOrHigher() {
		return _ANDROID_SDK_VERSION >= android.os.Build.VERSION_CODES.FROYO;
	}
	
	/** Returns true if running on Android 2.3 or higher. */
	public static boolean isGingerbreadOrHigher() {
		return _ANDROID_SDK_VERSION >= android.os.Build.VERSION_CODES.GINGERBREAD;
	}
}
