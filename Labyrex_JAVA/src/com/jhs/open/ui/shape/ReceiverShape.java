package com.jhs.open.ui.shape;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import com.jhs.open.Define;
import com.jhs.open.bean.ReceiverBean;
import com.jhs.open.tool.GraphicsTools;
import com.jhs.open.ui.Canvas;

/**
 * 接收器
 * 
 * @author JHS
 * 
 */
public class ReceiverShape extends Canvas {
	public ReceiverBean bean;

	public Image bgImage;

	public ReceiverShape(ReceiverBean bean) {
		super(bean.x * Define.Main.grid_size, bean.y * Define.Main.grid_size, Define.Main.grid_size,
				Define.Main.grid_size);
		this.bean = bean;
	}

	@Override
	public void init() {
	}

	public void reset() {
		this.x = bean.x * Define.Main.grid_size;
		this.y = bean.y * Define.Main.grid_size;
		this.width = Define.Main.grid_size;
		this.height = Define.Main.grid_size;
	}

	public void reset(ReceiverBean bean) {
		this.bean = bean;
		reset();
	}

	@Override
	public void render(Graphics g) {
		offsetX = x;
		offsetY = y;
		if (owner != null) {
			offsetX = owner.x + x;
			offsetY = owner.y + y;
		}

		if (bgImage != null) {
			g.drawImage(bgImage, offsetX, offsetY, Define.Main.grid_size, Define.Main.grid_size, null);
		}

		if (isSelected) {
			GraphicsTools.backupGraphics(g);
			g.setColor(new Color(Color.RED.getRed(), Color.RED.getGreen(), Color.RED.getBlue(), 70));
			g.fillRect(offsetX, offsetY, Define.Main.grid_size, Define.Main.grid_size);
			GraphicsTools.restoreGraphics(g);
		}

		super.render(g);
	}
}
