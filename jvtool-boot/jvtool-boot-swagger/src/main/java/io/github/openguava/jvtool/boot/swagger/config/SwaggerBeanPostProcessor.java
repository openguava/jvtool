package io.github.openguava.jvtool.boot.swagger.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;

import io.github.openguava.jvtool.lang.util.LogUtils;
import io.github.openguava.jvtool.lang.util.ReflectUtils;
import springfox.documentation.spring.web.plugins.WebFluxRequestHandlerProvider;
import springfox.documentation.spring.web.plugins.WebMvcRequestHandlerProvider;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

/**
 * swagger 在 springboot 2.6.x 不兼容问题的处理
 *
 * @author openguava
 */
@Component
public class SwaggerBeanPostProcessor implements BeanPostProcessor {
	
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if (bean instanceof WebMvcRequestHandlerProvider || bean instanceof WebFluxRequestHandlerProvider) {
			customizeSpringfoxHandlerMappings(getHandlerMappings(bean));
		}
		return bean;
	}

	private <T extends RequestMappingInfoHandlerMapping> void customizeSpringfoxHandlerMappings(List<T> mappings) {
		List<T> copy = mappings.stream().filter(mapping -> !hasPatternParser(mapping))
				.collect(Collectors.toList());
		mappings.clear();
		mappings.addAll(copy);
	}

	@SuppressWarnings("unchecked")
	private List<RequestMappingInfoHandlerMapping> getHandlerMappings(Object bean) {
		try {
			Field field = ReflectionUtils.findField(bean.getClass(), "handlerMappings");
			field.setAccessible(true);
			return (List<RequestMappingInfoHandlerMapping>) field.get(bean);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new IllegalStateException(e);
		}
	}
	
	/**
	 * PatternParser
	 * spring 5.3.x
	 * @param <T>
	 * @param mapping
	 * @return
	 */
	private <T extends RequestMappingInfoHandlerMapping> boolean hasPatternParser(T mapping) {
		return mapping.getPatternParser() != null;
		/*
		Method method = ReflectUtils.getMethodByName(mapping.getClass(), false, "getPatternParser");
		if(method == null) {
			return false;
		}
		try {
			Object patternParser = ReflectUtils.invokeMethod(method, mapping, new Object[] {});
			return patternParser != null;
		} catch (Exception e) {
			LogUtils.error(SwaggerBeanPostProcessor.class, e.getMessage(), e);
			return false;
		}
		*/
	}
}
