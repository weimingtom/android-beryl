package org.beryl.graphics;

import android.graphics.Bitmap;

public interface IBitmapStats {
	void add(final Bitmap bitmap);
	void reportStats();
}
