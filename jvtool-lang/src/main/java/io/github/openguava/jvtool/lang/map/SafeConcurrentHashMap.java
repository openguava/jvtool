package io.github.openguava.jvtool.lang.map;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * 安全的ConcurrentHashMap实现<br>
 * 此类用于解决在JDK8中调用{@link ConcurrentHashMap#computeIfAbsent(Object, Function)}可能造成的死循环问题。<br>
 * 方法来自Dubbo，见：issues#2349<br>
 * <p>
 * 相关bug见：@see <a href="https://bugs.openjdk.java.net/browse/JDK-8161372">https://bugs.openjdk.java.net/browse/JDK-8161372</a>
 *
 * @param <K> 键类型
 * @param <V> 值类型
 */
public class SafeConcurrentHashMap<K, V> extends ConcurrentHashMap<K, V> {
	private static final long serialVersionUID = 1L;

	// region == 构造 ==

	/**
	 * 构造，默认初始大小（16）
	 */
	public SafeConcurrentHashMap() {
		super();
	}

	/**
	 * 构造
	 *
	 * @param initialCapacity 预估初始大小
	 */
	public SafeConcurrentHashMap(int initialCapacity) {
		super(initialCapacity);
	}

	/**
	 * 构造
	 *
	 * @param m 初始键值对
	 */
	public SafeConcurrentHashMap(Map<? extends K, ? extends V> m) {
		super(m);
	}

	/**
	 * 构造
	 *
	 * @param initialCapacity 初始容量
	 * @param loadFactor      增长系数
	 */
	public SafeConcurrentHashMap(int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor);
	}

	/**
	 * 构造
	 *
	 * @param initialCapacity  初始容量
	 * @param loadFactor       增长系数
	 * @param concurrencyLevel 并发级别，即Segment的个数
	 */
	public SafeConcurrentHashMap(int initialCapacity,
								 float loadFactor, int concurrencyLevel) {
		super(initialCapacity, loadFactor, concurrencyLevel);
	}
	// endregion == 构造 ==

	/**
	 * 如果 key 对应的 value 不存在，则使用获取 mappingFunction 重新计算后的值，并保存为该 key 的 value，否则返回 value。<br>
	 * 方法来自Dubbo，解决使用ConcurrentHashMap.computeIfAbsent导致的死循环问题。（issues#2349）<br>
	 * A temporary workaround for Java 8 specific performance issue JDK-8161372 .<br>
	 * This class should be removed once we drop Java 8 support.
	 *
	 * @param key             键
	 * @param mappingFunction 值不存在时值的生成函数
	 * @see <a href="https://bugs.openjdk.java.net/browse/JDK-8161372">https://bugs.openjdk.java.net/browse/JDK-8161372</a>
	 * @return 值
	 */
	@Override
	public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
		V value = this.get(key);
		if(value == null) {
			this.putIfAbsent(key, mappingFunction.apply(key));
			value = this.get(key);
		}
		return value;
	}
}
