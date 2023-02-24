package io.github.openguava.jvtool.lang.auth.exception;

import io.github.openguava.jvtool.lang.exception.AuthException;
import io.github.openguava.jvtool.lang.result.IResult;

/**
 * 内部认证异常
 * @author openguava
 *
 */
public class InnerAuthException extends AuthException {

	private static final long serialVersionUID = 1L;

    public InnerAuthException(String message) {
    	super(IResult.STATUS_CODE_ERROR, message);
    }
    
    public InnerAuthException(Throwable throwable) {
    	super(IResult.STATUS_CODE_ERROR, throwable);
    }
    
    public InnerAuthException(String message, Throwable throwable) {
    	super(IResult.STATUS_CODE_ERROR, message, throwable);
    }
    
    public InnerAuthException(Integer code, String message, Throwable throwable) {
    	super(code, message, throwable);
    }
}
