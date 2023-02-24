package io.github.openguava.jvtool.lang.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import io.github.openguava.jvtool.lang.result.R;

/**
 * 压缩工具类
 * @author openguava
 *
 */
public class CompressUtils {

	protected CompressUtils() {
		
	}
	
	/**
	 * gzip 压缩文件
	 * @param source 源普通文件
	 * @param target 目标压缩文件
	 * @param bufferSize 缓冲区大小
	 * @return
	 */
	public static R<Long> gzipCompress(String source, String target, int bufferSize) {
		return gzipCompress(FileUtils.getFile(source), FileUtils.getFile(target), bufferSize);
	}
	
	/**
	 * gzip 压缩文件
	 * @param source 源普通文件
	 * @param target 目标压缩文件
	 * @param bufferSize 缓冲区大小
	 * @return
	 */
	public static R<Long> gzipCompress(File source, File target, int bufferSize) {
		InputStream inputStream = FileUtils.getFileInputStream(source);
		if(inputStream == null) {
			return R.setFail("读取文件失败");
		}
		OutputStream outputStream = FileUtils.getFileOutputStream(target, false);
		if(outputStream == null) {
			IoUtils.close(inputStream);
			return R.setFail("写出压缩文件失败");
		}
		long size = gzipCompress(inputStream, outputStream, bufferSize);
		IoUtils.close(inputStream);
		IoUtils.close(outputStream);
		if(size < 1) {
			return R.setFail("压缩失败");
		}
		return R.setOk(size);
	}
	
	/**
	 * gzip 压缩数据流
	 * @param source 源普通数据流
	 * @param target 目标压缩数据流
	 * @param bufferSize 缓冲区大小
	 * @return
	 */
	public static long gzipCompress(InputStream source, OutputStream target, int bufferSize) {
		try (GZIPOutputStream newOutput = (target instanceof GZIPOutputStream) ? null : new GZIPOutputStream(target)) {
			return IoUtils.copyLarge(source, newOutput != null ? newOutput : target, bufferSize, null);
		} catch (IOException e) {
			LogUtils.error(CompressUtils.class, e.getMessage(), e);
			return -1L;
		}
	}
	
	/**
	 * gzip 解压
	 * @param source 源压缩文件 
	 * @param target 目标普通文件
	 * @param bufferSize 缓冲区大小
	 * @return
	 */
	public static R<Long> gzipDecompress(String source, String target, int bufferSize) {
		return gzipDecompress(FileUtils.getFile(source), FileUtils.getFile(target), bufferSize);
	}
	
	/**
	 * gzip 解压
	 * @param source 源压缩文件 
	 * @param target 目标普通文件
	 * @param bufferSize 缓冲区大小
	 * @return
	 */
	public static R<Long> gzipDecompress(File source, File target, int bufferSize) {
		InputStream inputStream = FileUtils.getFileInputStream(source);
		if(inputStream == null) {
			return R.setFail("读取压缩文件失败");
		}
		OutputStream outputStream = FileUtils.getFileOutputStream(target, false);
		if(outputStream == null) {
			IoUtils.close(inputStream);
			return R.setFail("写出文件失败");
		}
		long size = gzipDecompress(inputStream, outputStream, bufferSize);
		IoUtils.close(inputStream);
		IoUtils.close(outputStream);
		if(size < 1) {
			return R.setFail("压缩失败");
		}
		return R.setOk(size);
	}
	
	/**
	 * gzip 解压
	 * @param source 源压缩数据流
	 * @param target 目标普通数据流
	 * @param bufferSize 缓冲区大小
	 * @return
	 */
	public static long gzipDecompress(InputStream source, OutputStream target, int bufferSize) {
		try (GZIPInputStream newInput = (source instanceof GZIPInputStream) ? null : new GZIPInputStream(source)) {
			return IoUtils.copyLarge(newInput != null ? newInput: source, target, bufferSize, null);
		} catch (Exception e) {
			LogUtils.error(CompressUtils.class, e.getMessage(), e);
			return -1L;
		}
	}
	
	public static void main(String[] args) {
		gzipCompress("c:\\java.txt", "c:\\java.gz", 1024);
		gzipDecompress("c:\\java.gz", "c:\\java.log", 1024);
	}
}
