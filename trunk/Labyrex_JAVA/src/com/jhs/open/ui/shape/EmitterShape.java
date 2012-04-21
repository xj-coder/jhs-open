package com.jhs.open.ui.shape;

import java.awt.Graphics;
import java.awt.Image;

import com.jhs.open.Define;
import com.jhs.open.bean.EmitterBean;
import com.jhs.open.ui.Canvas;


/**
 * 发射器
 * 
 * @author JHS
 * 
 */
public class EmitterShape extends Canvas {
	public EmitterBean bean;

	public Image bgImage;

	public EmitterShape(int x, int y, EmitterBean bean) {
		super(x, y, Define.Main.grid_size, Define.Main.grid_size);
		this.bean = bean;
	}

	@Override
	public void init() {
	}

	@Override
	public void render(Graphics g) {
		if (bgImage != null) {
			g.drawImage(bgImage, owner.x + x, owner.y + y, Define.Main.grid_size, Define.Main.grid_size, null);
			// g.drawImage(ImgSelector.emitterSelector(this), owner.x + x, owner.y + y, Define.Main.grid_size,
			// Define.Main.grid_size, null);
		}

		super.render(g);
	}
}
