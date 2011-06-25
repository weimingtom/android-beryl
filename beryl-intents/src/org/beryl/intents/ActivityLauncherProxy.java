package org.beryl.intents;

import org.beryl.intents.ActivityIntentLauncher.IActivityLauncherProxy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class ActivityLauncherProxy implements IActivityLauncherProxy {
	final Activity activity;
	
	ActivityLauncherProxy(final Activity activity) {
		this.activity = activity;
	}
	
	public void startActivity(Intent intent) {
		activity.startActivity(intent);
	}
	
	public void startActivityForResult(Intent intent, int requestCode) {
		activity.startActivityForResult(intent, requestCode);
	}
	
	public Context getContext() {
		return activity;
	}
}