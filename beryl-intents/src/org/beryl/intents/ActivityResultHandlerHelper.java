package org.beryl.intents;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public abstract class ActivityResultHandlerHelper implements IActivityResultHandler {

	protected int resultCode;
	protected Context context;
	protected Bundle resultBundle;
	protected Intent data;
	
	public void prepareResult(Context context, Bundle resultBundle, int resultCode, Intent data) {
		this.context = context;
		this.resultBundle = resultBundle;
		this.resultCode = resultCode;
		this.data = data;
		
		onPrepareResult();
	}

	protected abstract void onPrepareResult();

	protected boolean isSuccessful() {
		return resultCode == Activity.RESULT_OK;
	}
}
