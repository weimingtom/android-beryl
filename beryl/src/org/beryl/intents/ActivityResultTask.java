package org.beryl.intents;

import org.beryl.diagnostics.Logger;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;

public class ActivityResultTask extends AsyncTask<Void, Void, Void> {

	ActivityIntentLauncher launcher;
	IActivityResultHandler handler;
	int resultCode;
	Intent data;
	
	public ActivityResultTask(ActivityIntentLauncher launcher, IActivityResultHandler handler, int resultCode, Intent data) {
		this.launcher = launcher;
		this.handler = handler;
		this.resultCode = resultCode;
		this.data = data;
	}
	
	protected final Void doInBackground(Void... params) {
		handler.prepareResult(launcher.getContext(), launcher.getResultBundle(), resultCode, data);
		return null;
	}
	
	protected final void onPostExecute(Void result) {
		try {
			if(resultCode == Activity.RESULT_OK) {
				handler.onResultCompleted();
			} else if(resultCode == Activity.RESULT_CANCELED) {
				handler.onResultCanceled();
			} else {
				handler.onResultCustomCode(resultCode);
			}
		} catch(Exception e) {
			Logger.e(e);
		} finally {
			launcher = null;
			handler = null;
			data = null;
		}
	}
}
