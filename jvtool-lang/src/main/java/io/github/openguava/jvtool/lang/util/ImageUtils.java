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
	
	/**
	 * 获取图片文件扩展名
	 * @param imageBytes 图片数据
	 * @return
	 */
	public static String getImageFileExtendName(byte[] imageBytes) {
		if(ArrayUtils.isEmpty(imageBytes)) {
			return null;
		}
		String strFileExtendName = "JPG";
		if ((imageBytes[0] == 71) && (imageBytes[1] == 73) && (imageBytes[2] == 70) && (imageBytes[3] == 56)
				&& ((imageBytes[4] == 55) || (imageBytes[4] == 57)) && (imageBytes[5] == 97)) {
			strFileExtendName = "GIF";
		} else if ((imageBytes[6] == 74) && (imageBytes[7] == 70) && (imageBytes[8] == 73) && (imageBytes[9] == 70)) {
			strFileExtendName = "JPG";
		} else if ((imageBytes[0] == 66) && (imageBytes[1] == 77)) {
			strFileExtendName = "BMP";
		} else if ((imageBytes[1] == 80) && (imageBytes[2] == 78) && (imageBytes[3] == 71)) {
			strFileExtendName = "PNG";
		}
		return strFileExtendName;
	}
}
