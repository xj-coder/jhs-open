package com.jhs.open.ui.canvas.game;

import java.awt.Color;
import java.awt.Graphics;

import com.jhs.open.tool.GraphicsTools;
import com.jhs.open.ui.Canvas;


public class InfoCanvas extends Canvas {
	private static final long serialVersionUID = 7579261364033303534L;

	public InfoCanvas(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	@Override
	public void render(Graphics g) {
		GraphicsTools.backupGraphics(g);

		g.setColor(Color.yellow);
		g.drawRect(x, y, width, height);

		GraphicsTools.restoreGraphics(g);
		super.render(g);
	}
}
