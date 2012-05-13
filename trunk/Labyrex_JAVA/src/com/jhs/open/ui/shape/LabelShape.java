package com.jhs.open.ui.shape;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.jhs.open.ui.Canvas;

public class LabelShape extends Canvas {

	public String text = "";
	public Font font;
	public Color fgColor;
	public Color bgColor;

	public LabelShape(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	@Override
	public void init() {
		font = new Font(Font.DIALOG, Font.PLAIN, 12);
		fgColor = Color.red;
	}

	@Override
	public void render(Graphics g) {
		super.render(g);

		Font f = g.getFont();
		Color c = g.getColor();

		offsetX = x;
		offsetY = y;
		if (owner != null) {
			offsetX = owner.x + x;
			offsetY = owner.y + y;
		}

		if (bgColor != null) {
			g.setColor(bgColor);
			g.fillRect(offsetX + x, offsetY + y, width, height);
		}
		g.setFont(font);
		g.setColor(fgColor);
		g.drawString(text, offsetX + x, offsetY + y);

		g.setFont(f);
		g.setColor(c);
	}

}
