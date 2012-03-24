package c.city.desolate.ui.canvas.game;

import c.city.desolate.tool.GraphicsTools;
import c.city.desolate.ui.Canvas;

import java.awt.*;

public class InfoCanvas extends Canvas {
	private static final long serialVersionUID = 7579261364033303534L;

	public InfoCanvas(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	@Override
	public void render(Graphics g) {
		GraphicsTools.backupGraphics((Graphics2D) g);

		g.setColor(Color.yellow);
		g.drawRect(x, y, width, height);

		GraphicsTools.restoreGraphics((Graphics2D) g);
		super.render(g);
	}
}
