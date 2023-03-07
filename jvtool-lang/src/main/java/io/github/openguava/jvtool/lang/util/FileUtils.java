package io.github.openguava.jvtool.lang.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import io.github.openguava.jvtool.lang.constant.CharConstants;
import io.github.openguava.jvtool.lang.constant.PropertyConstants;
import io.github.openguava.jvtool.lang.constant.StringConstants;

/**
 * 文件 工具类
 * 
 * @author openguava
 *
 */
public class FileUtils {

	/**
	 * 特殊后缀
	 */
	private static final CharSequence[] SPECIAL_SUFFIX = { "tar.bz2", "tar.Z", "tar.gz", "tar.xz" };

	/**
	 * 类Unix路径分隔符
	 */
	public static final char UNIX_SEPARATOR = CharConstants.CHAR_SLASH;

	/**
	 * Windows路径分隔符
	 */
	public static final char WINDOWS_SEPARATOR = CharConstants.CHAR_BACKSLASH;

	protected FileUtils() {

	}

	/**
	 * 获取文件对象
	 * 
	 * @param filePath
	 * @return
	 */
	public static File getFile(String filePath) {
		if (filePath == null) {
			return null;
		}
		return new File(filePath);
	}
	
	/**
	 * 获取随机文件
	 * @param dir
	 * @param extension
	 * @return
	 */
	public static File getRandomFile(File dir, String extension) {
		if(dir == null) {
			// 获取临时目录
			dir = new File(getTempDir());
		}
		if(!dir.exists()) {
			dir.mkdirs();
		}
		StringBuilder fileName = new StringBuilder(IdUtils.randomUUID());
		if(StringUtils.isNotEmpty(extension)) {
			if(!extension.startsWith(".")) {
				fileName.append(".");
			}
			fileName.append(extension);
		}
		return new File(dir, fileName.toString());
	}

	/**
	 * 获取文件列表
	 * 
	 * @param rootDir
	 * @param fileFilter
	 * @return
	 */
	public static List<File> getFiles(String rootDir, FileFilter fileFilter) {
		return getFiles(getFile(rootDir), fileFilter);
	}

	/**
	 * 获取文件/目录列表
	 * 
	 * @param rootFile   文件
	 * @param fileFilter 文件过滤器
	 * @return
	 */
	public static List<File> getFiles(File rootFile, FileFilter fileFilter) {
		List<File> fileList = new ArrayList<>();
		return getFiles(rootFile, fileFilter, fileList);
	}

	/**
	 * 获取文件/目录列表
	 * 
	 * @param rootFile   文件
	 * @param fileFilter 文件过滤器
	 * @param fileList   文件列表存储对象
	 * @return
	 */
	public static List<File> getFiles(File rootFile, FileFilter fileFilter, List<File> fileList) {
		if (!exists(rootFile)) {
			return fileList;
		}
		// fileList
		if (fileList == null) {
			fileList = new ArrayList<>();
		}
		// 添加当前文件
		if (fileFilter == null || fileFilter.accept(rootFile)) {
			fileList.add(rootFile);
		}
		// 递归添加子文件和子目录
		if (rootFile.isDirectory()) {
			File[] childFiles = rootFile.listFiles(fileFilter);
			if (ArrayUtils.isNotEmpty(childFiles)) {
				for (File childFile : childFiles) {
					getFiles(childFile, fileFilter, fileList);
				}
			}
		}
		return fileList;
	}

	/**
	 * 获取文件名
	 * 
	 * @param filePath
	 * @return
	 */
	public static String getFileName(File file) {
		return file != null ? file.getName() : null;
	}

	/**
	 * 返回文件名<br>
	 * 
	 * <pre>
	 * "d:/test/aaa" 返回 "aaa"
	 * "/test/aaa.jpg" 返回 "aaa.jpg"
	 * </pre>
	 *
	 * @param filePath 文件
	 * @return 文件名
	 */
	public static String getFileName(String filePath) {
		if (filePath == null) {
			return null;
		}
		int len = filePath.length();
		if (len == 0) {
			return filePath;
		}
		if (CharUtils.isFileSeparator(filePath.charAt(len - 1))) {
			// 以分隔符结尾的去掉结尾分隔符
			len--;
		}
		int begin = 0;
		char c;
		for (int i = len - 1; i > -1; i--) {
			c = filePath.charAt(i);
			if (CharUtils.isFileSeparator(c)) {
				// 查找最后一个路径分隔符（/或者\）
				begin = i + 1;
				break;
			}
		}
		return filePath.substring(begin, len);
	}

	/**
	 * 返回主文件名
	 *
	 * @param fileName 完整文件名
	 * @return 主文件名
	 */
	public static String getFileNameWithoutExtension(String fileName) {
		String name = getFileName(fileName);
		String ext = getFileExtension(name);
		return StringUtils.isEmpty(ext) ? name : StringUtils.subBefore(name, "." + ext, true);
	}

	/**
	 * 获得文件的扩展名（后缀名），扩展名不带“.”
	 *
	 * @param fileName 文件名
	 * @return 扩展名
	 */
	public static String getFileExtension(String fileName) {
		if (fileName == null) {
			return null;
		}
		int index = fileName.lastIndexOf(StringConstants.STRING_DOT);
		if (index == -1) {
			return StringConstants.STRING_EMPTY;
		} else {
			// issue#I4W5FS@Gitee
			int secondToLastIndex = fileName.substring(0, index).lastIndexOf(StringConstants.STRING_DOT);
			String substr = fileName.substring(secondToLastIndex == -1 ? index : secondToLastIndex + 1);
			if (StringUtils.containsAny(substr, SPECIAL_SUFFIX)) {
				return substr;
			}
			String ext = fileName.substring(index + 1);
			// 扩展名中不能包含路径相关的符号
			return StringUtils.containsAny(ext, new char[] { UNIX_SEPARATOR, WINDOWS_SEPARATOR })
					? StringConstants.STRING_EMPTY
					: ext;
		}
	}

	/**
	 * 获取文件最后修改时间
	 * 
	 * @param filePath 文件路径
	 * @return
	 */
	public static Date getFileLastModified(String filePath) {
		File file = getFile(filePath);
		return existsFile(file) ? new Date(file.lastModified()) : null;
	}

	/**
	 * 获取文件长度
	 * 
	 * @param filePath
	 * @return
	 */
	public static Long getFileLength(String filePath) {
		return getFileLength(getFile(filePath));
	}

	/**
	 * 获取文件长度
	 * 
	 * @param file
	 * @return
	 */
	public static Long getFileLength(File file) {
		if (file == null || !file.exists() || !file.isFile()) {
			return null;
		}
		return file.length();
	}

	/**
	 * 获取文件输入流
	 * 
	 * @param file
	 * @return
	 */
	public static FileInputStream getFileInputStream(File file) {
		if (file == null || !file.exists()) {
			return null;
		}
		try {
			return new FileInputStream(file);
		} catch (Exception e) {
			LogUtils.error(FileUtils.class, e.getMessage(), e);
			return null;
		}
	}

	/**
	 * 获取文件输出流
	 * 
	 * @param file
	 * @param append
	 * @return
	 */
	public static FileOutputStream getFileOutputStream(File file, Boolean append) {
		if (file == null) {
			return null;
		}
		try {
			// 自动创建文件目录
			if (!file.exists()) {
				createDir(file.getParentFile());
			}
			if (append != null) {
				return new FileOutputStream(file, append.booleanValue());
			} else {
				return new FileOutputStream(file);
			}
		} catch (Exception e) {
			LogUtils.error(FileUtils.class, e.getMessage(), e);
			return null;
		}
	}

	/**
	 * 文件或目录是否存在
	 * 
	 * @param filePath
	 * @return
	 */
	public static boolean exists(String path) {
		File file = getFile(path);
		return exists(file);
	}

	/**
	 * 文件或目录是否存在
	 * 
	 * @param file
	 * @return
	 */
	public static boolean exists(File file) {
		return file != null && file.exists();
	}

	/**
	 * 文件是否存在
	 * 
	 * @param filePath
	 * @return
	 */
	public static boolean existsFile(String filePath) {
		File file = getFile(filePath);
		return existsFile(file);
	}

	/**
	 * 文件是否存在
	 * 
	 * @param file
	 * @return
	 */
	public static boolean existsFile(File file) {
		return file != null && file.exists() && file.isFile();
	}

	/**
	 * 创建文件
	 * 
	 * @param filePath
	 * @return
	 */
	public static boolean createFile(String filePath) {
		File file = getFile(filePath);
		if (file == null) {
			return false;
		}
		if (file.exists()) {
			return !file.isDirectory();
		}
		try {
			return file.createNewFile();
		} catch (Exception e) {
			LogUtils.error(FileUtils.class, e.getMessage(), e);
			return false;
		}
	}

	/**
	 * 删除文件
	 * 
	 * @param filePath 文件路径
	 * @return
	 */
	public static boolean deleteFile(String filePath) {
		File file = getFile(filePath);
		if (file == null) {
			return false;
		}
		if (!file.exists() || !file.isFile()) {
			return false;
		}
		try {
			return file.delete();
		} catch (Exception e) {
			LogUtils.error(FileUtils.class, e.getMessage(), e);
			return false;
		}
	}

	/**
	 * 目录是否存在
	 * 
	 * @param filePath
	 * @return
	 */
	public static boolean existsDir(String dirPath) {
		File dir = getFile(dirPath);
		return existsDir(dir);
	}

	/**
	 * 目录是否存在
	 * 
	 * @param dir
	 * @return
	 */
	public static boolean existsDir(File dir) {
		return dir != null && dir.exists() && dir.isDirectory();
	}

	/**
	 * 创建目录
	 * 
	 * @param dirPath
	 * @return
	 */
	public static boolean createDir(String dirPath) {
		return createDir(getFile(dirPath));
	}

	/**
	 * 创建目录
	 * 
	 * @param dir
	 * @return
	 */
	public static boolean createDir(File dir) {
		if (dir == null) {
			return false;
		}
		if (dir.exists()) {
			return !dir.isFile();
		}
		return dir.mkdirs();
	}

	/**
	 * 删除目录
	 * 
	 * @param dirPath 目录路径
	 * @return
	 */
	public static boolean deleteDir(String dirPath) {
		File file = getFile(dirPath);
		if (file == null) {
			return false;
		}
		if (!file.exists() || !file.isDirectory()) {
			return false;
		}
		try {
			return file.delete();
		} catch (Exception e) {
			LogUtils.error(FileUtils.class, e.getMessage(), e);
			return false;
		}
	}

	/**
	 * 获取当前工作用户目录
	 * 
	 * @return
	 */
	public static String getUserWorkDir() {
		return SystemUtils.getProperty(PropertyConstants.SYSTEM_PROPERTY_USER_DIR);
	}

	/**
	 * 获取操作系统用户目录
	 * 
	 * @return
	 */
	public static String getUserHomeDir() {
		return SystemUtils.getProperty(PropertyConstants.SYSTEM_PROPERTY_USER_HOME);
	}

	/**
	 * 获取临时目录
	 * 
	 * @return
	 */
	public static String getTempDir() {
		return SystemUtils.getProperty(PropertyConstants.SYSTEM_PROPERTY_JAVA_IO_TMPDIR);
	}

	/**
	 * 读取字节数组
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public static byte[] readBytes(String filePath) throws IOException {
		File file = getFile(filePath);
		return readBytes(file);
	}

	/**
	 * 读取字节数组
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static byte[] readBytes(File file) throws IOException {
		if (file == null || !file.exists() || !file.isFile()) {
			return null;
		}
		long len = file.length();
		if (len >= Integer.MAX_VALUE) {
			throw new IOException("File is larger then max array size");
		}
		byte[] bytes = new byte[(int) len];

		int readLength;
		try (FileInputStream in = getFileInputStream(file);) {
			readLength = in.read(bytes);
			if (readLength < len) {
				throw new IOException(StringUtils.format("File length is [{}] but read [{}]!", len, readLength));
			}
		} catch (Exception e) {
			throw new IOException(e);
		}
		return bytes;
	}

	/**
	 * 读取文件文本
	 * 
	 * @param file
	 * @param charset
	 * @return
	 * @throws IOException
	 */
	public static String readText(String filePath, String charset) throws IOException {
		return readText(getFile(filePath), charset);
	}

	/**
	 * 读取文件文本
	 * 
	 * @param file
	 * @param charset
	 * @return
	 * @throws IOException
	 */
	public static String readText(File file, String charset) throws IOException {
		byte[] bytes = readBytes(file);
		return bytes == null ? null : StringUtils.toString(bytes, charset);
	}

	/**
	 * 读取字符串行
	 * 
	 * @param filePath
	 * @param encoding
	 * @return
	 * @throws IOException
	 */
	public static List<String> readLines(String filePath, String encoding) throws IOException {
		return readLines(getFile(filePath), encoding);
	}

	/**
	 * 读取字符串行
	 * 
	 * @param file     文件
	 * @param encoding 字符集
	 * @return
	 * @throws IOException
	 */
	public static List<String> readLines(File file, String encoding) throws IOException {
		if (!existsFile(file)) {
			return null;
		}
		try (FileInputStream input = getFileInputStream(file);) {
			return IoUtils.readLines(input, encoding);
		} catch (Exception e) {
			LogUtils.error(FileUtils.class, e.getMessage(), e);
			return null;
		}
	}

	/**
	 * 写出文件二进制数据
	 * 
	 * @param filePath
	 * @param bytes
	 * @return
	 * @throws IOException
	 */
	public static int writeBytes(String filePath, byte[] bytes) throws IOException {
		return writeBytes(getFile(filePath), bytes);
	}

	/**
	 * 写出文件二进制数据
	 * 
	 * @param file
	 * @param bytes
	 * @return
	 * @throws IOException
	 */
	public static int writeBytes(File file, byte[] bytes) throws IOException {
		return writeBytes(file, bytes, false);
	}

	/**
	 * 写出文件二进制数据
	 * 
	 * @param file
	 * @param bytes
	 * @param append 是否追加数据
	 * @return
	 * @throws IOException
	 */
	public static int writeBytes(File file, byte[] bytes, final boolean append) throws IOException {
		try (FileOutputStream output = getFileOutputStream(file, append);) {
			return IoUtils.copy(bytes, output);
		} catch (FileNotFoundException e) {
			throw new IOException(e);
		}
	}

	/**
	 * 写出文件字符串数据
	 * 
	 * @param file    文件
	 * @param data    字符串
	 * @param charset 字符集
	 * @param append  是否追加数据
	 * @throws IOException
	 */
	public static long writeString(final File file, final String data, final String charset) throws IOException {
		return writeString(file, data, charset, false);
	}

	/**
	 * 写出文件字符串数据
	 * 
	 * @param file    文件
	 * @param data    字符串
	 * @param charset 字符集
	 * @param append  是否追加数据
	 * @throws IOException
	 */
	public static long writeString(final File file, final String data, final String charset, final boolean append)
			throws IOException {
		try (OutputStream out = getFileOutputStream(file, append)) {
			return IoUtils.write(data, out, charset);
		}
	}

	/**
	 * 写出文件字符串数据
	 * 
	 * @param file    文件
	 * @param data    字符串
	 * @param charset 字符集
	 * @param append  是否追加数据
	 * @throws IOException
	 */
	public static long writeString(final File file, final String data, final Charset charset) throws IOException {
		return writeString(file, data, charset, false);
	}

	/**
	 * 写出文件字符串数据
	 * 
	 * @param file    文件
	 * @param data    字符串
	 * @param charset 字符集
	 * @param append  是否追加数据
	 * @throws IOException
	 */
	public static long writeString(final File file, final String data, final Charset charset, final boolean append)
			throws IOException {
		try (OutputStream out = getFileOutputStream(file, append)) {
			return IoUtils.write(data, out, charset);
		}
	}
	
	/**
     * Writes the {@code toString()} value of each item in a collection to
     * the specified {@code File} line by line.
     * The specified character encoding and the line ending will be used.
     *
     * @param file       the file to write to
     * @param charset   the name of the requested charset, {@code null} means platform default
     * @param lines      the lines to write, {@code null} entries produce blank lines
     * @param lineEnding the line separator to use, {@code null} is system default
     * @throws IOException                          in case of an I/O error
     * @throws java.io.UnsupportedEncodingException if the encoding is not supported by the VM
     * @since 2.1
     */
    public static long writeLines(final File file, final Charset charset, final Collection<?> lines) throws IOException {
    	return writeLines(file, charset, lines, null, false);
    }
	
	/**
     * Writes the {@code toString()} value of each item in a collection to
     * the specified {@code File} line by line.
     * The specified character encoding and the line ending will be used.
     *
     * @param file       the file to write to
     * @param charset   the name of the requested charset, {@code null} means platform default
     * @param lines      the lines to write, {@code null} entries produce blank lines
     * @param lineEnding the line separator to use, {@code null} is system default
     * @throws IOException                          in case of an I/O error
     * @throws java.io.UnsupportedEncodingException if the encoding is not supported by the VM
     * @since 2.1
     */
    public static long writeLines(final File file, final Charset charset, final Collection<?> lines,
                                  final String lineEnding) throws IOException {
    	return writeLines(file, charset, lines, lineEnding, false);
    }
	
    /**
     * Writes the {@code toString()} value of each item in a collection to
     * the specified {@code File} line by line.
     * The specified character encoding and the line ending will be used.
     *
     * @param file       the file to write to
     * @param charset   the name of the requested charset, {@code null} means platform default
     * @param lines      the lines to write, {@code null} entries produce blank lines
     * @param lineEnding the line separator to use, {@code null} is system default
     * @param append     if {@code true}, then the lines will be added to the
     *                   end of the file rather than overwriting
     * @throws IOException                          in case of an I/O error
     * @throws java.io.UnsupportedEncodingException if the encoding is not supported by the VM
     * @since 2.1
     */
    public static long writeLines(final File file, final Charset charset, final Collection<?> lines,
                                  final String lineEnding, final boolean append) throws IOException {
        try (OutputStream out = new BufferedOutputStream(getFileOutputStream(file, append))) {
            return IoUtils.writeLines(lines, lineEnding, out, charset);
        }
    }

	public static void main(String[] args) {
		
	}
}
