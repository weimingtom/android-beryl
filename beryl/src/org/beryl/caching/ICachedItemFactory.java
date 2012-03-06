package org.beryl.caching;

public interface ICachedItemFactory<T> {
	CachedItem<T> create(T value);
}
