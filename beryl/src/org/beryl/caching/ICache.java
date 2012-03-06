package org.beryl.caching;

public interface ICache<TKey, TData> {

	void add(TKey key, TData value);
	TData remove(TKey key);
	TData get(TKey key);
	void clear();
	
}
