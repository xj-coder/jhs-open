package com.jhs.open.tool;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

public class ImageTools {
	private static Map<String, BufferedImage> imgMap = new HashMap<String, BufferedImage>();

	public static BufferedImage getImage(String path) {
		BufferedImage img = imgMap.get(path);
		if (img == null) {
			img = loadImage(path);
			imgMap.put(path, img);
		}
		return img;
	}

	/**
	 * 剪切图片
	 * 
	 * @param path
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param format
	 * @return
	 */
	public static Image cut(String path, int x, int y, int width, int height, String format) {
		Image result = null;

		ImageInputStream iis = null;

		try {

			Iterator<ImageReader> it = ImageIO.getImageReadersByFormatName(format);
			ImageReader reader = it.next();

			iis = ImageIO.createImageInputStream(new File(path));

			reader.setInput(iis, true);

			ImageReadParam param = reader.getDefaultReadParam();

			Rectangle rect = new Rectangle(x, y, width, height);

			param.setSourceRegion(rect);

			BufferedImage bi = reader.read(0, param);

			result = Toolkit.getDefaultToolkit().createImage(bi.getSource());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (iis != null) {
				try {
					iis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	/** */
	/**
	 * 旋转图片为指定角度
	 * 
	 * @param bufferedimage
	 *            目标图像
	 * @param degree
	 *            旋转角度
	 * @return
	 */
	public static Image rotateImage(final Image image, final int degree) {
		int w = image.getWidth(null);
		int h = image.getHeight(null);
		BufferedImage img;
		Graphics2D graphics2d;
		(graphics2d = (img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB)).createGraphics()).setRenderingHint(
				RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		graphics2d.rotate(Math.toRadians(degree), w / 2, h / 2);
		graphics2d.drawImage(image, 0, 0, null);
		graphics2d.dispose();
		return img;
	}

	/** */
	/**
	 * 变更图像为指定大小
	 * 
	 * @param bufferedimage
	 *            目标图像
	 * @param w
	 *            宽
	 * @param h
	 *            高
	 * @return
	 */
	public static BufferedImage resizeImage(final BufferedImage bufferedimage, final int w, final int h) {
		int type = bufferedimage.getColorModel().getTransparency();
		BufferedImage img;
		Graphics2D graphics2d;
		(graphics2d = (img = createImage(w, h, type)).createGraphics()).setRenderingHint(
				RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		graphics2d
				.drawImage(bufferedimage, 0, 0, w, h, 0, 0, bufferedimage.getWidth(), bufferedimage.getHeight(), null);
		graphics2d.dispose();
		return img;
	}

	/** */
	/**
	 * 水平翻转图像
	 * 
	 * @param bufferedimage
	 *            目标图像
	 * @return
	 */
	public static BufferedImage flipImage(final BufferedImage bufferedimage) {
		int w = bufferedimage.getWidth();
		int h = bufferedimage.getHeight();
		BufferedImage img;
		Graphics2D graphics2d;
		(graphics2d = (img = createImage(w, h, bufferedimage.getColorModel().getTransparency())).createGraphics())
				.drawImage(bufferedimage, 0, 0, w, h, w, 0, 0, h, null);
		graphics2d.dispose();
		return img;
	}

	public static BufferedImage createImage(int width, int height, int type) {
		return new BufferedImage(width, height, type);
	}

	public static BufferedImage createImage(int width, int height, boolean flag) {
		if (flag) {
			return new BufferedImage(width, height, 2);
		} else {
			return new BufferedImage(width, height, 1);
		}
	}

	private static BufferedImage loadImage(String path) {
		BufferedImage bufferedimage = null;
		try {
			bufferedimage = ImageIO.read(new File(path));
		} catch (Exception exception) {
			System.err.print(new StringBuilder().append("Could not load file ").append(path).toString());
		}
		return bufferedimage;
	}
}
