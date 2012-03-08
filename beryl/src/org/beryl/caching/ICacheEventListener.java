package org.beryl.caching;

public interface ICacheEventListener {
	void onReset();
	void onGet(String key, ICachedItem item);
	void onAdd(String key, ICachedItem item);
	void onRemove(String key, ICachedItem item);
}
