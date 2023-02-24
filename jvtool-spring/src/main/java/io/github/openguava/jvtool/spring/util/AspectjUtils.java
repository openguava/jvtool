package io.github.openguava.jvtool.spring.util;

import java.lang.annotation.Annotation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;

/**
 * aspectj 工具类
 * @author Administrator
 *
 */
public class AspectjUtils {

	protected AspectjUtils() {
		
	}
	
	/**
	 * 获取切入点函数签名 
	 * @param joinPoint
	 * @return
	 */
	public static MethodSignature getMethodSignature(JoinPoint joinPoint) {
		if(joinPoint == null) {
			return null;
		}
		return (MethodSignature)joinPoint.getSignature();
	}
	
	/**
	 * 获取函数注解
	 * @param <T>
	 * @param methodSignature 函数签名
	 * @param annotationType 注解类型
	 * @return
	 */
	public <T extends Annotation> T getMethodAnnotation(MethodSignature methodSignature, Class<T> annotationType) {
		if(methodSignature == null) {
			return null;
		}
		return AnnotationUtils.findAnnotation(methodSignature.getMethod(), annotationType);
	}
	
	/**
	 * 获取函数声明类型注解
	 * @param <T>
	 * @param methodSignature 函数签名
	 * @param annotationType 注解类型
	 * @return
	 */
	public <T extends Annotation> T getMethodDeclaringTypeAnnotation(MethodSignature methodSignature, Class<T> annotationType) {
		if(methodSignature == null) {
			return null;
		}
		return AnnotationUtils.findAnnotation(methodSignature.getDeclaringType(), annotationType);
	}
}
