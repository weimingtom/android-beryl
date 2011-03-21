package org.beryl.intents;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

public class Helpers {
	
	public static boolean canHandleIntent(final Context context, final Intent intent) {

		if (intent != null) {
			final PackageManager pm = context.getPackageManager();
			if (pm != null) {
				if (pm.queryIntentActivities(intent, 0).size() > 0) {
					return true;
				}
			}
		}

		return false;
	}
}
