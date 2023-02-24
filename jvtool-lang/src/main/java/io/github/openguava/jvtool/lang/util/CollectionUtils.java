package io.github.openguava.jvtool.lang.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * 集合工具类
 * @author openguava
 *
 */
public class CollectionUtils {
	
	protected CollectionUtils() {
		
	}
	
	/**
	 * 判断集合是否为空
	 * @param c
	 * @return
	 */
    public static boolean isEmpty(Collection<?> c) {
        return c == null || c.isEmpty();
    }
    
    /**
     * 判断map集合是否为空
     * @param e
     * @return
     */
    public static boolean isEmpty(Map<?, ?> m) {
    	return m == null || m.isEmpty();
    }
    
	/**
	 * 判断 {@link Set} 是否为空
	 * @param set
	 * @return
	 */
	public static boolean isEmpty(Set<?> set) {
		return set == null || set.isEmpty();
	}
	
    /**
     * 判断list是否为空
     * @param list
     * @return
     */
    public static boolean isEmpty(List<?> list) {
    	return list == null || list.isEmpty();
	}
    
	/**
	 * 判断数组是否为空
	 * 
	 * @param array
	 * @return
	 */
	@SafeVarargs
	public static <T> boolean isEmpty(T... array) {
		return ArrayUtils.isEmpty(array);
	}
    
    /**
     * 判断集合当前是否为空
     * @param e
     * @return
     */
    public static boolean isEmpty(Enumeration<?> e) {
    	return e == null || !e.hasMoreElements();
    }
    
	/**
	 * 判断 Iterator 是否为空
	 * @param iterator
	 * @return
	 */
	public static boolean isEmpty(Iterator<?> iterator) {
		return iterator == null || !iterator.hasNext();
	}
	
    /**
	 * 判断 iterable 是否为空
	 * @param iterable
	 * @return
	 */
	public static boolean isEmpty(Iterable<?> iterable) {
		return iterable == null || isEmpty(iterable.iterator());
	}
    
    /**
     * 判断集合是否为非空
     * @param c
     * @return
     */
    public static boolean isNotEmpty(Collection<?> c) {
        return !isEmpty(c);
    }
    
    /**
     *  判断map集合是否为非空
     * @param m
     * @return
     */
    public static boolean isNotEmpty(Map<?, ?> m) {
        return !isEmpty(m);
    }
    
	/**
	 * 判断 {@link Set} 是否为非空
	 * @param set
	 * @return
	 */
	public static boolean isNotEmpty(Set<?> set) {
		return !isEmpty(set);
	}
	
	/**
	 * 判断list是否为非空
	 *
	 * @param <T>   数组元素类型
	 * @param array 数组
	 * @return
	 */
	public static boolean isNotEmpty(List<?> list) {
		return !isEmpty(list);
	}
    
	/**
	 * 判断数组是否为非空
	 * 
	 * @param array
	 * @return
	 */
	@SafeVarargs
	public static <T> boolean isNotEmpty(T... array) {
		return ArrayUtils.isNotEmpty(array);
	}
	
    /**
     * 判断集合当前是否为非空
     * @param e
     * @return
     */
    public static boolean isNotEmpty(Enumeration<?> e) {
        return !isEmpty(e);
    }
    
	/**
	 * 判断 Iterator 是否为非空
	 * @param iterator
	 * @return
	 */
	public static boolean isNotEmpty(Iterator<?> iterator) {
		return !isEmpty(iterator);
	}
	
	/**
	 * 判断 iterable 是否为非空
	 * @param iterable
	 * @return
	 */
	public static boolean isNotEmpty(Iterable<?> iterable) {
		return !isEmpty(iterable);
	}
	
	/**
	 * 创建ArrayList
	 * @param <T>
	 * @param clazz
	 * @return
	 */
	public static <T> List<T> newArrayList(Class<T> clazz) {
		return clazz != null ? new ArrayList<>() : null;
	}
	
	/**
	 * 创建HashSet
	 * @param <T>
	 * @param clazz
	 * @return
	 */
	public static <T> Set<T> newHashSet(Class<T> clazz) {
		return clazz != null ? new HashSet<>() : null;
	}
	
	/**
	 * 创建 HashMap
	 * @param <K>
	 * @param <V>
	 * @param clazzK
	 * @param clazzV
	 * @return
	 */
	public static <K, V> Map<K, V> newHashMap(Class<K> clazzK, Class<V> clazzV) {
		return (clazzK != null && clazzV != null) ? new HashMap<>() : null;
	}
	
	/**
	 * 创建LinkedHashMap
	 * @param <K>
	 * @param <V>
	 * @param clazzK
	 * @param clazzV
	 * @return
	 */
	public static <K, V> Map<K, V> newLinkedHashMap(Class<K> clazzK, Class<V> clazzV) {
		return (clazzK != null && clazzV != null) ? new LinkedHashMap<>() : null;
	}
	
	/**
	 * 创建 ConcurrentHashMap
	 * @param <K>
	 * @param <V>
	 * @param clazzK
	 * @param clazzV
	 * @return
	 */
	public static <K, V> Map<K, V> newConcurrentHashMap(Class<K> clazzK, Class<V> clazzV) {
		return (clazzK != null && clazzV != null) ? new ConcurrentHashMap<>() : null;
	}
	
	/**
	 * 集合List排序
	 * @param <T>
	 * @param list
	 * @param c
	 */
	public static <T> void sort(List<T> list, Comparator<? super T> c) {
		if(isEmpty(list)) {
			return;
		}
		Collections.sort(list, c);
	}
	
	/**
	 * 根据索引获取List值
	 * @param <T>
	 * @param list
	 * @param index
	 * @return
	 */
	public static <T> T get(List<T> list, int index) {
		if(isEmpty(list)) {
			return null;
		}
		return (index > -1 && index < list.size()) ? list.get(index) : null;
	}
	
	/**
	 * 根据索引获取数组值
	 * @param <T>
	 * @param array
	 * @param index
	 * @return
	 */
	public static <T> T get(T[] array, int index) {
		return ArrayUtils.get(array, index);
	}
	
	/**
	 * 根据key获取Map值
	 * @param <K>
	 * @param <V>
	 * @param map
	 * @param key 键
	 * @return
	 */
	public static <K, V> V get(Map<K, V> map, K key) {
		return get(map, key, null);
	}
	
	/**
	 * 根据key获取Map值
	 * @param <K>
	 * @param <V>
	 * @param map
	 * @param key 键
	 * @param defaultValue 默认值
	 * @return
	 */
	public static <K, V> V get(Map<K, V> map, K key, V defaultValue) {
		if(isEmpty(map)) {
			return defaultValue;
		}
		return map.getOrDefault(key, defaultValue);
	}
	
	/**
	 * 获取集合首个元素
	 * @param <T>
	 * @param items
	 * @return
	 */
	public static <T> T first(Iterable<T> items) {
		return first(items, null);
	}
	
	/**
	 * 获取数组首个元素
	 * 
	 * @param <T>
	 * @param array
	 * @return
	 */
	@SafeVarargs
	public static <T> T first(T... array) {
		return ArrayUtils.first(array);
	}
	
	/**
	 * 获取集合首个元素
	 * @param <T>
	 * @param items
	 * @param predicate
	 * @return
	 */
	public static <T> T first(Iterable<T> items, Predicate<T> predicate) {
		if(isEmpty(items)) {
			return null;
		}
		if(predicate == null) {
			return items.iterator().next();
		}
		for (T item : items) {
			if (predicate.test(item)) {
				return item;
			}
		}
		return null;
	}
	
	/**
	 * 获取数组匹配的首个元素
	 * 
	 * @param <T>
	 * @param array
	 * @param predicate
	 * @return
	 */
	public static <T> T first(T[] array, Predicate<T> predicate) {
		return ArrayUtils.first(array, predicate);
	}
	
	/**
	 * 遍历集合
	 * @param <T>
	 * @param items
	 * @param consumer
	 */
	public static <T> void each(Iterable<T> items, Consumer<T> consumer) {
		if(isEmpty(items)) {
			return;
		}
		for(T item : items) {
			consumer.accept(item);
		}
	}
	
	/**
	 * 遍历数组
	 * @param <T>
	 * @param array
	 * @param consumer
	 */
	public static <T> void each(T[] array, Consumer<T> consumer) {
		ArrayUtils.each(array, consumer);
	}
	
	/**
	 * 集合过滤为List<T>
	 * @param <T> 输入类型
	 * @param items
	 * @param predicates
	 * @return
	 */
	@SafeVarargs
	public static <T> List<T> filter(Iterable<T> items, Predicate<T>... predicates) {
		List<T> result = new ArrayList<>();
		if(isEmpty(items)) {
			return result;
		}
		for(T item : items) {
			boolean match = true;
			for(Predicate<T> predicate : predicates) {
				if(!predicate.test(item)) {
					match = false;
					break;
				}
			}
			if(match) {
				result.add(item);
			}
		}
		return result;
	}
	
	/**
	 * 数组过滤为List<T>
	 * @param <T> 输入类型
	 * @param items
	 * @param predicates
	 * @return
	 */
	@SafeVarargs
	public static <T> List<T> filter(T[] array, Predicate<T>... predicates) {
		return ArrayUtils.filter(array, predicates);
	}
	
	/**
	 * 集合任意匹配
	 * @param <T>
	 * @param items
	 * @param predicates
	 * @return
	 */
	@SafeVarargs
	public static <T> boolean anyMatch(Iterable<T> items, Predicate<T>... predicates) {
		if(isEmpty(items)) {
			return false;
		}
		for(T item : items) {
			for(Predicate<T> predicate : predicates) {
				if(predicate.test(item)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 数组任意匹配
	 * @param <T>
	 * @param array
	 * @param predicates
	 * @return
	 */
	@SafeVarargs
	public static <T> boolean anyMatch(T[] array, Predicate<T>... predicates) {
		return ArrayUtils.anyMatch(array, predicates);
	}
	
	/**
	 * 数组转换为List<T>
	 * @param <T> 输入类型
	 * @param items
	 * @return
	 */
	public static <T> List<T> toList(T[] items) {
		return ArrayUtils.toList(items);
	}
	
	/**
	 * 数组转换为List<R>
	 * @param <T>
	 * @param <R>
	 * @param items
	 * @param func
	 * @return
	 */
	public static <T, R> List<R> toList(T[] items, Function<T, R> func) {
		return ArrayUtils.toList(items, func);
	}
	
	/**
	 * 集合转换为List<T>
	 * @param <T> 输入类型
	 * @param items
	 * @return
	 */
	public static <T> List<T> toList(Iterable<T> items) {
		return toList(items, x -> x);
	}
	
	/**
	 * 集合转换为List<R>
	 * @param <T>
	 * @param <R>
	 * @param items
	 * @param func
	 * @return
	 */
	public static <T, R> List<R> toList(Iterable<T> items, Function<T, R> func) {
		List<R> result = new ArrayList<>();
		if(isEmpty(items)) {
			return result;
		}
		for(T item : items) {
			result.add(func.apply(item));
		}
		return result;
	}
	
	/**
	 * 集合转换为Set<T>
	 * @param <T> 输入类型
	 * @param items
	 * @return
	 */
	public static <T> Set<T> toSet(Iterable<T> items) {
		return toSet(items, x -> x);
	}
	
	/**
	 * 集合转换为Set<R>
	 * @param <T> 输入类型
	 * @param <R> 输出类型
	 * @param items
	 * @param func
	 * @return
	 */
	public static <T, R> Set<R> toSet(Iterable<T> items, Function<T, R> func) {
		Set<R> set = new HashSet<>();
		if(isEmpty(items)) {
			return set;
		}
		for(T item : items) {
			set.add(func.apply(item));
		}
		return set;
	}
	
	public static void main(String[] args) {
		List<String> list = newArrayList(String.class);
		list.add("1");
		list.add("2");
		System.out.println(get(list, 3));
	}
}
