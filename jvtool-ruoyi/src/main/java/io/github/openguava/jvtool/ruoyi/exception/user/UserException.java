package io.github.openguava.jvtool.ruoyi.exception.user;

import io.github.openguava.jvtool.ruoyi.exception.base.BaseException;

/**
 * 用户信息异常类
 * 
 * @author ruoyi
 */
public class UserException extends BaseException {
	
	private static final long serialVersionUID = 1L;

	public UserException(String code, Object[] args) {
		super("user", code, args, null);
	}
	
	public UserException(String code, Object[] args, String defaultMessage) {
		super("user", code, args, defaultMessage);
	}
}
