package org.beryl.content;

/** Interface for loading content. Modeled after android's android.content.Loader class. */
public interface ILoader<T> {

	/** Start loading the content. (Does not start a separate thread.) */
	void start();
	
	/** Cancel the load operation. */
	void cancel();
	
	/** Returns true if the load is complete. */
	boolean isCompleted();
	
	/** Returns true if the load was interrupted by a cancel request. */
	boolean isCanceled();
	
	/** Returns the progress percentage from 0 to 100. */
	int getProgress();
	
	/** Returns the progress percentage in the form of human friendly text. */
	String getProgressDescription();
	
	void registerListener(OnLoadCompletedListener<T> listener);
	T getResult();
	Exception getError();
	
	public static interface OnLoadCompletedListener<T> {
		void onLoadCompleted(T result);
		void onError(Exception e);
	}
}
