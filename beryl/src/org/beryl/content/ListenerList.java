package org.beryl.content;

import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

/** Thread-safe collection of listener objects. */
public class ListenerList<T> implements IListenerList<T> {
	protected final ConcurrentLinkedQueue<T> listeners = new ConcurrentLinkedQueue<T>();
	
	public void registerListener(T listener) {
		listeners.add(listener);
	}
	
	public void unregisterListener(T listener) {
		deleteAndPrune(listener);
	}

	protected void deleteAndPrune(T listener) {
		final LinkedList<T> deleteTargets = new LinkedList<T>();
		for(T target : listeners) {
			if(target == listener) {
				deleteTargets.add(target);
			}
		}
		
		listeners.removeAll(deleteTargets);
	}
	
	public void unregisterAll() {
		listeners.clear();
	}
	
	@SuppressWarnings("unchecked")
	public T[] getListeners() {
		return (T[])listeners.toArray();
	}
}
