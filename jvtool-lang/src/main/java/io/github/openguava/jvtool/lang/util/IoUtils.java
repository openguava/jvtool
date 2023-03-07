package io.github.openguava.jvtool.lang.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

import io.github.openguava.jvtool.lang.result.IResult;
import io.github.openguava.jvtool.lang.result.R;

/**
 * io工具类
 * @author openguava
 *
 */
public class IoUtils {
	
	/** 默认数据缓冲区大小 */
	public static int DEFAULT_BUFFER_SIZE = 1024 * 4;
	
	/** 数据流末尾 */
	public static final int EOF = -1;
	
    /**
     * Instances should NOT be constructed in standard programming.
     */
    protected IoUtils() {
    	
    }
    
    /**
     * 获取输入流长度
     * @param input
     * @return
     */
    public static Long getLength(InputStream input) {
    	if(input == null) {
    		return null;
    	}
    	try {
			return Integer.toUnsignedLong(input.available());
		} catch (Exception e) {
			LogUtils.debug(IoUtils.class, e.getMessage(), e);
			return null;
		}
    }
    
    /**
     * 获取字节数组输入流
     * @param bytes
     * @return
     */
    public static ByteArrayInputStream getByteArrayInputStream(byte[] bytes) {
    	return bytes == null ? null : new ByteArrayInputStream(bytes);
    }
    
    /**
     * 获取字节数字输出流
     * @return
     */
    public static ByteArrayOutputStream getByteArrayOutputStream() {
    	return new ByteArrayOutputStream();
    }
	
    /**
     * Get the contents of an <code>InputStream</code> as a <code>byte[]</code>.
     * <p>
     * This method buffers the input internally, so there is no need to use a
     * <code>BufferedInputStream</code>.
     * 
     * @param input  the <code>InputStream</code> to read from
     * @return the requested byte array
     * @throws NullPointerException if the input is null
     * @throws IOException if an I/O error occurs
     */
    public static byte[] toByteArray(InputStream input) throws IOException {
    	try (ByteArrayOutputStream output = new ByteArrayOutputStream();){
    		copy(input, output);
            return output.toByteArray();
		}
    }

    /**
     * Get the contents of a <code>Reader</code> as a <code>byte[]</code>
     * using the default character encoding of the platform.
     * <p>
     * This method buffers the input internally, so there is no need to use a
     * <code>BufferedReader</code>.
     * 
     * @param input  the <code>Reader</code> to read from
     * @return the requested byte array
     * @throws NullPointerException if the input is null
     * @throws IOException if an I/O error occurs
     */
    public static byte[] toByteArray(Reader input) throws IOException {
        try(ByteArrayOutputStream output = new ByteArrayOutputStream();) {
	        copy(input, output, null);
	        return output.toByteArray();
        }
    }
    
    /**
     * Get the contents of a <code>Reader</code> as a <code>byte[]</code>
     * using the specified character encoding.
     * <p>
     * Character encoding names can be found at
     * <a href="http://www.iana.org/assignments/character-sets">IANA</a>.
     * <p>
     * This method buffers the input internally, so there is no need to use a
     * <code>BufferedReader</code>.
     * 
     * @param input  the <code>Reader</code> to read from
     * @param encoding  the encoding to use, null means platform default
     * @return the requested byte array
     * @throws NullPointerException if the input is null
     * @throws IOException if an I/O error occurs
     * @since Commons IO 1.1
     */
    public static byte[] toByteArray(Reader input, String encoding)
            throws IOException {
    	try(ByteArrayOutputStream output = new ByteArrayOutputStream();) {
	        copy(input, output, encoding);
	        return output.toByteArray();
    	}
    }

    /**
     * Get the contents of an <code>InputStream</code> as a String
     * using the default character encoding of the platform.
     * <p>
     * This method buffers the input internally, so there is no need to use a
     * <code>BufferedInputStream</code>.
     * 
     * @param input  the <code>InputStream</code> to read from
     * @return the requested String
     * @throws NullPointerException if the input is null
     * @throws IOException if an I/O error occurs
     */
    public static String toString(InputStream input) throws IOException {
    	try(StringWriter sw = new StringWriter();) {
	        copy(input, sw, null);
	        return sw.toString();
    	}
    }

    
    /**
     * Get the contents of an <code>InputStream</code> as a String
     * using the specified character encoding.
     * <p>
     * Character encoding names can be found at
     * <a href="http://www.iana.org/assignments/character-sets">IANA</a>.
     * <p>
     * This method buffers the input internally, so there is no need to use a
     * <code>BufferedInputStream</code>.
     * 
     * @param input  the <code>InputStream</code> to read from
     * @param encoding  the encoding to use, null means platform default
     * @return the requested String
     * @throws NullPointerException if the input is null
     * @throws IOException if an I/O error occurs
     */
    public static String toString(InputStream input, String encoding)
            throws IOException {
    	try(StringWriter sw = new StringWriter();) {
	        copy(input, sw, encoding);
	        return sw.toString();
    	}
    }

    /**
     * 拷贝二进制数据到输出流
     * @param buf
     * @param output
     * @return
     * @throws IOException
     */
    public static int copy(byte[] buf, OutputStream output) throws IOException {
    	try(ByteArrayInputStream input = new ByteArrayInputStream(buf);) {
	    	return copy(input, output);
    	}
    }
    
    /**
     * Copy bytes from an <code>InputStream</code> to an
     * <code>OutputStream</code>.
     * <p>
     * This method buffers the input internally, so there is no need to use a
     * <code>BufferedInputStream</code>.
     * <p>
     * Large streams (over 2GB) will return a bytes copied value of
     * <code>-1</code> after the copy has completed since the correct
     * number of bytes cannot be returned as an int. For large streams
     * use the <code>copyLarge(InputStream, OutputStream)</code> method.
     * 
     * @param input  the <code>InputStream</code> to read from
     * @param output  the <code>OutputStream</code> to write to
     * @return the number of bytes copied
     * @throws NullPointerException if the input or output is null
     * @throws IOException if an I/O error occurs
     * @throws ArithmeticException if the byte count is too large
     * @since Commons IO 1.1
     */
    public static int copy(InputStream input, OutputStream output) throws IOException {
        long count = copyLarge(input, output, DEFAULT_BUFFER_SIZE, null);
        if (count > Integer.MAX_VALUE) {
            return -1;
        }
        return (int) count;
    }

    /**
     * Copy bytes from a large (over 2GB) <code>InputStream</code> to an
     * <code>OutputStream</code>.
     * <p>
     * This method buffers the input internally, so there is no need to use a
     * <code>BufferedInputStream</code>.
     * 
     * @param input  the <code>InputStream</code> to read from
     * @param output  the <code>OutputStream</code> to write to
     * @param bufferSize
     * @param copyProgress
     * @return the number of bytes copied
     * @throws NullPointerException if the input or output is null
     * @throws IOException if an I/O error occurs
     * @since Commons IO 1.3
     */
    public static long copyLarge(InputStream input, OutputStream output, int bufferSize, IoCopyProgress copyProgress)
            throws IOException {
        byte[] buffer = new byte[bufferSize];
        long count = 0;
        int n = 0;
        if(copyProgress != null) {
        	copyProgress.start();
        }
        while (EOF != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
            if(copyProgress != null) {
            	copyProgress.progress(count);
            }
        }
        if(copyProgress != null) {
        	copyProgress.finish();
        }
        return count;
    }

    /**
     * Copy bytes from an <code>InputStream</code> to chars on a
     * <code>Writer</code> using the specified character encoding.
     * <p>
     * This method buffers the input internally, so there is no need to use a
     * <code>BufferedInputStream</code>.
     * <p>
     * Character encoding names can be found at
     * <a href="http://www.iana.org/assignments/character-sets">IANA</a>.
     * <p>
     * This method uses {@link InputStreamReader}.
     *
     * @param input  the <code>InputStream</code> to read from
     * @param output  the <code>Writer</code> to write to
     * @param encoding  the encoding to use, null means platform default
     * @throws NullPointerException if the input or output is null
     * @throws IOException if an I/O error occurs
     * @since Commons IO 1.1
     */
    public static void copy(InputStream input, Writer output, String encoding)
            throws IOException {
    	InputStreamReader in = null;
    	if(encoding == null || encoding.isEmpty()) {
    		in = new InputStreamReader(input);
    	} else {
    		in = new InputStreamReader(input, encoding);
    	}
    	// copy reader -> writer
    	copy(in, output);
    }
    
    /**
     * Copy chars from a <code>Reader</code> to bytes on an
     * <code>OutputStream</code> using the specified character encoding, and
     * calling flush.
     * <p>
     * This method buffers the input internally, so there is no need to use a
     * <code>BufferedReader</code>.
     * <p>
     * Character encoding names can be found at
     * <a href="http://www.iana.org/assignments/character-sets">IANA</a>.
     * <p>
     * Due to the implementation of OutputStreamWriter, this method performs a
     * flush.
     * <p>
     * This method uses {@link OutputStreamWriter}.
     *
     * @param input  the <code>Reader</code> to read from
     * @param output  the <code>OutputStream</code> to write to
     * @param encoding  the encoding to use, null means platform default
     * @throws NullPointerException if the input or output is null
     * @throws IOException if an I/O error occurs
     * @since Commons IO 1.1
     */
    public static void copy(Reader input, OutputStream output, String encoding)
            throws IOException {
    	OutputStreamWriter out = null;
    	if(encoding == null || encoding.isEmpty()) {
    		out = new OutputStreamWriter(output);
    	} else {
    		out = new OutputStreamWriter(output, encoding);
    	}
    	// copy reader -> writer
    	copy(input, out);
    	// flush
        out.flush();
    }
	
    /**
     * Copy chars from a <code>Reader</code> to a <code>Writer</code>.
     * <p>
     * This method buffers the input internally, so there is no need to use a
     * <code>BufferedReader</code>.
     * <p>
     * Large streams (over 2GB) will return a chars copied value of
     * <code>-1</code> after the copy has completed since the correct
     * number of chars cannot be returned as an int. For large streams
     * use the <code>copyLarge(Reader, Writer)</code> method.
     *
     * @param input  the <code>Reader</code> to read from
     * @param output  the <code>Writer</code> to write to
     * @return the number of characters copied
     * @throws NullPointerException if the input or output is null
     * @throws IOException if an I/O error occurs
     * @throws ArithmeticException if the character count is too large
     * @since Commons IO 1.1
     */
    public static int copy(Reader input, Writer output) throws IOException {
        long count = copyLarge(input, output, DEFAULT_BUFFER_SIZE, null);
        if (count > Integer.MAX_VALUE) {
            return -1;
        }
        return (int)count;
    }
    
    /**
     * Copy chars from a large (over 2GB) <code>Reader</code> to a <code>Writer</code>.
     * <p>
     * This method buffers the input internally, so there is no need to use a
     * <code>BufferedReader</code>.
     *
     * @param input  the <code>Reader</code> to read from
     * @param output  the <code>Writer</code> to write to
     * @param bufferSize the buffer size
     * @param copyProgress 
     * @return the number of characters copied
     * @throws NullPointerException if the input or output is null
     * @throws IOException if an I/O error occurs
     * @since Commons IO 1.3
     */
    public static long copyLarge(Reader input, Writer output, int bufferSize, IoCopyProgress copyProgress) throws IOException {
        char[] buffer = new char[bufferSize];
        long count = 0;
        int n = 0;
		if (null != copyProgress) {
			copyProgress.start();
		}
        while (EOF != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
    		if (null != copyProgress) {
    			copyProgress.progress(count);
    		}
        }
		if (null != copyProgress) {
			copyProgress.finish();
		}
        return count;
    }
    
    /**
     * Writes bytes from a {@code byte[]} to an {@code OutputStream}.
     *
     * @param data the byte array to write, do not modify during output,
     * null ignored
     * @param output the {@code OutputStream} to write to
     * @throws NullPointerException if output is null
     * @throws IOException          if an I/O error occurs
     * @since 1.1
     */
    public static long write(final byte[] data, final OutputStream output)
            throws IOException {
    	if(data == null) {
    		return 0L;
    	}
    	output.write(data);
    	return data.length;
    }
    
    /**
     * Writes chars from a {@code String} to bytes on an
     * {@code OutputStream} using the specified character encoding.
     * <p>
     * Character encoding names can be found at
     * <a href="http://www.iana.org/assignments/character-sets">IANA</a>.
     * <p>
     * This method uses {@link String#getBytes(String)}.
     *
     * @param data the {@code String} to write, null ignored
     * @param output the {@code OutputStream} to write to
     * @param charsetName the name of the requested charset, null means platform default
     * @throws NullPointerException        if output is null
     * @throws IOException                 if an I/O error occurs
     * @throws java.nio.charset.UnsupportedCharsetException thrown instead of {@link java.io
     * .UnsupportedEncodingException} in version 2.2 if the encoding is not supported.
     * @since 1.1
     */
    public static long write(final String data, final OutputStream output, final String charsetName)
            throws IOException {
        return write(data, output, CharsetUtils.charset(charsetName));
    }
    
    /**
     * Writes chars from a {@code String} to bytes on an
     * {@code OutputStream} using the specified character encoding.
     * <p>
     * This method uses {@link String#getBytes(String)}.
     *
     * @param data the {@code String} to write, null ignored
     * @param output the {@code OutputStream} to write to
     * @param charset the charset to use, null means platform default
     * @throws NullPointerException if output is null
     * @throws IOException          if an I/O error occurs
     * @since 2.3
     */
    public static long write(final String data, final OutputStream output, final Charset charset) throws IOException {
        if(data == null) {
        	return 0L;
        }
        byte[] bytes = data.getBytes(CharsetUtils.charset(charset));
        output.write(bytes);
        return bytes.length;
    }
    
    /**
     * Writes the {@code toString()} value of each item in a collection to
     * an {@code OutputStream} line by line, using the specified character
     * encoding and the specified line ending.
     *
     * @param lines the lines to write, null entries produce blank lines
     * @param output the {@code OutputStream} to write to, not null, not closed
     * @param charset the charset to use, null means platform default
     * @throws NullPointerException if the output is null
     * @throws IOException          if an I/O error occurs
     * @since 2.3
     */
    public static long writeLines(final Collection<?> lines, final OutputStream output,
                                  final Charset charset) throws IOException {
    	return writeLines(lines, null, output, charset);
    }
    
    /**
     * Writes the {@code toString()} value of each item in a collection to
     * an {@code OutputStream} line by line, using the specified character
     * encoding and the specified line ending.
     *
     * @param lines the lines to write, null entries produce blank lines
     * @param lineEnding the line separator to use, null is system default
     * @param output the {@code OutputStream} to write to, not null, not closed
     * @param charset the charset to use, null means platform default
     * @throws NullPointerException if the output is null
     * @throws IOException          if an I/O error occurs
     * @since 2.3
     */
    public static long writeLines(final Collection<?> lines, String lineEnding, final OutputStream output,
                                  final Charset charset) throws IOException {
        if (lines == null) {
            return 0L;
        }
        if (lineEnding == null) {
            lineEnding = System.lineSeparator();
        }
        long writeBytes = 0L;
        final Charset cs = CharsetUtils.charset(charset);
        for (final Object line : lines) {
        	byte[] buffer = null;
            if (line != null) {
                output.write((buffer = line.toString().getBytes(cs)));
                writeBytes += buffer.length;
            }
            output.write((buffer = lineEnding.getBytes(cs)));
            writeBytes += buffer.length;
        }
        return writeBytes;
    }
    
    /**
     * Get the contents of an <code>InputStream</code> as a list of Strings,
     * one entry per line, using the specified character encoding.
     * <p>
     * Character encoding names can be found at
     * <a href="http://www.iana.org/assignments/character-sets">IANA</a>.
     * <p>
     * This method buffers the input internally, so there is no need to use a
     * <code>BufferedInputStream</code>.
     *
     * @param input  the <code>InputStream</code> to read from, not null
     * @param encoding  the encoding to use, null means platform default
     * @return the list of Strings, never null
     * @throws NullPointerException if the input is null
     * @throws IOException if an I/O error occurs
     * @since Commons IO 1.1
     */
    public static List<String> readLines(InputStream input, String encoding) throws IOException {
    	InputStreamReader reader = null;
    	if(encoding == null || encoding.isEmpty()) {
    		reader = new InputStreamReader(input);
    	} else {
    		reader = new InputStreamReader(input, encoding);
    	}
    	return readLines(reader);
    }
    
    /**
     * Get the contents of a <code>Reader</code> as a list of Strings,
     * one entry per line.
     * <p>
     * This method buffers the input internally, so there is no need to use a
     * <code>BufferedReader</code>.
     *
     * @param input  the <code>Reader</code> to read from, not null
     * @return the list of Strings, never null
     * @throws NullPointerException if the input is null
     * @throws IOException if an I/O error occurs
     * @since Commons IO 1.1
     */
    public static List<String> readLines(Reader input) throws IOException {
        BufferedReader reader = new BufferedReader(input);
        List<String> list = new ArrayList<>();
        String line = reader.readLine();
        while (line != null) {
            list.add(line);
            line = reader.readLine();
        }
        return list;
    }
    
    /**
     * Compare the contents of two Streams to determine if they are equal or
     * not.
     * <p>
     * This method buffers the input internally using
     * <code>BufferedInputStream</code> if they are not already buffered.
     *
     * @param input1  the first stream
     * @param input2  the second stream
     * @return true if the content of the streams are equal or they both don't
     * exist, false otherwise
     * @throws NullPointerException if either input is null
     * @throws IOException if an I/O error occurs
     */
    public static boolean contentEquals(InputStream input1, InputStream input2)
            throws IOException {
        if (!(input1 instanceof BufferedInputStream)) {
            input1 = new BufferedInputStream(input1);
        }
        if (!(input2 instanceof BufferedInputStream)) {
            input2 = new BufferedInputStream(input2);
        }
        int ch = input1.read();
        while (-1 != ch) {
            int ch2 = input2.read();
            if (ch != ch2) {
                return false;
            }
            ch = input1.read();
        }
        int ch2 = input2.read();
        return (ch2 == -1);
    }

    /**
     * Compare the contents of two Readers to determine if they are equal or
     * not.
     * <p>
     * This method buffers the input internally using
     * <code>BufferedReader</code> if they are not already buffered.
     *
     * @param input1  the first reader
     * @param input2  the second reader
     * @return true if the content of the readers are equal or they both don't
     * exist, false otherwise
     * @throws NullPointerException if either input is null
     * @throws IOException if an I/O error occurs
     * @since Commons IO 1.1
     */
    public static boolean contentEquals(Reader input1, Reader input2)
            throws IOException {
        if (!(input1 instanceof BufferedReader)) {
            input1 = new BufferedReader(input1);
        }
        if (!(input2 instanceof BufferedReader)) {
            input2 = new BufferedReader(input2);
        }
        int ch = input1.read();
        while (-1 != ch) {
            int ch2 = input2.read();
            if (ch != ch2) {
                return false;
            }
            ch = input1.read();
        }
        int ch2 = input2.read();
        return (ch2 == -1);
    }
	
	/**
     * Unconditionally close an <code>Reader</code>.
     * <p>
     * Equivalent to {@link Reader#close()}, except any exceptions will be ignored.
     * This is typically used in finally blocks.
     *
     * @param input  the Reader to close, may be null or already closed
     */
    public static void closeQuietly(Reader input) {
        try {
            if (input != null) {
                input.close();
            }
        } catch (IOException ioe) {
            // ignore
        }
    }

    /**
     * Unconditionally close a <code>Writer</code>.
     * <p>
     * Equivalent to {@link Writer#close()}, except any exceptions will be ignored.
     * This is typically used in finally blocks.
     *
     * @param output  the Writer to close, may be null or already closed
     */
    public static void closeQuietly(Writer output) {
        try {
            if (output != null) {
                output.close();
            }
        } catch (IOException ioe) {
            // ignore
        }
    }

    /**
     * Unconditionally close an <code>InputStream</code>.
     * <p>
     * Equivalent to {@link InputStream#close()}, except any exceptions will be ignored.
     * This is typically used in finally blocks.
     *
     * @param input  the InputStream to close, may be null or already closed
     */
    public static void closeQuietly(InputStream input) {
        try {
            if (input != null) {
                input.close();
            }
        } catch (IOException ioe) {
            // ignore
        }
    }

    /**
     * Unconditionally close an <code>OutputStream</code>.
     * <p>
     * Equivalent to {@link OutputStream#close()}, except any exceptions will be ignored.
     * This is typically used in finally blocks.
     *
     * @param output  the OutputStream to close, may be null or already closed
     */
    public static void closeQuietly(OutputStream output) {
        try {
            if (output != null) {
                output.close();
            }
        } catch (IOException ioe) {
            // ignore
        }
    }

	
	/**
	 * 关闭<br>
	 * 关闭失败不会抛出异常
	 *
	 * @param closeable 被关闭的对象
	 * @return
	 */
	public static boolean close(Closeable closeable) {
		if(closeable == null) {
			return false;
		}
		try {
			closeable.close();
			return true;
		} catch (Exception e) {
			// 静默关闭
			LogUtils.warn(IoUtils.class, e.getMessage(), e);
			return false;
		}
	}
	
	/**
	 * 关闭
	 * @param closeable
	 * @return
	 */
	public static boolean close(AutoCloseable closeable) {
		if(closeable == null) {
			return false;
		}
		try {
			closeable.close();
			return true;
		} catch (Exception e) {
			// 静默关闭
			LogUtils.warn(IoUtils.class, e.getMessage(), e);
			return false;
		}
	}
	
	/**
	 * 从缓存中刷出数据
	 *
	 * @param flushable {@link Flushable}
	 * @return
	 */
	public static boolean flush(Flushable flushable) {
		if(flushable == null) {
			return false;
		}
		try {
			flushable.flush();
			return true;
		} catch (Exception e) {
			// 静默刷出
			LogUtils.warn(IoUtils.class, e.getMessage(), e);
			return false;
		}
	}
	
	/**
	 * 写入流转为换写入动作
	 * @param writeStream
	 * @return
	 */
	public static Function<OutputStream, IResult<?>> toWriteFunc(final InputStream writeStream) {
		return toWriteFunc(writeStream, true, false);
	}
	
	/**
	 * 写入流转为换写入动作
	 * @param writeStream
	 * @param flushOutputStream 是否刷新输出流
	 * @return
	 */
	public static Function<OutputStream, IResult<?>> toWriteFunc(final InputStream writeStream, final boolean flushOutputStream) {
		return toWriteFunc(writeStream, flushOutputStream, false);
	}
	
	/**
	 * 写入流转为换写入动作
	 * @param writeStream
	 * @param flushOutputStream 是否刷新输出流
	 * @param closeWriteStream 是否关闭写入流
	 * @return
	 */
	public static Function<OutputStream, IResult<?>> toWriteFunc(final InputStream writeStream, final boolean flushOutputStream, final boolean closeWriteStream) {
		if(writeStream == null) {
			return null;
		}
		return new Function<OutputStream, IResult<?>>() {
			
			@Override
			public IResult<?> apply(OutputStream output) {
				try {
					IoUtils.copy(writeStream, output);
					if(flushOutputStream) {
						IoUtils.flush(output);
					}
					return R.setOk();
				} catch (Exception e) {
					LogUtils.error(IoUtils.class, e.getMessage(), e);
					return R.setFail(e.getMessage());
				} finally {
					if(closeWriteStream) {
						IoUtils.close(writeStream);
					}
				}
			}
		};
	}
	
	/**
	 * 读取流转换为读取动作
	 * @param readStream 读入流
	 * @param flushReadStream 是否刷新输入流 
	 * @return
	 */
	public static Function<InputStream, IResult<?>> toReadFunc(final OutputStream readStream) {
		return toReadFunc(readStream, true, false);
	}
	
	/**
	 * 读取流转换为读取动作
	 * @param readStream 读入流
	 * @param flushReadStream 是否刷新输入流 
	 * @return
	 */
	public static Function<InputStream, IResult<?>> toReadFunc(final OutputStream readStream, final boolean flushReadStream) {
		return toReadFunc(readStream, flushReadStream, false);
	}
	/**
	 * 读取流转换为读取动作
	 * @param readStream 读入流
	 * @param flushReadStream 是否刷新输入流
	 * @param closeInputStream 是否关闭输入流 
	 * @return
	 */
	public static Function<InputStream, IResult<?>> toReadFunc(final OutputStream readStream, final boolean flushReadStream, final boolean closeInputStream) {
		if(readStream == null) {
			return null;
		}
		return new Function<InputStream, IResult<?>>() {
			
			@Override
			public IResult<?> apply(InputStream input) {
				try {
					IoUtils.copy(input, readStream);
					if(flushReadStream) {
						IoUtils.flush(readStream);
					}
					return R.setOk();
				} catch (Exception e) {
					LogUtils.error(IoUtils.class, e.getMessage(), e);
					return R.setFail(e.getMessage());
				} finally {
					if(closeInputStream) {
						IoUtils.close(input);
					}
				}
			}
		};
	}
	
	/**
	 * io拷贝进度
	 * @author openguava
	 *
	 */
	public static interface IoCopyProgress {
		
		/**
		 * 开始
		 */
		public void start();
		
		/**
		 * 进行中
		 * @param size 已经进行的大小
		 */
		public void progress(long size);
		
		/**
		 * 结束
		 */
		public void finish();
	}
}
