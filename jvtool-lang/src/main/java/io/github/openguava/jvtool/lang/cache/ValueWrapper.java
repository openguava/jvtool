package io.github.openguava.jvtool.lang.cache;

/**
 * A (wrapper) object representing a cache value.
 */
@FunctionalInterface
public interface ValueWrapper<T> {

	/**
	 * Return the actual value in the cache.
	 */
	T get();
}
