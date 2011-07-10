package org.beryl.intents;

import org.beryl.diagnostics.Logger;

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
		
		try {
			final Intent baseIntent = builder.getIntent();
			Intent intent;
			
			if(builder.isChoosable()) {
				intent = Intent.createChooser(baseIntent, builder.getChooserTitle());
			} else {
				intent = baseIntent;
			}
			
			if(builder instanceof IIntentBuilderForResult) {
				IIntentBuilderForResult builderForResult = (IIntentBuilderForResult) builder;
				launcher.obtainResultBundle(builderForResult.getResultBundle());
				
				if(IntentHelper.canHandleIntent(launcher.getContext(), intent)) {
					launcher.startActivityForResult(intent, this.requestCode);
				} else {
					launcher.onStartActivityForResultFailed(intent, this.requestCode);
				}
			} else {
				if(IntentHelper.canHandleIntent(launcher.getContext(), intent)) {
					launcher.startActivity(intent);
				} else {
					launcher.onStartActivityFailed(intent);
				}
			}
		} catch(Exception e) {
			Logger.e(e);
		} finally {
			launcher.onLaunchTaskComplete();
			
			builder = null;
			launcher = null;
		}
	}
}