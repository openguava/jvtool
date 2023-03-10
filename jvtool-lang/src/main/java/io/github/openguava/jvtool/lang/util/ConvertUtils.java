package io.github.openguava.jvtool.lang.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import io.github.openguava.jvtool.lang.constant.CharsetConstants;

/**
 * 转换工具类
 * @author openguava
 *
 */
public class ConvertUtils {
	
	protected ConvertUtils() {
		
	}
	
    /**
	 * 转换为byte<br>
	 * 如果给定的值为<code>null</code>，或者转换失败，返回默认值<br>
	 * 转换失败不会报错
	 * 
	 * @param value        被转换的值
	 * @return 结果
	 */
	public static Boolean toBool(Object value) {
		return BooleanUtils.toBoolean(value);
	}
	
    /**
	 * 转换为byte<br>
	 * 如果给定的值为<code>null</code>，或者转换失败，返回默认值<br>
	 * 转换失败不会报错
	 * 
	 * @param value        被转换的值
	 * @return 结果
	 */
	public static Boolean toBoolean(Object value) {
		return BooleanUtils.toBoolean(value);
	}
	
    /**
	 * 转换为byte<br>
	 * 如果给定的值为<code>null</code>，或者转换失败，返回默认值<br>
	 * 转换失败不会报错
	 * 
	 * @param value        被转换的值
	 * @param defaultValue 转换错误时的默认值
	 * @return 结果
	 */
	public static Boolean toBool(Object value, Boolean defaultValue) {
		return BooleanUtils.toBoolean(value, defaultValue);
	}
	
    /**
	 * 转换为byte<br>
	 * 如果给定的值为<code>null</code>，或者转换失败，返回默认值<br>
	 * 转换失败不会报错
	 * 
	 * @param value        被转换的值
	 * @param defaultValue 转换错误时的默认值
	 * @return 结果
	 */
	public static Boolean toBoolean(Object value, Boolean defaultValue) {
		return BooleanUtils.toBoolean(value, defaultValue);
	}
	
	/**
     * 转换为byte<br>
     * 如果给定的值为<code>null</code>，或者转换失败，返回默认值<code>null</code><br>
     * 转换失败不会报错
     *
     * @param value 被转换的值
     * @return 结果
     */
    public static Byte toByte(Object value) {
    	return ByteUtils.toByte(value);
    }
    
    /**
	 * 转换为byte<br>
	 * 如果给定的值为<code>null</code>，或者转换失败，返回默认值<br>
	 * 转换失败不会报错
	 * 
	 * @param value        被转换的值
	 * @param defaultValue 转换错误时的默认值
	 * @return 结果
	 */
	public static Byte toByte(Object value, Byte defaultValue) {
		return ByteUtils.toByte(value, defaultValue);
	}
	
    /**
     * 转换为Number<br>
     * 如果给定的值为空，或者转换失败，返回默认值<code>null</code><br>
     * 转换失败不会报错
     *
     * @param value 被转换的值
     * @return 结果
     */
    public static Number toNumber(Object value) {
        return NumberUtils.toNumber(value);
    }
    
	/**
	 * 转换为Number<br>
	 * 如果给定的值为空，或者转换失败，返回默认值<br>
	 * 转换失败不会报错
	 *
	 * @param value        被转换的值
	 * @param defaultValue 转换错误时的默认值
	 * @return 结果
	 */
	public static Number toNumber(Object value, Number defaultValue) {
		return NumberUtils.toNumber(value, defaultValue);
	}

	/**
     * 转换为int<br>
     * 如果给定的值为<code>null</code>，或者转换失败，返回默认值<code>null</code><br>
     * 转换失败不会报错
     *
     * @param value 被转换的值
     * @return 结果
     */
    public static Integer toInt(Object value) {
        return NumberUtils.toInt(value);
    }
	
	/**
	 * 转换为int<br>
	 * 如果给定的值为空，或者转换失败，返回默认值<br>
	 * 转换失败不会报错
	 * 
	 * @param value        被转换的值
	 * @param defaultValue 转换错误时的默认值
	 * @return 结果
	 */
	public static Integer toInt(Object value, Integer defaultValue) {
		return NumberUtils.toInt(value, defaultValue);
	}
	
	/**
	 * 转换为Integer数组<br>
	 * 
	 * @param split 分隔符
	 * @param str   被转换的值
	 * @return 结果
	 */
	public static Integer[] toIntArray(String split, String str) {
		return NumberUtils.toIntArray(split, str);
	}
	
	/**
     * 转换为long<br>
     * 如果给定的值为<code>null</code>，或者转换失败，返回默认值<code>null</code><br>
     * 转换失败不会报错
     *
     * @param value 被转换的值
     * @return 结果
     */
    public static Long toLong(Object value) {
        return NumberUtils.toLong(value);
    }
    
	/**
	 * 转换为long<br>
	 * 如果给定的值为空，或者转换失败，返回默认值<br>
	 * 转换失败不会报错
	 * 
	 * @param value        被转换的值
	 * @param defaultValue 转换错误时的默认值
	 * @return 结果
	 */
	public static Long toLong(Object value, Long defaultValue) {
		return NumberUtils.toLong(value, defaultValue);
	}
	
	/**
	 * 转换为Long数组<br>
	 * 
	 * @param split 分隔符
	 * @param str   被转换的值
	 * @return 结果
	 */
	public static Long[] toLongArray(String split, String str) {
		return NumberUtils.toLongArray(split, str);
	}
	
	/**
     * 转换为Float<br>
     * 如果给定的值为空，或者转换失败，返回默认值<code>null</code><br>
     * 转换失败不会报错
     *
     * @param value 被转换的值
     * @return 结果
     */
    public static Float toFloat(Object value) {
        return NumberUtils.toFloat(value);
    }
    
	/**
	 * 转换为Float<br>
	 * 如果给定的值为空，或者转换失败，返回默认值<br>
	 * 转换失败不会报错
	 * 
	 * @param value        被转换的值
	 * @param defaultValue 转换错误时的默认值
	 * @return 结果
	 */
	public static Float toFloat(Object value, Float defaultValue) {
		return NumberUtils.toFloat(value, defaultValue);
	}
	
	/**
	 * 转换为Float数组<br>
	 * 
	 * @param split 分隔符
	 * @param str   被转换的值
	 * @return 结果
	 */
	public static Float[] toFloatArray(String split, String str) {
		return NumberUtils.toFloatArray(split, str);
	}
	
    /**
     * 转换为double<br>
     * 如果给定的值为空，或者转换失败，返回默认值<code>null</code><br>
     * 转换失败不会报错
     *
     * @param value 被转换的值
     * @return 结果
     */
    public static Double toDouble(Object value) {
        return NumberUtils.toDouble(value);
    }
	
	/**
	 * 转换为double<br>
	 * 如果给定的值为空，或者转换失败，返回默认值<br>
	 * 转换失败不会报错
	 * 
	 * @param value        被转换的值
	 * @param defaultValue 转换错误时的默认值
	 * @return 结果
	 */
	public static Double toDouble(Object value, Double defaultValue) {
		return NumberUtils.toDouble(value, defaultValue);
	}
	
	/**
	 * 转换为Double数组<br>
	 * 
	 * @param split 分隔符
	 * @param str   被转换的值
	 * @return 结果
	 */
	public static Double[] toDoubleArray(String split, String str) {
		return NumberUtils.toDoubleArray(split, str);
	}
	
    /**
     * 转换为BigDecimal<br>
     * 如果给定的值为空，或者转换失败，返回默认值<br>
     * 转换失败不会报错
     *
     * @param value 被转换的值
     * @return 结果
     */
    public static BigDecimal toBigDecimal(Object value) {
        return NumberUtils.toBigDecimal(value);
    }
    
	/**
	 * 转换为BigDecimal<br>
	 * 如果给定的值为空，或者转换失败，返回默认值<br>
	 * 转换失败不会报错
	 * 
	 * @param value        被转换的值
	 * @param defaultValue 转换错误时的默认值
	 * @return 结果
	 */
	public static BigDecimal toBigDecimal(Object value, BigDecimal defaultValue) {
		return NumberUtils.toBigDecimal(value, defaultValue);
	}
	
	/**
	 * 转换为BigDecimal数组<br>
	 * 
	 * @param split 分隔符
	 * @param str   被转换的值
	 * @return 结果
	 */
	public static BigDecimal[] toBigDecimalArray(String split, String str) {
		return NumberUtils.toBigDecimalArray(split, str);
	}
	
	/**
	 * 转换为BigInteger<br>
	 * 如果给定的值为空，或者转换失败，返回默认值<code>null</code><br>
	 * 转换失败不会报错
	 *
	 * @param value 被转换的值
	 * @return 结果
	 */
	public static BigInteger toBigInteger(Object value) {
		return NumberUtils.toBigInteger(value);
	}
	
	/**
	 * 转换为BigInteger<br>
	 * 如果给定的值为空，或者转换失败，返回默认值<br>
	 * 转换失败不会报错
	 *
	 * @param value        被转换的值
	 * @param defaultValue 转换错误时的默认值
	 * @return 结果
	 */
	public static BigInteger toBigInteger(Object value, BigInteger defaultValue) {
		return NumberUtils.toBigInteger(value, defaultValue);
	}
	
	/**
	 * 转换为String
	 * @param value
	 * @return
	 */
	public static String toStr(Object value) {
		return StringUtils.toString(value, CharsetConstants.CHARSET_UTF_8);
	}
	
	/**
	 * 转换为String
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static String toStr(Object value, String defaultValue) {
		if(value == null) {
			return defaultValue;
		}
		return StringUtils.toString(value, CharsetConstants.CHARSET_UTF_8);
	}
	
	/**
	 * 转换为String
	 * @param value
	 * @return
	 */
	public static String toString(Object value) {
		return StringUtils.toString(value, CharsetConstants.CHARSET_UTF_8);
	}
	
	/**
	 * 转换为String
	 * @param value
	 * @param defaultValue 默认值
	 * @return
	 */
	public static String toString(Object value, String defaultValue) {
		if(value == null) {
			return defaultValue;
		}
		return StringUtils.toString(value, CharsetConstants.CHARSET_UTF_8);
	}
	
	/**
	 * 转换为String数组<br>
	 * 
	 * @param str 被转换的值
	 * @return 结果
	 */
	public static String[] toStrArray(String str) {
		return toStrArray(",", str);
	}

	/**
	 * 转换为String数组<br>
	 * 
	 * @param split 分隔符
	 * @param str   被转换的值
	 * @return 结果
	 */
	public static String[] toStrArray(String split, String str) {
		return str.split(split);
	}
	
	/**
	 * 转换为 Date
	 * @param value
	 * @return
	 */
	public static Date toDate(Object value) {
		return DateUtils.toDate(value, null);
	}
	
	/**
	 * 转换为 Date
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static Date toDate(Object value, Date defaultValue) {
		return DateUtils.toDate(value, defaultValue);
	}
	
	/**
	 * 转换为Object
	 * @param <T>
	 * @param value
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T toObject(Object value, Class<T> clazz) {
		if(value == null) {
			return null;
		}
		if(String.class.isAssignableFrom(clazz)) {//String
			return (T)ConvertUtils.toString(value);
		} else if(Long.class.isAssignableFrom(clazz)) {//Long
			return (T)ConvertUtils.toLong(value);
		} else if(BigInteger.class.isAssignableFrom(clazz)) {//BigInteger
			return (T)ConvertUtils.toBigInteger(value);
		} else if(Integer.class.isAssignableFrom(clazz)) {//Integer
			return (T)ConvertUtils.toInt(value);
		} else if(Double.class.isAssignableFrom(clazz)) {//Double
			return (T)ConvertUtils.toDouble(value);
		} else if(Float.class.isAssignableFrom(clazz)) {//Float
			return (T)ConvertUtils.toFloat(value);
		} else if(BigDecimal.class.isAssignableFrom(clazz)) {//BigDecimal
			return (T)ConvertUtils.toBigDecimal(value);
		} else if(Boolean.class.isAssignableFrom(clazz)) {//Boolean
			return (T)ConvertUtils.toBoolean(value);
		} else if(Number.class.isAssignableFrom(clazz)) {//Number
			return (T)ConvertUtils.toNumber(value);
		} else if(Byte.class.isAssignableFrom(clazz)) {//Byte
			return (T)ConvertUtils.toByte(value);
		}  else if(Date.class.isAssignableFrom(clazz)) {//Date
			return (T)ConvertUtils.toDate(value);
		} 
		return (T)value;
	}
}
