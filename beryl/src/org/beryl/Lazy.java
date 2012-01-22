package org.beryl;

/** (thread-safe) Support class for lazy initialization. */
public abstract class Lazy<T> {

	private volatile T data = null;
	
	/** (thread-safe) Gets the lazy initialized value. */
	public synchronized T get() {
		
		T result = data;
		if(result == null) {
			synchronized(this) {
				result = data;
				if(result == null) {
					data = result = onSet();
				}
			}
		}
		
		return result;
	}
	
	/** Callback method to set the lazy-initialized variable. */
	protected abstract T onSet();
}
