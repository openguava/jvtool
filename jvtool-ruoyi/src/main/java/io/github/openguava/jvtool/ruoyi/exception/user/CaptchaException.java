package io.github.openguava.jvtool.ruoyi.exception.user;

/**
 * 验证码错误异常类
 * 
 * @author ruoyi
 */
public class CaptchaException extends UserException {
	
	private static final long serialVersionUID = 1L;

	public CaptchaException() {
		super("user.jcaptcha.error", null);
	}

	public CaptchaException(String msg) {
		super("user.jcaptcha.error", null, msg);
	}
}
