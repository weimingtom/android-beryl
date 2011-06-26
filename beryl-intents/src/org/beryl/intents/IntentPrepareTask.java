package org.beryl.intents;

import android.content.Intent;
import android.os.AsyncTask;

public class IntentPrepareTask extends AsyncTask<Void, Void, Void> {

	IIntentBuilder builder;
	ActivityIntentLauncher launcher;
	int requestCode;
	
	public IntentPrepareTask(final ActivityIntentLauncher launcher, final IIntentBuilder builder, int requestCode) {
		this.builder = builder;
		this.launcher = launcher;
		this.requestCode = requestCode;
	}

	protected Void doInBackground(Void... params) {
		builder.prepareIntent(this.launcher.getContext());
		return null;
	}
	
	protected void onPostExecute(Void result) {
		final Intent intent = builder.getIntent();
		
		if(builder instanceof IIntentBuilderForResult) {
			IIntentBuilderForResult builderForResult = (IIntentBuilderForResult) builder;
			launcher.obtainResultBundle(builderForResult.getResultBundle());
			launcher.startActivityForResult(intent, this.requestCode);
		} else {
			launcher.startActivity(intent);
		}
		launcher.onLaunchTaskComplete();
		
		builder = null;
		launcher = null;
	}
}