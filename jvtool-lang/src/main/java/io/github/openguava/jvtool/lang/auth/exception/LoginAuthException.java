package io.github.openguava.jvtool.lang.auth.exception;

import io.github.openguava.jvtool.lang.exception.AuthException;
import io.github.openguava.jvtool.lang.result.IResult;

/**
 * 登录认证异常
 * @author openguava
 *
 */
public class LoginAuthException extends AuthException {

	private static final long serialVersionUID = 1L;

    public LoginAuthException(String message) {
    	super(IResult.STATUS_CODE_UNAUTHORIZED, message);
    }
    
    public LoginAuthException(Throwable throwable) {
    	super(IResult.STATUS_CODE_UNAUTHORIZED, throwable);
    }
    
    public LoginAuthException(String message, Throwable throwable) {
    	super(IResult.STATUS_CODE_UNAUTHORIZED, message, throwable);
    }
    
    public LoginAuthException(Integer code, String message, Throwable throwable) {
    	super(code, message, throwable);
    }
}
