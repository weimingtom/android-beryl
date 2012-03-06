package org.beryl.caching;

public class CachedItemFactory<T> {

	public CachedItem<T> create(T value) {
		return new CachedItem<T>(value);
	}
}
