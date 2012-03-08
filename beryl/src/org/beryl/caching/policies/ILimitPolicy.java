package org.beryl.caching.policies;

import org.beryl.caching.ICachedItem;

public interface ILimitPolicy {
	void reset();
	void add(ICachedItem item);
	void onRemove(ICachedItem item);
}
