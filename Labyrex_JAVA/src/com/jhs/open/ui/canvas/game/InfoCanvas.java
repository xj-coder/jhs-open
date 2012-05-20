package com.jhs.open.ui.canvas.game;

import java.awt.Graphics;

import com.jhs.open.tool.GraphicsTools;
import com.jhs.open.ui.Canvas;

public class InfoCanvas extends Canvas {

	public InfoCanvas(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	@Override
	public void render(Graphics g) {
		GraphicsTools.backupGraphics(g);

		GraphicsTools.restoreGraphics(g);
		super.render(g);
	}
}
