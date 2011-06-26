package org.beryl.intents.android;

import java.io.File;

import org.beryl.app.AndroidVersion;
import org.beryl.diagnostics.Logger;
import org.beryl.intents.IActivityResultHandler;
import org.beryl.intents.IIntentBuilderForResult;
import org.beryl.intents.IntentHelper;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

public class Gallery {

	public static final Bitmap loadBitmapFromUri(Context context, Uri fileUri) {
		Bitmap result = null;
		String filePath = null;
		if (fileUri.getScheme().equals("file")) {
			filePath = fileUri.getPath();
		} else if (fileUri.getScheme().equals("content")) {
			filePath = findPictureFilePath(context, fileUri);
		}

		if (filePath != null) {
			result = BitmapFactory.decodeFile(filePath);
		}

		return result;
	}

	public static final String findPictureFilePath(Context context, Uri dataUri) {
		String filePath = null;
		final String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = null;
		try {
			cursor = context.getContentResolver().query(dataUri, projection,
					null, null, null);
			int data_index = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			if (cursor.moveToFirst()) {
				filePath = cursor.getString(data_index);
			}
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}

		return filePath;
	}

	// http://stackoverflow.com/questions/5944282/retrieve-picasa-image-for-upload-from-gallery
	public static class GetImage implements IIntentBuilderForResult {
		public String TypeFilter = "image/*";
		public boolean ForceDefaultHandlers = false;
		public Bitmap.CompressFormat CompressFormat = Bitmap.CompressFormat.PNG;

		private Intent intent = null;
		private String TemporaryImagePath = null;

		public void prepareIntent(Context context) {
			try {
				if(AndroidVersion.isBeforeHoneycomb()) {
					File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
					dir.mkdirs();
					File tempFile = new File(dir, "galleryresult-temp.png");
					
					//.createTempFile("GalleryResult", ".png");
					TemporaryImagePath = tempFile.getAbsolutePath();
	
					tempFile.getParentFile().mkdirs();
					
					tempFile.createNewFile();
					Logger.d("IsFile= " + tempFile.isFile());
					tempFile.setWritable(true, false);
					tempFile.setReadable(true, false);
	
					intent = new Intent(Intent.ACTION_GET_CONTENT);
					intent.setType(TypeFilter);
	
					if (ForceDefaultHandlers) {
						intent.addCategory(Intent.CATEGORY_DEFAULT);
					}
	
					final Uri uri = Uri.fromFile(tempFile);
					intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
					final String formatName = CompressFormat.name();
					intent.putExtra("outputFormat", formatName);
				} else {
					intent = IntentHelper.getContentByType("image/*");
				}
			} catch (Exception e) {

			}
		}

		public Intent getIntent() {
			return intent;
		}

		public Bundle getResultBundle() {
			Bundle data = new Bundle();
			data.putString("transientImagePath", TemporaryImagePath);
			return data;
		}
	}

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

	public final static Bitmap fromSEND_getImage(Context context, Intent data) {
		Bitmap result = null;
		Uri dataUri = data.getParcelableExtra(Intent.EXTRA_STREAM);
		result = Gallery.loadBitmapFromUri(context, dataUri);

		return result;
	}

	public static Bitmap onActivityResult_getImage(Context context, Intent data) {
		Bitmap result = null;
		Uri dataUri = data.getData();
		final String filePath = Gallery.findPictureFilePath(context, dataUri);
		if (filePath != null) {
			result = BitmapFactory.decodeFile(filePath);
		}
		return result;
	}

	public static final Intent sendImage(final String filePath) {
		final Uri uri = Uri.parse(filePath);
		return sendImage(uri);
	}

	public static final Intent sendImage(final Uri contentUri) {
		final String mimeType = IntentHelper.getMimeTypeFromUrl(contentUri);
		final Intent intent = new Intent(Intent.ACTION_SEND);

		intent.putExtra(Intent.EXTRA_STREAM, contentUri);
		intent.setType(mimeType);
		intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

		return intent;
	}

	public abstract static class SendImageResult implements IActivityResultHandler {
		
		public Bitmap bitmapResult = null;
		
		public void prepareResult(Context context, Bundle resultBundle, int resultCode, Intent data) {
			Uri dataUri = data.getParcelableExtra(Intent.EXTRA_STREAM);
			bitmapResult = Gallery.loadBitmapFromUri(context, dataUri);
		}
	}

	public abstract static class GetImageResult implements IActivityResultHandler {
		
		public Bitmap bitmapResult = null;
		
		public void prepareResult(Context context, Bundle resultBundle, int resultCode, Intent data) {
			bitmapResult = null;
			Uri imageUri = null;
			String filePath = null;
			boolean fromTransientPath = false;
			String tempFilePath = null;

			if(resultBundle != null) {
				tempFilePath = resultBundle.getString("transientImagePath");
				if(tempFilePath != null) {
					File tempFile = new File(tempFilePath);
					imageUri = Uri.fromFile(tempFile);
					fromTransientPath = true;
				}
			}
			
			if(imageUri == null || imageUri.toString().length() == 0) {
				if(data != null) {
					imageUri = data.getData();
				}
			}
			
			if(imageUri != null) {

				if(imageUri.getScheme().equals("file")) {
					filePath = imageUri.getPath();
				} else if(imageUri.getScheme().equals("content")) {
					filePath = findPictureFilePath(context, imageUri);
				}
				
				if(filePath != null) {
					bitmapResult = BitmapFactory.decodeFile(filePath);
					if(fromTransientPath) {
						File delTarget = new File(filePath);
						delTarget.delete();
					}
				}
			}
		}
	}
}
