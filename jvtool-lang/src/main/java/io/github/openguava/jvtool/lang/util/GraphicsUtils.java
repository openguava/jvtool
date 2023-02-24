package io.github.openguava.jvtool.lang.util;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

/**
 * 绘图工具类
 * @author openguava
 *
 */
public class GraphicsUtils {

	protected GraphicsUtils() {
		
	}
	
	/**
	 * 创建{@link Graphics2D}
	 *
	 * @param image {@link BufferedImage}
	 * @param color {@link Color}背景颜色以及当前画笔颜色，{@code null}表示不设置背景色
	 * @return {@link Graphics2D}
	 */
	public static Graphics2D createGraphics(BufferedImage image, Color color) {
		final Graphics2D g = image.createGraphics();
		if (color != null) {
			// 填充背景
			g.setColor(color);
			g.fillRect(0, 0, image.getWidth(), image.getHeight());
		}
		return g;
	}
	
	/**
	 * 设置画笔透明度
	 *
	 * @param g 画笔
	 * @param alpha 透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
	 * @return 画笔
	 */
	public static Graphics2D setAlpha(Graphics2D g, float alpha){
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
		return g;
	}
	
	/**
	 * 绘制字符串，默认抗锯齿
	 *
	 * @param g     {@link Graphics}画笔
	 * @param str   字符串
	 * @param font  字体，字体大小决定了在背景中绘制的大小
	 * @param color 字体颜色，{@code null} 表示使用黑色
	 * @param point 绘制字符串的位置坐标
	 * @return 画笔对象
	 */
	public static Graphics drawString(Graphics g, String str, Font font, Color color, Point point) {
		// 抗锯齿
		if (g instanceof Graphics2D) {
			((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		}
		g.setFont(font);
		g.setColor(color != null ? color : Color.BLACK);
		g.drawString(str, point.x, point.y);

		return g;
	}
	
	/**
	 * 绘制图片
	 *
	 * @param g         画笔
	 * @param img       要绘制的图片
	 * @param point 绘制的位置，基于左上角
	 * @return 画笔对象
	 */
	public static Graphics drawImage(Graphics g, Image img, Point point) {
		return drawImage(g, img, new Rectangle(point.x, point.y, img.getWidth(null), img.getHeight(null)));
	}
	
	/**
	 * 绘制图片
	 *
	 * @param g         画笔
	 * @param img       要绘制的图片
	 * @param rectangle 矩形对象，表示矩形区域的x，y，width，height,，基于左上角
	 * @return 画笔对象
	 */
	public static Graphics drawImage(Graphics g, Image img, Rectangle rectangle) {
		g.drawImage(img, rectangle.x, rectangle.y, rectangle.width, rectangle.height, null); // 绘制切割后的图
		return g;
	}
}
