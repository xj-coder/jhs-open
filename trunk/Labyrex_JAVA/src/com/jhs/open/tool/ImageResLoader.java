package com.jhs.open.tool;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ImageResLoader {

	private static Map<String, BufferedImage> imgMap = new HashMap<String, BufferedImage>();

	public static BufferedImage getImage(String path) {
		BufferedImage img = imgMap.get(path);
		if (img == null) {
			img = loadImage(path);
			imgMap.put(path, img);
		}
		return img;
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
