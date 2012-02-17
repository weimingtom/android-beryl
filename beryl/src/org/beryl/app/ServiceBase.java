package org.beryl.app;

import org.beryl.diagnostics.Log;
import org.beryl.diagnostics.Logger;

import android.app.Service;
import android.content.Intent;

public abstract class ServiceBase extends Service {

	protected final Log log = Logger.newInstance(getTag());
	
	@Override
	public void onCreate() {
		super.onCreate();
		Thread.currentThread().setName(getTag());
	}
	
	// This is the old onStart method that will be called on the pre-2.0
	// platform.  On 2.0 or later we override onStartCommand() so this
	// method will not be called.
	@Override
	public final void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
	    handleOnStartCommand(intent, 0, startId);
	}

	@Override
	public final int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);
	    return handleOnStartCommand(intent, flags, startId);
	}

	/** Tag used for logging and service identification. */
	protected abstract String getTag();
	protected abstract int handleOnStartCommand(Intent intent, int flags, int startId);
}
