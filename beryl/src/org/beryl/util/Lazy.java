package org.beryl.util;

/**
 * Variable that is not set immediately but when it is first necessary.
 */
public abstract class Lazy<T> {

	private volatile T data = null;
	
	/** Gets the value of the variable. */
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
	
	/** Callback to get the value of the variable when it is time to get it.
	 * This method will only be called once. */
	public abstract T onSet();
}
