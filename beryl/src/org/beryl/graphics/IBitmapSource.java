package org.beryl.graphics;

import org.beryl.IDisposable;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcelable;

/** Contract for holding the source reference to a bitmap and also manages the loading of that bitmap into memory. */
public interface IBitmapSource extends Parcelable, IDisposable {

	/** Loads the bitmap into memory if it is not already available. */
	boolean load(Context context);
	
	/** Loads the bitmap into memory if it is not already available. */
	boolean load(Context context, BitmapFactory.Options options);
	
	/** Gets the bitmap that is held by the source.
	 * If the bitmap is not available, null is returned. */
	Bitmap get();
	
	/** Returns true if the bitmap is loaded into memory. */
	boolean isAvailable();
	
	BitmapProperties getProperties(Context context);
}
