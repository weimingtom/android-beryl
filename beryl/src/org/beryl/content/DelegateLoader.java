package org.beryl.content;

public class DelegateLoader<T> implements ILoader<T> {

	ILoader<T> delegate = null;
	
	public void start() {
		delegate.start();
	}

	public void cancel() {
		delegate.cancel();
	}

	public boolean isCompleted() {
		return delegate.isCompleted();
	}

	public boolean isCanceled() {
		return delegate.isCanceled();
	}

	public int getProgress() {
		return delegate.getProgress();
	}

	public String getProgressDescription() {
		return delegate.getProgressDescription();
	}

	public void registerListener(ILoader.OnLoadCompletedListener<T> listener) {
		delegate.registerListener(listener);
	}

	public T getResult() {
		return delegate.getResult();
	}

	public Exception getError() {
		return delegate.getError();
	}
}
