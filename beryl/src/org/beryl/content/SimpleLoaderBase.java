package org.beryl.content;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import android.content.Context;

public abstract class SimpleLoaderBase<T> implements ILoader<T> {

	private T result = null;
	private Exception error = null;
	private final AtomicBoolean isCanceled = new AtomicBoolean(false);
	private final AtomicBoolean isCompleted = new AtomicBoolean(false);
	private final AtomicInteger progressCompleted = new AtomicInteger(0);
	private String progressDescription = null;
	
	protected final Context context;
	
	public SimpleLoaderBase(final Context context) {
		this.context = context;
	}
	
	protected Context getContext() {
		return context;
	}
	
	protected void resetObject() {
		isCanceled.set(false);
		isCompleted.set(false);
		progressCompleted.set(0);
	}
	
	public final void start() {
		resetObject();
		loadData();
	}
	
	protected void setProgressDescription(String progress) {
		progressDescription = progress;
	}
	
	protected abstract void loadData();
	
	protected void setProgress(int progress) {
		progressCompleted.lazySet(progress);
	}
	
	protected void finishWithError(Exception e) {
		this.error = e;
		listeners.onError(e);
		listeners.unregisterAll();
		isCompleted.set(true);
	}
	
	protected void finishWithResult(T result) {
		this.result = result;
		listeners.onLoadCompleted(result);
		listeners.unregisterAll();
		isCompleted.set(true);
	}

	public void cancel() {
		isCanceled.set(true);
	}

	public boolean isCompleted() {
		return isCompleted.get();
	}

	public boolean isCanceled() {
		return isCanceled.get();
	}

	public int getProgress() {
		return progressCompleted.get();
	}

	public String getProgressDescription() {
		return progressDescription;
	}

	public void registerListener(ILoader.OnLoadCompletedListener<T> listener) {
		listeners.registerListener(listener);
	}
	
	public T getResult() {
		return result;
	}

	public Exception getError() {
		return error;
	}

	protected LoadCompletedListenerList listeners = new LoadCompletedListenerList();
	
	class LoadCompletedListenerList extends ListenerList<ILoader.OnLoadCompletedListener<T>> {
		void onLoadCompleted(T result) {
			final ILoader.OnLoadCompletedListener<T>[] listeners = getListeners();
			for(ILoader.OnLoadCompletedListener<T> listener : listeners) {
				listener.onLoadCompleted(result);
			}
		}
		
		void onError(Exception e) {
			ILoader.OnLoadCompletedListener<T>[] listeners = getListeners();
			for(ILoader.OnLoadCompletedListener<T> listener : listeners) {
				listener.onError(e);
			}
		}
	}
}
