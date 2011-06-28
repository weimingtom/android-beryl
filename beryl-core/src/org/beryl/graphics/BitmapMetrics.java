package org.beryl.graphics;

import org.beryl.util.Memory;

import android.graphics.BitmapFactory;

public class BitmapMetrics {

	BitmapFactory.Options options = null;
	
	public BitmapMetrics() {
		this.options = new BitmapFactory.Options();
	}
	public BitmapMetrics(BitmapFactory.Options options) {
		setBitmapOptions(options);
	}
	
	public void setBitmapOptions(BitmapFactory.Options options) {
		this.options = options;
	}
	
	public long getNumPixels() {
		return getNumberOfPixels(this.options.outWidth,  this.options.outHeight);
	}
	
	public long getSizeInBytes() {
		return getNumPixels() * 4; // Assuming RGBA_8888.
	}
	
	public long getSizeInBytes(int sampleSize) {
		return getMemoryFootprintForRgba8888(this.options.outWidth / sampleSize,  this.options.outHeight / sampleSize);
	}
	
	public float getSizeInMegaBytes() {
		return Memory.bytesToMegabytes(getSizeInBytes());
	}
	
	public boolean isSafeToLoad() {
		return isSafeToLoad(Memory.getReasonableMemoryCushion(), 1);
	}
	
	public boolean isSafeToLoad(long withMemoryRemaining, int sampleSize) {
		long freeMem = Memory.freeMemory();
		long imageSize = getSizeInBytes();
		return freeMem - withMemoryRemaining - (imageSize / sampleSize) > 0;
	}
	
	public int getRecommendedSampleSize(long maxMemorySize) {
		int sampleSize = 1;
		
		while(getSizeInBytes(sampleSize) > maxMemorySize) {
			sampleSize *= 2;
		}
		
		return sampleSize;
	}
	
	public static long getNumberOfPixels(long width, long height) {
		return width * height;
	}
	
	public static long getMemoryFootprintForRgba8888(long width, long height) {
		return getNumberOfPixels(width, height) * 4;
	}
}
