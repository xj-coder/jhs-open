package com.jhs.open.ui.shape;

import java.awt.Color;
import java.awt.Graphics;

import com.jhs.open.Define;
import com.jhs.open.bean.MirrorBean;
import com.jhs.open.tool.GraphicsTools;
import com.jhs.open.tool.ImgSelector;
import com.jhs.open.ui.Canvas;

/**
 * 挡板
 * 
 * @author JHS
 * 
 */
public class MirrorShape extends Canvas {
	public MirrorBean bean;

	public MirrorShape(MirrorBean bean) {
		super(bean.x * Define.Main.grid_size, bean.y * Define.Main.grid_size,
				Define.Main.grid_size, Define.Main.grid_size);
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

	public void reset(MirrorBean bean) {
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

		g.drawImage(ImgSelector.mirrorSelector(this), offsetX, offsetY,
				Define.Main.grid_size, Define.Main.grid_size, null);

		if (bean.isLock) {
			GraphicsTools.backupGraphics(g);
			g.setColor(Color.white);

			g.fillOval(offsetX + Define.Main.grid_size / 4, offsetY
					+ Define.Main.grid_size / 4, Define.Main.grid_size / 2,
					Define.Main.grid_size / 2);
			GraphicsTools.restoreGraphics(g);
		}

		if (isSelected) {
			GraphicsTools.backupGraphics(g);
			g.setColor(new Color(Color.RED.getRed(), Color.RED.getGreen(),
					Color.RED.getBlue(), 70));
			g.fillRect(offsetX, offsetY, Define.Main.grid_size,
					Define.Main.grid_size);
			GraphicsTools.restoreGraphics(g);
		}

		super.render(g);
	}
}
