package org.beryl.intents.android;

import org.beryl.intents.IntentHelper;

import android.content.Intent;
import android.net.Uri;

public class Sharing {

	public static final Intent sendEmail(final String address, final String subject, final String body) {
		final Intent intent = new Intent(Intent.ACTION_SEND);
    	intent.putExtra(Intent.EXTRA_EMAIL, new String[] {address});
    	intent.putExtra(Intent.EXTRA_SUBJECT, subject);
    	intent.putExtra(Intent.EXTRA_TEXT, body);
    	intent.setType("message/rfc822");
    	
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
}
