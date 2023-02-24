package io.github.openguava.jvtool.lang.exception;

/**
 * 认证异常
 * @author openguava
 *
 */
public class AuthException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final Integer code;
	
	public Integer getCode() {
		return this.code;
	}
    
    @Override
    public String getMessage() {
    	return super.getMessage();
    }
    
    public AuthException(Integer code, String message) {
    	super(message);
    	this.code = code;
    }
    
    public AuthException(Integer code, Throwable throwable) {
    	super(throwable);
    	this.code = code;
    }
    
    public AuthException(Integer code, String message, Throwable throwable) {
    	super(message, throwable);
    	this.code = code;
    }
}
