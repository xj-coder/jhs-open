package c.city.desolate.ui.shape;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import c.city.desolate.ui.Canvas;

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

		if (bgColor != null) {
			g.setColor(bgColor);
			g.fillRect(owner.x + x, owner.y + y, width, height);
		}
		g.setFont(font);
		g.setColor(fgColor);
		g.drawString(text, owner.x + x, owner.y + y);

		g.setFont(f);
		g.setColor(c);
	}

}
