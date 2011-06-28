package org.beryl.intents;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public class ActivityIntentLauncher implements Parcelable {

	private IntentPrepareTask currentPrepareTask = null;
	private Bundle pendingResultBundle = null;
	private IActivityLauncherProxy launcherProxy = null;

	public ActivityIntentLauncher() {
	}
	
	public ActivityIntentLauncher(Parcel in) {
		readFromParcel(in);
	}
	
	public void setActivityLauncherFromActivity(Activity activity) {
		setActivityLauncher(new ActivityLauncherProxy(activity));
	}
	
	public void setActivityLauncher(IActivityLauncherProxy proxy) {
		this.launcherProxy = proxy;
	}
	
	public void beginStartActivity(IIntentBuilder builder) {
		if(currentPrepareTask == null) {
			currentPrepareTask = new IntentPrepareTask(this, builder, 0);
			currentPrepareTask.execute();
		}
	}
	
	public void beginStartActivity(IIntentBuilderForResult builder, int requestCode) {
		if(currentPrepareTask == null) {
			currentPrepareTask = new IntentPrepareTask(this, builder, requestCode);
			currentPrepareTask.execute();
		}
	}

	public Context getContext() {
		return launcherProxy.getContext();
	}
	
	void obtainResultBundle(Bundle bundle) {
		pendingResultBundle = bundle;
	}
	
	Bundle getResultBundle() {
		Bundle bundle = pendingResultBundle;
		pendingResultBundle = null;
		return bundle;
	}
	
	void startActivity(Intent intent) {
		launcherProxy.startActivity(intent);
	}
	
	void startActivityForResult(Intent intent, int requestCode) {
		launcherProxy.startActivityForResult(intent, requestCode);
	}
	
	void onLaunchTaskComplete() {
		currentPrepareTask = null;
	}
	
	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int flags) {
    	dest.writeBundle(pendingResultBundle);
    }
 
    public void readFromParcel(Parcel in) {
    	pendingResultBundle = in.readBundle();
    }

    public static final Parcelable.Creator<ActivityIntentLauncher> CREATOR = new Parcelable.Creator<ActivityIntentLauncher>() {
        public ActivityIntentLauncher createFromParcel(Parcel in) {
            return new ActivityIntentLauncher(in);
        }

        public ActivityIntentLauncher[] newArray(int size) {
            return new ActivityIntentLauncher[size];
        }
    };

	public interface IActivityLauncherProxy {
		void startActivity(Intent intent);
		void startActivityForResult(Intent intent, int requestCode);
		Context getContext();
	}
}
