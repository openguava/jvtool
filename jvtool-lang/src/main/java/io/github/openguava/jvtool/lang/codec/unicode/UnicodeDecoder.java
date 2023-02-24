package io.github.openguava.jvtool.lang.codec.unicode;

import io.github.openguava.jvtool.lang.util.StringUtils;

/**
 * Unicode 解码
 * @author openguava
 *
 */
public class UnicodeDecoder {

	/**
	 * Unicode字符串转为普通字符串<br>
	 * Unicode字符串的表现方式为：\\uXXXX
	 * 
	 * @param unicode Unicode字符串
	 * @return 普通字符串
	 */
	public static String decode(String unicode) {
		if (unicode == null || unicode.length() == 0) {
			return unicode;
		}

		final int len = unicode.length();
		StringBuilder sb = new StringBuilder(len);
		int i = -1;
		int pos = 0;
		while ((i = StringUtils.indexOfIgnoreCase(unicode, "\\u", pos)) != -1) {
			sb.append(unicode, pos, i);//写入Unicode符之前的部分
			pos  = i;
			if (i + 5 < len) {
				char c = 0;
				try {
					c = (char) Integer.parseInt(unicode.substring(i + 2, i + 6), 16);
					sb.append(c);
					pos = i + 6;//跳过整个Unicode符
				} catch (NumberFormatException e) {
					//非法Unicode符，跳过
					sb.append(unicode, pos, i+2);//写入"\\u"
					pos = i + 2;
				}
			}else {
				pos = i;//非Unicode符，结束
				break;
			}
		}
		if(pos < len) {
			sb.append(unicode,pos, len);
		}
		return sb.toString();
	}
}
