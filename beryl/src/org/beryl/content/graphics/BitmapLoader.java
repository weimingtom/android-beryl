package org.beryl.content.graphics;

import org.beryl.content.ILoader;
import org.beryl.content.SimpleLoaderBase;
import org.beryl.diagnostics.ExceptionReporter;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

public class BitmapLoader extends SimpleLoaderBase<Bitmap> {

	private Uri imagePath = null;
	private ILoader loader;
	
	public BitmapLoader(Context context) {
		super(context);
	}
	
	public void setImageUri(Uri imagePath) {
		this.imagePath = imagePath;
		
	}

	@Override
	protected void loadData() {
		determineLoadStrategy(imagePath);
	}
	
	private void determineLoadStrategy(Uri imageUri) {
		final String scheme = imageUri.getScheme();
		
		if (scheme.equals("file")) {
			final FileBitmapLoader delegate = new FileBitmapLoader(context);
			delegate.setFilePath(imageUri.getPath());
			loader = delegate;
		} else if (scheme.equals("content")) {
			final String filePath = resolveImageFilePathFromContentUri(context, imageUri);
			final FileBitmapLoader delegate = new FileBitmapLoader(context);
			delegate.setFilePath(filePath);
			loader = delegate;
		} else if(scheme.equals("http")) {
			// TODO: Need a downloader loader.
			/*
			final HttpBitmapLoader delegate = new HttpBitmapLoader(context);
			delegate.setUri(imageUri);
			loader = delegate;
			*/
		}
	}
	
	// TODO: This is a duplicate from Gallery.so and so.. :/ Feature envy.
	public static final String resolveImageFilePathFromContentUri(Context context, Uri dataUri) {
		String filePath = null;
		final String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = null;
		try {
			final ContentResolver cr = context.getContentResolver();
			cursor = cr.query(dataUri, projection, null, null, null);
			int data_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

			if (cursor.moveToFirst()) {
				filePath = cursor.getString(data_index);
			}
		} catch(Exception e) {
			ExceptionReporter.report(e);
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}

		return filePath;
	}
}
