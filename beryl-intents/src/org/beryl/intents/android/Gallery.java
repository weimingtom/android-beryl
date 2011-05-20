package org.beryl.intents.android;

import org.beryl.intents.IntentHelper;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;

public class Gallery {

	// TODO: Support remote resources, http://stackoverflow.com/questions/5944282/retrieve-picasa-image-for-upload-from-gallery
	// TODO: Need an intent to handle SEND from Gallery.
	
	public static final Intent getImage() {
		return getImage("image/*");
	}
	
	public static final Intent getImageDefaultOnly() {
		return getImageDefaultOnly("image/*");
	}
	
	public static final Intent getImage(String imageType) {
		return IntentHelper.getContentByType(imageType);
	}
	
	public static final Intent getImageDefaultOnly(String imageType) {
		final Intent intent = IntentHelper.getContentByType(imageType);
		intent.addCategory(Intent.CATEGORY_DEFAULT);
		return intent;
	}
	
	public static Bitmap onActivityResult_getImage(Context context, Intent data) {
		Bitmap result = null;
		Uri dataUri = data.getData();
		final String filePath = findPictureFilePath(context, dataUri);
		if(filePath != null) {
			result = BitmapFactory.decodeFile(filePath);
		}
		return result;
	}

	static String findPictureFilePath(Context context, Uri dataUri) {
		String filePath = null;
		final String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = null;
		try {
			cursor = context.getContentResolver().query(dataUri, projection, null, null, null);
			int data_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			if(cursor.moveToFirst()) {
				filePath = cursor.getString(data_index);
			}
		} finally {
			if(cursor != null) {
				cursor.close();
			}
		}
		
		return filePath;
	}
}
