package org.beryl.intents.android;

import android.content.Intent;
import android.net.Uri;

public class Web {

	public static final Intent viewUrl(final String url) {
		final Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(Uri.parse(url));
		return intent;
	}
}
