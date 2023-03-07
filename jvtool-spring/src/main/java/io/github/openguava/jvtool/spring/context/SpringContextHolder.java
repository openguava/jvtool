package io.github.openguava.jvtool.spring.context;

import java.util.Locale;

import javax.servlet.ServletContext;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;

import io.github.openguava.jvtool.lang.util.LogUtils;
import io.github.openguava.jvtool.lang.util.ServletUtils;
import io.github.openguava.jvtool.spring.bean.condition.ConditionalOnMissBean;

@Component
@ConditionalOnMissBean
public class SpringContextHolder implements BeanFactoryPostProcessor, ApplicationContextAware {
	
	/** spring 上下文环境  */
	private static volatile ApplicationContext applicationContext;
	
	/**
	 * 获取 spring 上下文环境
	 * @return
	 */
	public static ApplicationContext getApplicationContext() {
		if(SpringContextHolder.applicationContext == null) {
			synchronized (SpringContextHolder.class) {
				if(SpringContextHolder.applicationContext == null) {
					ServletContext sc = SpringContextHolder.getServletContext();
					if(sc != null) {
		    			SpringContextHolder.applicationContext = WebApplicationContextUtils.getWebApplicationContext(sc);
		    		} else {
		    			SpringContextHolder.applicationContext = ContextLoader.getCurrentWebApplicationContext();
		    		}
				}
			}
		}
		return SpringContextHolder.applicationContext;
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		synchronized (SpringContextHolder.class) {
			init(applicationContext);
		}
	}
	
	/** servlet 上下文环境 */
	private static volatile ServletContext servletContext;
	
	/**
	 * 获取 servlet 上下文环境
	 * @return
	 */
	public static ServletContext getServletContext() {
		if(SpringContextHolder.servletContext == null) {
			synchronized (SpringContextHolder.class) {
				if(SpringContextHolder.servletContext == null) {
					SpringContextHolder.servletContext = ServletUtils.getRequestServletContext(null);
				}
			}
		}
		return SpringContextHolder.servletContext;
	}
	
	public static void setServletContext(ServletContext servletContext) {
		synchronized (SpringContextHolder.class) {
			SpringContextHolder.servletContext = servletContext;
		}
	}
	
	/** spring 环境 */
	private static Environment environment;
	
	/**
	 * 获取 spring 环境
	 * @return
	 */
	public static Environment getEnvironment() {
		if(SpringContextHolder.environment == null) {
			synchronized (SpringContextHolder.class) {
				if(SpringContextHolder.environment == null) {
					SpringContextHolder.environment = getApplicationContext().getEnvironment();
				}
			}
		}
		return SpringContextHolder.environment;
	}
	
	public static void setEnvironment(Environment environment) {
		synchronized (SpringContextHolder.class) {
			SpringContextHolder.environment = environment;
		}
	}
	
	private static MessageSourceAccessor messageSourceAccessor;
	
	public static MessageSourceAccessor getMessageSourceAccessor() {
		if(SpringContextHolder.messageSourceAccessor == null) {
			synchronized (SpringContextHolder.class) {
				SpringContextHolder.messageSourceAccessor = new MessageSourceAccessor(getApplicationContext());
			}
		}
		return SpringContextHolder.messageSourceAccessor;
	}
	
	public static void setMessageSourceAccessor(MessageSourceAccessor messageSourceAccessor) {
		synchronized (SpringContextHolder.class) {
			SpringContextHolder.messageSourceAccessor = messageSourceAccessor;
		}
	}
	
	/** spring bean工厂 */
	private static ConfigurableListableBeanFactory beanFactory;
	
	public static ConfigurableListableBeanFactory getBeanFactory() {
		return SpringContextHolder.beanFactory;
	}

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		// beanFactory
		SpringContextHolder.beanFactory = beanFactory;
		
		// ServletUtils
		ServletUtils.setRequestSupplier(() -> getServletRequestAttributes().getRequest());
		ServletUtils.setResponseSupplier(() -> getServletRequestAttributes().getResponse());
		
		LogUtils.info(SpringContextHolder.class, "----------  {} Initializing ----------", this.getClass().getSimpleName());
	}
	
	/**
	 * 初始化
	 * @param applicationContext
	 */
	private static void init(ApplicationContext applicationContext) {
		SpringContextHolder.applicationContext = applicationContext;
		SpringContextHolder.environment = applicationContext.getEnvironment();
		if (applicationContext instanceof WebApplicationContext) {
			SpringContextHolder.servletContext = ((WebApplicationContext)applicationContext).getServletContext();
		}
		SpringContextHolder.messageSourceAccessor = new MessageSourceAccessor(applicationContext, Locale.SIMPLIFIED_CHINESE);
	}
	
	/**
	 * 获取当前 servlet 请求属性
	 * @return
	 */
	public static ServletRequestAttributes getServletRequestAttributes() {
		try {
			return (ServletRequestAttributes)RequestContextHolder.currentRequestAttributes();
		} catch (Exception e) {
			LogUtils.error(SpringContextHolder.class, e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * 获取spring 环境 properties的值，没有获取到返回null
	 * @param key 该key对应的value值
	 * @param defaultValue 默认值
	 * @return
	 */
    public static String getEnvironmentProperty(String key, String defaultValue) {
    	return getEnvironment().getProperty(key, defaultValue);
    }
}
