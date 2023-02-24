package io.github.openguava.jvtool.lang.cache;

import io.github.openguava.jvtool.lang.constant.StringConstants;

/**
 * 抽象缓存
 * @author openguava
 *
 * @param <K>
 * @param <V>
 */
public abstract class AbstractCache<K, V> implements Cache<K, V> {

	private static final long serialVersionUID = 1L;
	
	/** 缓存名称 */
	protected final String name;
	
	@Override
	public String getName() {
		return this.name;
	}
	
	/**
	 * 缓存失效时长， {@code 0} 表示无限制，单位毫秒
	 */
	protected int timeout = 0;
	
	public int getTimeout() {
		return this.timeout;
	}
	
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
	
	/**
	 * 初始化
	 */
	public AbstractCache() {
		this.name = StringConstants.STRING_EMPTY;
	}
	
	/**
	 * 初始化
	 * @param name
	 */
	public AbstractCache(String name) {
		this.name = name;
	}

	@Override
	public Object getNativeCache() {
		return this;
	}
}
