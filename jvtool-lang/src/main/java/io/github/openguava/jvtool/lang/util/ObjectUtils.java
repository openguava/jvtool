package io.github.openguava.jvtool.lang.util;

import java.lang.reflect.Field;
import java.util.function.Function;
import java.util.function.Supplier;

import io.github.openguava.jvtool.lang.SFunction;

/**
 * object 工具类
 * @author openguava
 *
 */
public class ObjectUtils {
	
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
}
