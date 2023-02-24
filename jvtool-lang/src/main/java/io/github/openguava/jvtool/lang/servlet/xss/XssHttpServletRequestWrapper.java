package io.github.openguava.jvtool.lang.servlet.xss;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import io.github.openguava.jvtool.lang.constant.CharsetConstants;
import io.github.openguava.jvtool.lang.constant.HttpConstants;
import io.github.openguava.jvtool.lang.http.HtmlFilter;
import io.github.openguava.jvtool.lang.util.CharsetUtils;
import io.github.openguava.jvtool.lang.util.IoUtils;
import io.github.openguava.jvtool.lang.util.StringUtils;

/**
 * xss 过滤处理 request 封装
 * 
 * @author openguava
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

	public XssHttpServletRequestWrapper(HttpServletRequest request) {
		super(request);
	}

	@Override
	public String[] getParameterValues(String name) {
		String[] values = super.getParameterValues(name);
		if(values == null) {
			return super.getParameterValues(name);
		}
		String[] escapesValues = new String[values.length];
		for (int i = 0; i < values.length; i++) {
			// 防xss攻击和过滤前后空格
			escapesValues[i] = new HtmlFilter().filter(values[i]).trim();
		}
		return escapesValues;
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		// 非json类型，直接返回
		if (!isJsonRequest()) {
			return super.getInputStream();
		}

		// 为空，直接返回
		String json = IoUtils.toString(super.getInputStream(), CharsetConstants.CHARSET_DEFAULT_NAME);
		if (StringUtils.isEmpty(json)) {
			return super.getInputStream();
		}

		// xss过滤
		json = new HtmlFilter().filter(json).trim();
		byte[] jsonBytes = json.getBytes(CharsetConstants.CHARSET_DEFAULT);
		final ByteArrayInputStream bis = new ByteArrayInputStream(jsonBytes);
		return new ServletInputStream() {
			@Override
			public boolean isFinished() {
				return true;
			}

			@Override
			public boolean isReady() {
				return true;
			}

			@Override
			public int available() throws IOException {
				return jsonBytes.length;
			}

			@Override
			public void setReadListener(ReadListener readListener) {
			}

			@Override
			public int read() throws IOException {
				return bis.read();
			}
		};
	}

	/**
	 * 是否是json请求
	 * 
	 * @param request
	 */
	public boolean isJsonRequest() {
		String header = super.getHeader(HttpConstants.HTTP_HEADER_CONTENT_TYPE);
		return StringUtils.startWithIgnoreCase(header, HttpConstants.HTTP_CONTENTTYPE_APPLICATION_JSON);
	}
}
