package io.github.openguava.jvtool.lang.servlet.repeat;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import io.github.openguava.jvtool.lang.constant.HttpConstants;
import io.github.openguava.jvtool.lang.util.StringUtils;

/**
 * Repeatable 过滤器
 * 
 * @author openguava
 */
public class RepeatableFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		ServletRequest requestWrapper = null;
		if (request instanceof HttpServletRequest
				// MediaType.APPLICATION_JSON_VALUE
				&& StringUtils.startsWithIgnoreCase(request.getContentType(), HttpConstants.HTTP_CONTENTTYPE_APPLICATION_JSON)) {
			requestWrapper = new RepeatedlyRequestWrapper((HttpServletRequest) request, response);
		}
		if (requestWrapper == null) {
			chain.doFilter(request, response);
		} else {
			chain.doFilter(requestWrapper, response);
		}
	}

}
