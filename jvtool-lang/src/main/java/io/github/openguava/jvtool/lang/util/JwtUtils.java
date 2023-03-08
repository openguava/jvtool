package io.github.openguava.jvtool.lang.util;

import java.util.Map;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * jwt 工具类
 * @author openguava
 *
 */
public class JwtUtils {

	protected JwtUtils() {
		
	}
	
	/**
	 * 签名 SHA-512
	 * @param claims 数据声明
	 * @param secret 密钥
	 * @return
	 */
	public static String sign(Map<String, Object> claims, String secret) {
		return sign(claims, SignatureAlgorithm.HS512, secret);
	}
	
	/**
	 * 签名 SHA-512
	 * @param claims 数据声明
	 * @param secret 密钥
	 * @return
	 */
	public static String sign(Claims claims, String secret) {
		return sign(claims, SignatureAlgorithm.HS512, secret);
	}
	
	/**
	 * 签名
	 * @param claims 数据声明
	 * @param secret 密钥
	 * @return
	 */
	public static String sign(Map<String, Object> claims, SignatureAlgorithm alg, String secret) {
		return Jwts.builder().setClaims(claims).signWith(alg, secret).compact();
	}
	
	/**
	 * 签名
	 * @param claims 数据声明
	 * @param secret 密钥
	 * @return
	 */
	public static String sign(Claims claims, SignatureAlgorithm alg, String secret) {
		return Jwts.builder().setClaims(claims).signWith(alg, secret).compact();
	}
	
	/**
	 * 从令牌中获取数据声明
	 *
	 * @param token 令牌
	 * @param secret 密钥
	 * @return 数据声明
	 */
	public static Claims parse(String token, String secret) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}
	
	/**
	 * 获取数据声明值
	 * @param claims 数据声明
	 * @param key 键
	 * @return
	 */
	public static Object get(Claims claims, String key) {
		if(claims == null) {
			return null;
		}
		return claims.get(key);
	}
	
	public static String getString(Claims claims, String key) {
		if(claims == null) {
			return null;
		}
		return ConvertUtils.toStr(claims.get(key));
	}
}
