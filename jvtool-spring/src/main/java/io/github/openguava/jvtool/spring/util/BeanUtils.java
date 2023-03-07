package io.github.openguava.jvtool.spring.util;

import java.util.Map;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ConfigurableApplicationContext;

import io.github.openguava.jvtool.lang.util.ArrayUtils;
import io.github.openguava.jvtool.spring.context.SpringContextHolder;

/**
 * bean 工具类
 * @author openguava
 *
 */
public class BeanUtils {
	
	protected BeanUtils() {
		
	}
	
	/**
     * 获取 bean 对象
     * @param name bean名称
     * @return
     */
    public static Object getBean(String name) {
    	return SpringContextHolder.getApplicationContext().getBean(name);
    }
    
    /**
     * 获取 bean 对象
     * @param <T>
     * @param name
     * @param requiredType
     * @return
     */
    public static <T> T getBean(String name, Class<T> requiredType) {
    	return SpringContextHolder.getApplicationContext().getBean(name, requiredType);
    }
    
	/**
	 * 获取 bean 对象
	 * @param requiredType bean类型
	 * @param args bean构造参数
	 * @return
	 */
	public static <T> T getBean(Class<T> requiredType, Object... args) {
		return SpringContextHolder.getApplicationContext().getBean(requiredType, args);
	}
	
	/**
	 * 根据类型获取 Bean的实例
	 * @param <T>
	 * @param type bean类型
	 * @return
	 */
	public static <T> Map<String, T> getBeansOfType(Class<T> type) {
		return getBeansOfType(type, true, false);
	}
	
	/**
	 * 根据类型获取 Bean的实例
	 * @param <T>
	 * @param type bean类型
	 * @param includeNonSingletons 是否为非单例
	 * @param allowEagerInit 是否为懒加载
	 * @return
	 */
	public static <T> Map<String, T> getBeansOfType(Class<T> type, boolean includeNonSingletons, boolean allowEagerInit) {
		return SpringContextHolder.getApplicationContext().getBeansOfType(type, includeNonSingletons, allowEagerInit);
	}
	
	/**
	 *  根据类型获取 Bean名称
	 * @param type bean类型
	 * @return
	 */
	public static String[] getBeanNamesForType(Class<?> type) {
		return getBeanNamesForType(type, true, false);
	}
	
	/**
	 * 根据类型获取 Bean名称
	 * @param type bean类型
	 * @param includeNonSingletons 是否为非单例
	 * @param allowEagerInit 是否为懒加载
	 * @return
	 */
	public static String[] getBeanNamesForType(Class<?> type, boolean includeNonSingletons, boolean allowEagerInit) {
		return SpringContextHolder.getApplicationContext().getBeanNamesForType(type, includeNonSingletons, allowEagerInit);
	}
	
	/**
	 * 判断是否包含与所给名称匹配的bean定义，是则返回true
	 *
	 * @param name bean名称
	 * @return 
	 */
	public static boolean containsBean(String name) {
		return SpringContextHolder.getApplicationContext().containsBean(name);
	}
	
	/**
	 * 动态注册bean
	 * @param <T>
	 * @param clazz bean类型
	 * @param args bean构造参数
	 * @return
	 */
	public static <T> T registerBean(Class<T> clazz, Object... args) {
		return registerBean(clazz.getName(), clazz, args);
	}
	
	/**
	 * 动态注册 bean
	 * @param <T>
	 * @param name bean名称
	 * @param type bean类型
	 * @param args bean构造参数
	 * @return
	 */
	public static <T> T registerBean(String name, Class<T> type, Object... args) {
		ConfigurableApplicationContext applicationContext = (ConfigurableApplicationContext)SpringContextHolder.getApplicationContext();
		return registerBean(applicationContext, name, type, args);
		
	}
	/**
	 * 动态注册 bean
	 * @param <T>
	 * @param applicationContext
	 * @param name bean名称
	 * @param type bean类型
	 * @param args bean构造参数
	 * @return
	 */
	public static <T> T registerBean(ConfigurableApplicationContext applicationContext, String name, Class<T> type, Object... args) {
		// beanDefinitionBuilder
		BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(type);
		// args
		ArrayUtils.each(args, x -> beanDefinitionBuilder.addConstructorArgValue(x));
		// beanDefinition
		BeanDefinition beanDefinition = beanDefinitionBuilder.getRawBeanDefinition();
		// beanDefinitionRegistry
		BeanDefinitionRegistry beanDefinitionRegistry = (BeanDefinitionRegistry)applicationContext.getBeanFactory();
		beanDefinitionRegistry.registerBeanDefinition(name, beanDefinition);
		return applicationContext.getBean(name, type);
	}

	/**
	 * 拷贝属性
	 * @param source 源对象
	 * @param target 目标对象
	 * @param ignoreProperties 忽略的属性
	 */
	public static void copyProperties(Object source, Object target, String... ignoreProperties) {
		org.springframework.beans.BeanUtils.copyProperties(source, target, ignoreProperties);
	}
}
