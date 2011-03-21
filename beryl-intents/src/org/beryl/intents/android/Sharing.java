package org.beryl.intents.android;

import android.content.Intent;

public class Sharing {

	public static final Intent sendEmail(final String address, final String subject, final String body) {
		final Intent intent = new Intent(Intent.ACTION_SEND);
    	intent.putExtra(Intent.EXTRA_EMAIL, new String[] {address});
    	intent.putExtra(Intent.EXTRA_SUBJECT, subject);
    	intent.putExtra(Intent.EXTRA_TEXT, body);
    	intent.setType("message/rfc822");
    	
    	return intent;
	}
}
