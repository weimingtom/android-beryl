package org.beryl.intents.android;

import org.beryl.diagnostics.ExceptionReporter;
import org.beryl.graphics.BitmapLoader;
import org.beryl.intents.IActivityResultHandler;
import org.beryl.intents.IIntentBuilderForResult;
import org.beryl.intents.IntentHelper;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

public class Gallery {
	private static final int NUM_INSTANCES = 4;

	public static final Bitmap loadBitmapFromUri(Context context, Uri fileUri) {
		Bitmap result = null;
		String filePath = uriToPhysicalPath(context, fileUri);

		if (filePath != null) {
			result = BitmapLoader.tryDecodeBitmapFileConsideringInstances(filePath, NUM_INSTANCES);
		}

		return result;
	}

	public static String uriToPhysicalPath(Context context, Uri fileUri) {
		String filePath = null;
		if (fileUri.getScheme().equals("file")) {
			filePath = fileUri.getPath();
		} else if (fileUri.getScheme().equals("content")) {
			filePath = findPictureFilePath(context, fileUri);
		}

		return filePath;
	}

	public static final String findPictureFilePath(Context context, Uri dataUri) {
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

	// http://stackoverflow.com/questions/5944282/retrieve-picasa-image-for-upload-from-gallery
	public static class GetImage implements IIntentBuilderForResult {
		public String TypeFilter = "image/*";
		public boolean ForceDefaultHandlers = false;
		public Bitmap.CompressFormat CompressFormat = Bitmap.CompressFormat.PNG;

		private Intent intent = null;

		public void prepareIntent(Context context) {
			createGenericGetImageIntent();
		}

		private void createGenericGetImageIntent() {
			intent = IntentHelper.getContentByType("image/*");
		}

		public boolean isValid() {
			return intent != null;
		}

		public Intent getIntent() {
			return intent;
		}

		public Bundle getResultBundle() {
			Bundle data = new Bundle();
			return data;
		}

		public boolean isChoosable() {
			return false;
		}

		public CharSequence getChooserTitle() {
			return "Which Gallery?";
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

			// NOTE: Picasa support used to be around here. Look at previous versions to see the code.
			if(data != null) {
				imageUri = data.getData();
			}

			if(imageUri != null) {
				filePath = uriToPhysicalPath(context, imageUri);

				if(filePath != null) {
					bitmapResult = BitmapLoader.tryDecodeBitmapFileConsideringInstances(filePath, NUM_INSTANCES);
				}
			}
		}
	}

	public static void deleteImageUri(Context context, Uri imageUri) {
		final ContentResolver cr = context.getContentResolver();
		cr.delete(imageUri, null, null);
	}
}
