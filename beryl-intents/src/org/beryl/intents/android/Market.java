package org.beryl.intents.android;

import android.content.Intent;
import android.net.Uri;

public class Market {

	public static final String Application_BaseMarketUri = "market://details?id=";
	
	public static final Intent getViewPackageOnMarket(final String package_name) {
		final Intent result = new Intent(Intent.ACTION_VIEW);
		result.setData(Uri.parse(Application_BaseMarketUri + package_name));
    	return result;
	}
}
