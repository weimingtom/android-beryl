package org.beryl.caching;

public interface IEvictionPolicy<T> {
	void add(CachedItem<T> item);
	void onRemove(CachedItem<T> item);
	void evict(CachedItem<T> item);
}
