package com.jhs.open.ui.shape;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Stroke;

import com.jhs.open.Define;
import com.jhs.open.bean.MapBean;
import com.jhs.open.control.event.card.CardMouseClickedEvent;
import com.jhs.open.control.event.card.CardMouseMoveEvent;
import com.jhs.open.tool.ImageTools;
import com.jhs.open.ui.Canvas;

public class CardShape extends Canvas {

	private Image cardWImage;
	private Image cardBImage;

	private MapBean map;

	public CardShape(int x, int y, MapBean map) {
		super(x, y, Define.MenuPanel.card_size, Define.MenuPanel.card_size);
		this.map = map;
	}

	@Override
	public void init() {
		cardWImage = ImageTools.getImage(Define.MenuPanel.card_white_image_path);
		cardBImage = ImageTools.getImage(Define.MenuPanel.card_black_image_path);

		addMouseListener(new CardMouseClickedEvent(this));
		addMouseListener(new CardMouseMoveEvent());
	}

	public MapBean getMapBean() {
		return map;
	}

	public void setMapBean(MapBean map) {
		this.map = map;
	}

	@Override
	public void render(Graphics g) {
		super.render(g);

		offsetX = x;
		offsetY = y;
		if (owner != null) {
			offsetX = owner.x + x;
			offsetY = owner.y + y;
		}

		Graphics2D g2 = (Graphics2D) g;
		if (map.isEnabled) {
			g2.drawImage(cardWImage, offsetX, offsetY, width, height, null);

			if (isOver) {
				Stroke s = g2.getStroke();
				Color c = g2.getColor();

				g2.setStroke(new BasicStroke(Define.MenuPanel.border_size * 3 / 2));
				g2.setColor(new Color(0xff, 0xff, 0xff, 200));
				g2.drawRect(offsetX - Define.MenuPanel.border_size, offsetY - Define.MenuPanel.border_size, width
						+ Define.MenuPanel.border_size, height + Define.MenuPanel.border_size);

				g2.setStroke(s);
				g2.setColor(c);
			}
		} else {
			g2.drawImage(cardBImage, offsetX, offsetY, Define.MenuPanel.card_size, Define.MenuPanel.card_size, null);
		}
	}
}
