package c.city.desolate.tool;

import java.awt.*;
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
	}

}

class GraphicsDate {
	Stroke stroke;
	Color color;
	Paint paint;
}
