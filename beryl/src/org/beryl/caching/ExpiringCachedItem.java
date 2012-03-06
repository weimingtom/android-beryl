package org.beryl.caching;

/** Cached object that can expire based on an expiration policy. */
public class ExpiringCachedItem<T> extends CachedItem<T> {

	protected final IExpirationPolicy policy;
	
	public ExpiringCachedItem(final T value, final IExpirationPolicy policy) {
		super(value);
		this.policy = policy;
	}

	public IExpirationPolicy getExpirationPolicy() {
		return policy;
	}
	
	@Override
	public T get() {
		if(policy.isExpired()) {
			this.value.clear();
		}
		
		return this.value.get();
	}
}
