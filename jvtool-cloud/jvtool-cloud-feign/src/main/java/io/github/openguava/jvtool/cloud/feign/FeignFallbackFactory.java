package io.github.openguava.jvtool.cloud.feign;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import io.github.openguava.jvtool.lang.util.LogUtils;

/**
 * feign 回调工厂
 * @author openguava
 *
 * @param <T>
 * @param <I>
 */
public class FeignFallbackFactory<T, I extends T> implements org.springframework.cloud.openfeign.FallbackFactory<T> {

	private Class<I> fallbackClass;
	
	@SuppressWarnings("unchecked")
	public Class<I> getFallbackClass() {
		if(this.fallbackClass == null) {
			// 泛型参数判断
			if(this.getClass().getGenericSuperclass() instanceof ParameterizedType) {
				ParameterizedType paramType = (ParameterizedType) this.getClass().getGenericSuperclass();
				Type argType = paramType.getActualTypeArguments().length > 1 ? paramType.getActualTypeArguments()[1] : null;
				if (argType instanceof Class) {
					this.fallbackClass = (Class<I>)argType;
				}
			}
		}
		return fallbackClass;
	}
	
	public FeignFallbackFactory() {
		
	}
	
	public FeignFallbackFactory(Class<I> clazz) {
		this.fallbackClass = clazz;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T create(Throwable cause) {
		I fallback = null;

		// 获取 fallbackClass 构造函数
		Constructor<?>[] constructors = this.getFallbackClass().getConstructors();
		if(constructors == null || constructors.length == 0) {
			return null;
		}
		for(Constructor<?> constructor : constructors) {
			try {
				if(constructor.getParameterCount() == 0) {
					fallback = (I)constructor.newInstance();
				} else if(constructor.getParameterCount() == 1) {
					fallback = (I)constructor.newInstance(cause);
				}
			} catch (Exception e) {
				LogUtils.warn(FeignFallbackFactory.class, e.getMessage(), e);
			}
		}
		if(fallback == null) {
			return null;
		}
		
		// throwable
		if(fallback instanceof FeignFallback) {
			((FeignFallback)fallback).setThrowable(cause);
		}
		return (T)fallback;
	}
}
