package io.github.openguava.jvtool.lang.auth.exception;

import io.github.openguava.jvtool.lang.exception.AuthException;
import io.github.openguava.jvtool.lang.result.IResult;

/**
 * 权限认证异常
 * @author openguava
 *
 */
public class PermissionAuthException extends AuthException {

	private static final long serialVersionUID = 1L;

	public PermissionAuthException(String message) {
    	super(IResult.STATUS_CODE_FORBIDDEN, message);
    }
    
    public PermissionAuthException(Throwable throwable) {
    	super(IResult.STATUS_CODE_FORBIDDEN, throwable);
    }
    
    public PermissionAuthException(String message, Throwable throwable) {
    	super(IResult.STATUS_CODE_FORBIDDEN, message, throwable);
    }
    
    public PermissionAuthException(Integer code, String message, Throwable throwable) {
    	super(code, message, throwable);
    }
}
