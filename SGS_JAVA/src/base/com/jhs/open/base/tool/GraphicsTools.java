package com.jhs.open.base.tool;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Stroke;
import java.util.HashMap;
import java.util.Map;

public class GraphicsTools {

	private static Map<Graphics, GraphicsDate> graphicsMap = new HashMap<Graphics, GraphicsDate>();

	public static void backupGraphics(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		GraphicsDate gd = new GraphicsDate();
		gd.stroke = g2.getStroke();
		gd.color = g2.getColor();
		gd.paint = g2.getPaint();

		graphicsMap.put(g, gd);
	}

	public static void restoreGraphics(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		GraphicsDate gd = graphicsMap.get(g2);
		if (gd != null) {
			g2.setStroke(gd.stroke);
			g2.setColor(gd.color);
			g2.setPaint(gd.paint);
		}
		graphicsMap.remove(g2);
	}

}

class GraphicsDate {
	Stroke stroke;
	Color color;
	Paint paint;
}
