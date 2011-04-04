package org.beryl.schedule;

import java.lang.ref.WeakReference;

import android.content.Context;
/**
 * A scheduled job that will automatically be abandoned if the Context that it works against becomes garbage collected.
 * Inherit from this class any time there's a job that references a Context object.
 * This prevents Context based memory leaks which are expensive.
 * Note: Do not save your own reference to Context, use the getContext() method and make sure to check for nulls.
 */
public abstract class AbstractContextAwareSchedulableJob extends AbstractSchedulableJob {

	private WeakReference<Context> contextRef;
	
	public AbstractContextAwareSchedulableJob(Context context) {
		contextRef = new WeakReference<Context>(context);
	}
	
	protected Context getContext() {
		return contextRef.get();
	}
	
	public boolean isAbandoned() {
		return contextRef.get() == null;
	}
}
