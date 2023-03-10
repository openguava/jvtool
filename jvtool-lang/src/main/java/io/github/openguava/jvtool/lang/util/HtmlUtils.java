package io.github.openguava.jvtool.lang.util;

import io.github.openguava.jvtool.lang.Validate;
import io.github.openguava.jvtool.lang.html.HtmlCharacterEntityDecoder;
import io.github.openguava.jvtool.lang.html.HtmlCharacterEntityReferences;
import io.github.openguava.jvtool.lang.html.HtmlFilter;

/**
 * html 工具类
 * @author openguava
 *
 */
public class HtmlUtils {

	/**
	 * Shared instance of pre-parsed HTML character entity references.
	 */
	private static final HtmlCharacterEntityReferences characterEntityReferences = new HtmlCharacterEntityReferences();
	
	protected HtmlUtils() {
		
	}
	
	/**
	 * Turn special characters into HTML character references.
	 * <p>Handles the complete character set defined in the HTML 4.01 recommendation.
	 * <p>Escapes all special characters to their corresponding
	 * entity reference (e.g. {@code &lt;}).
	 * <p>Reference:
	 * <a href="https://www.w3.org/TR/html4/sgml/entities.html">
	 * https://www.w3.org/TR/html4/sgml/entities.html
	 * </a>
	 * @param input the (unescaped) input string
	 * @return the escaped string
	 */
	public static String htmlEscape(String input) {
		return htmlEscape(input, HtmlCharacterEntityReferences.DEFAULT_CHARACTER_ENCODING);
	}
	
	/**
	 * Turn special characters into HTML character references.
	 * <p>Handles the complete character set defined in the HTML 4.01 recommendation.
	 * <p>Escapes all special characters to their corresponding
	 * entity reference (e.g. {@code &lt;}) at least as required by the
	 * specified encoding. In other words, if a special character does
	 * not have to be escaped for the given encoding, it may not be.
	 * <p>Reference:
	 * <a href="https://www.w3.org/TR/html4/sgml/entities.html">
	 * https://www.w3.org/TR/html4/sgml/entities.html
	 * </a>
	 * @param input the (unescaped) input string
	 * @param encoding the name of a supported {@link java.nio.charset.Charset charset}
	 * @return the escaped string
	 * @since 4.1.2
	 */
	public static String htmlEscape(String input, String encoding) {
		Validate.notNull(input, "Input is required");
		Validate.notNull(encoding, "Encoding is required");
		StringBuilder escaped = new StringBuilder(input.length() * 2);
		for (int i = 0; i < input.length(); i++) {
			char character = input.charAt(i);
			String reference = characterEntityReferences.convertToReference(character, encoding);
			if (reference != null) {
				escaped.append(reference);
			}
			else {
				escaped.append(character);
			}
		}
		return escaped.toString();
	}
	
	/**
	 * Turn special characters into HTML character references.
	 * <p>Handles the complete character set defined in the HTML 4.01 recommendation.
	 * <p>Escapes all special characters to their corresponding numeric
	 * reference in decimal format (&amp;#<i>Decimal</i>;).
	 * <p>Reference:
	 * <a href="https://www.w3.org/TR/html4/sgml/entities.html">
	 * https://www.w3.org/TR/html4/sgml/entities.html
	 * </a>
	 * @param input the (unescaped) input string
	 * @return the escaped string
	 */
	public static String htmlEscapeDecimal(String input) {
		return htmlEscapeDecimal(input, HtmlCharacterEntityReferences.DEFAULT_CHARACTER_ENCODING);
	}

	/**
	 * Turn special characters into HTML character references.
	 * <p>Handles the complete character set defined in the HTML 4.01 recommendation.
	 * <p>Escapes all special characters to their corresponding numeric
	 * reference in decimal format (&amp;#<i>Decimal</i>;) at least as required by the
	 * specified encoding. In other words, if a special character does
	 * not have to be escaped for the given encoding, it may not be.
	 * <p>Reference:
	 * <a href="https://www.w3.org/TR/html4/sgml/entities.html">
	 * https://www.w3.org/TR/html4/sgml/entities.html
	 * </a>
	 * @param input the (unescaped) input string
	 * @param encoding the name of a supported {@link java.nio.charset.Charset charset}
	 * @return the escaped string
	 * @since 4.1.2
	 */
	public static String htmlEscapeDecimal(String input, String encoding) {
		Validate.notNull(input, "Input is required");
		Validate.notNull(encoding, "Encoding is required");
		StringBuilder escaped = new StringBuilder(input.length() * 2);
		for (int i = 0; i < input.length(); i++) {
			char character = input.charAt(i);
			if (characterEntityReferences.isMappedToReference(character, encoding)) {
				escaped.append(HtmlCharacterEntityReferences.DECIMAL_REFERENCE_START);
				escaped.append((int) character);
				escaped.append(HtmlCharacterEntityReferences.REFERENCE_END);
			}
			else {
				escaped.append(character);
			}
		}
		return escaped.toString();
	}
	
	/**
	 * Turn HTML character references into their plain text UNICODE equivalent.
	 * <p>Handles complete character set defined in HTML 4.01 recommendation
	 * and all reference types (decimal, hex, and entity).
	 * <p>Correctly converts the following formats:
	 * <blockquote>
	 * &amp;#<i>Entity</i>; - <i>(Example: &amp;amp;) case sensitive</i>
	 * &amp;#<i>Decimal</i>; - <i>(Example: &amp;#68;)</i><br>
	 * &amp;#x<i>Hex</i>; - <i>(Example: &amp;#xE5;) case insensitive</i><br>
	 * </blockquote>
	 * <p>Gracefully handles malformed character references by copying original
	 * characters as is when encountered.
	 * <p>Reference:
	 * <a href="https://www.w3.org/TR/html4/sgml/entities.html">
	 * https://www.w3.org/TR/html4/sgml/entities.html
	 * </a>
	 * @param input the (escaped) input string
	 * @return the unescaped string
	 */
	public static String htmlUnescape(String input) {
		return new HtmlCharacterEntityDecoder(characterEntityReferences, input).decode();
	}
	
	/**
	 * Turn special characters into HTML character references.
	 * <p>Handles the complete character set defined in the HTML 4.01 recommendation.
	 * <p>Escapes all special characters to their corresponding numeric
	 * reference in hex format (&amp;#x<i>Hex</i>;).
	 * <p>Reference:
	 * <a href="https://www.w3.org/TR/html4/sgml/entities.html">
	 * https://www.w3.org/TR/html4/sgml/entities.html
	 * </a>
	 * @param input the (unescaped) input string
	 * @return the escaped string
	 */
	public static String htmlEscapeHex(String input) {
		return htmlEscapeHex(input, HtmlCharacterEntityReferences.DEFAULT_CHARACTER_ENCODING);
	}

	/**
	 * Turn special characters into HTML character references.
	 * <p>Handles the complete character set defined in the HTML 4.01 recommendation.
	 * <p>Escapes all special characters to their corresponding numeric
	 * reference in hex format (&amp;#x<i>Hex</i>;) at least as required by the
	 * specified encoding. In other words, if a special character does
	 * not have to be escaped for the given encoding, it may not be.
	 * <p>Reference:
	 * <a href="https://www.w3.org/TR/html4/sgml/entities.html">
	 * https://www.w3.org/TR/html4/sgml/entities.html
	 * </a>
	 * @param input the (unescaped) input string
	 * @param encoding the name of a supported {@link java.nio.charset.Charset charset}
	 * @return the escaped string
	 * @since 4.1.2
	 */
	public static String htmlEscapeHex(String input, String encoding) {
		Validate.notNull(input, "Input is required");
		Validate.notNull(encoding, "Encoding is required");
		StringBuilder escaped = new StringBuilder(input.length() * 2);
		for (int i = 0; i < input.length(); i++) {
			char character = input.charAt(i);
			if (characterEntityReferences.isMappedToReference(character, encoding)) {
				escaped.append(HtmlCharacterEntityReferences.HEX_REFERENCE_START);
				escaped.append(Integer.toString(character, 16));
				escaped.append(HtmlCharacterEntityReferences.REFERENCE_END);
			}
			else {
				escaped.append(character);
			}
		}
		return escaped.toString();
	}
	
	/**
	 * html 标签清理
	 * @param input
	 * @return
	 */
	public static String htmlClean(String input) {
		return new HtmlFilter().filter(input);
	}
	
	/**
	 * html 标签清理
	 * @param input
	 * @param validateEntity
	 * @return
	 */
	public static String htmlClean(String input, boolean validateEntity) {
		return new HtmlFilter().filter(input, validateEntity);
	}
	
	/**
	 * Turn JavaScript special characters into escaped characters.
	 * @param input the input string
	 * @return the string with escaped characters
	 */
	public static String javaScriptEscape(String input) {
		StringBuilder filtered = new StringBuilder(input.length());
		char prevChar = '\u0000';
		char c;
		for (int i = 0; i < input.length(); i++) {
			c = input.charAt(i);
			if (c == '"') {
				filtered.append("\\\"");
			}
			else if (c == '\'') {
				filtered.append("\\'");
			}
			else if (c == '\\') {
				filtered.append("\\\\");
			}
			else if (c == '/') {
				filtered.append("\\/");
			}
			else if (c == '\t') {
				filtered.append("\\t");
			}
			else if (c == '\n') {
				if (prevChar != '\r') {
					filtered.append("\\n");
				}
			}
			else if (c == '\r') {
				filtered.append("\\n");
			}
			else if (c == '\f') {
				filtered.append("\\f");
			}
			else if (c == '\b') {
				filtered.append("\\b");
			}
			// No '\v' in Java, use octal value for VT ascii char
			else if (c == '\013') {
				filtered.append("\\v");
			}
			else if (c == '<') {
				filtered.append("\\u003C");
			}
			else if (c == '>') {
				filtered.append("\\u003E");
			}
			// Unicode for PS (line terminator in ECMA-262)
			else if (c == '\u2028') {
				filtered.append("\\u2028");
			}
			// Unicode for LS (line terminator in ECMA-262)
			else if (c == '\u2029') {
				filtered.append("\\u2029");
			}
			else {
				filtered.append(c);
			}
			prevChar = c;

		}
		return filtered.toString();
	}
	
	public static void main(String[] args) {
		System.out.print(htmlClean("{\"name\": \"11\"}", false));
		System.out.print(javaScriptEscape("function(){ var a = 1;}"));
	}
}
