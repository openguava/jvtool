package io.github.openguava.jvtool.lang.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;

/**
 * 图像工具类
 * @author openguava
 *
 */
public class ImageUtils {

	protected ImageUtils() {
		
	}
	
	/**
	 * 从文件中读取图片
	 *
	 * @param imageFile 图片文件
	 * @return 图片
	 */
	public static BufferedImage readImage(String imageFile) {
		return readImage(FileUtils.getFile(imageFile));
	}
	/**
	 * 从文件中读取图片
	 *
	 * @param imageFile 图片文件
	 * @return 图片
	 */
	public static BufferedImage readImage(File imageFile) {
		if(imageFile == null || !imageFile.exists()) {
			return null;
		}
		try {
			BufferedImage result = ImageIO.read(imageFile);
			return result;
		} catch (IOException e) {
			LogUtils.error(ImageUtils.class, e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * 从流中读取图片
	 *
	 * @param imageStream 文件数据流
	 * @return 图片
	 */
	public static BufferedImage readImage(InputStream inputStream) {
		if(inputStream == null) {
			return null;
		}
		try {
			BufferedImage result = ImageIO.read(inputStream);
			return result;
		} catch (IOException e) {
			LogUtils.error(ImageUtils.class, e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * 从图片流中读取图片
	 *
	 * @param imageStream 图片数据流
	 * @return 图片
	 */
	public static BufferedImage readImage(ImageInputStream imageStream) {
		if(imageStream != null) {
			return null;
		}
		try {
			BufferedImage result = ImageIO.read(imageStream);
			return result;
		} catch (IOException e) {
			LogUtils.error(ImageUtils.class, e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * 获取{@link ImageInputStream}
	 *
	 * @param in {@link InputStream}
	 * @return {@link ImageInputStream}
	 */
	public static ImageInputStream getImageInputStream(InputStream in) {
		if(in == null) {
			return null;
		}
		try {
			ImageOutputStream result = ImageIO.createImageOutputStream(in);
			return result;
		} catch (IOException e) {
			LogUtils.error(ImageUtils.class, e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * 获取{@link ImageOutputStream}
	 *
	 * @param outFile {@link File}
	 * @return {@link ImageOutputStream}
	 */
	public static ImageOutputStream getImageOutputStream(File outFile) {
		if(outFile == null) {
			return null;
		}
		try {
			ImageOutputStream result = ImageIO.createImageOutputStream(outFile);
			return result;
		} catch (IOException e) {
			LogUtils.error(ImageUtils.class, e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * 获取{@link ImageOutputStream}
	 *
	 * @param out {@link OutputStream}
	 * @return {@link ImageOutputStream}
	 */
	public static ImageOutputStream getImageOutputStream(OutputStream out) {
		if(out == null) {
			return null;
		}
		try {
			ImageOutputStream result = ImageIO.createImageOutputStream(out);
			return result;
		} catch (IOException e) {
			LogUtils.error(ImageUtils.class, e.getMessage(), e);
			return null;
		}
	}
}
