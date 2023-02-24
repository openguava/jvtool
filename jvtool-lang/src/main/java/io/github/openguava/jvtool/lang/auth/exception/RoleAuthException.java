package io.github.openguava.jvtool.lang.auth.exception;

import io.github.openguava.jvtool.lang.exception.AuthException;
import io.github.openguava.jvtool.lang.result.IResult;

/**
 * 角色认证异常
 * @author openguava
 *
 */
public class RoleAuthException extends AuthException {

	private static final long serialVersionUID = 1L;

    public RoleAuthException(String message) {
    	super(IResult.STATUS_CODE_FORBIDDEN, message);
    }
    
    public RoleAuthException(Throwable throwable) {
    	super(IResult.STATUS_CODE_FORBIDDEN, throwable);
    }
    
    public RoleAuthException(String message, Throwable throwable) {
    	super(IResult.STATUS_CODE_FORBIDDEN, message, throwable);
    }
    
    public RoleAuthException(Integer code, String message, Throwable throwable) {
    	super(code, message, throwable);
    }
}
