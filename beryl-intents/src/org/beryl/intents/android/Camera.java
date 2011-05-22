package org.beryl.intents.android;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;

public class Camera {

	//http://achorniy.wordpress.com/2010/04/26/howto-launch-android-camera-using-intents/
	public static Intent requestHighQualityPictureAndRegisterMedia(Context context, String title, String fileName, String description) {
		ContentValues values = new ContentValues();
		values.put(MediaStore.Images.Media.TITLE, fileName);
		values.put(MediaStore.Images.Media.DESCRIPTION, description);
		values.put(MediaStore.Images.Media.DISPLAY_NAME, title);
		
		final Uri imageUri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
		final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1); // High Quality.
		return intent;
	}
	
	/**
	 * 
	 * @param context
	 * @param fileUri Comes from the original Intent. Uri intent.getExtra(MediaStore.EXTRA_OUTPUT).
	 * @return
	 */
	public static Bitmap onActivityResult_requestHighQualityPictureAndRegisterMedia(Context context, Uri fileUri) {
		Bitmap result = null;
		final String filePath = Gallery.findPictureFilePath(context, fileUri);
		if(filePath != null) {
			result = BitmapFactory.decodeFile(filePath);
		}
		return result;
	}

	public static Intent requestHighQualityPicture(Uri fileUri) {
		final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
		intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1); // High Quality.
		return intent;
	}

	public static Bitmap onActivityResult_requestHighQualityPicture(Uri fileUri) {
		final String filePath = fileUri.getPath();
		final Bitmap result = BitmapFactory.decodeFile(filePath);
		return result;
	}
}
