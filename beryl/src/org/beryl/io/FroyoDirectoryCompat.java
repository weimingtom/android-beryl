package org.beryl.io;

import java.io.File;

import android.content.Context;
import android.os.Environment;

class FroyoDirectoryCompat extends CupcakeDirectoryCompat {

	@Override
	public File getPublicApplication(final Context context) {
		File directory;
		directory = context.getExternalFilesDir(null);
		return directory;
	}
}
