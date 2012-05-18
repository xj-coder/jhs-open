package com.jhs.open.ui.shape;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Stroke;

import com.jhs.open.Define;
import com.jhs.open.bean.GroupBean;
import com.jhs.open.control.event.group.GroupCardMouseClickedEvent;
import com.jhs.open.control.event.group.GroupCardMouseMoveEvent;
import com.jhs.open.tool.ImageTools;
import com.jhs.open.ui.Canvas;

public class GroupShape extends Canvas {

	private Image cardWImage;
	private Image cardBImage;

	private GroupBean group;

	public boolean isAvtivity = false;

	public GroupShape(int x, int y, GroupBean group) {
		super(x, y, Define.MenuPanel.group_card_size, Define.MenuPanel.group_card_size);
		this.group = group;
	}

	@Override
	public void init() {
		cardWImage = ImageTools.getImage(Define.MenuPanel.group_card_white_image_path);
		cardBImage = ImageTools.getImage(Define.MenuPanel.group_card_black_image_path);

		addMouseListener(new GroupCardMouseClickedEvent(this));
		addMouseListener(new GroupCardMouseMoveEvent());
	}

	public GroupBean getGroup() {
		return group;
	}

	public void setGroup(GroupBean group) {
		this.group = group;
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
		if (isAvtivity) {
			g2.drawImage(cardWImage, offsetX, offsetY, width, height, null);
		} else {
			g2.drawImage(cardBImage, offsetX, offsetY, Define.MenuPanel.group_card_size,
					Define.MenuPanel.group_card_size, null);
		}
		if (isOver) {
			Stroke s = g2.getStroke();
			Color c = g2.getColor();

			g2.setStroke(new BasicStroke(Define.MenuPanel.group_card_border * 3 / 2));
			g2.setColor(new Color(0xff, 0xff, 0xff, 200));
			g2.drawRect(offsetX - Define.MenuPanel.group_card_border, offsetY - Define.MenuPanel.group_card_border,
					width + Define.MenuPanel.group_card_border, height + Define.MenuPanel.group_card_border);

			g2.setStroke(s);
			g2.setColor(c);
		}
	}
}
