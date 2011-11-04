package org.beryl.content;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

/** Thread-safe collection of listener objects.
 * Listener objects are weakly referenced so it will not retain objects from the garbage collector.
 * NOTE: Do not use in set-it-and-forget-it situations. */
public class WeakListenerList<T> implements IListenerList<T> {
	protected final ConcurrentLinkedQueue<WeakReference<T>> listeners = new ConcurrentLinkedQueue<WeakReference<T>>();
	
	public void registerListener(T listener) {
		listeners.add(new WeakReference<T>(listener));
	}
	
	public void unregisterListener(T listener) {
		deleteAndPrune(listener);
	}

	protected void deleteAndPrune(T listener) {
		final LinkedList<WeakReference<T>> deleteTargets = new LinkedList<WeakReference<T>>();
		for(WeakReference<T> target : listeners) {
			if(target.get() == null || target.get() == listener) {
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
		ArrayList<T> arrayListeners = new ArrayList<T>();
		for(WeakReference<T> target : listeners) {
			T item = target.get();
			if(item != null) {
				arrayListeners.add(item);
			}
		}
		return (T[])arrayListeners.toArray();
	}
}
