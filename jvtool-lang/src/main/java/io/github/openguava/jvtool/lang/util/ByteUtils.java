package io.github.openguava.jvtool.lang.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.DoubleAdder;
import java.util.concurrent.atomic.LongAdder;

import io.github.openguava.jvtool.lang.constant.ByteConstants;
import io.github.openguava.jvtool.lang.constant.CharConstants;
import io.github.openguava.jvtool.lang.constant.StringConstants;
import io.github.openguava.jvtool.lang.exception.UtilException;

/**
 * 对数字和字节进行转换。<br>
 * 假设数据存储是以大端模式存储的：<br>
 * <ul>
 *     <li>byte: 字节类型 占8位二进制 00000000</li>
 *     <li>char: 字符类型 占2个字节 16位二进制 byte[0] byte[1]</li>
 *     <li>int : 整数类型 占4个字节 32位二进制 byte[0] byte[1] byte[2] byte[3]</li>
 *     <li>long: 长整数类型 占8个字节 64位二进制 byte[0] byte[1] byte[2] byte[3] byte[4] byte[5]</li>
 *     <li>long: 长整数类型 占8个字节 64位二进制 byte[0] byte[1] byte[2] byte[3] byte[4] byte[5] byte[6] byte[7]</li>
 *     <li>float: 浮点数(小数) 占4个字节 32位二进制 byte[0] byte[1] byte[2] byte[3]</li>
 *     <li>double: 双精度浮点数(小数) 占8个字节 64位二进制 byte[0] byte[1] byte[2] byte[3] byte[4]byte[5] byte[6] byte[7]</li>
 * </ul>
 *
 * @author openguava
 */
public class ByteUtils {

	public static final ByteOrder DEFAULT_ORDER = ByteOrder.LITTLE_ENDIAN;
	
	/**
	 * CPU的字节序
	 */
	public static final ByteOrder CPU_ENDIAN = "little".equals(System.getProperty("sun.cpu.endian")) ? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN;

	protected ByteUtils() {
		
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
        return toByte(value, null);
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
		if (value == null) {
			return defaultValue;
		}
		if (value instanceof Byte) {
			return (Byte) value;
		}
		if (value instanceof Number) {
			return ((Number) value).byteValue();
		}
		final String valueStr = StringUtils.toStringOrNull(value);
		if (StringUtils.isEmpty(valueStr)) {
			return defaultValue;
		}
		try {
			return Byte.parseByte(valueStr);
		} catch (Exception e) {
			return defaultValue;
		}
	}
	
	/**
	 * int转byte
	 *
	 * @param intValue int值
	 * @return byte值
	 */
	public static byte intToByte(int intValue) {
		return (byte) intValue;
	}

	/**
	 * byte转无符号int
	 *
	 * @param byteValue byte值
	 * @return 无符号int值
	 * @since hutool 3.2.0
	 */
	public static int byteToUnsignedInt(byte byteValue) {
		// Java 总是把 byte 当做有符处理；我们可以通过将其和 0xFF 进行二进制与得到它的无符值
		return byteValue & 0xFF;
	}

	/**
	 * byte数组转short<br>
	 * 默认以小端序转换
	 *
	 * @param bytes byte数组
	 * @return short值
	 */
	public static short bytesToShort(byte[] bytes) {
		return bytesToShort(bytes, DEFAULT_ORDER);
	}

	/**
	 * byte数组转short<br>
	 * 自定义端序
	 *
	 * @param bytes     byte数组，长度必须为2
	 * @param byteOrder 端序
	 * @return short值
	 */
	public static short bytesToShort(final byte[] bytes, final ByteOrder byteOrder) {
		return bytesToShort(bytes, 0, byteOrder);
	}

	/**
	 * byte数组转short<br>
	 * 自定义端序
	 *
	 * @param bytes     byte数组，长度必须大于2
	 * @param start     开始位置
	 * @param byteOrder 端序
	 * @return short值
	 */
	public static short bytesToShort(final byte[] bytes, final int start, final ByteOrder byteOrder) {
		if (ByteOrder.LITTLE_ENDIAN == byteOrder) {
			//小端模式，数据的高字节保存在内存的高地址中，而数据的低字节保存在内存的低地址中
			return (short) (bytes[start] & 0xff | (bytes[start + 1] & 0xff) << Byte.SIZE);
		} else {
			return (short) (bytes[start + 1] & 0xff | (bytes[start] & 0xff) << Byte.SIZE);
		}
	}

	/**
	 * short转byte数组<br>
	 * 默认以小端序转换
	 *
	 * @param shortValue short值
	 * @return byte数组
	 */
	public static byte[] shortToBytes(short shortValue) {
		return shortToBytes(shortValue, DEFAULT_ORDER);
	}

	/**
	 * short转byte数组<br>
	 * 自定义端序
	 *
	 * @param shortValue short值
	 * @param byteOrder  端序
	 * @return byte数组
	 */
	public static byte[] shortToBytes(short shortValue, ByteOrder byteOrder) {
		byte[] b = new byte[ByteConstants.SHORT_BYTES];
		if (ByteOrder.LITTLE_ENDIAN == byteOrder) {
			b[0] = (byte) (shortValue & 0xff);
			b[1] = (byte) ((shortValue >> Byte.SIZE) & 0xff);
		} else {
			b[1] = (byte) (shortValue & 0xff);
			b[0] = (byte) ((shortValue >> Byte.SIZE) & 0xff);
		}
		return b;
	}

	/**
	 * byte[]转int值<br>
	 * 默认以小端序转换
	 *
	 * @param bytes byte数组
	 * @return int值
	 */
	public static int bytesToInt(byte[] bytes) {
		return bytesToInt(bytes, DEFAULT_ORDER);
	}

	/**
	 * byte[]转int值<br>
	 * 自定义端序
	 *
	 * @param bytes     byte数组
	 * @param byteOrder 端序
	 * @return int值
	 */
	public static int bytesToInt(byte[] bytes, ByteOrder byteOrder) {
		return bytesToInt(bytes, 0, byteOrder);
	}

	/**
	 * byte[]转int值<br>
	 * 自定义端序
	 *
	 * @param bytes     byte数组
	 * @param start 开始位置（包含）
	 * @param byteOrder 端序
	 * @return int值
	 * @since hutool 5.7.21
	 */
	public static int bytesToInt(byte[] bytes, int start, ByteOrder byteOrder) {
		if (ByteOrder.LITTLE_ENDIAN == byteOrder) {
			return bytes[start] & 0xFF | //
					(bytes[1 + start] & 0xFF) << 8 | //
					(bytes[2 + start] & 0xFF) << 16 | //
					(bytes[3 + start] & 0xFF) << 24; //
		} else {
			return bytes[3 + start] & 0xFF | //
					(bytes[2 + start] & 0xFF) << 8 | //
					(bytes[1 + start] & 0xFF) << 16 | //
					(bytes[start] & 0xFF) << 24; //
		}

	}

	/**
	 * int转byte数组<br>
	 * 默认以小端序转换
	 *
	 * @param intValue int值
	 * @return byte数组
	 */
	public static byte[] intToBytes(int intValue) {
		return intToBytes(intValue, DEFAULT_ORDER);
	}

	/**
	 * int转byte数组<br>
	 * 自定义端序
	 *
	 * @param intValue  int值
	 * @param byteOrder 端序
	 * @return byte数组
	 */
	public static byte[] intToBytes(int intValue, ByteOrder byteOrder) {

		if (ByteOrder.LITTLE_ENDIAN == byteOrder) {
			return new byte[]{ //
					(byte) (intValue & 0xFF), //
					(byte) ((intValue >> 8) & 0xFF), //
					(byte) ((intValue >> 16) & 0xFF), //
					(byte) ((intValue >> 24) & 0xFF) //
			};

		} else {
			return new byte[]{ //
					(byte) ((intValue >> 24) & 0xFF), //
					(byte) ((intValue >> 16) & 0xFF), //
					(byte) ((intValue >> 8) & 0xFF), //
					(byte) (intValue & 0xFF) //
			};
		}

	}

	/**
	 * long转byte数组<br>
	 * 默认以小端序转换<br>
	 * from: https://stackoverflow.com/questions/4485128/how-do-i-convert-long-to-byte-and-back-in-java
	 *
	 * @param longValue long值
	 * @return byte数组
	 */
	public static byte[] longToBytes(long longValue) {
		return longToBytes(longValue, DEFAULT_ORDER);
	}

	/**
	 * long转byte数组<br>
	 * 自定义端序<br>
	 * from: https://stackoverflow.com/questions/4485128/how-do-i-convert-long-to-byte-and-back-in-java
	 *
	 * @param longValue long值
	 * @param byteOrder 端序
	 * @return byte数组
	 */
	public static byte[] longToBytes(long longValue, ByteOrder byteOrder) {
		byte[] result = new byte[ByteConstants.LONG_BYTES];
		if (ByteOrder.LITTLE_ENDIAN == byteOrder) {
			for (int i = 0; i < result.length; i++) {
				result[i] = (byte) (longValue & 0xFF);
				longValue >>= Byte.SIZE;
			}
		} else {
			for (int i = (result.length - 1); i >= 0; i--) {
				result[i] = (byte) (longValue & 0xFF);
				longValue >>= Byte.SIZE;
			}
		}
		return result;
	}

	/**
	 * byte数组转long<br>
	 * 默认以小端序转换<br>
	 * from: https://stackoverflow.com/questions/4485128/how-do-i-convert-long-to-byte-and-back-in-java
	 *
	 * @param bytes byte数组
	 * @return long值
	 */
	public static long bytesToLong(byte[] bytes) {
		return bytesToLong(bytes, DEFAULT_ORDER);
	}

	/**
	 * byte数组转long<br>
	 * 自定义端序<br>
	 * from: https://stackoverflow.com/questions/4485128/how-do-i-convert-long-to-byte-and-back-in-java
	 *
	 * @param bytes     byte数组
	 * @param byteOrder 端序
	 * @return long值
	 */
	public static long bytesToLong(byte[] bytes, ByteOrder byteOrder) {
		return bytesToLong(bytes, 0, byteOrder);
	}

	/**
	 * byte数组转long<br>
	 * 自定义端序<br>
	 * from: https://stackoverflow.com/questions/4485128/how-do-i-convert-long-to-byte-and-back-in-java
	 *
	 * @param bytes     byte数组
	 * @param start     计算数组开始位置
	 * @param byteOrder 端序
	 * @return long值
	 * @since hutool 5.7.21
	 */
	public static long bytesToLong(byte[] bytes, int start, ByteOrder byteOrder) {
		long values = 0;
		if (ByteOrder.LITTLE_ENDIAN == byteOrder) {
			for (int i = (ByteConstants.LONG_BYTES - 1); i >= 0; i--) {
				values <<= Byte.SIZE;
				values |= (bytes[i + start] & 0xff);
			}
		} else {
			for (int i = 0; i < ByteConstants.LONG_BYTES; i++) {
				values <<= Byte.SIZE;
				values |= (bytes[i + start] & 0xff);
			}
		}

		return values;
	}

	/**
	 * float转byte数组，默认以小端序转换<br>
	 *
	 * @param floatValue float值
	 * @return byte数组
	 * @since hutool 5.7.18
	 */
	public static byte[] floatToBytes(float floatValue) {
		return floatToBytes(floatValue, DEFAULT_ORDER);
	}

	/**
	 * float转byte数组，自定义端序<br>
	 *
	 * @param floatValue float值
	 * @param byteOrder  端序
	 * @return byte数组
	 * @since hutool 5.7.18
	 */
	public static byte[] floatToBytes(float floatValue, ByteOrder byteOrder) {
		return intToBytes(Float.floatToIntBits(floatValue), byteOrder);
	}

	/**
	 * byte数组转float<br>
	 * 默认以小端序转换<br>
	 *
	 * @param bytes byte数组
	 * @return float值
	 * @since hutool 5.7.18
	 */
	public static double bytesToFloat(byte[] bytes) {
		return bytesToFloat(bytes, DEFAULT_ORDER);
	}

	/**
	 * byte数组转float<br>
	 * 自定义端序<br>
	 *
	 * @param bytes     byte数组
	 * @param byteOrder 端序
	 * @return float值
	 * @since hutool 5.7.18
	 */
	public static float bytesToFloat(byte[] bytes, ByteOrder byteOrder) {
		return Float.intBitsToFloat(bytesToInt(bytes, byteOrder));
	}

	/**
	 * double转byte数组<br>
	 * 默认以小端序转换<br>
	 *
	 * @param doubleValue double值
	 * @return byte数组
	 */
	public static byte[] doubleToBytes(double doubleValue) {
		return doubleToBytes(doubleValue, DEFAULT_ORDER);
	}

	/**
	 * double转byte数组<br>
	 * 自定义端序<br>
	 * from: https://stackoverflow.com/questions/4485128/how-do-i-convert-long-to-byte-and-back-in-java
	 *
	 * @param doubleValue double值
	 * @param byteOrder   端序
	 * @return byte数组
	 */
	public static byte[] doubleToBytes(double doubleValue, ByteOrder byteOrder) {
		return longToBytes(Double.doubleToLongBits(doubleValue), byteOrder);
	}

	/**
	 * byte数组转Double<br>
	 * 默认以小端序转换<br>
	 *
	 * @param bytes byte数组
	 * @return long值
	 */
	public static double bytesToDouble(byte[] bytes) {
		return bytesToDouble(bytes, DEFAULT_ORDER);
	}

	/**
	 * byte数组转double<br>
	 * 自定义端序<br>
	 *
	 * @param bytes     byte数组
	 * @param byteOrder 端序
	 * @return long值
	 */
	public static double bytesToDouble(byte[] bytes, ByteOrder byteOrder) {
		return Double.longBitsToDouble(bytesToLong(bytes, byteOrder));
	}

	/**
	 * 将{@link Number}转换为
	 *
	 * @param number 数字
	 * @return bytes
	 */
	public static byte[] numberToBytes(Number number) {
		return numberToBytes(number, DEFAULT_ORDER);
	}

	/**
	 * 将{@link Number}转换为
	 *
	 * @param number    数字
	 * @param byteOrder 端序
	 * @return bytes
	 */
	public static byte[] numberToBytes(Number number, ByteOrder byteOrder) {
		if(number instanceof Byte){
			return new byte[]{number.byteValue()};
		}else if (number instanceof Double) {
			return doubleToBytes((Double) number, byteOrder);
		} else if (number instanceof Long) {
			return longToBytes((Long) number, byteOrder);
		} else if (number instanceof Integer) {
			return intToBytes((Integer) number, byteOrder);
		} else if (number instanceof Short) {
			return shortToBytes((Short) number, byteOrder);
		} else if (number instanceof Float) {
			return floatToBytes((Float) number, byteOrder);
		} else {
			return doubleToBytes(number.doubleValue(), byteOrder);
		}
	}

	/**
	 * byte数组转换为指定类型数字
	 *
	 * @param <T>         数字类型
	 * @param bytes       byte数组
	 * @param targetClass 目标数字类型
	 * @param byteOrder   端序
	 * @return 转换后的数字
	 * @throws IllegalArgumentException 不支持的数字类型，如用户自定义数字类型
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Number> T bytesToNumber(byte[] bytes, Class<T> targetClass, ByteOrder byteOrder) throws IllegalArgumentException {
		Number number;
		if (Byte.class == targetClass) {
			number = bytes[0];
		} else if (Short.class == targetClass) {
			number = bytesToShort(bytes, byteOrder);
		} else if (Integer.class == targetClass) {
			number = bytesToInt(bytes, byteOrder);
		} else if (AtomicInteger.class == targetClass) {
			number = new AtomicInteger(bytesToInt(bytes, byteOrder));
		} else if (Long.class == targetClass) {
			number = bytesToLong(bytes, byteOrder);
		} else if (AtomicLong.class == targetClass) {
			number = new AtomicLong(bytesToLong(bytes, byteOrder));
		} else if (LongAdder.class == targetClass) {
			final LongAdder longValue = new LongAdder();
			longValue.add(bytesToLong(bytes, byteOrder));
			number = longValue;
		} else if (Float.class == targetClass) {
			number = bytesToFloat(bytes, byteOrder);
		} else if (Double.class == targetClass) {
			number = bytesToDouble(bytes, byteOrder);
		} else if (DoubleAdder.class == targetClass) {
			final DoubleAdder doubleAdder = new DoubleAdder();
			doubleAdder.add(bytesToDouble(bytes, byteOrder));
			number = doubleAdder;
		} else if (BigDecimal.class == targetClass) {
			number = NumberUtils.numberToBigDecimal(bytesToDouble(bytes, byteOrder));
		} else if (BigInteger.class == targetClass) {
			number = BigInteger.valueOf(bytesToLong(bytes, byteOrder));
		} else if (Number.class == targetClass) {
			// 用户没有明确类型具体类型，默认Double
			number = bytesToDouble(bytes, byteOrder);
		} else {
			// 用户自定义类型不支持
			throw new IllegalArgumentException("Unsupported Number type: " + targetClass.getName());
		}
		return (T) number;
	}
	
	/**
	 * 字符串转换为字节数组
	 * @param str
	 * @param charset
	 * @return
	 */
	public static byte[] stringToBytes(CharSequence str, String charset) {
		return stringToBytes(str, StringUtils.isBlank(charset) ? Charset.defaultCharset() : Charset.forName(charset));
	}
	
	/**
	 * 字符串转换为字节数组
	 * @param str
	 * @param charset
	 * @return
	 */
	public static byte[] stringToBytes(CharSequence str, Charset charset) {
		if (str == null) {
			return null;
		}
		if (charset == null) {
			return str.toString().getBytes(Charset.defaultCharset());
		}
		return str.toString().getBytes(charset);
	}
	
	/**
	 * 字节数组转换为字符串
	 * @param bytes
	 * @param charset
	 * @return
	 */
	public static String bytesToString(byte[] bytes, String charset) {
		return bytesToString(bytes, StringUtils.isEmpty(charset) ? Charset.defaultCharset() : Charset.forName(charset));
	}
	
	/**
	 * 字节数组转换为字符串
	 * @param bytes
	 * @param charset
	 * @return
	 */
	public static String bytesToString(byte[] bytes, Charset charset) {
		if (bytes == null) {
			return null;
		}
		if (charset == null) {
			return new String(bytes);
		}
		return new String(bytes, charset);
	}
	
	/**
	 * byte数组转16进制串
	 * @param bytes 被转换的byte数组
	 * @return
	 */
	public static String bytesToHex(byte[] bytes, boolean toLowerCase) {
		if(bytes == null) {
			return null;
		}
		if(bytes.length == 0) {
			return StringConstants.STRING_EMPTY;
		}
		char[] toDigits = toLowerCase ? CharConstants.CHARS_HEX_DIGITS_LOWER : CharConstants.CHARS_HEX_DIGITS_UPPER;
		int l = bytes.length;
		char[] out = new char[l << 1];
		// two characters from the hex value.
		for (int i = 0, j = 0; i < l; i++) {
			out[j++] = toDigits[(0xF0 & bytes[i]) >>> 4];
			out[j++] = toDigits[0x0F & bytes[i]];
		}
		return new String(out);
	}
	
	/**
	 * hex字符串转换为byte数组
	 * @param hex Byte字符串，每个Byte之间没有分隔符
	 * @return
	 */
	public static byte[] hexToBytes(String hex) {
		if(hex == null) {
			return null;
		}
		if(hex.length() == 0) {
			return new byte[0];
		}
		char[] hexData = hex.toCharArray();
		int len = hexData.length;
		if ((len & 0x01) != 0) {
			throw new UtilException("Odd number of characters.");
		}
		byte[] out = new byte[len >> 1];
		// two characters form the hex value.
		for (int i = 0, j = 0; j < len; i++) {
			int f = Character.digit(hexData[j], 16) << 4; //toDigit(hexData[j], j) << 4;
			j++;
			f = f | Character.digit(hexData[j], 16); //toDigit(hexData[j], j);
			j++;
			out[i] = (byte) (f & 0xFF);
		}
		return out;
	}
}