package io.github.openguava.jvtool.lang.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import io.github.openguava.jvtool.lang.constant.ArrayConstants;

/**
 * 数组工具类
 * 
 * @author openguava
 *
 */
public class ArrayUtils {

	/** 数组中元素未找到的下标，值为-1 */
	public static final int INDEX_NOT_FOUND = -1;

	/** Class空数组 */
	public static final Class<?>[] EMPTY_CLASS_ARRAY = ArrayConstants.EMPTY_CLASS_ARRAY;

	/**
	 * 判断数组是否为空
	 * 
	 * @param array
	 * @return
	 */
	@SafeVarargs
	public static <T> boolean isEmpty(T... array) {
		return array == null || array.length == 0;
	}

	/**
	 * <p>
	 * 判断长整型数组是否为非空
	 * </p>
	 * <p>
	 * Checks if an array of primitive longs is empty or {@code null}.
	 *
	 * @param array the array to test
	 * @return {@code true} if the array is empty or {@code null}
	 * @since 2.1
	 */
	public static boolean isEmpty(final long[] array) {
		return getLength(array) == 0;
	}

	/**
	 * <p>
	 * 判断整型数组是否为非空
	 * </p>
	 * <p>
	 * Checks if an array of primitive ints is empty or {@code null}.
	 *
	 * @param array the array to test
	 * @return {@code true} if the array is empty or {@code null}
	 * @since 2.1
	 */
	public static boolean isEmpty(final int[] array) {
		return getLength(array) == 0;
	}

	/**
	 * <p>
	 * 判断短整型数组是否为非空
	 * </p>
	 * <p>
	 * Checks if an array of primitive shorts is empty or {@code null}.
	 *
	 * @param array the array to test
	 * @return {@code true} if the array is empty or {@code null}
	 * @since 2.1
	 */
	public static boolean isEmpty(final short[] array) {
		return getLength(array) == 0;
	}

	/**
	 * <p>
	 * 判断字符数组是否为非空
	 * </p>
	 * <p>
	 * Checks if an array of primitive chars is empty or {@code null}.
	 *
	 * @param array the array to test
	 * @return {@code true} if the array is empty or {@code null}
	 * @since 2.1
	 */
	public static boolean isEmpty(final char[] array) {
		return getLength(array) == 0;
	}

	/**
	 * <p>
	 * 判断字节数组是否为非空
	 * </p>
	 * <p>
	 * Checks if an array of primitive bytes is empty or {@code null}.
	 *
	 * @param array the array to test
	 * @return {@code true} if the array is empty or {@code null}
	 * @since 2.1
	 */
	public static boolean isEmpty(final byte[] array) {
		return getLength(array) == 0;
	}

	/**
	 * <p>
	 * 判断双精度浮点型数组是否为非空
	 * </p>
	 * <p>
	 * Checks if an array of primitive doubles is empty or {@code null}.
	 *
	 * @param array the array to test
	 * @return {@code true} if the array is empty or {@code null}
	 * @since 2.1
	 */
	public static boolean isEmpty(final double[] array) {
		return getLength(array) == 0;
	}

	/**
	 * <p>
	 * 判断浮点型数组是否为非空
	 * </p>
	 * <p>
	 * Checks if an array of primitive floats is empty or {@code null}.
	 *
	 * @param array the array to test
	 * @return {@code true} if the array is empty or {@code null}
	 * @since 2.1
	 */
	public static boolean isEmpty(final float[] array) {
		return getLength(array) == 0;
	}

	/**
	 * <p>
	 * 判断布尔型数组是否为非空
	 * </p>
	 * <p>
	 * Checks if an array of primitive booleans is empty or {@code null}.
	 *
	 * @param array the array to test
	 * @return {@code true} if the array is empty or {@code null}
	 * @since 2.1
	 */
	public static boolean isEmpty(final boolean[] array) {
		return getLength(array) == 0;
	}

	/**
	 * 判断数组是否为非空
	 * 
	 * @param array
	 * @return
	 */
	@SafeVarargs
	public static <T> boolean isNotEmpty(T... array) {
		return !isEmpty(array);
	}
	
	/**
	 * <p>
	 * Checks if an array of primitive longs is not empty and not {@code null}.
	 *
	 * @param array the array to test
	 * @return {@code true} if the array is not empty and not {@code null}
	 * @since 2.5
	 */
	public static boolean isNotEmpty(final long[] array) {
		return !isEmpty(array);
	}

	/**
	 * <p>
	 * Checks if an array of primitive ints is not empty and not {@code null}.
	 *
	 * @param array the array to test
	 * @return {@code true} if the array is not empty and not {@code null}
	 * @since 2.5
	 */
	public static boolean isNotEmpty(final int[] array) {
		return !isEmpty(array);
	}

	/**
	 * <p>
	 * Checks if an array of primitive shorts is not empty and not {@code null}.
	 *
	 * @param array the array to test
	 * @return {@code true} if the array is not empty and not {@code null}
	 * @since 2.5
	 */
	public static boolean isNotEmpty(final short[] array) {
		return !isEmpty(array);
	}

	/**
	 * <p>
	 * Checks if an array of primitive chars is not empty and not {@code null}.
	 *
	 * @param array the array to test
	 * @return {@code true} if the array is not empty and not {@code null}
	 * @since 2.5
	 */
	public static boolean isNotEmpty(final char[] array) {
		return !isEmpty(array);
	}

	/**
	 * <p>
	 * Checks if an array of primitive bytes is not empty and not {@code null}.
	 *
	 * @param array the array to test
	 * @return {@code true} if the array is not empty and not {@code null}
	 * @since 2.5
	 */
	public static boolean isNotEmpty(final byte[] array) {
		return !isEmpty(array);
	}

	/**
	 * <p>
	 * Checks if an array of primitive doubles is not empty and not {@code null}.
	 *
	 * @param array the array to test
	 * @return {@code true} if the array is not empty and not {@code null}
	 * @since 2.5
	 */
	public static boolean isNotEmpty(final double[] array) {
		return !isEmpty(array);
	}

	/**
	 * <p>
	 * Checks if an array of primitive floats is not empty and not {@code null}.
	 *
	 * @param array the array to test
	 * @return {@code true} if the array is not empty and not {@code null}
	 * @since 2.5
	 */
	public static boolean isNotEmpty(final float[] array) {
		return !isEmpty(array);
	}

	/**
	 * <p>
	 * Checks if an array of primitive booleans is not empty and not {@code null}.
	 *
	 * @param array the array to test
	 * @return {@code true} if the array is not empty and not {@code null}
	 * @since 2.5
	 */
	public static boolean isNotEmpty(final boolean[] array) {
		return !isEmpty(array);
	}
	
	/**
	 * <p>
	 * 获取数组长度
	 * </p>
	 * <p>
	 * Returns the length of the specified array. This method can deal with
	 * {@code Object} arrays and with primitive arrays.
	 *
	 * <p>
	 * If the input array is {@code null}, {@code 0} is returned.
	 *
	 * <pre>
	 * ArrayUtils.getLength(null)            = 0
	 * ArrayUtils.getLength([])              = 0
	 * ArrayUtils.getLength([null])          = 1
	 * ArrayUtils.getLength([true, false])   = 2
	 * ArrayUtils.getLength([1, 2, 3])       = 3
	 * ArrayUtils.getLength(["a", "b", "c"]) = 3
	 * </pre>
	 *
	 * @param array the array to retrieve the length from, may be null
	 * @return The length of the array, or {@code 0} if the array is {@code null}
	 * @throws IllegalArgumentException if the object argument is not an array.
	 * @since 2.1
	 */
	public static int getLength(final Object array) {
		if (array == null) {
			return 0;
		}
		return Array.getLength(array);
	}

	/**
	 * 是否包含{@code null}元素
	 *
	 * @param <T>   数组元素类型
	 * @param array 被检查的数组
	 * @return 是否包含{@code null}元素
	 * @since 3.0.7
	 */
	@SuppressWarnings("unchecked")
	public static <T> boolean hasNull(T... array) {
		if (isNotEmpty(array)) {
			for (T element : array) {
				if (element == null) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 对象是否为数组对象
	 *
	 * @param obj 对象
	 * @return 是否为数组对象，如果为{@code null} 返回false
	 */
	public static boolean isArray(Object obj) {
		return null != obj && obj.getClass().isArray();
	}

	/**
	 * 新建一个空数组
	 * 
	 * @param componentType 元素类型
	 * @param newSize       大小
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] newArray(Class<?> componentType, int newSize) {
		return (T[]) Array.newInstance(componentType, newSize);
	}

	/**
	 * 返回数组中指定元素所在位置，未找到返回{@link #INDEX_NOT_FOUND}
	 * 
	 * @param array 数组
	 * @param value 被检查的元素
	 * @return 数组中指定元素所在位置，未找到返回{@link #INDEX_NOT_FOUND}
	 */
	public static <T> int indexOf(T[] array, T value) {
		if (null != array) {
			for (int i = 0; i < array.length; i++) {
				if (value == array[i]) {
					return i;
				}
			}
		}
		return INDEX_NOT_FOUND;
	}

	/**
	 * 返回数组中指定元素所在最后的位置，未找到返回{@link #INDEX_NOT_FOUND}
	 * 
	 * @param array 数组
	 * @param value 被检查的元素
	 * @return 数组中指定元素所在位置，未找到返回{@link #INDEX_NOT_FOUND}
	 */
	public static <T> int lastIndexOf(T[] array, T value) {
		if (null != array) {
			for (int i = array.length - 1; i >= 0; i--) {
				if (value == array[i]) {
					return i;
				}
			}
		}
		return INDEX_NOT_FOUND;
	}

	/**
	 * 返回数组中指定元素所在位置，忽略大小写，未找到返回{@link #INDEX_NOT_FOUND}
	 *
	 * @param array 数组
	 * @param value 被检查的元素
	 * @return 数组中指定元素所在位置，未找到返回{@link #INDEX_NOT_FOUND}
	 */
	public static int indexOfIgnoreCase(CharSequence[] array, CharSequence value) {
		if (null != array) {
			for (int i = 0; i < array.length; i++) {
				if (StringUtils.equalsIgnoreCase(array[i], value)) {
					return i;
				}
			}
		}
		return INDEX_NOT_FOUND;
	}

	/**
	 * 数组中是否包含元素
	 *
	 * @param <T>   数组元素类型
	 * @param array 数组
	 * @param value 被检查的元素
	 * @return 是否包含
	 */
	public static <T> boolean contains(T[] array, T value) {
		return indexOf(array, value) > INDEX_NOT_FOUND;
	}

	/**
	 * 数组中是否包含指定元素中的任意一个
	 *
	 * @param <T>    数组元素类型
	 * @param array  数组
	 * @param values 被检查的多个元素
	 * @return 是否包含指定元素中的任意一个
	 * @since 4.1.20
	 */
	@SuppressWarnings("unchecked")
	public static <T> boolean containsAny(T[] array, T... values) {
		for (T value : values) {
			if (contains(array, value)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 数组中是否包含指定元素中的全部
	 *
	 * @param <T>    数组元素类型
	 * @param array  数组
	 * @param values 被检查的多个元素
	 * @return 是否包含指定元素中的全部
	 * @since 5.4.7
	 */
	@SuppressWarnings("unchecked")
	public static <T> boolean containsAll(T[] array, T... values) {
		for (T value : values) {
			if (!contains(array, value)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 数组中是否包含元素，忽略大小写
	 *
	 * @param array 数组
	 * @param value 被检查的元素
	 * @return 是否包含
	 * @since 3.1.2
	 */
	public static boolean containsIgnoreCase(CharSequence[] array, CharSequence value) {
		return indexOfIgnoreCase(array, value) > INDEX_NOT_FOUND;
	}

	/**
	 * 将新元素插入到到已有数组中的某个位置<br>
	 * 添加新元素会生成一个新数组或原有数组<br>
	 * 如果插入位置为为负数，那么生成一个由插入元素顺序加已有数组顺序的新数组
	 *
	 * @param <T>    数组元素类型
	 * @param buffer 已有数组
	 * @param index  位置，大于长度追加，否则替换，&lt;0表示从头部追加
	 * @param values 新值
	 * @return 新数组或原有数组
	 * @since 5.7.23
	 */
	@SuppressWarnings({ "unchecked" })
	public static <T> T[] replace(T[] buffer, int index, T... values) {
		if (isEmpty(values)) {
			return buffer;
		}
		if (isEmpty(buffer)) {
			return values;
		}
		if (index < 0) {
			// 从头部追加
			return insert(buffer, 0, values);
		}
		if (index >= buffer.length) {
			// 超出长度，尾部追加
			return append(buffer, values);
		}

		if (buffer.length >= values.length + index) {
			System.arraycopy(values, 0, buffer, index, values.length);
			return buffer;
		}

		// 替换长度大于原数组长度，新建数组
		int newArrayLength = index + values.length;
		final T[] result = newArray(buffer.getClass().getComponentType(), newArrayLength);
		System.arraycopy(buffer, 0, result, 0, index);
		System.arraycopy(values, 0, result, index, values.length);
		return result;
	}

	/**
	 * 将新元素添加到已有数组中<br>
	 * 添加新元素会生成一个新的数组，不影响原数组
	 *
	 * @param <T>         数组元素类型
	 * @param buffer      已有数组
	 * @param newElements 新元素
	 * @return 新数组
	 */
	@SafeVarargs
	public static <T> T[] append(T[] buffer, T... newElements) {
		if (isEmpty(buffer)) {
			return newElements;
		}
		return insert(buffer, buffer.length, newElements);
	}

	/**
	 * 将新元素添加到已有数组中<br>
	 * 添加新元素会生成一个新的数组，不影响原数组
	 *
	 * @param <T>         数组元素类型
	 * @param array       已有数组
	 * @param newElements 新元素
	 * @return 新数组
	 */
	@SafeVarargs
	public static <T> Object append(Object array, T... newElements) {
		if (isEmpty(array)) {
			return newElements;
		}
		return insert(array, getLength(array), newElements);
	}

	/**
	 * 将新元素插入到到已有数组中的某个位置<br>
	 * 添加新元素会生成一个新的数组，不影响原数组<br>
	 * 如果插入位置为为负数，从原数组从后向前计数，若大于原数组长度，则空白处用null填充
	 *
	 * @param <T>         数组元素类型
	 * @param buffer      已有数组
	 * @param index       插入位置，此位置为对应此位置元素之前的空档
	 * @param newElements 新元素
	 * @return 新数组
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] insert(T[] buffer, int index, T... newElements) {
		return (T[]) insert((Object) buffer, index, newElements);
	}

	/**
	 * 将新元素插入到到已有数组中的某个位置<br>
	 * 添加新元素会生成一个新的数组，不影响原数组<br>
	 * 如果插入位置为为负数，从原数组从后向前计数，若大于原数组长度，则空白处用null填充
	 *
	 * @param <T>         数组元素类型
	 * @param array       已有数组
	 * @param index       插入位置，此位置为对应此位置元素之前的空档
	 * @param newElements 新元素
	 * @return 新数组
	 */
	@SuppressWarnings({ "unchecked" })
	public static <T> Object insert(Object array, int index, T... newElements) {
		if (isEmpty(newElements)) {
			return array;
		}
		if (isEmpty(array)) {
			return newElements;
		}
		final int len = getLength(array);
		if (index < 0) {
			index = (index % len) + len;
		}
		final T[] result = newArray(array.getClass().getComponentType(), Math.max(len, index) + newElements.length);
		System.arraycopy(array, 0, result, 0, Math.min(len, index));
		System.arraycopy(newElements, 0, result, index, newElements.length);
		if (index < len) {
			System.arraycopy(array, index, result, index + newElements.length, len - index);
		}
		return result;
	}

	/**
	 * 生成一个新的重新设置大小的数组<br>
	 * 调整大小后拷贝原数组到新数组下。扩大则占位前N个位置，缩小则截断
	 *
	 * @param <T>           数组元素类型
	 * @param data          原数组
	 * @param newSize       新的数组大小
	 * @param componentType 数组元素类型
	 * @return 调整后的新数组
	 */
	public static <T> T[] resize(T[] data, int newSize, Class<?> componentType) {
		if (newSize < 0) {
			return data;
		}
		final T[] newArray = newArray(componentType, newSize);
		if (newSize > 0 && isNotEmpty(data)) {
			System.arraycopy(data, 0, newArray, 0, Math.min(data.length, newSize));
		}
		return newArray;
	}

	/**
	 * 生成一个新的重新设置大小的数组<br>
	 * 调整大小后拷贝原数组到新数组下。扩大则占位前N个位置，其它位置补充0，缩小则截断
	 *
	 * @param array   原数组
	 * @param newSize 新的数组大小
	 * @return 调整后的新数组
	 */
	public static Object resize(Object array, int newSize) {
		if (newSize < 0) {
			return array;
		}
		if (null == array) {
			return null;
		}
		final int length = getLength(array);
		final Object newArray = Array.newInstance(array.getClass().getComponentType(), newSize);
		if (newSize > 0 && isNotEmpty(array)) {
			System.arraycopy(array, 0, newArray, 0, Math.min(length, newSize));
		}
		return newArray;
	}

	/**
	 * 生成一个新的重新设置大小的数组<br>
	 * 新数组的类型为原数组的类型，调整大小后拷贝原数组到新数组下。扩大则占位前N个位置，缩小则截断
	 *
	 * @param <T>     数组元素类型
	 * @param buffer  原数组
	 * @param newSize 新的数组大小
	 * @return 调整后的新数组
	 */
	public static <T> T[] resize(T[] buffer, int newSize) {
		return resize(buffer, newSize, buffer.getClass().getComponentType());
	}

	/**
	 * <p>
	 * 转原始字符数组
	 * </p>
	 * <p>
	 * Converts an array of object Characters to primitives.
	 *
	 * <p>
	 * This method returns {@code null} for a {@code null} input array.
	 *
	 * @param array a {@code Character} array, may be {@code null}
	 * @return a {@code char} array, {@code null} if null array input
	 * @throws NullPointerException if array content is {@code null}
	 */
	public static char[] toPrimitive(final Character[] array) {
		if (array == null) {
			return null;
		} else if (array.length == 0) {
			return ArrayConstants.EMPTY_CHAR_ARRAY;
		}
		final char[] result = new char[array.length];
		for (int i = 0; i < array.length; i++) {
			result[i] = array[i].charValue();
		}
		return result;
	}

	/**
	 * <p>
	 * 转原始字符数组
	 * </p>
	 * <p>
	 * Converts an array of object Character to primitives handling {@code null}.
	 *
	 * <p>
	 * This method returns {@code null} for a {@code null} input array.
	 *
	 * @param array        a {@code Character} array, may be {@code null}
	 * @param valueForNull the value to insert if {@code null} found
	 * @return a {@code char} array, {@code null} if null array input
	 */
	public static char[] toPrimitive(final Character[] array, final char valueForNull) {
		if (array == null) {
			return null;
		} else if (array.length == 0) {
			return ArrayConstants.EMPTY_CHAR_ARRAY;
		}
		final char[] result = new char[array.length];
		for (int i = 0; i < array.length; i++) {
			final Character b = array[i];
			result[i] = (b == null ? valueForNull : b.charValue());
		}
		return result;
	}

	/**
	 * <p>
	 * Converts an array of primitive chars to objects.
	 *
	 * <p>
	 * This method returns {@code null} for a {@code null} input array.
	 *
	 * @param array a {@code char} array
	 * @return a {@code Character} array, {@code null} if null array input
	 */
	public static Character[] toObject(final char[] array) {
		if (array == null) {
			return null;
		} else if (array.length == 0) {
			return ArrayConstants.EMPTY_CHARACTER_OBJECT_ARRAY;
		}
		final Character[] result = new Character[array.length];
		for (int i = 0; i < array.length; i++) {
			result[i] = Character.valueOf(array[i]);
		}
		return result;
	}

	// Long array converters
	// ----------------------------------------------------------------------
	/**
	 * <p>
	 * 转原始长整型数组
	 * </p>
	 * <p>
	 * Converts an array of object Longs to primitives.
	 *
	 * <p>
	 * This method returns {@code null} for a {@code null} input array.
	 *
	 * @param array a {@code Long} array, may be {@code null}
	 * @return a {@code long} array, {@code null} if null array input
	 * @throws NullPointerException if array content is {@code null}
	 */
	public static long[] toPrimitive(final Long[] array) {
		if (array == null) {
			return null;
		} else if (array.length == 0) {
			return ArrayConstants.EMPTY_LONG_ARRAY;
		}
		final long[] result = new long[array.length];
		for (int i = 0; i < array.length; i++) {
			result[i] = array[i].longValue();
		}
		return result;
	}

	/**
	 * <p>
	 * 转原始长整型数组
	 * </p>
	 * <p>
	 * Converts an array of object Long to primitives handling {@code null}.
	 *
	 * <p>
	 * This method returns {@code null} for a {@code null} input array.
	 *
	 * @param array        a {@code Long} array, may be {@code null}
	 * @param valueForNull the value to insert if {@code null} found
	 * @return a {@code long} array, {@code null} if null array input
	 */
	public static long[] toPrimitive(final Long[] array, final long valueForNull) {
		if (array == null) {
			return null;
		} else if (array.length == 0) {
			return ArrayConstants.EMPTY_LONG_ARRAY;
		}
		final long[] result = new long[array.length];
		for (int i = 0; i < array.length; i++) {
			final Long b = array[i];
			result[i] = (b == null ? valueForNull : b.longValue());
		}
		return result;
	}

	/**
	 * <p>
	 * Converts an array of primitive longs to objects.
	 *
	 * <p>
	 * This method returns {@code null} for a {@code null} input array.
	 *
	 * @param array a {@code long} array
	 * @return a {@code Long} array, {@code null} if null array input
	 */
	public static Long[] toObject(final long[] array) {
		if (array == null) {
			return null;
		} else if (array.length == 0) {
			return ArrayConstants.EMPTY_LONG_OBJECT_ARRAY;
		}
		final Long[] result = new Long[array.length];
		for (int i = 0; i < array.length; i++) {
			result[i] = Long.valueOf(array[i]);
		}
		return result;
	}

	// Int array converters
	// ----------------------------------------------------------------------
	/**
	 * <p>
	 * 转原始整型数组
	 * </p>
	 * <p>
	 * Converts an array of object Integers to primitives.
	 *
	 * <p>
	 * This method returns {@code null} for a {@code null} input array.
	 *
	 * @param array a {@code Integer} array, may be {@code null}
	 * @return an {@code int} array, {@code null} if null array input
	 * @throws NullPointerException if array content is {@code null}
	 */
	public static int[] toPrimitive(final Integer[] array) {
		if (array == null) {
			return null;
		} else if (array.length == 0) {
			return ArrayConstants.EMPTY_INT_ARRAY;
		}
		final int[] result = new int[array.length];
		for (int i = 0; i < array.length; i++) {
			result[i] = array[i].intValue();
		}
		return result;
	}

	/**
	 * <p>
	 * 转原始整型数组
	 * </p>
	 * <p>
	 * Converts an array of object Integer to primitives handling {@code null}.
	 *
	 * <p>
	 * This method returns {@code null} for a {@code null} input array.
	 *
	 * @param array        a {@code Integer} array, may be {@code null}
	 * @param valueForNull the value to insert if {@code null} found
	 * @return an {@code int} array, {@code null} if null array input
	 */
	public static int[] toPrimitive(final Integer[] array, final int valueForNull) {
		if (array == null) {
			return null;
		} else if (array.length == 0) {
			return ArrayConstants.EMPTY_INT_ARRAY;
		}
		final int[] result = new int[array.length];
		for (int i = 0; i < array.length; i++) {
			final Integer b = array[i];
			result[i] = (b == null ? valueForNull : b.intValue());
		}
		return result;
	}

	/**
	 * <p>
	 * Converts an array of primitive ints to objects.
	 *
	 * <p>
	 * This method returns {@code null} for a {@code null} input array.
	 *
	 * @param array an {@code int} array
	 * @return an {@code Integer} array, {@code null} if null array input
	 */
	public static Integer[] toObject(final int[] array) {
		if (array == null) {
			return null;
		} else if (array.length == 0) {
			return ArrayConstants.EMPTY_INTEGER_OBJECT_ARRAY;
		}
		final Integer[] result = new Integer[array.length];
		for (int i = 0; i < array.length; i++) {
			result[i] = Integer.valueOf(array[i]);
		}
		return result;
	}

	// Short array converters
	// ----------------------------------------------------------------------
	/**
	 * <p>
	 * 转原始短整型数组
	 * </p>
	 * <p>
	 * Converts an array of object Shorts to primitives.
	 *
	 * <p>
	 * This method returns {@code null} for a {@code null} input array.
	 *
	 * @param array a {@code Short} array, may be {@code null}
	 * @return a {@code byte} array, {@code null} if null array input
	 * @throws NullPointerException if array content is {@code null}
	 */
	public static short[] toPrimitive(final Short[] array) {
		if (array == null) {
			return null;
		} else if (array.length == 0) {
			return ArrayConstants.EMPTY_SHORT_ARRAY;
		}
		final short[] result = new short[array.length];
		for (int i = 0; i < array.length; i++) {
			result[i] = array[i].shortValue();
		}
		return result;
	}

	/**
	 * <p>
	 * 转原始短整型数组
	 * </p>
	 * <p>
	 * Converts an array of object Short to primitives handling {@code null}.
	 *
	 * <p>
	 * This method returns {@code null} for a {@code null} input array.
	 *
	 * @param array        a {@code Short} array, may be {@code null}
	 * @param valueForNull the value to insert if {@code null} found
	 * @return a {@code byte} array, {@code null} if null array input
	 */
	public static short[] toPrimitive(final Short[] array, final short valueForNull) {
		if (array == null) {
			return null;
		} else if (array.length == 0) {
			return ArrayConstants.EMPTY_SHORT_ARRAY;
		}
		final short[] result = new short[array.length];
		for (int i = 0; i < array.length; i++) {
			final Short b = array[i];
			result[i] = (b == null ? valueForNull : b.shortValue());
		}
		return result;
	}

	/**
	 * <p>
	 * Converts an array of primitive shorts to objects.
	 *
	 * <p>
	 * This method returns {@code null} for a {@code null} input array.
	 *
	 * @param array a {@code short} array
	 * @return a {@code Short} array, {@code null} if null array input
	 */
	public static Short[] toObject(final short[] array) {
		if (array == null) {
			return null;
		} else if (array.length == 0) {
			return ArrayConstants.EMPTY_SHORT_OBJECT_ARRAY;
		}
		final Short[] result = new Short[array.length];
		for (int i = 0; i < array.length; i++) {
			result[i] = Short.valueOf(array[i]);
		}
		return result;
	}

	// Byte array converters
	// ----------------------------------------------------------------------
	/**
	 * <p>
	 * 转原始字节数组
	 * </p>
	 * <p>
	 * Converts an array of object Bytes to primitives.
	 *
	 * <p>
	 * This method returns {@code null} for a {@code null} input array.
	 *
	 * @param array a {@code Byte} array, may be {@code null}
	 * @return a {@code byte} array, {@code null} if null array input
	 * @throws NullPointerException if array content is {@code null}
	 */
	public static byte[] toPrimitive(final Byte[] array) {
		if (array == null) {
			return null;
		} else if (array.length == 0) {
			return ArrayConstants.EMPTY_BYTE_ARRAY;
		}
		final byte[] result = new byte[array.length];
		for (int i = 0; i < array.length; i++) {
			result[i] = array[i].byteValue();
		}
		return result;
	}

	/**
	 * <p>
	 * 转原始字节数组
	 * </p>
	 * <p>
	 * Converts an array of object Bytes to primitives handling {@code null}.
	 *
	 * <p>
	 * This method returns {@code null} for a {@code null} input array.
	 *
	 * @param array        a {@code Byte} array, may be {@code null}
	 * @param valueForNull the value to insert if {@code null} found
	 * @return a {@code byte} array, {@code null} if null array input
	 */
	public static byte[] toPrimitive(final Byte[] array, final byte valueForNull) {
		if (array == null) {
			return null;
		} else if (array.length == 0) {
			return ArrayConstants.EMPTY_BYTE_ARRAY;
		}
		final byte[] result = new byte[array.length];
		for (int i = 0; i < array.length; i++) {
			final Byte b = array[i];
			result[i] = (b == null ? valueForNull : b.byteValue());
		}
		return result;
	}

	/**
	 * <p>
	 * Converts an array of primitive bytes to objects.
	 *
	 * <p>
	 * This method returns {@code null} for a {@code null} input array.
	 *
	 * @param array a {@code byte} array
	 * @return a {@code Byte} array, {@code null} if null array input
	 */
	public static Byte[] toObject(final byte[] array) {
		if (array == null) {
			return null;
		} else if (array.length == 0) {
			return ArrayConstants.EMPTY_BYTE_OBJECT_ARRAY;
		}
		final Byte[] result = new Byte[array.length];
		for (int i = 0; i < array.length; i++) {
			result[i] = Byte.valueOf(array[i]);
		}
		return result;
	}

	// Double array converters
	// ----------------------------------------------------------------------
	/**
	 * <p>
	 * 转原始双精度浮点型数组
	 * </p>
	 * <p>
	 * Converts an array of object Doubles to primitives.
	 *
	 * <p>
	 * This method returns {@code null} for a {@code null} input array.
	 *
	 * @param array a {@code Double} array, may be {@code null}
	 * @return a {@code double} array, {@code null} if null array input
	 * @throws NullPointerException if array content is {@code null}
	 */
	public static double[] toPrimitive(final Double[] array) {
		if (array == null) {
			return null;
		} else if (array.length == 0) {
			return ArrayConstants.EMPTY_DOUBLE_ARRAY;
		}
		final double[] result = new double[array.length];
		for (int i = 0; i < array.length; i++) {
			result[i] = array[i].doubleValue();
		}
		return result;
	}

	/**
	 * <p>
	 * 转原始双精度浮点型数组
	 * </p>
	 * <p>
	 * Converts an array of object Doubles to primitives handling {@code null}.
	 *
	 * <p>
	 * This method returns {@code null} for a {@code null} input array.
	 *
	 * @param array        a {@code Double} array, may be {@code null}
	 * @param valueForNull the value to insert if {@code null} found
	 * @return a {@code double} array, {@code null} if null array input
	 */
	public static double[] toPrimitive(final Double[] array, final double valueForNull) {
		if (array == null) {
			return null;
		} else if (array.length == 0) {
			return ArrayConstants.EMPTY_DOUBLE_ARRAY;
		}
		final double[] result = new double[array.length];
		for (int i = 0; i < array.length; i++) {
			final Double b = array[i];
			result[i] = (b == null ? valueForNull : b.doubleValue());
		}
		return result;
	}

	/**
	 * <p>
	 * Converts an array of primitive doubles to objects.
	 *
	 * <p>
	 * This method returns {@code null} for a {@code null} input array.
	 *
	 * @param array a {@code double} array
	 * @return a {@code Double} array, {@code null} if null array input
	 */
	public static Double[] toObject(final double[] array) {
		if (array == null) {
			return null;
		} else if (array.length == 0) {
			return ArrayConstants.EMPTY_DOUBLE_OBJECT_ARRAY;
		}
		final Double[] result = new Double[array.length];
		for (int i = 0; i < array.length; i++) {
			result[i] = Double.valueOf(array[i]);
		}
		return result;
	}

	// Float array converters
	// ----------------------------------------------------------------------
	/**
	 * <p>
	 * 转原始浮点型数组
	 * </p>
	 * <p>
	 * Converts an array of object Floats to primitives.
	 *
	 * <p>
	 * This method returns {@code null} for a {@code null} input array.
	 *
	 * @param array a {@code Float} array, may be {@code null}
	 * @return a {@code float} array, {@code null} if null array input
	 * @throws NullPointerException if array content is {@code null}
	 */
	public static float[] toPrimitive(final Float[] array) {
		if (array == null) {
			return null;
		} else if (array.length == 0) {
			return ArrayConstants.EMPTY_FLOAT_ARRAY;
		}
		final float[] result = new float[array.length];
		for (int i = 0; i < array.length; i++) {
			result[i] = array[i].floatValue();
		}
		return result;
	}

	/**
	 * <p>
	 * 转原始浮点型数组
	 * </p>
	 * <p>
	 * Converts an array of object Floats to primitives handling {@code null}.
	 *
	 * <p>
	 * This method returns {@code null} for a {@code null} input array.
	 *
	 * @param array        a {@code Float} array, may be {@code null}
	 * @param valueForNull the value to insert if {@code null} found
	 * @return a {@code float} array, {@code null} if null array input
	 */
	public static float[] toPrimitive(final Float[] array, final float valueForNull) {
		if (array == null) {
			return null;
		} else if (array.length == 0) {
			return ArrayConstants.EMPTY_FLOAT_ARRAY;
		}
		final float[] result = new float[array.length];
		for (int i = 0; i < array.length; i++) {
			final Float b = array[i];
			result[i] = (b == null ? valueForNull : b.floatValue());
		}
		return result;
	}

	/**
	 * <p>
	 * Converts an array of primitive floats to objects.
	 *
	 * <p>
	 * This method returns {@code null} for a {@code null} input array.
	 *
	 * @param array a {@code float} array
	 * @return a {@code Float} array, {@code null} if null array input
	 */
	public static Float[] toObject(final float[] array) {
		if (array == null) {
			return null;
		} else if (array.length == 0) {
			return ArrayConstants.EMPTY_FLOAT_OBJECT_ARRAY;
		}
		final Float[] result = new Float[array.length];
		for (int i = 0; i < array.length; i++) {
			result[i] = Float.valueOf(array[i]);
		}
		return result;
	}

	/**
	 * <p>
	 * Create an array of primitive type from an array of wrapper types.
	 *
	 * <p>
	 * This method returns {@code null} for a {@code null} input array.
	 *
	 * @param array an array of wrapper object
	 * @return an array of the corresponding primitive type, or the original array
	 * @since 3.5
	 */
	public static Object toPrimitive(final Object array) {
		if (array == null) {
			return null;
		}
		final Class<?> ct = array.getClass().getComponentType();
		final Class<?> pt = ClassUtils.wrapperToPrimitive(ct);
		if (Integer.TYPE.equals(pt)) {
			return toPrimitive((Integer[]) array);
		}
		if (Long.TYPE.equals(pt)) {
			return toPrimitive((Long[]) array);
		}
		if (Short.TYPE.equals(pt)) {
			return toPrimitive((Short[]) array);
		}
		if (Double.TYPE.equals(pt)) {
			return toPrimitive((Double[]) array);
		}
		if (Float.TYPE.equals(pt)) {
			return toPrimitive((Float[]) array);
		}
		return array;
	}

	// Boolean array converters
	// ----------------------------------------------------------------------
	/**
	 * <p>
	 * 转原始布尔型数组
	 * </p>
	 * <p>
	 * Converts an array of object Booleans to primitives.
	 *
	 * <p>
	 * This method returns {@code null} for a {@code null} input array.
	 *
	 * @param array a {@code Boolean} array, may be {@code null}
	 * @return a {@code boolean} array, {@code null} if null array input
	 * @throws NullPointerException if array content is {@code null}
	 */
	public static boolean[] toPrimitive(final Boolean[] array) {
		if (array == null) {
			return null;
		} else if (array.length == 0) {
			return ArrayConstants.EMPTY_BOOLEAN_ARRAY;
		}
		final boolean[] result = new boolean[array.length];
		for (int i = 0; i < array.length; i++) {
			result[i] = array[i].booleanValue();
		}
		return result;
	}

	/**
	 * <p>
	 * 转原始布尔型数组
	 * </p>
	 * <p>
	 * Converts an array of object Booleans to primitives handling {@code null}.
	 *
	 * <p>
	 * This method returns {@code null} for a {@code null} input array.
	 *
	 * @param array        a {@code Boolean} array, may be {@code null}
	 * @param valueForNull the value to insert if {@code null} found
	 * @return a {@code boolean} array, {@code null} if null array input
	 */
	public static boolean[] toPrimitive(final Boolean[] array, final boolean valueForNull) {
		if (array == null) {
			return null;
		} else if (array.length == 0) {
			return ArrayConstants.EMPTY_BOOLEAN_ARRAY;
		}
		final boolean[] result = new boolean[array.length];
		for (int i = 0; i < array.length; i++) {
			final Boolean b = array[i];
			result[i] = (b == null ? valueForNull : b.booleanValue());
		}
		return result;
	}

	/**
	 * <p>
	 * Converts an array of primitive booleans to objects.
	 *
	 * <p>
	 * This method returns {@code null} for a {@code null} input array.
	 *
	 * @param array a {@code boolean} array
	 * @return a {@code Boolean} array, {@code null} if null array input
	 */
	public static Boolean[] toObject(final boolean[] array) {
		if (array == null) {
			return null;
		} else if (array.length == 0) {
			return ArrayConstants.EMPTY_BOOLEAN_OBJECT_ARRAY;
		}
		final Boolean[] result = new Boolean[array.length];
		for (int i = 0; i < array.length; i++) {
			result[i] = (array[i] ? Boolean.TRUE : Boolean.FALSE);
		}
		return result;
	}

	/**
	 * 包装 {@link System#arraycopy(Object, int, Object, int, int)}<br>
	 * 数组复制
	 * 
	 * @param src     源数组
	 * @param srcPos  源数组开始位置
	 * @param dest    目标数组
	 * @param destPos 目标数组开始位置
	 * @param length  拷贝数组长度
	 * @return 目标数组
	 */
	public static Object copy(Object src, int srcPos, Object dest, int destPos, int length) {
		System.arraycopy(src, srcPos, dest, destPos, length);
		return dest;
	}

	/**
	 * 包装 {@link System#arraycopy(Object, int, Object, int, int)}<br>
	 * 数组复制，缘数组和目标数组都是从位置0开始复制
	 * 
	 * @param src    源数组
	 * @param dest   目标数组
	 * @param length 拷贝数组长度
	 * @return 目标数组
	 */
	public static Object copy(Object src, Object dest, int length) {
		System.arraycopy(src, 0, dest, 0, length);
		return dest;
	}

	/**
	 * <p>
	 * 泛型数组克隆
	 * </p>
	 * <p>
	 * Shallow clones an array returning a typecast result and handling
	 * {@code null}.
	 *
	 * <p>
	 * The objects in the array are not cloned, thus there is no special handling
	 * for multi-dimensional arrays.
	 *
	 * <p>
	 * This method returns {@code null} for a {@code null} input array.
	 *
	 * @param <T>   the component type of the array
	 * @param array the array to shallow clone, may be {@code null}
	 * @return the cloned array, {@code null} if {@code null} input
	 */
	public static <T> T[] clone(final T[] array) {
		if (array == null) {
			return null;
		}
		return array.clone();
	}

	/**
	 * <p>
	 * 长整形数组克隆
	 * </p>
	 * <p>
	 * Clones an array returning a typecast result and handling {@code null}.
	 *
	 * <p>
	 * This method returns {@code null} for a {@code null} input array.
	 *
	 * @param array the array to clone, may be {@code null}
	 * @return the cloned array, {@code null} if {@code null} input
	 */
	public static long[] clone(final long[] array) {
		if (array == null) {
			return null;
		}
		return array.clone();
	}

	/**
	 * <p>
	 * 整型数组克隆
	 * </p>
	 * <p>
	 * Clones an array returning a typecast result and handling {@code null}.
	 *
	 * <p>
	 * This method returns {@code null} for a {@code null} input array.
	 *
	 * @param array the array to clone, may be {@code null}
	 * @return the cloned array, {@code null} if {@code null} input
	 */
	public static int[] clone(final int[] array) {
		if (array == null) {
			return null;
		}
		return array.clone();
	}

	/**
	 * <p>
	 * 短整型数组克隆
	 * </p>
	 * <p>
	 * Clones an array returning a typecast result and handling {@code null}.
	 *
	 * <p>
	 * This method returns {@code null} for a {@code null} input array.
	 *
	 * @param array the array to clone, may be {@code null}
	 * @return the cloned array, {@code null} if {@code null} input
	 */
	public static short[] clone(final short[] array) {
		if (array == null) {
			return null;
		}
		return array.clone();
	}

	/**
	 * <p>
	 * 字符数组克隆
	 * </p>
	 * <p>
	 * Clones an array returning a typecast result and handling {@code null}.
	 *
	 * <p>
	 * This method returns {@code null} for a {@code null} input array.
	 *
	 * @param array the array to clone, may be {@code null}
	 * @return the cloned array, {@code null} if {@code null} input
	 */
	public static char[] clone(final char[] array) {
		if (array == null) {
			return null;
		}
		return array.clone();
	}

	/**
	 * <p>
	 * 字节数组克隆
	 * </p>
	 * <p>
	 * Clones an array returning a typecast result and handling {@code null}.
	 *
	 * <p>
	 * This method returns {@code null} for a {@code null} input array.
	 *
	 * @param array the array to clone, may be {@code null}
	 * @return the cloned array, {@code null} if {@code null} input
	 */
	public static byte[] clone(final byte[] array) {
		if (array == null) {
			return null;
		}
		return array.clone();
	}

	/**
	 * <p>
	 * 双精度浮点型数组克隆
	 * </p>
	 * <p>
	 * Clones an array returning a typecast result and handling {@code null}.
	 *
	 * <p>
	 * This method returns {@code null} for a {@code null} input array.
	 *
	 * @param array the array to clone, may be {@code null}
	 * @return the cloned array, {@code null} if {@code null} input
	 */
	public static double[] clone(final double[] array) {
		if (array == null) {
			return null;
		}
		return array.clone();
	}

	/**
	 * <p>
	 * 浮点型数组克隆
	 * </p>
	 * <p>
	 * Clones an array returning a typecast result and handling {@code null}.
	 *
	 * <p>
	 * This method returns {@code null} for a {@code null} input array.
	 *
	 * @param array the array to clone, may be {@code null}
	 * @return the cloned array, {@code null} if {@code null} input
	 */
	public static float[] clone(final float[] array) {
		if (array == null) {
			return null;
		}
		return array.clone();
	}

	/**
	 * <p>
	 * 布尔型数组克隆
	 * </p>
	 * <p>
	 * Clones an array returning a typecast result and handling {@code null}.
	 *
	 * <p>
	 * This method returns {@code null} for a {@code null} input array.
	 *
	 * @param array the array to clone, may be {@code null}
	 * @return the cloned array, {@code null} if {@code null} input
	 */
	public static boolean[] clone(final boolean[] array) {
		if (array == null) {
			return null;
		}
		return array.clone();
	}

	/**
	 * <p>
	 * Adds all the elements of the given arrays into a new array.
	 * <p>
	 * The new array contains all of the element of {@code array1} followed by all
	 * of the elements {@code array2}. When an array is returned, it is always a new
	 * array.
	 *
	 * <pre>
	 * ArrayUtils.addAll(null, null)     = null
	 * ArrayUtils.addAll(array1, null)   = cloned copy of array1
	 * ArrayUtils.addAll(null, array2)   = cloned copy of array2
	 * ArrayUtils.addAll([], [])         = []
	 * ArrayUtils.addAll([null], [null]) = [null, null]
	 * ArrayUtils.addAll(["a", "b", "c"], ["1", "2", "3"]) = ["a", "b", "c", "1", "2", "3"]
	 * </pre>
	 *
	 * @param <T>    the component type of the array
	 * @param array1 the first array whose elements are added to the new array, may
	 *               be {@code null}
	 * @param array2 the second array whose elements are added to the new array, may
	 *               be {@code null}
	 * @return The new array, {@code null} if both arrays are {@code null}. The type
	 *         of the new array is the type of the first array, unless the first
	 *         array is null, in which case the type is the same as the second
	 *         array.
	 * @since 2.1
	 * @throws IllegalArgumentException if the array types are incompatible
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] addAll(final T[] array1, final T... array2) {
		if (array1 == null) {
			return clone(array2);
		} else if (array2 == null) {
			return clone(array1);
		}
		final Class<?> type1 = array1.getClass().getComponentType();
		// OK, because array is of type T
		final T[] joinedArray = (T[]) Array.newInstance(type1, array1.length + array2.length);
		System.arraycopy(array1, 0, joinedArray, 0, array1.length);
		try {
			System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
		} catch (final ArrayStoreException ase) {
			// Check if problem was due to incompatible types
			/*
			 * We do this here, rather than before the copy because: - it would be a wasted
			 * check most of the time - safer, in case check turns out to be too strict
			 */
			final Class<?> type2 = array2.getClass().getComponentType();
			if (!type1.isAssignableFrom(type2)) {
				throw new IllegalArgumentException(
						"Cannot store " + type2.getName() + " in an array of " + type1.getName(), ase);
			}
			throw ase; // No, so rethrow original
		}
		return joinedArray;
	}

	/**
	 * <p>
	 * Adds all the elements of the given arrays into a new array.
	 * <p>
	 * The new array contains all of the element of {@code array1} followed by all
	 * of the elements {@code array2}. When an array is returned, it is always a new
	 * array.
	 *
	 * <pre>
	 * ArrayUtils.addAll(array1, null)   = cloned copy of array1
	 * ArrayUtils.addAll(null, array2)   = cloned copy of array2
	 * ArrayUtils.addAll([], [])         = []
	 * </pre>
	 *
	 * @param array1 the first array whose elements are added to the new array.
	 * @param array2 the second array whose elements are added to the new array.
	 * @return The new boolean[] array.
	 * @since 2.1
	 */
	public static boolean[] addAll(final boolean[] array1, final boolean... array2) {
		if (array1 == null) {
			return clone(array2);
		} else if (array2 == null) {
			return clone(array1);
		}
		final boolean[] joinedArray = new boolean[array1.length + array2.length];
		System.arraycopy(array1, 0, joinedArray, 0, array1.length);
		System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
		return joinedArray;
	}

	/**
	 * <p>
	 * Adds all the elements of the given arrays into a new array.
	 * <p>
	 * The new array contains all of the element of {@code array1} followed by all
	 * of the elements {@code array2}. When an array is returned, it is always a new
	 * array.
	 *
	 * <pre>
	 * ArrayUtils.addAll(array1, null)   = cloned copy of array1
	 * ArrayUtils.addAll(null, array2)   = cloned copy of array2
	 * ArrayUtils.addAll([], [])         = []
	 * </pre>
	 *
	 * @param array1 the first array whose elements are added to the new array.
	 * @param array2 the second array whose elements are added to the new array.
	 * @return The new char[] array.
	 * @since 2.1
	 */
	public static char[] addAll(final char[] array1, final char... array2) {
		if (array1 == null) {
			return clone(array2);
		} else if (array2 == null) {
			return clone(array1);
		}
		final char[] joinedArray = new char[array1.length + array2.length];
		System.arraycopy(array1, 0, joinedArray, 0, array1.length);
		System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
		return joinedArray;
	}

	/**
	 * <p>
	 * Adds all the elements of the given arrays into a new array.
	 * <p>
	 * The new array contains all of the element of {@code array1} followed by all
	 * of the elements {@code array2}. When an array is returned, it is always a new
	 * array.
	 *
	 * <pre>
	 * ArrayUtils.addAll(array1, null)   = cloned copy of array1
	 * ArrayUtils.addAll(null, array2)   = cloned copy of array2
	 * ArrayUtils.addAll([], [])         = []
	 * </pre>
	 *
	 * @param array1 the first array whose elements are added to the new array.
	 * @param array2 the second array whose elements are added to the new array.
	 * @return The new byte[] array.
	 * @since 2.1
	 */
	public static byte[] addAll(final byte[] array1, final byte... array2) {
		if (array1 == null) {
			return clone(array2);
		} else if (array2 == null) {
			return clone(array1);
		}
		final byte[] joinedArray = new byte[array1.length + array2.length];
		System.arraycopy(array1, 0, joinedArray, 0, array1.length);
		System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
		return joinedArray;
	}

	/**
	 * <p>
	 * Adds all the elements of the given arrays into a new array.
	 * <p>
	 * The new array contains all of the element of {@code array1} followed by all
	 * of the elements {@code array2}. When an array is returned, it is always a new
	 * array.
	 *
	 * <pre>
	 * ArrayUtils.addAll(array1, null)   = cloned copy of array1
	 * ArrayUtils.addAll(null, array2)   = cloned copy of array2
	 * ArrayUtils.addAll([], [])         = []
	 * </pre>
	 *
	 * @param array1 the first array whose elements are added to the new array.
	 * @param array2 the second array whose elements are added to the new array.
	 * @return The new short[] array.
	 * @since 2.1
	 */
	public static short[] addAll(final short[] array1, final short... array2) {
		if (array1 == null) {
			return clone(array2);
		} else if (array2 == null) {
			return clone(array1);
		}
		final short[] joinedArray = new short[array1.length + array2.length];
		System.arraycopy(array1, 0, joinedArray, 0, array1.length);
		System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
		return joinedArray;
	}

	/**
	 * <p>
	 * Adds all the elements of the given arrays into a new array.
	 * <p>
	 * The new array contains all of the element of {@code array1} followed by all
	 * of the elements {@code array2}. When an array is returned, it is always a new
	 * array.
	 *
	 * <pre>
	 * ArrayUtils.addAll(array1, null)   = cloned copy of array1
	 * ArrayUtils.addAll(null, array2)   = cloned copy of array2
	 * ArrayUtils.addAll([], [])         = []
	 * </pre>
	 *
	 * @param array1 the first array whose elements are added to the new array.
	 * @param array2 the second array whose elements are added to the new array.
	 * @return The new int[] array.
	 * @since 2.1
	 */
	public static int[] addAll(final int[] array1, final int... array2) {
		if (array1 == null) {
			return clone(array2);
		} else if (array2 == null) {
			return clone(array1);
		}
		final int[] joinedArray = new int[array1.length + array2.length];
		System.arraycopy(array1, 0, joinedArray, 0, array1.length);
		System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
		return joinedArray;
	}

	/**
	 * <p>
	 * Adds all the elements of the given arrays into a new array.
	 * <p>
	 * The new array contains all of the element of {@code array1} followed by all
	 * of the elements {@code array2}. When an array is returned, it is always a new
	 * array.
	 *
	 * <pre>
	 * ArrayUtils.addAll(array1, null)   = cloned copy of array1
	 * ArrayUtils.addAll(null, array2)   = cloned copy of array2
	 * ArrayUtils.addAll([], [])         = []
	 * </pre>
	 *
	 * @param array1 the first array whose elements are added to the new array.
	 * @param array2 the second array whose elements are added to the new array.
	 * @return The new long[] array.
	 * @since 2.1
	 */
	public static long[] addAll(final long[] array1, final long... array2) {
		if (array1 == null) {
			return clone(array2);
		} else if (array2 == null) {
			return clone(array1);
		}
		final long[] joinedArray = new long[array1.length + array2.length];
		System.arraycopy(array1, 0, joinedArray, 0, array1.length);
		System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
		return joinedArray;
	}

	/**
	 * <p>
	 * Adds all the elements of the given arrays into a new array.
	 * <p>
	 * The new array contains all of the element of {@code array1} followed by all
	 * of the elements {@code array2}. When an array is returned, it is always a new
	 * array.
	 *
	 * <pre>
	 * ArrayUtils.addAll(array1, null)   = cloned copy of array1
	 * ArrayUtils.addAll(null, array2)   = cloned copy of array2
	 * ArrayUtils.addAll([], [])         = []
	 * </pre>
	 *
	 * @param array1 the first array whose elements are added to the new array.
	 * @param array2 the second array whose elements are added to the new array.
	 * @return The new float[] array.
	 * @since 2.1
	 */
	public static float[] addAll(final float[] array1, final float... array2) {
		if (array1 == null) {
			return clone(array2);
		} else if (array2 == null) {
			return clone(array1);
		}
		final float[] joinedArray = new float[array1.length + array2.length];
		System.arraycopy(array1, 0, joinedArray, 0, array1.length);
		System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
		return joinedArray;
	}

	/**
	 * <p>
	 * Adds all the elements of the given arrays into a new array.
	 * <p>
	 * The new array contains all of the element of {@code array1} followed by all
	 * of the elements {@code array2}. When an array is returned, it is always a new
	 * array.
	 *
	 * <pre>
	 * ArrayUtils.addAll(array1, null)   = cloned copy of array1
	 * ArrayUtils.addAll(null, array2)   = cloned copy of array2
	 * ArrayUtils.addAll([], [])         = []
	 * </pre>
	 *
	 * @param array1 the first array whose elements are added to the new array.
	 * @param array2 the second array whose elements are added to the new array.
	 * @return The new double[] array.
	 * @since 2.1
	 */
	public static double[] addAll(final double[] array1, final double... array2) {
		if (array1 == null) {
			return clone(array2);
		} else if (array2 == null) {
			return clone(array1);
		}
		final double[] joinedArray = new double[array1.length + array2.length];
		System.arraycopy(array1, 0, joinedArray, 0, array1.length);
		System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
		return joinedArray;
	}

	/**
	 * <p>
	 * Copies the given array and adds the given element at the end of the new
	 * array.
	 *
	 * <p>
	 * The new array contains the same elements of the input array plus the given
	 * element in the last position. The component type of the new array is the same
	 * as that of the input array.
	 *
	 * <p>
	 * If the input array is {@code null}, a new one element array is returned whose
	 * component type is the same as the element, unless the element itself is null,
	 * in which case the return type is Object[]
	 *
	 * <pre>
	 * ArrayUtils.add(null, null)      = IllegalArgumentException
	 * ArrayUtils.add(null, "a")       = ["a"]
	 * ArrayUtils.add(["a"], null)     = ["a", null]
	 * ArrayUtils.add(["a"], "b")      = ["a", "b"]
	 * ArrayUtils.add(["a", "b"], "c") = ["a", "b", "c"]
	 * </pre>
	 *
	 * @param <T>     the component type of the array
	 * @param array   the array to "add" the element to, may be {@code null}
	 * @param element the object to add, may be {@code null}
	 * @return A new array containing the existing elements plus the new element The
	 *         returned array type will be that of the input array (unless null), in
	 *         which case it will have the same type as the element. If both are
	 *         null, an IllegalArgumentException is thrown
	 * @since 2.1
	 * @throws IllegalArgumentException if both arguments are null
	 */
	public static <T> T[] add(final T[] array, final T element) {
		Class<?> type;
		if (array != null) {
			type = array.getClass().getComponentType();
		} else if (element != null) {
			type = element.getClass();
		} else {
			throw new IllegalArgumentException("Arguments cannot both be null");
		}
		@SuppressWarnings("unchecked") // type must be T
		final T[] newArray = (T[]) copyArrayGrow1(array, type);
		newArray[newArray.length - 1] = element;
		return newArray;
	}

	/**
	 * <p>
	 * Copies the given array and adds the given element at the end of the new
	 * array.
	 *
	 * <p>
	 * The new array contains the same elements of the input array plus the given
	 * element in the last position. The component type of the new array is the same
	 * as that of the input array.
	 *
	 * <p>
	 * If the input array is {@code null}, a new one element array is returned whose
	 * component type is the same as the element.
	 *
	 * <pre>
	 * ArrayUtils.add(null, true)          = [true]
	 * ArrayUtils.add([true], false)       = [true, false]
	 * ArrayUtils.add([true, false], true) = [true, false, true]
	 * </pre>
	 *
	 * @param array   the array to copy and add the element to, may be {@code null}
	 * @param element the object to add at the last index of the new array
	 * @return A new array containing the existing elements plus the new element
	 * @since 2.1
	 */
	public static boolean[] add(final boolean[] array, final boolean element) {
		final boolean[] newArray = (boolean[]) copyArrayGrow1(array, Boolean.TYPE);
		newArray[newArray.length - 1] = element;
		return newArray;
	}

	/**
	 * <p>
	 * Copies the given array and adds the given element at the end of the new
	 * array.
	 *
	 * <p>
	 * The new array contains the same elements of the input array plus the given
	 * element in the last position. The component type of the new array is the same
	 * as that of the input array.
	 *
	 * <p>
	 * If the input array is {@code null}, a new one element array is returned whose
	 * component type is the same as the element.
	 *
	 * <pre>
	 * ArrayUtils.add(null, 0)   = [0]
	 * ArrayUtils.add([1], 0)    = [1, 0]
	 * ArrayUtils.add([1, 0], 1) = [1, 0, 1]
	 * </pre>
	 *
	 * @param array   the array to copy and add the element to, may be {@code null}
	 * @param element the object to add at the last index of the new array
	 * @return A new array containing the existing elements plus the new element
	 * @since 2.1
	 */
	public static byte[] add(final byte[] array, final byte element) {
		final byte[] newArray = (byte[]) copyArrayGrow1(array, Byte.TYPE);
		newArray[newArray.length - 1] = element;
		return newArray;
	}

	/**
	 * <p>
	 * Copies the given array and adds the given element at the end of the new
	 * array.
	 *
	 * <p>
	 * The new array contains the same elements of the input array plus the given
	 * element in the last position. The component type of the new array is the same
	 * as that of the input array.
	 *
	 * <p>
	 * If the input array is {@code null}, a new one element array is returned whose
	 * component type is the same as the element.
	 *
	 * <pre>
	 * ArrayUtils.add(null, '0')       = ['0']
	 * ArrayUtils.add(['1'], '0')      = ['1', '0']
	 * ArrayUtils.add(['1', '0'], '1') = ['1', '0', '1']
	 * </pre>
	 *
	 * @param array   the array to copy and add the element to, may be {@code null}
	 * @param element the object to add at the last index of the new array
	 * @return A new array containing the existing elements plus the new element
	 * @since 2.1
	 */
	public static char[] add(final char[] array, final char element) {
		final char[] newArray = (char[]) copyArrayGrow1(array, Character.TYPE);
		newArray[newArray.length - 1] = element;
		return newArray;
	}

	/**
	 * <p>
	 * Copies the given array and adds the given element at the end of the new
	 * array.
	 *
	 * <p>
	 * The new array contains the same elements of the input array plus the given
	 * element in the last position. The component type of the new array is the same
	 * as that of the input array.
	 *
	 * <p>
	 * If the input array is {@code null}, a new one element array is returned whose
	 * component type is the same as the element.
	 *
	 * <pre>
	 * ArrayUtils.add(null, 0)   = [0]
	 * ArrayUtils.add([1], 0)    = [1, 0]
	 * ArrayUtils.add([1, 0], 1) = [1, 0, 1]
	 * </pre>
	 *
	 * @param array   the array to copy and add the element to, may be {@code null}
	 * @param element the object to add at the last index of the new array
	 * @return A new array containing the existing elements plus the new element
	 * @since 2.1
	 */
	public static double[] add(final double[] array, final double element) {
		final double[] newArray = (double[]) copyArrayGrow1(array, Double.TYPE);
		newArray[newArray.length - 1] = element;
		return newArray;
	}

	/**
	 * <p>
	 * Copies the given array and adds the given element at the end of the new
	 * array.
	 *
	 * <p>
	 * The new array contains the same elements of the input array plus the given
	 * element in the last position. The component type of the new array is the same
	 * as that of the input array.
	 *
	 * <p>
	 * If the input array is {@code null}, a new one element array is returned whose
	 * component type is the same as the element.
	 *
	 * <pre>
	 * ArrayUtils.add(null, 0)   = [0]
	 * ArrayUtils.add([1], 0)    = [1, 0]
	 * ArrayUtils.add([1, 0], 1) = [1, 0, 1]
	 * </pre>
	 *
	 * @param array   the array to copy and add the element to, may be {@code null}
	 * @param element the object to add at the last index of the new array
	 * @return A new array containing the existing elements plus the new element
	 * @since 2.1
	 */
	public static float[] add(final float[] array, final float element) {
		final float[] newArray = (float[]) copyArrayGrow1(array, Float.TYPE);
		newArray[newArray.length - 1] = element;
		return newArray;
	}

	/**
	 * <p>
	 * Copies the given array and adds the given element at the end of the new
	 * array.
	 *
	 * <p>
	 * The new array contains the same elements of the input array plus the given
	 * element in the last position. The component type of the new array is the same
	 * as that of the input array.
	 *
	 * <p>
	 * If the input array is {@code null}, a new one element array is returned whose
	 * component type is the same as the element.
	 *
	 * <pre>
	 * ArrayUtils.add(null, 0)   = [0]
	 * ArrayUtils.add([1], 0)    = [1, 0]
	 * ArrayUtils.add([1, 0], 1) = [1, 0, 1]
	 * </pre>
	 *
	 * @param array   the array to copy and add the element to, may be {@code null}
	 * @param element the object to add at the last index of the new array
	 * @return A new array containing the existing elements plus the new element
	 * @since 2.1
	 */
	public static int[] add(final int[] array, final int element) {
		final int[] newArray = (int[]) copyArrayGrow1(array, Integer.TYPE);
		newArray[newArray.length - 1] = element;
		return newArray;
	}

	/**
	 * <p>
	 * Copies the given array and adds the given element at the end of the new
	 * array.
	 *
	 * <p>
	 * The new array contains the same elements of the input array plus the given
	 * element in the last position. The component type of the new array is the same
	 * as that of the input array.
	 *
	 * <p>
	 * If the input array is {@code null}, a new one element array is returned whose
	 * component type is the same as the element.
	 *
	 * <pre>
	 * ArrayUtils.add(null, 0)   = [0]
	 * ArrayUtils.add([1], 0)    = [1, 0]
	 * ArrayUtils.add([1, 0], 1) = [1, 0, 1]
	 * </pre>
	 *
	 * @param array   the array to copy and add the element to, may be {@code null}
	 * @param element the object to add at the last index of the new array
	 * @return A new array containing the existing elements plus the new element
	 * @since 2.1
	 */
	public static long[] add(final long[] array, final long element) {
		final long[] newArray = (long[]) copyArrayGrow1(array, Long.TYPE);
		newArray[newArray.length - 1] = element;
		return newArray;
	}

	/**
	 * <p>
	 * Copies the given array and adds the given element at the end of the new
	 * array.
	 *
	 * <p>
	 * The new array contains the same elements of the input array plus the given
	 * element in the last position. The component type of the new array is the same
	 * as that of the input array.
	 *
	 * <p>
	 * If the input array is {@code null}, a new one element array is returned whose
	 * component type is the same as the element.
	 *
	 * <pre>
	 * ArrayUtils.add(null, 0)   = [0]
	 * ArrayUtils.add([1], 0)    = [1, 0]
	 * ArrayUtils.add([1, 0], 1) = [1, 0, 1]
	 * </pre>
	 *
	 * @param array   the array to copy and add the element to, may be {@code null}
	 * @param element the object to add at the last index of the new array
	 * @return A new array containing the existing elements plus the new element
	 * @since 2.1
	 */
	public static short[] add(final short[] array, final short element) {
		final short[] newArray = (short[]) copyArrayGrow1(array, Short.TYPE);
		newArray[newArray.length - 1] = element;
		return newArray;
	}

	/**
	 * Returns a copy of the given array of size 1 greater than the argument. The
	 * last value of the array is left to the default value.
	 *
	 * @param array                 The array to copy, must not be {@code null}.
	 * @param newArrayComponentType If {@code array} is {@code null}, create a size
	 *                              1 array of this type.
	 * @return A new copy of the array of size 1 greater than the input.
	 */
	private static Object copyArrayGrow1(final Object array, final Class<?> newArrayComponentType) {
		if (array != null) {
			final int arrayLength = Array.getLength(array);
			final Object newArray = Array.newInstance(array.getClass().getComponentType(), arrayLength + 1);
			System.arraycopy(array, 0, newArray, 0, arrayLength);
			return newArray;
		}
		return Array.newInstance(newArrayComponentType, 1);
	}

	/**
	 * <p>
	 * Inserts the specified element at the specified position in the array. Shifts
	 * the element currently at that position (if any) and any subsequent elements
	 * to the right (adds one to their indices).
	 *
	 * <p>
	 * This method returns a new array with the same elements of the input array
	 * plus the given element on the specified position. The component type of the
	 * returned array is always the same as that of the input array.
	 *
	 * <p>
	 * If the input array is {@code null}, a new one element array is returned whose
	 * component type is the same as the element.
	 *
	 * <pre>
	 * ArrayUtils.add(null, 0, null)      = IllegalArgumentException
	 * ArrayUtils.add(null, 0, "a")       = ["a"]
	 * ArrayUtils.add(["a"], 1, null)     = ["a", null]
	 * ArrayUtils.add(["a"], 1, "b")      = ["a", "b"]
	 * ArrayUtils.add(["a", "b"], 3, "c") = ["a", "b", "c"]
	 * </pre>
	 *
	 * @param <T>     the component type of the array
	 * @param array   the array to add the element to, may be {@code null}
	 * @param index   the position of the new object
	 * @param element the object to add
	 * @return A new array containing the existing elements and the new element
	 * @throws IndexOutOfBoundsException if the index is out of range (index &lt; 0
	 *                                   || index &gt; array.length).
	 * @throws IllegalArgumentException  if both array and element are null
	 * @deprecated this method has been superseded by
	 *             {@link #insert(int, Object[], Object...) insert(int, T[], T...)}
	 *             and may be removed in a future release. Please note the handling
	 *             of {@code null} input arrays differs in the new method: inserting
	 *             {@code X} into a {@code null} array results in {@code null} not
	 *             {@code X}.
	 */
	@Deprecated
	public static <T> T[] add(final T[] array, final int index, final T element) {
		Class<?> clss = null;
		if (array != null) {
			clss = array.getClass().getComponentType();
		} else if (element != null) {
			clss = element.getClass();
		} else {
			throw new IllegalArgumentException("Array and element cannot both be null");
		}
		@SuppressWarnings("unchecked") // the add method creates an array of type clss, which is type T
		final T[] newArray = (T[]) add(array, index, element, clss);
		return newArray;
	}

	/**
	 * <p>
	 * Inserts the specified element at the specified position in the array. Shifts
	 * the element currently at that position (if any) and any subsequent elements
	 * to the right (adds one to their indices).
	 *
	 * <p>
	 * This method returns a new array with the same elements of the input array
	 * plus the given element on the specified position. The component type of the
	 * returned array is always the same as that of the input array.
	 *
	 * <p>
	 * If the input array is {@code null}, a new one element array is returned whose
	 * component type is the same as the element.
	 *
	 * <pre>
	 * ArrayUtils.add(null, 0, true)          = [true]
	 * ArrayUtils.add([true], 0, false)       = [false, true]
	 * ArrayUtils.add([false], 1, true)       = [false, true]
	 * ArrayUtils.add([true, false], 1, true) = [true, true, false]
	 * </pre>
	 *
	 * @param array   the array to add the element to, may be {@code null}
	 * @param index   the position of the new object
	 * @param element the object to add
	 * @return A new array containing the existing elements and the new element
	 * @throws IndexOutOfBoundsException if the index is out of range (index &lt; 0
	 *                                   || index &gt; array.length).
	 * @deprecated this method has been superseded by
	 *             {@link #insert(int, boolean[], boolean...)} and may be removed in
	 *             a future release. Please note the handling of {@code null} input
	 *             arrays differs in the new method: inserting {@code X} into a
	 *             {@code null} array results in {@code null} not {@code X}.
	 */
	@Deprecated
	public static boolean[] add(final boolean[] array, final int index, final boolean element) {
		return (boolean[]) add(array, index, Boolean.valueOf(element), Boolean.TYPE);
	}

	/**
	 * <p>
	 * Inserts the specified element at the specified position in the array. Shifts
	 * the element currently at that position (if any) and any subsequent elements
	 * to the right (adds one to their indices).
	 *
	 * <p>
	 * This method returns a new array with the same elements of the input array
	 * plus the given element on the specified position. The component type of the
	 * returned array is always the same as that of the input array.
	 *
	 * <p>
	 * If the input array is {@code null}, a new one element array is returned whose
	 * component type is the same as the element.
	 *
	 * <pre>
	 * ArrayUtils.add(null, 0, 'a')            = ['a']
	 * ArrayUtils.add(['a'], 0, 'b')           = ['b', 'a']
	 * ArrayUtils.add(['a', 'b'], 0, 'c')      = ['c', 'a', 'b']
	 * ArrayUtils.add(['a', 'b'], 1, 'k')      = ['a', 'k', 'b']
	 * ArrayUtils.add(['a', 'b', 'c'], 1, 't') = ['a', 't', 'b', 'c']
	 * </pre>
	 *
	 * @param array   the array to add the element to, may be {@code null}
	 * @param index   the position of the new object
	 * @param element the object to add
	 * @return A new array containing the existing elements and the new element
	 * @throws IndexOutOfBoundsException if the index is out of range (index &lt; 0
	 *                                   || index &gt; array.length).
	 * @deprecated this method has been superseded by
	 *             {@link #insert(int, char[], char...)} and may be removed in a
	 *             future release. Please note the handling of {@code null} input
	 *             arrays differs in the new method: inserting {@code X} into a
	 *             {@code null} array results in {@code null} not {@code X}.
	 */
	@Deprecated
	public static char[] add(final char[] array, final int index, final char element) {
		return (char[]) add(array, index, Character.valueOf(element), Character.TYPE);
	}

	/**
	 * <p>
	 * Inserts the specified element at the specified position in the array. Shifts
	 * the element currently at that position (if any) and any subsequent elements
	 * to the right (adds one to their indices).
	 *
	 * <p>
	 * This method returns a new array with the same elements of the input array
	 * plus the given element on the specified position. The component type of the
	 * returned array is always the same as that of the input array.
	 *
	 * <p>
	 * If the input array is {@code null}, a new one element array is returned whose
	 * component type is the same as the element.
	 *
	 * <pre>
	 * ArrayUtils.add([1], 0, 2)         = [2, 1]
	 * ArrayUtils.add([2, 6], 2, 3)      = [2, 6, 3]
	 * ArrayUtils.add([2, 6], 0, 1)      = [1, 2, 6]
	 * ArrayUtils.add([2, 6, 3], 2, 1)   = [2, 6, 1, 3]
	 * </pre>
	 *
	 * @param array   the array to add the element to, may be {@code null}
	 * @param index   the position of the new object
	 * @param element the object to add
	 * @return A new array containing the existing elements and the new element
	 * @throws IndexOutOfBoundsException if the index is out of range (index &lt; 0
	 *                                   || index &gt; array.length).
	 * @deprecated this method has been superseded by
	 *             {@link #insert(int, byte[], byte...)} and may be removed in a
	 *             future release. Please note the handling of {@code null} input
	 *             arrays differs in the new method: inserting {@code X} into a
	 *             {@code null} array results in {@code null} not {@code X}.
	 */
	@Deprecated
	public static byte[] add(final byte[] array, final int index, final byte element) {
		return (byte[]) add(array, index, Byte.valueOf(element), Byte.TYPE);
	}

	/**
	 * <p>
	 * Inserts the specified element at the specified position in the array. Shifts
	 * the element currently at that position (if any) and any subsequent elements
	 * to the right (adds one to their indices).
	 *
	 * <p>
	 * This method returns a new array with the same elements of the input array
	 * plus the given element on the specified position. The component type of the
	 * returned array is always the same as that of the input array.
	 *
	 * <p>
	 * If the input array is {@code null}, a new one element array is returned whose
	 * component type is the same as the element.
	 *
	 * <pre>
	 * ArrayUtils.add([1], 0, 2)         = [2, 1]
	 * ArrayUtils.add([2, 6], 2, 10)     = [2, 6, 10]
	 * ArrayUtils.add([2, 6], 0, -4)     = [-4, 2, 6]
	 * ArrayUtils.add([2, 6, 3], 2, 1)   = [2, 6, 1, 3]
	 * </pre>
	 *
	 * @param array   the array to add the element to, may be {@code null}
	 * @param index   the position of the new object
	 * @param element the object to add
	 * @return A new array containing the existing elements and the new element
	 * @throws IndexOutOfBoundsException if the index is out of range (index &lt; 0
	 *                                   || index &gt; array.length).
	 * @deprecated this method has been superseded by
	 *             {@link #insert(int, short[], short...)} and may be removed in a
	 *             future release. Please note the handling of {@code null} input
	 *             arrays differs in the new method: inserting {@code X} into a
	 *             {@code null} array results in {@code null} not {@code X}.
	 */
	@Deprecated
	public static short[] add(final short[] array, final int index, final short element) {
		return (short[]) add(array, index, Short.valueOf(element), Short.TYPE);
	}

	/**
	 * <p>
	 * Inserts the specified element at the specified position in the array. Shifts
	 * the element currently at that position (if any) and any subsequent elements
	 * to the right (adds one to their indices).
	 *
	 * <p>
	 * This method returns a new array with the same elements of the input array
	 * plus the given element on the specified position. The component type of the
	 * returned array is always the same as that of the input array.
	 *
	 * <p>
	 * If the input array is {@code null}, a new one element array is returned whose
	 * component type is the same as the element.
	 *
	 * <pre>
	 * ArrayUtils.add([1], 0, 2)         = [2, 1]
	 * ArrayUtils.add([2, 6], 2, 10)     = [2, 6, 10]
	 * ArrayUtils.add([2, 6], 0, -4)     = [-4, 2, 6]
	 * ArrayUtils.add([2, 6, 3], 2, 1)   = [2, 6, 1, 3]
	 * </pre>
	 *
	 * @param array   the array to add the element to, may be {@code null}
	 * @param index   the position of the new object
	 * @param element the object to add
	 * @return A new array containing the existing elements and the new element
	 * @throws IndexOutOfBoundsException if the index is out of range (index &lt; 0
	 *                                   || index &gt; array.length).
	 * @deprecated this method has been superseded by
	 *             {@link #insert(int, int[], int...)} and may be removed in a
	 *             future release. Please note the handling of {@code null} input
	 *             arrays differs in the new method: inserting {@code X} into a
	 *             {@code null} array results in {@code null} not {@code X}.
	 */
	@Deprecated
	public static int[] add(final int[] array, final int index, final int element) {
		return (int[]) add(array, index, Integer.valueOf(element), Integer.TYPE);
	}

	/**
	 * <p>
	 * Inserts the specified element at the specified position in the array. Shifts
	 * the element currently at that position (if any) and any subsequent elements
	 * to the right (adds one to their indices).
	 *
	 * <p>
	 * This method returns a new array with the same elements of the input array
	 * plus the given element on the specified position. The component type of the
	 * returned array is always the same as that of the input array.
	 *
	 * <p>
	 * If the input array is {@code null}, a new one element array is returned whose
	 * component type is the same as the element.
	 *
	 * <pre>
	 * ArrayUtils.add([1L], 0, 2L)           = [2L, 1L]
	 * ArrayUtils.add([2L, 6L], 2, 10L)      = [2L, 6L, 10L]
	 * ArrayUtils.add([2L, 6L], 0, -4L)      = [-4L, 2L, 6L]
	 * ArrayUtils.add([2L, 6L, 3L], 2, 1L)   = [2L, 6L, 1L, 3L]
	 * </pre>
	 *
	 * @param array   the array to add the element to, may be {@code null}
	 * @param index   the position of the new object
	 * @param element the object to add
	 * @return A new array containing the existing elements and the new element
	 * @throws IndexOutOfBoundsException if the index is out of range (index &lt; 0
	 *                                   || index &gt; array.length).
	 * @deprecated this method has been superseded by
	 *             {@link #insert(int, long[], long...)} and may be removed in a
	 *             future release. Please note the handling of {@code null} input
	 *             arrays differs in the new method: inserting {@code X} into a
	 *             {@code null} array results in {@code null} not {@code X}.
	 */
	@Deprecated
	public static long[] add(final long[] array, final int index, final long element) {
		return (long[]) add(array, index, Long.valueOf(element), Long.TYPE);
	}

	/**
	 * <p>
	 * Inserts the specified element at the specified position in the array. Shifts
	 * the element currently at that position (if any) and any subsequent elements
	 * to the right (adds one to their indices).
	 *
	 * <p>
	 * This method returns a new array with the same elements of the input array
	 * plus the given element on the specified position. The component type of the
	 * returned array is always the same as that of the input array.
	 *
	 * <p>
	 * If the input array is {@code null}, a new one element array is returned whose
	 * component type is the same as the element.
	 *
	 * <pre>
	 * ArrayUtils.add([1.1f], 0, 2.2f)               = [2.2f, 1.1f]
	 * ArrayUtils.add([2.3f, 6.4f], 2, 10.5f)        = [2.3f, 6.4f, 10.5f]
	 * ArrayUtils.add([2.6f, 6.7f], 0, -4.8f)        = [-4.8f, 2.6f, 6.7f]
	 * ArrayUtils.add([2.9f, 6.0f, 0.3f], 2, 1.0f)   = [2.9f, 6.0f, 1.0f, 0.3f]
	 * </pre>
	 *
	 * @param array   the array to add the element to, may be {@code null}
	 * @param index   the position of the new object
	 * @param element the object to add
	 * @return A new array containing the existing elements and the new element
	 * @throws IndexOutOfBoundsException if the index is out of range (index &lt; 0
	 *                                   || index &gt; array.length).
	 * @deprecated this method has been superseded by
	 *             {@link #insert(int, float[], float...)} and may be removed in a
	 *             future release. Please note the handling of {@code null} input
	 *             arrays differs in the new method: inserting {@code X} into a
	 *             {@code null} array results in {@code null} not {@code X}.
	 */
	@Deprecated
	public static float[] add(final float[] array, final int index, final float element) {
		return (float[]) add(array, index, Float.valueOf(element), Float.TYPE);
	}

	/**
	 * <p>
	 * Inserts the specified element at the specified position in the array. Shifts
	 * the element currently at that position (if any) and any subsequent elements
	 * to the right (adds one to their indices).
	 *
	 * <p>
	 * This method returns a new array with the same elements of the input array
	 * plus the given element on the specified position. The component type of the
	 * returned array is always the same as that of the input array.
	 *
	 * <p>
	 * If the input array is {@code null}, a new one element array is returned whose
	 * component type is the same as the element.
	 *
	 * <pre>
	 * ArrayUtils.add([1.1], 0, 2.2)              = [2.2, 1.1]
	 * ArrayUtils.add([2.3, 6.4], 2, 10.5)        = [2.3, 6.4, 10.5]
	 * ArrayUtils.add([2.6, 6.7], 0, -4.8)        = [-4.8, 2.6, 6.7]
	 * ArrayUtils.add([2.9, 6.0, 0.3], 2, 1.0)    = [2.9, 6.0, 1.0, 0.3]
	 * </pre>
	 *
	 * @param array   the array to add the element to, may be {@code null}
	 * @param index   the position of the new object
	 * @param element the object to add
	 * @return A new array containing the existing elements and the new element
	 * @throws IndexOutOfBoundsException if the index is out of range (index &lt; 0
	 *                                   || index &gt; array.length).
	 * @deprecated this method has been superseded by
	 *             {@link #insert(int, double[], double...)} and may be removed in a
	 *             future release. Please note the handling of {@code null} input
	 *             arrays differs in the new method: inserting {@code X} into a
	 *             {@code null} array results in {@code null} not {@code X}.
	 */
	@Deprecated
	public static double[] add(final double[] array, final int index, final double element) {
		return (double[]) add(array, index, Double.valueOf(element), Double.TYPE);
	}

	/**
	 * Underlying implementation of add(array, index, element) methods. The last
	 * parameter is the class, which may not equal element.getClass for primitives.
	 *
	 * @param array   the array to add the element to, may be {@code null}
	 * @param index   the position of the new object
	 * @param element the object to add
	 * @param clss    the type of the element being added
	 * @return A new array containing the existing elements and the new element
	 */
	private static Object add(final Object array, final int index, final Object element, final Class<?> clss) {
		if (array == null) {
			if (index != 0) {
				throw new IndexOutOfBoundsException("Index: " + index + ", Length: 0");
			}
			final Object joinedArray = Array.newInstance(clss, 1);
			Array.set(joinedArray, 0, element);
			return joinedArray;
		}
		final int length = Array.getLength(array);
		if (index > length || index < 0) {
			throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + length);
		}
		final Object result = Array.newInstance(clss, length + 1);
		System.arraycopy(array, 0, result, 0, index);
		Array.set(result, index, element);
		if (index < length) {
			System.arraycopy(array, index, result, index + 1, length - index);
		}
		return result;
	}
	
	/**
	 * 根据索引获取数组值
	 * @param <T>
	 * @param array
	 * @param index
	 * @return
	 */
	public static <T> T get(T[] array, int index) {
		if(isEmpty(array)) {
			return null;
		}
		return (index > -1 && index < array.length) ? array[index] : null;
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
		return first(array, null);
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
		if (isEmpty(array)) {
			return null;
		}
		if (predicate == null) {
			return array[0];
		}
		for (T item : array) {
			if (predicate.test(item)) {
				return item;
			}
		}
		return null;
	}
	
	/**
	 * 遍历数组
	 * @param <T>
	 * @param array
	 * @param consumer
	 */
	public static <T> void each(T[] array, Consumer<T> consumer) {
		if(isEmpty(array)) {
			return;
		}
		for(T item : array) {
			consumer.accept(item);
		}
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
		List<T> result = new ArrayList<>();
		if(isEmpty(array)) {
			return result;
		}
		for(T item : array) {
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
	 * 数组任意匹配
	 * @param <T>
	 * @param array
	 * @param predicates
	 * @return
	 */
	@SafeVarargs
	public static <T> boolean anyMatch(T[] array, Predicate<T>... predicates) {
		if(isEmpty(array)) {
			return false;
		}
		for(T item : array) {
			for(Predicate<T> predicate : predicates) {
				if(predicate.test(item)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 数组转换为List<T>
	 * @param <T> 输入类型
	 * @param items
	 * @return
	 */
	@SafeVarargs
	public static <T> List<T> toList(T... items) {
		return toList(items, x -> x);
	}
	
	/**
	 * 数组转换为List<T>
	 * @param <T>
	 * @param <R>
	 * @param items
	 * @param func
	 * @return
	 */
	public static <T, R> List<R> toList(T[] items, Function<T, R> func) {
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
	 * List转数组
	 * @param <T>
	 * @param list
	 * @param clazz 泛型类型
	 * @return
	 */
	public static <T> T[] toArray(List<T> list, Class<T> clazz) {
		T[] array = newArray(clazz, list != null ? list.size() : 0);
		if(list != null) {
			list.toArray(array);
		}
		return array;
	}

	/**
	 * 数组或集合转String
	 *
	 * @param obj 集合或数组对象
	 * @return 数组字符串，与集合转字符串格式相同
	 */
	public static String toString(Object obj) {
		if (null == obj) {
			return null;
		}
		if (!ArrayUtils.isArray(obj)) {
			return obj.toString();
		}
		if (obj instanceof long[]) {
			return Arrays.toString((long[]) obj);
		} else if (obj instanceof int[]) {
			return Arrays.toString((int[]) obj);
		} else if (obj instanceof short[]) {
			return Arrays.toString((short[]) obj);
		} else if (obj instanceof char[]) {
			return Arrays.toString((char[]) obj);
		} else if (obj instanceof byte[]) {
			return Arrays.toString((byte[]) obj);
		} else if (obj instanceof boolean[]) {
			return Arrays.toString((boolean[]) obj);
		} else if (obj instanceof float[]) {
			return Arrays.toString((float[]) obj);
		} else if (obj instanceof double[]) {
			return Arrays.toString((double[]) obj);
		} else if (ArrayUtils.isArray(obj)) {
			// 对象数组
			try {
				return Arrays.deepToString((Object[]) obj);
			} catch (Exception ignore) {
				LogUtils.warn(ArrayUtils.class, ignore.getMessage(), ignore);
			}
		}
		return obj.toString();
	}

	public static void main(String[] args) {
		
	}
}
