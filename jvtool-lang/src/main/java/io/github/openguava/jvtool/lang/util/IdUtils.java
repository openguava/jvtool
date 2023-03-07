package io.github.openguava.jvtool.lang.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import io.github.openguava.jvtool.lang.constant.StringConstants;
import io.github.openguava.jvtool.lang.id.UUID;

/**
 * id 工具类
 * @author openguava
 *
 */
public class IdUtils {

	/** 序列map */
	private static final Map<String, AtomicInteger> SEQ_MAP = new ConcurrentHashMap<>();
	
	/**
	 * 获取序列
	 * @return
	 */
	public static AtomicInteger getSeq() {
		return getSeq(null);
	}
	
	/**
	 * 获取序列
	 * @param key
	 * @return
	 */
	public static AtomicInteger getSeq(String key) {
		return SEQ_MAP.computeIfAbsent(key != null ? key : StringConstants.STRING_EMPTY, x -> new AtomicInteger(1));
	}
	
	/**
	 * 获取随机UUID
	 * 
	 * @return 随机UUID
	 */
	public static String randomUUID() {
		return UUID.randomUUID().toString();
	}
	
	/**
	 * 简化的UUID，去掉了横线
	 * 
	 * @return 简化的UUID，去掉了横线
	 */
	public static String simpleUUID() {
		return UUID.randomUUID().toString(true);
	}
	
	/**
	 * 获取随机UUID，使用性能更好的ThreadLocalRandom生成UUID
	 * 
	 * @return 随机UUID
	 */
	public static String fastUUID() {
		return UUID.fastUUID().toString();
	}
	
	/**
	 * 简化的UUID，去掉了横线，使用性能更好的ThreadLocalRandom生成UUID
	 * 
	 * @return 简化的UUID，去掉了横线
	 */
	public static String fastSimpleUUID() {
		return UUID.fastUUID().toString(true);
	}
	
	/**
	 * 序列循环递增字符串[1, 10 的 (length)幂次方), 用0左补齐length位数
	 * 
	 * @return 序列值
	 */
	public static synchronized String getSeqStr(String key, int length) {
		AtomicInteger atomicInt = getSeq(key);
		// 先取值再+1
		int value = atomicInt.getAndIncrement();

		// 如果更新后值>=10 的 (length)幂次方则重置为1
		int maxSeq = (int) Math.pow(10, length);
		if (atomicInt.get() >= maxSeq) {
			atomicInt.set(1);
		}
		// 转字符串，用0左补齐
		return StringUtils.padLeft(value, length);
	}
	
	/**
	 * 获取序列 int
	 * @param key
	 * @return
	 */
	public static synchronized int getSeqInt(String key) {
		return getSeq(key).getAndIncrement();
	}
	
	/**
	 * 设置序列 int
	 * @param key
	 * @param val
	 */
	public static synchronized void setSeqInt(String key, int val) {
		getSeq(key).set(val);
	}
	
	public static void main(String[] args) {
		
	}
}
