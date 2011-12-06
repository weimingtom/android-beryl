package org.beryl.io;

import java.io.File;

import android.content.Context;

interface IDirectoryCompat {
	
	File getPublicApplication(Context context);
	File getPublicMusic(final boolean autoCreate);
	File getPublicPodcast(final boolean autoCreate);
	File getPublicRingtone(final boolean autoCreate);
	File getPublicAlarm(final boolean autoCreate);
	File getPublicNotification(final boolean autoCreate);
	File getPublicPicture(final boolean autoCreate);
	File getPublicMovie(final boolean autoCreate);
	File getPublicDownload(final boolean autoCreate);

	File getPublicPictureLibrary(final String libraryName);
}
