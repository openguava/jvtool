package io.github.openguava.jvtool.spring.context;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

/**
 * AttributeContextHolder
 * @author openguava
 *
 */
public class AttributeContextHolder {

	public static void setAttribute(String name, Object value) {
		RequestContextHolder.currentRequestAttributes().setAttribute(name, value, RequestAttributes.SCOPE_REQUEST);
	}

	public static Object getAttribute(String name) {
		return RequestContextHolder.currentRequestAttributes().getAttribute(name, RequestAttributes.SCOPE_REQUEST);
	}
}