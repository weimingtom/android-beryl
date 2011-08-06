package org.beryl.graphics;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import org.beryl.diagnostics.Logger;
import org.beryl.util.Memory;

import android.graphics.Bitmap;

public class BitmapStats implements IBitmapStats {

	private static final IBitmapStats stats = new BitmapStats();
	public static IBitmapStats getInstance() {
		return stats;
	}
	public static void addBitmap(final Bitmap bitmap) {
		getInstance().add(bitmap);
	}
	
	public static void logStats() {
		getInstance().reportStats();
	}
	
	private ArrayList<WeakReference<Bitmap>> loadedBitmaps = new ArrayList<WeakReference<Bitmap>>();
	
	public BitmapStats() {
		
	}
	
	public synchronized void add(final Bitmap bitmap) {
		purge();
		loadedBitmaps.add(new WeakReference<Bitmap>(bitmap));
		reportStats();
	}

	public void reportStats() {
		long totalSize = 0;
		int numBitmaps = 0;
		ArrayList<WeakReference<Bitmap>> countedBitmaps = new ArrayList<WeakReference<Bitmap>>(loadedBitmaps);
		
		for(WeakReference<Bitmap> imageRef : countedBitmaps) {
			final Bitmap image = imageRef.get();
			if(image != null && ! image.isRecycled()) {
				totalSize = BitmapMetrics.getSizeInBytes(image);
				numBitmaps++;
			}
		}
		
		Logger.d("Bitmap Memory: " + Memory.toString(totalSize) + " from " + numBitmaps + " bitmaps.");
	}

	private void purge() {
		final ArrayList<WeakReference<Bitmap>> drainables = new ArrayList<WeakReference<Bitmap>>();
		
		for(WeakReference<Bitmap> imageRef : loadedBitmaps) {
			final Bitmap image = imageRef.get();
			if(image == null || image.isRecycled()) {
				drainables.add(imageRef);
			}
		}
		
		for(WeakReference<Bitmap> deleteImage : drainables) {
			loadedBitmaps.remove(deleteImage);
		}
	}
}
