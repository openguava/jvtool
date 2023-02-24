package io.github.openguava.jvtool.lang.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 异常工具类
 * @author openguava
 *
 */
public class ExceptionUtils {
	
	/**
	 * Exception
	 * @param message
	 * @param cause
	 * @return
	 */
	public static Exception newException(String message, Throwable cause) {
		return cause == null ? new Exception(message) : new Exception(message, cause);
	}
	
	/**
	 * IOException
	 * @param message
	 * @param cause
	 * @return
	 */
	public static IOException newIOException(String message, Throwable cause) {
		return cause == null ? new IOException(message) : new IOException(message, cause);
	}
	
	/**
	 * RuntimeException
	 * @param message
	 * @param cause
	 * @return
	 */
	public static RuntimeException newRuntimeException(String message, Throwable cause) {
		return cause == null ? new RuntimeException(message) : new RuntimeException(message, cause);
	}
	
	/**
	 * 获取exception的详细错误信息。
	 */
	public static String getStackTrace(Throwable e) {
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw, true));
		return sw.toString();
	}
	
	/**
	 * 获取异常堆栈信息
	 * @param e
	 * @param packageName 限制包名
	 * @return
	 */
	public static String getStackTrace(Throwable e, String packageName) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw, true));
        String str = sw.toString();
        if (packageName == null) {
            return str;
        }
        String[] arrs = str.split("\n");
        StringBuilder sbuf = new StringBuilder();
        sbuf.append(arrs[0] + "\n");
        for (int i = 0; i < arrs.length; i++) {
            String temp = arrs[i];
            if (temp != null && temp.indexOf(packageName) > 0) {
                sbuf.append(temp + "\n");
            }
        }
        return sbuf.toString();
    }
}
