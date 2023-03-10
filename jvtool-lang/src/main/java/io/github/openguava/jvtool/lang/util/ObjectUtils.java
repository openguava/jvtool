package io.github.openguava.jvtool.lang.util;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Function;
import java.util.function.Supplier;

import io.github.openguava.jvtool.lang.SFunction;

/**
 * object 工具类
 * @author openguava
 *
 */
public class ObjectUtils {
	private static final int INITIAL_HASH = 7;
	private static final int MULTIPLIER = 31;
	
	protected ObjectUtils() {
		
	}

	/**
	 * 判断对象是否为空
	 * @param obj
	 * @return
	 */
	public static boolean isNull(Object obj) {
		return obj == null;
	}
	
	/**
	 * 判断对象属性是否为空
	 * <p>示例:</p>
	 * <p>R r = R.setOk();</p>
	 * <p>isNull(r, R::getCode))</p>
	 * @param <T>
	 * @param obj
	 * @param prop
	 * @return
	 */
	public static <T> boolean isNull(T obj, Function<T, ?> prop) {
		return obj == null || prop == null || prop.apply(obj) == null;
	}
	
	/**
	 * 判断对象是否为非空
	 * @param obj
	 * @return
	 */
	public static boolean isNotNull(Object obj) {
		return !isNull(obj);
	}
	
	/**
	 * 判断对象属性是否为非空
	 * @param <T>
	 * @param obj
	 * @param prop
	 * @return
	 */
	public static <T> boolean isNotNull(T obj, Function<T, ?> prop) {
		return !isNull(obj, prop);
	}
	
	/**
	 * 字符串是否为空
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(CharSequence str) {
		return StringUtils.isEmpty(str);
	}
	
	/**
	 * 判断数组是否为空
	 * @param <T>
	 * @param array
	 * @return
	 */
	@SafeVarargs
	public static <T> boolean isEmpty(T... array) {
		return ArrayUtils.isEmpty(array);
	}
	
	/**
	 * 判断集合是否为空
	 * @param <T>
	 * @param c
	 * @return
	 */
	public static boolean isEmpty(Collection<?> c) {
		return CollectionUtils.isEmpty(c);
	}
	
	/**
	 * 判断 Iterator 是否为空
	 * @param iterator
	 * @return
	 */
	public static boolean isEmpty(Iterator<?> iterator) {
		return CollectionUtils.isEmpty(iterator);
	}
	
    /**
	 * 判断 iterable 是否为空
	 * @param iterable
	 * @return
	 */
	public static boolean isEmpty(Iterable<?> iterable) {
		return CollectionUtils.isEmpty(iterable);
	}
	
	/**
	 * 字符串是否为非空
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(CharSequence str) {
		return StringUtils.isNotEmpty(str);
	}
	
	/**
	 * 判断数组是否为非空
	 * @param <T>
	 * @param array
	 * @return
	 */
	@SafeVarargs
	public static <T> boolean isNotEmpty(T... array) {
		return ArrayUtils.isNotEmpty(array);
	}
	
	/**
	 * 判断集合是否为非空
	 * @param <T>
	 * @param c
	 * @return
	 */
	public static boolean isNotEmpty(Collection<?> c) {
		return CollectionUtils.isNotEmpty(c);
	}
	
	/**
	 * 判断 Iterator 是否为非空
	 * @param iterator
	 * @return
	 */
	public static boolean isNotEmpty(Iterator<?> iterator) {
		return CollectionUtils.isNotEmpty(iterator);
	}
	
    /**
	 * 判断 iterable 是否为非空
	 * @param iterable
	 * @return
	 */
	public static boolean isNotEmpty(Iterable<?> iterable) {
		return CollectionUtils.isNotEmpty(iterable);
	}
	
	/**
	 * ifnull 表达式
	 * @param <T>
	 * @param obj 对象
	 * @param def 默认值
	 * @return
	 */
	public static <T> T ifNull(T obj, T def) {
		return obj != null ? obj : def;
	}
	
	/**
	 * ifnull 表达式
	 * @param <T>
	 * @param obj 对象
	 * @param defSupplier 默认值提供者
	 * @return
	 */
	public static <T> T ifNull(T obj, Supplier<T> defSupplier) {
		if(obj != null) {
			return obj;
		} else {
			return defSupplier == null ? null : defSupplier.get();
		}
	}
	
	/**
	 * ifnull 表达式
	 * @param <T>
	 * @param <P>
	 * @param obj 对象
	 * @param prop 属性表达式
	 * @param def 属性默认值
	 * @return
	 */
	public static <T, P> P ifNull(T obj, Function<T, P> prop, P def) {
		P val = get(obj, prop);
		return val != null ? val : def;
	}
	
	/**
	 * nvl 表达式(同ifNull)
	 * @param <T>
	 * @param obj 对象
	 * @param def 默认值
	 * @return
	 */
	public static <T> T nvl(T obj, T def) {
		return ifNull(obj, def);
	}
	
	/**
	 * nvl 表达式(同ifNull)
	 * @param <T>
	 * @param obj 对象
	 * @param defSupplier 默认值提供者
	 * @return
	 */
	public static <T> T nvl(T obj, Supplier<T> defSupplier) {
		return ifNull(obj, defSupplier);
	}
	
	/**
	 * nvl 表达式(同ifNull)
	 * @param <T>
	 * @param <P>
	 * @param obj 对象
	 * @param prop 属性表达式
	 * @param def 属性默认值
	 * @return
	 */
	public static <T, P> P nvl(T obj, Function<T, P> prop, P def) {
		return ifNull(obj, prop, def);
	}
	
	/**
	 * 判断两个对象是否相等
	 * @param <T>
	 * @param src
	 * @param dist
	 * @return
	 */
	public static <T> boolean equals(T src, T dist) {
		if(src == dist) {
			return true;
		}
		if(src == null || dist == null) {
			return false;
		}
		return src.equals(dist);
	}
	
	/**
	 * 判断两个对象是否不相等
	 * @param <T>
	 * @param src
	 * @param dist
	 * @return
	 */
	public static <T> boolean notEquals(T src, T dist) {
		return !equals(src, dist);
	}
	
	/**
	 * 判断两个对象属性是否相等
	 * @param <T>
	 * @param <P>
	 * @param src
	 * @param dist
	 * @param prop
	 * @return
	 */
	public static <T, P> boolean equals(T src, T dist, Function<T, P> prop) {
		P propSrc = get(src, prop);
		P propDist = get(dist, prop);
		if(propSrc == propDist) {
			return true;
		}
		if(propSrc == null || propDist == null) {
			return false;
		}
		return propSrc.equals(propDist);
	}
	
	/**
	 * 判断两个对象属性是否不相等
	 * @param <T>
	 * @param <P>
	 * @param src
	 * @param dist
	 * @param prop
	 * @return
	 */
	public static <T, P> boolean notEquals(T src, T dist, Function<T, P> prop) {
		return !equals(src, dist, prop);
	}
	
	/**
	 * 获取对象属性值
	 * <p>示例</p>
	 * <p>R r = R.setOk(); </p>
	 * <p>get(r, R::getMsg)</p>
	 * @param <T>
	 * @param <P>
	 * @param obj
	 * @param prop
	 * @return
	 */
	public static <T, P> P get(T obj, Function<T, P> prop) {
		return (obj != null && prop != null) ? prop.apply(obj) : null;
	}
	
	/**
	 * 设置对象属性
	 * <p>示例</p>
	 * <p>R r = R.setOk(); </p>
	 * <p>set(r, R::getMsg, "hello")</p>
	 * @param <T>
	 * @param <P>
	 * @param obj
	 * @param prop
	 * @param value
	 * @return
	 */
	public static <T, P> boolean set(T obj, SFunction<T, P> prop, P value) {
		if(obj == null || prop == null) {
			return false;
		}
		Field field = ReflectUtils.getLambdaImplField(prop);
		if(field == null) {
			return false;
		}
		ReflectUtils.setFieldValue(obj, field, value);
		return true;
	}
	
		// ---------------------------------------------------------------------
		// Convenience methods for content-based equality/hash-code handling
		// ---------------------------------------------------------------------
	
		/**
		 * Determine if the given objects are equal, returning {@code true} if both are
		 * {@code null} or {@code false} if only one is {@code null}.
		 * <p>
		 * Compares arrays with {@code Arrays.equals}, performing an equality check
		 * based on the array elements rather than the array reference.
		 * 
		 * @param o1 first Object to compare
		 * @param o2 second Object to compare
		 * @return whether the given objects are equal
		 * @see Object#equals(Object)
		 * @see java.util.Arrays#equals
		 */
		public static boolean nullSafeEquals(Object o1, Object o2) {
			if (o1 == o2) {
				return true;
			}
			if (o1 == null || o2 == null) {
				return false;
			}
			if (o1.equals(o2)) {
				return true;
			}
			if (o1.getClass().isArray() && o2.getClass().isArray()) {
				return arrayEquals(o1, o2);
			}
			return false;
		}
	
		/**
		 * Compare the given arrays with {@code Arrays.equals}, performing an equality
		 * check based on the array elements rather than the array reference.
		 * 
		 * @param o1 first array to compare
		 * @param o2 second array to compare
		 * @return whether the given objects are equal
		 * @see #nullSafeEquals(Object, Object)
		 * @see java.util.Arrays#equals
		 */
		private static boolean arrayEquals(Object o1, Object o2) {
			if (o1 instanceof Object[] && o2 instanceof Object[]) {
				return Arrays.equals((Object[]) o1, (Object[]) o2);
			}
			if (o1 instanceof boolean[] && o2 instanceof boolean[]) {
				return Arrays.equals((boolean[]) o1, (boolean[]) o2);
			}
			if (o1 instanceof byte[] && o2 instanceof byte[]) {
				return Arrays.equals((byte[]) o1, (byte[]) o2);
			}
			if (o1 instanceof char[] && o2 instanceof char[]) {
				return Arrays.equals((char[]) o1, (char[]) o2);
			}
			if (o1 instanceof double[] && o2 instanceof double[]) {
				return Arrays.equals((double[]) o1, (double[]) o2);
			}
			if (o1 instanceof float[] && o2 instanceof float[]) {
				return Arrays.equals((float[]) o1, (float[]) o2);
			}
			if (o1 instanceof int[] && o2 instanceof int[]) {
				return Arrays.equals((int[]) o1, (int[]) o2);
			}
			if (o1 instanceof long[] && o2 instanceof long[]) {
				return Arrays.equals((long[]) o1, (long[]) o2);
			}
			if (o1 instanceof short[] && o2 instanceof short[]) {
				return Arrays.equals((short[]) o1, (short[]) o2);
			}
			return false;
		}
	
		/**
		 * Return as hash code for the given object; typically the value of
		 * {@code Object#hashCode()}}. If the object is an array, this method will
		 * delegate to any of the {@code nullSafeHashCode} methods for arrays in this
		 * class. If the object is {@code null}, this method returns 0.
		 * 
		 * @see Object#hashCode()
		 * @see #nullSafeHashCode(Object[])
		 * @see #nullSafeHashCode(boolean[])
		 * @see #nullSafeHashCode(byte[])
		 * @see #nullSafeHashCode(char[])
		 * @see #nullSafeHashCode(double[])
		 * @see #nullSafeHashCode(float[])
		 * @see #nullSafeHashCode(int[])
		 * @see #nullSafeHashCode(long[])
		 * @see #nullSafeHashCode(short[])
		 */
		public static int nullSafeHashCode(Object obj) {
			if (obj == null) {
				return 0;
			}
			if (obj.getClass().isArray()) {
				if (obj instanceof Object[]) {
					return nullSafeHashCode((Object[]) obj);
				}
				if (obj instanceof boolean[]) {
					return nullSafeHashCode((boolean[]) obj);
				}
				if (obj instanceof byte[]) {
					return nullSafeHashCode((byte[]) obj);
				}
				if (obj instanceof char[]) {
					return nullSafeHashCode((char[]) obj);
				}
				if (obj instanceof double[]) {
					return nullSafeHashCode((double[]) obj);
				}
				if (obj instanceof float[]) {
					return nullSafeHashCode((float[]) obj);
				}
				if (obj instanceof int[]) {
					return nullSafeHashCode((int[]) obj);
				}
				if (obj instanceof long[]) {
					return nullSafeHashCode((long[]) obj);
				}
				if (obj instanceof short[]) {
					return nullSafeHashCode((short[]) obj);
				}
			}
			return obj.hashCode();
		}
	
		/**
		 * Return a hash code based on the contents of the specified array. If
		 * {@code array} is {@code null}, this method returns 0.
		 */
		public static int nullSafeHashCode(Object[] array) {
			if (array == null) {
				return 0;
			}
			int hash = INITIAL_HASH;
			for (Object element : array) {
				hash = MULTIPLIER * hash + nullSafeHashCode(element);
			}
			return hash;
		}
	
		/**
		 * Return a hash code based on the contents of the specified array. If
		 * {@code array} is {@code null}, this method returns 0.
		 */
		public static int nullSafeHashCode(boolean[] array) {
			if (array == null) {
				return 0;
			}
			int hash = INITIAL_HASH;
			for (boolean element : array) {
				hash = MULTIPLIER * hash + Boolean.hashCode(element);
			}
			return hash;
		}
	
		/**
		 * Return a hash code based on the contents of the specified array. If
		 * {@code array} is {@code null}, this method returns 0.
		 */
		public static int nullSafeHashCode(byte[] array) {
			if (array == null) {
				return 0;
			}
			int hash = INITIAL_HASH;
			for (byte element : array) {
				hash = MULTIPLIER * hash + element;
			}
			return hash;
		}
	
		/**
		 * Return a hash code based on the contents of the specified array. If
		 * {@code array} is {@code null}, this method returns 0.
		 */
		public static int nullSafeHashCode(char[] array) {
			if (array == null) {
				return 0;
			}
			int hash = INITIAL_HASH;
			for (char element : array) {
				hash = MULTIPLIER * hash + element;
			}
			return hash;
		}
	
		/**
		 * Return a hash code based on the contents of the specified array. If
		 * {@code array} is {@code null}, this method returns 0.
		 */
		public static int nullSafeHashCode(double[] array) {
			if (array == null) {
				return 0;
			}
			int hash = INITIAL_HASH;
			for (double element : array) {
				hash = MULTIPLIER * hash + Double.hashCode(element);
			}
			return hash;
		}
	
		/**
		 * Return a hash code based on the contents of the specified array. If
		 * {@code array} is {@code null}, this method returns 0.
		 */
		public static int nullSafeHashCode(float[] array) {
			if (array == null) {
				return 0;
			}
			int hash = INITIAL_HASH;
			for (float element : array) {
				hash = MULTIPLIER * hash + Float.hashCode(element);
			}
			return hash;
		}
	
		/**
		 * Return a hash code based on the contents of the specified array. If
		 * {@code array} is {@code null}, this method returns 0.
		 */
		public static int nullSafeHashCode(int[] array) {
			if (array == null) {
				return 0;
			}
			int hash = INITIAL_HASH;
			for (int element : array) {
				hash = MULTIPLIER * hash + element;
			}
			return hash;
		}
	
		/**
		 * Return a hash code based on the contents of the specified array. If
		 * {@code array} is {@code null}, this method returns 0.
		 */
		public static int nullSafeHashCode(long[] array) {
			if (array == null) {
				return 0;
			}
			int hash = INITIAL_HASH;
			for (long element : array) {
				hash = MULTIPLIER * hash + Long.hashCode(element);
			}
			return hash;
		}
	
		/**
		 * Return a hash code based on the contents of the specified array. If
		 * {@code array} is {@code null}, this method returns 0.
		 */
		public static int nullSafeHashCode(short[] array) {
			if (array == null) {
				return 0;
			}
			int hash = INITIAL_HASH;
			for (short element : array) {
				hash = MULTIPLIER * hash + element;
			}
			return hash;
		}
}
