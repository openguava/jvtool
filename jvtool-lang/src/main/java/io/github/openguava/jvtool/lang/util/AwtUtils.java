package io.github.openguava.jvtool.lang.util;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.image.BufferedImage;

/**
 * awt 工具类
 * @author openguava
 *
 */
public class AwtUtils {

	private static volatile Robot robot;
	
	/**
	 * 获取 Robot
	 * @param quiet
	 * @return
	 * @throws AWTException
	 */
	public static Robot getRobot(boolean quiet) throws AWTException {
		if(robot == null) {
			synchronized (AwtUtils.class) {
				try {
					robot = new Robot();
				} catch (AWTException e) {
					if(!quiet) {
						throw e;
					} else {
						LogUtils.error(AwtUtils.class, e.getMessage(), e);
					}
				}
			}
		}
		return robot;
	}
	
	/**
	 * 获取系统剪贴板
	 * @return
	 */
	public static Clipboard getClipboard() {
		return Toolkit.getDefaultToolkit().getSystemClipboard();
	}
	
	/**
	 * 获取系统屏幕尺寸
	 * @return
	 */
	public static Dimension getScreenSize() {
		return Toolkit.getDefaultToolkit().getScreenSize();
	}
	
	/**
	 * 获取系统屏幕宽度
	 * @return
	 */
	public static double getScreenWidth() {
		return getScreenSize().getWidth();
	}
	
	/**
	 * 获取系统屏幕高度
	 * @return
	 */
	public static double getScreenHeight() {
		return getScreenSize().getHeight();
	}
	
	/**
	 * 获取系统屏幕矩形区域
	 * @return
	 */
	public static Rectangle getScreenRectangle() {
		Dimension dimension = getScreenSize();
		return new Rectangle(dimension);
	}
	
	/**
	 * 获取系统屏幕分辨率，以每英寸的点数为单位
	 * @return
	 */
	public static int getScreenResolution() {
		return Toolkit.getDefaultToolkit().getScreenResolution();
	}
	
	/**
	 * 屏幕截图
	 * @return
	 * @throws AWTException 
	 */
	public static BufferedImage captureScreen() throws AWTException {
		Rectangle screenRect = getScreenRectangle();
		return getRobot(false).createScreenCapture(screenRect);
	}
	
	/**
	 * 获取坐标点颜色
	 * @param x
	 * @param y
	 * @return
	 * @throws AWTException
	 */
	public static Color getPixelColor(int x, int y) throws AWTException {
		return getRobot(false).getPixelColor(x, y);
	}
	
	/**
	 * 鼠标移动
	 * @param x
	 * @param y
	 * @throws AWTException
	 */
	public static void mouseMove(int x, int y) throws AWTException {
		getRobot(false).mouseMove(x, y);
	}
	
	public static void main(String[] args) {
		
	}
}
