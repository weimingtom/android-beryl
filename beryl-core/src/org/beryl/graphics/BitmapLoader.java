package org.beryl.graphics;

import org.beryl.app.AndroidVersion;
import org.beryl.util.Memory;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

public class BitmapLoader {
	
	public static Bitmap tryDecodeBitmapFileConsideringInstances(String filePath, int numPossibleInstances) {
		return tryDecodeBitmapFile(filePath, Memory.getReasonableMemoryCushion(), numPossibleInstances);
	}
	
	/** Loads a bitmap scaled to accommodate for a number of possible bitmaps sized the same as well as consider memory padding. */
	public static Bitmap tryDecodeBitmapFile(String filePath, long memoryCushion, int numPossibleInstances) {
		Bitmap result = null;
		long freeMem = Memory.memoryLimit() - memoryCushion;
		long safeSize = freeMem / numPossibleInstances;
		
		// Don't load the bitmap if it's constraint is lower than 64KB.
		if(safeSize > Memory.KB_IN_BYTES * 64) {
			result = tryDecodeBitmapFile(filePath, safeSize);
		}
		
		return result;
	}
	
	public static Bitmap tryDecodeBitmapFile(String filePath, long maxSize) {
		
		Bitmap result = null;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		if(AndroidVersion.isGingerbreadMr1OrHigher()) {
			options.inPreferQualityOverSpeed = true;
		}
		BitmapFactory.decodeFile(filePath, options);
		
		BitmapMetrics metrics = new BitmapMetrics(options);
		int sampleSize = metrics.getRecommendedSampleSize(maxSize);
		options.inSampleSize = sampleSize;
		options.inJustDecodeBounds = false;
		
		result = BitmapFactory.decodeFile(filePath, options);
		
		BitmapStats.addBitmap(result);
		return result;
	}
	
	public static boolean isUsable(Bitmap bitmap) {
		if(bitmap != null) {
			if(! bitmap.isRecycled()) {
				return true;
			}
		}
		return false;
	}
	
	public static void safeRecycle(Bitmap bitmap) {
		if(isUsable(bitmap)) {
			bitmap.recycle();
		}
	}

	public static Bitmap rotateBitmap(Bitmap bitmap, float angle) {
		
		int width;
		int height;
		
		width = bitmap.getWidth();
		height = bitmap.getHeight();
		
		Matrix rotationMatrix = new Matrix();
		rotationMatrix.setRotate(angle);
		Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, rotationMatrix, true);
		BitmapStats.addBitmap(rotatedBitmap);
		return rotatedBitmap;
	}
}
