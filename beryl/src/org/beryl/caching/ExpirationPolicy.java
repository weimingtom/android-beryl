package org.beryl.caching;

import java.util.concurrent.atomic.AtomicBoolean;

/** Basic expiration policy. */
public class ExpirationPolicy implements IExpirationPolicy {

	private final AtomicBoolean forcedExpire = new AtomicBoolean(false);

	public void expire() {
		forcedExpire.set(true);
	}

	public boolean isExpired() {
		return ! forcedExpire.get();
	}
}
