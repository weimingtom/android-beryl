package org.beryl.content;

/** Represents a list of listener objects for an event. */
public interface IListenerList<T> {

	/** Subscribes the listener object to listen for this event. */
	void registerListener(T listener);
	
	/** Unsubscribes the listener object from listening to this event. */
	void unregisterListener(T listener);
	
	/** Unsubscribes all listeners from listening to this event. */
	void unregisterAll();
	
	/** Gets a array of all subscribed listeners. */
	T[] getListeners();
}
