package io.github.openguava.jvtool.lang.auth.exception;

import io.github.openguava.jvtool.lang.exception.AuthException;
import io.github.openguava.jvtool.lang.result.IResult;

/**
 * api认证异常
 * @author openguava
 *
 */
public class ApiAuthException extends AuthException {

	private static final long serialVersionUID = 1L;

    public ApiAuthException(String message) {
    	super(IResult.STATUS_CODE_ERROR, message);
    }
    
    public ApiAuthException(Throwable throwable) {
    	super(IResult.STATUS_CODE_ERROR, throwable);
    }
    
    public ApiAuthException(String message, Throwable throwable) {
    	super(IResult.STATUS_CODE_ERROR, message, throwable);
    }
    
    public ApiAuthException(Integer code, String message, Throwable throwable) {
    	super(code, message, throwable);
    }
}
