package io.github.openguava.jvtool.lang.auth.annotation;

/**
 * 认证模式
 * @author openguava
 *
 */
public enum Logical {
	/**
	 * 必须具有所有的元素
	 */
    AND, 
    
    /**
     * 只需具有其中一个元素
     */
    OR
}
