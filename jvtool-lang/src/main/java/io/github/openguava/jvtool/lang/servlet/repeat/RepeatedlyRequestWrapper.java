package io.github.openguava.jvtool.lang.servlet.repeat;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import io.github.openguava.jvtool.lang.constant.CharsetConstants;
import io.github.openguava.jvtool.lang.util.ServletUtils;

/**
 * 构建可重复读取 inputStream 的 request 封装
 * 
 * @author openguava
 */
public class RepeatedlyRequestWrapper extends HttpServletRequestWrapper {

	private final byte[] body;

	public byte[] getBody() {
		return this.body;
	}

	public RepeatedlyRequestWrapper(HttpServletRequest request, ServletResponse response) throws IOException {
		super(request);
		request.setCharacterEncoding(CharsetConstants.UTF_8);
		response.setCharacterEncoding(CharsetConstants.UTF_8);

		this.body = ServletUtils.getRequestBody(request);
	}

	@Override
	public BufferedReader getReader() throws IOException {
		return new BufferedReader(new InputStreamReader(this.getInputStream()));
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		final ByteArrayInputStream bais = new ByteArrayInputStream(this.body);
		return new ServletInputStream() {
			@Override
			public int read() throws IOException {
				return bais.read();
			}

			@Override
			public int available() throws IOException {
				return body.length;
			}

			@Override
			public boolean isFinished() {
				return false;
			}

			@Override
			public boolean isReady() {
				return false;
			}

			@Override
			public void setReadListener(ReadListener readListener) {

			}
		};
	}
}
