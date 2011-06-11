package org.beryl.app;

/** Convenience class for retrieving the current Android version that's running on the device.
 * 
 * Code example on how to use AndroidVersion to load a multi-versioned class at runtime for backwards compatibility without using reflection.
 * <pre class="code"><code class="java">
import org.beryl.app.AndroidVersion;

public class StrictModeEnabler {
    public static void enableOnThread() {
        IStrictModeEnabler enabler = getStrictModeEnabler();
    }
    // Strict Mode is only supported on Gingerbread or higher.
    private static IStrictModeEnabler getStrictModeEnabler() {
        if(AndroidVersion.isGingerbreadOrHigher()) {
                return new GingerbreadAndAboveStrictModeEnabler();
        } else {
                return new NoStrictModeEnabler();
        }
    }
}
</code></pre>*/
public class AndroidVersion {
	private static final int _ANDROID_SDK_VERSION;
	
	static {
		int android_sdk = 3; // 3 is Android 1.5 (Cupcake) which is the earliest Android SDK available.
		try {
			android_sdk = Integer.parseInt(android.os.Build.VERSION.SDK);
		}
		catch (Exception e) {}
		finally {}
		
		_ANDROID_SDK_VERSION = android_sdk;
	}
	
	/** Gets the SDK Level available to the device. */
	public static int getSdkVersion() {
		return _ANDROID_SDK_VERSION;
	}
	
	/** Returns true if running on Android 1.5 or higher. */
	public static boolean isCupcakeOrHigher() {
		return _ANDROID_SDK_VERSION >= android.os.Build.VERSION_CODES.CUPCAKE;
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
	
	/** Returns true if running on Android 3.0 or higher. */
	public static boolean isHoneycombOrHigher() {
		return _ANDROID_SDK_VERSION >= android.os.Build.VERSION_CODES.HONEYCOMB;
	}
	
	/** Returns true if running on an earlier version than Android 3.0. */
	public static boolean isBeforeHoneycomb() {
		return _ANDROID_SDK_VERSION < android.os.Build.VERSION_CODES.HONEYCOMB;
	}
	
	/** Returns true if running on an earlier version than Android 2.3. */
	public static boolean isBeforeGingerbread() {
		return _ANDROID_SDK_VERSION < android.os.Build.VERSION_CODES.GINGERBREAD;
	}
	
	/** Returns true if running on an earlier version than Android 2.2. */
	public static boolean isBeforeFroyo() {
		return _ANDROID_SDK_VERSION < android.os.Build.VERSION_CODES.FROYO;
	}
	
	/** Returns true if running on an earlier version than Android 2.1. */
	public static boolean isBeforeEclair() {
		return _ANDROID_SDK_VERSION < android.os.Build.VERSION_CODES.ECLAIR;
	}
	
	/** Returns true if running on an earlier version than Android 1.6. */
	public static boolean isBeforeDonut() {
		return _ANDROID_SDK_VERSION < android.os.Build.VERSION_CODES.DONUT;
	}
	
	/** Returns true if running on an earlier version than Android 1.5. */
	public static boolean isBeforeCupcake() {
		return _ANDROID_SDK_VERSION < android.os.Build.VERSION_CODES.CUPCAKE;
	}
	
	private AndroidVersion() {
		// Prevent users from instantiating this class.
	}
}
