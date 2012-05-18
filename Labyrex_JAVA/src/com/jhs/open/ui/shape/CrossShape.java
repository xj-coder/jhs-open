package com.jhs.open.ui.shape;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import com.jhs.open.Define;
import com.jhs.open.bean.MapBean;
import com.jhs.open.control.event.cross.CrossCardMouseClickedEvent;
import com.jhs.open.control.event.cross.CrossCardMouseMoveEvent;
import com.jhs.open.tool.ImageTools;
import com.jhs.open.ui.Canvas;

public class CrossShape extends Canvas {

	private Image cardWImage;
	private Image cardBImage;

	private MapBean map;

	public CrossShape(int x, int y, MapBean map) {
		super(x, y, Define.MenuPanel.cross_card_size, Define.MenuPanel.cross_card_size);
		this.map = map;
	}

	@Override
	public void init() {
		cardWImage = ImageTools.getImage(Define.MenuPanel.cross_card_white_image_path);
		cardBImage = ImageTools.getImage(Define.MenuPanel.cross_card_black_image_path);

		addMouseListener(new CrossCardMouseClickedEvent(map));
		addMouseListener(new CrossCardMouseMoveEvent());
	}

	public MapBean getMap() {
		return map;
	}

	public void setMap(MapBean map) {
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
		} else {
			g2.drawImage(cardBImage, offsetX, offsetY, width, height, null);
		}
	}
}