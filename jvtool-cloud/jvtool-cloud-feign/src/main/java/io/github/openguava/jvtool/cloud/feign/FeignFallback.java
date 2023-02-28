package io.github.openguava.jvtool.cloud.feign;

import feign.RetryableException;
import feign.codec.DecodeException;

/**
 * feign 回调
 * @author openguava
 *
 */
public class FeignFallback {

	private Throwable throwable;

	public Throwable getThrowable() {
		return this.throwable;
	}

	public void setThrowable(Throwable throwable) {
		this.throwable = throwable;
	}
	
	public String getThrowableMessage() {
		if (this.getThrowable() == null) {
			return "";
		}
		String message = this.getThrowable().getMessage();
		if (this.getThrowable() instanceof RetryableException) {
			return "请求失败：" + this.getThrowable().getMessage();
		}
		if (this.getThrowable() instanceof DecodeException) {
			return "解码失败：" + this.getThrowable().getMessage();
		}
		if (message.contains("does not have available server for client")) {
			return "请求失败：未找到可用的服务！";
		}
		return "网络错误：" + this.getThrowable().getMessage();
	}
	
	public FeignFallback() {

	}

	public FeignFallback(Throwable throwable) {
		this.throwable = throwable;
	}
}
