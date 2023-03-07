package io.github.openguava.jvtool.lang.constant;

/**
 * 字符串常量
 * @author openguava
 *
 */
public class StringConstants {

	/** 空字符 */
	public static final String STRING_EMPTY = "";
	
	/** 空json字符串  */
	public static final String STRING_EMPTY_JSON = "{}";
	
	/** 点符号 */
	public static final String STRING_DOT = ".";
	
	/** 空格字符串 */
	public static final String STRING_SPACE = String.valueOf(CharConstants.CHAR_SPACE);
	
	/** \r 字符串  */
	public static final String STRING_CR = String.valueOf(CharConstants.CHAR_CR);
	
	/** \n 字符串  */
	public static final String STRING_LF = String.valueOf(CharConstants.CHAR_LF);
	
	/** \r\n 换行字符串  */
	public static final String STRING_CRLF = new String(CharConstants.CHARS_CRLF);
	
	/** 当前系统换行字符 */
	public static final String STRING_LINE_SEPARATOR = System.lineSeparator();
	
	/** 10个数字字符串 */
	public static final String STRING_NUMBERS = new String(CharConstants.CHARS_NUMBERS);
	
	/** 26个字母字符串 */
	public static final String STRING_LETTERS = new String(CharConstants.CHARS_LETTERS);
	
	/** 空字符数组 */
	public static final String[] STRING_EMPTY_ARRAY = {};
}
