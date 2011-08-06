package org.beryl.io;

import java.io.File;

import org.beryl.app.AndroidVersion;
import org.beryl.diagnostics.ExceptionReporter;

import android.content.Context;
import android.os.Environment;

//http://developer.android.com/reference/android/os/Environment.html#DIRECTORY_PICTURES
public class DirectoryUtils {

	/** */
	public static File getApplicationExternalStorageDirectory(Context context, String type) {
		File directory = null;
		if(AndroidVersion.isFroyoOrHigher()) {
			directory = context.getExternalFilesDir(type);
		} else {
			String privateDataDirectory = "/Android/data/" + context.getPackageName() + "/files/";
			File baseDirectory = Environment.getExternalStorageDirectory();
			directory = appendDirectoryName(baseDirectory, privateDataDirectory);
		}
		
		return directory;
	}
	
	public static File getExternalStoragePublicDirectoryByType(String type, boolean autoCreate) {
		File directory = null;
		directory = Environment.getExternalStoragePublicDirectory(type);
		attemptAutoCreate(autoCreate, directory);
		
		return directory;
	}
	
	public static File getExternalStoragePublicDirectoryByName(String name, boolean autoCreate) {
		File directory = null;
		File baseDirectory = Environment.getExternalStorageDirectory();
		directory = appendDirectoryName(baseDirectory, name);
		attemptAutoCreate(autoCreate, directory);
		
		return directory;
	}
	
	public static File getPublicPictures() {
		return getPublicPictures(true);
	}
	
	public static File getPublicPictures(boolean autoCreate) {
		File directory = null;
		
		if(AndroidVersion.isFroyoOrHigher()) {
			directory = getExternalStoragePublicDirectoryByType(Environment.DIRECTORY_PICTURES, autoCreate);
		} else {
			directory = getExternalStoragePublicDirectoryByName("Pictures", autoCreate);
		}
		
		return directory;
	}
	
	public static File createPictureLibraryFolder(String libraryName) {
		return createPictureLibraryFolder(libraryName, true);
	}
	
	public static File createPictureLibraryFolder(String libraryName, boolean autoCreate) {
		File basePicturesPath = getPublicPictures();
		File libraryPath = appendDirectoryName(basePicturesPath, libraryName);

		return libraryPath;
	}
	
	public static File appendDirectoryName(File baseDir, String appendDirName) {
		File appendedDir = new File(baseDir, appendDirName);
		appendedDir.mkdirs();
		return appendedDir;
	}
	
	private static void attemptAutoCreate(boolean autoCreate, File directory) {
		if(autoCreate || directory != null) {
			try {
				directory.mkdirs();
			} catch(Exception e) {
				ExceptionReporter.report(e);
				// Ignore, banking on the fact that developers should check the storage state before using file IO.
			}
		}
	}
}
