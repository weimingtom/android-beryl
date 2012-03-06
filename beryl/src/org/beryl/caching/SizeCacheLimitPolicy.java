package org.beryl.caching;

import org.beryl.ISizeGettable;
import org.beryl.graphics.BitmapProperties;

import android.graphics.Bitmap;

public class SizeCacheLimitPolicy<T> implements ILimitPolicy<T> {

	private static final long DEFAULT_UNKNOWN_SIZE = 256; // Assume 256 bytes if we don't know the size of the object.
	
	// TODO: Refactor this later and make it better somehow.
	protected long getSizeOfItem(CachedItem<T> item) {
		long size = 0;
		if(item instanceof ISizeGettable) {
			size = ((ISizeGettable)item).getSizeInBytes();
		} else {
			T raw = item.get();
			if(raw != null) {
				if(raw instanceof String) {
					size = ((String) raw).length();
				} if (raw instanceof Bitmap) {
					size = BitmapProperties.getSizeInBytes((Bitmap)raw);
				} else {
					size = DEFAULT_UNKNOWN_SIZE;
				}
			}
		}
		
		return size;
	}
	
	public void add(CachedItem<T> item) {
		// TODO Auto-generated method stub
		
	}

	public void onRemove(CachedItem<T> item) {
		// TODO Auto-generated method stub
		
	}

	public void evict(CachedItem<T> item) {
		// TODO Auto-generated method stub
		
	}

}
