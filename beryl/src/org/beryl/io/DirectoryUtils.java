package org.beryl.io;

import java.io.File;

import org.beryl.app.AndroidVersion;

import android.content.Context;

/** Collection of directory manipulation method that are compatible with all versions of Android. 
 * Reference implementation: http://developer.android.com/guide/topics/data/data-storage.html
 * */
public class DirectoryUtils {

	private static final IDirectoryCompat directoryCompat;
	
	static {
		if(AndroidVersion.isBeforeFroyo()) {
			directoryCompat = new CupcakeDirectoryCompat();
		} else {
			directoryCompat = new FroyoDirectoryCompat();
		}
	}
	
	public static File getPublicMusic() {
		return getPublicMusic(true);
	}
	public static File getPublicMusic(boolean autoCreate) {
		return directoryCompat.getPublicMusic(autoCreate);
	}
	
	/** */
	public static File getPublicApplication(Context context) {
		return directoryCompat.getPublicApplication(context);
	}

	/* Special Folders */
	
	/** Gets a reference to the public pictures directory. This directory is monitored by the Gallery application.
	 * If the directory does not exist it will be created automatically.
	 * @return Reference to public pictures directory.
	 * */
	public static File getPublicPicture() {
		return getPublicPicture(true);
	}

	/** Gets a reference to the public pictures directory. This directory is monitored by the Gallery application.
	 * 
	 * @param autoCreate Automatically create the directory if it does not exist.
	 * @return Reference to public pictures directory.
	 */
	public static File getPublicPicture(boolean autoCreate) {
		return directoryCompat.getPublicPicture(autoCreate);
	}

	public static File getPublicPictureLibrary(String libraryName) {
		return directoryCompat.getPublicPictureLibrary(libraryName);
	}

}
