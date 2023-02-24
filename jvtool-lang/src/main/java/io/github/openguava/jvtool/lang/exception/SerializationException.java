package io.github.openguava.jvtool.lang.exception;

/**
 * 序列化异常
 * @author openguava
 *
 */
public class SerializationException extends RuntimeException {

	private static final long serialVersionUID = -1L;
	
    public SerializationException() {
        super();
    }
    
	public SerializationException(String msg) {
        super(msg);
    }
	
    public SerializationException(final Throwable cause) {
        super(cause);
    }
    
    public SerializationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
