package org.beryl.intents.android;

import org.beryl.graphics.BitmapLoader;
import org.beryl.intents.IActivityResultHandler;
import org.beryl.intents.IIntentBuilderForResult;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

public class Camera {

	public static class CaptureRequest implements IIntentBuilderForResult {
		public static final int PICTUREQUALITY_Lowest = 0;
		public static final int PICTUREQUALITY_Highest = 1;
		
		public String title = "Camera Picture";
		public String displayName = null;
		public String fileName = "";
		public String description = "Camera Picture";
		public int pictureQualityMode = PICTUREQUALITY_Highest;
		
		private Uri imageUri;
		
		private String getDisplayName() {
			if(displayName != null && displayName.length() > 0) {
				return displayName;
			} else {
				return title;
			}
		}
		
		public void prepareIntent(Context context) {
			final ContentValues values = new ContentValues();
			values.put(MediaStore.Images.Media.TITLE, title);
			values.put(MediaStore.Images.Media.DESCRIPTION, description);
			values.put(MediaStore.Images.Media.DISPLAY_NAME, getDisplayName());
			
			final ContentResolver cr = context.getContentResolver();
			this.imageUri = cr.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
		}

		public Intent getIntent() {
			final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, this.imageUri);
			intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, pictureQualityMode);
			return intent;
		}

		public Bundle getResultBundle() {
			Bundle bundle = new Bundle();
			bundle.putParcelable("captureImageUri", imageUri);
			return bundle;
		}

		public boolean isChoosable() {
			return false;
		}

		public CharSequence getChooserTitle() {
			return "";
		}
	}
	
	public abstract static class GetCameraCaptureResult implements IActivityResultHandler {

		public Bitmap bitmapResult = null;
		
		public void prepareResult(Context context, Bundle resultBundle, int resultCode, Intent data) {
			Uri imageUri = null;
			
			if(resultBundle != null && resultBundle.containsKey("captureImageUri")) {
				imageUri = resultBundle.getParcelable("captureImageUri");
			} else if(data != null) {
				imageUri = data.getParcelableExtra(MediaStore.EXTRA_OUTPUT);
			}
			
			if(resultCode == Activity.RESULT_OK) {
				bitmapResult = Gallery.loadBitmapFromUri(context, imageUri);
			} else if(resultCode == Activity.RESULT_CANCELED) {
				Gallery.deleteImageUri(context, imageUri);
			}
		}
	}
	
	//http://achorniy.wordpress.com/2010/04/26/howto-launch-android-camera-using-intents/
	public static Intent requestHighQualityPictureAndRegisterMedia(Context context, String title, String fileName, String description) {
		final ContentValues values = new ContentValues();
		values.put(MediaStore.Images.Media.TITLE, fileName);
		values.put(MediaStore.Images.Media.DESCRIPTION, description);
		values.put(MediaStore.Images.Media.DISPLAY_NAME, title);
		
		final ContentResolver cr = context.getContentResolver();
		final Uri imageUri = cr.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
		final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1); // High Quality.
		return intent;
	}
	
	/**
	 * 
	 * @param context
	 * @param fileUri Comes from the original Intent. Uri intent.getExtra(MediaStore.EXTRA_OUTPUT).
	 */
	public static Bitmap onActivityResult_requestHighQualityPictureAndRegisterMedia(Context context, Uri fileUri) {
		Bitmap result = null;
		final String filePath = Gallery.findPictureFilePath(context, fileUri);
		if(filePath != null) {
			result = BitmapLoader.tryDecodeBitmapFileConsideringInstances(filePath, 2);
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
		final Bitmap result = BitmapLoader.tryDecodeBitmapFileConsideringInstances(filePath, 2);
		return result;
	}
}
