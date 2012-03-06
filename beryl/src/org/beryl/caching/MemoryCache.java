package org.beryl.caching;

import java.util.concurrent.ConcurrentHashMap;

public class MemoryCache<TKey, TData> implements ICache<TKey, TData> {

	public static final <T> CachedItemFactory<T> defaultFactory() {
		return new CachedItemFactory<T>();
	}
	
	private final ConcurrentHashMap<TKey, CachedItem<TData>> cache;
	private final CachedItemFactory<TData> cacheItemFactory;
	
	public MemoryCache() {
		this.cache = new ConcurrentHashMap<TKey, CachedItem<TData>>();
		this.cacheItemFactory = defaultFactory();
	}
	
	public MemoryCache(CachedItemFactory<TData> cacheItemFactory) {
		this.cache = new ConcurrentHashMap<TKey, CachedItem<TData>>();
		this.cacheItemFactory = cacheItemFactory;
	}
	
	public void add(TKey key, TData value) {
		final CachedItem<TData> cacheValue = this.cacheItemFactory.create(value);
		cache.put(key, cacheValue);
	}
	
	public TData remove(TKey key) {
		final CachedItem<TData> cacheValue = this.cache.remove(key);
		return cacheValue.get();
	}
	
	public TData get(TKey key) {
		return cache.get(key).get();
	}
	
	public void clear() {
		this.cache.clear();
	}
	
	/*
	 * Caching Features
	 * - Cache Any Object
	 * - Clear Cache
	 * - Thread Safety
	 * - Remove
	 * - Imposed Limits (size)
	 * - General Expiration Policy
	 * - Different ways: LruCache, GC Managed, Weighted (hits against + Lru)
	 */
}
