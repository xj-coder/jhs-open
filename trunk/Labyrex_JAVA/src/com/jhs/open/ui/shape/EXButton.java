package com.jhs.open.ui.shape;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

import com.jhs.open.Define;
import com.jhs.open.control.event.exbutton.EXButtonMouseClickedEvent;
import com.jhs.open.control.event.exbutton.EXButtonMouseMoveEvent;
import com.jhs.open.tool.ImageTools;
import com.jhs.open.ui.Canvas;

public class EXButton extends Canvas {
	private String name;

	private Image bgImage;
	private Image bgrImage;
	private Image fgImage;

	private Image lineImage;

	private boolean isSelected = false;
	private boolean isToggle = false;

	public EXButton(String name) {
		this(name, 0, 0, 0, 0);
	}

	public EXButton(boolean isToggle) {
		this("", isToggle, 0, 0, 0, 0);
	}

	public EXButton(String name, boolean isToggle, int x, int y, int width, int height) {
		super(x, y, width, height);

		this.name = name;
		this.isToggle = isToggle;
	}

	public EXButton(String name, int x, int y, int width, int height) {
		this(name, false, x, y, width, height);
	}

	@Override
	public void init() {
		addMouseListener(new EXButtonMouseClickedEvent(this));
		addMouseListener(new EXButtonMouseMoveEvent());
	}

	public void setBgImage(Image bgImage) {
		this.bgImage = bgImage;
	}

	public void setBgrImage(Image bgrImage) {
		this.bgrImage = bgrImage;
	}

	public void setFgImage(Image fgImage) {
		this.fgImage = fgImage;
	}

	public void setLineImage(Image lineImage) {
		this.lineImage = lineImage;
	}

	@Override
	public boolean isSelected() {
		return isSelected;
	}

	@Override
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public boolean isToggle() {
		return isToggle;
	}

	public void setToggle(boolean isToggle) {
		this.isToggle = isToggle;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void render(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;// Graphics2D是Graphics的子类
		// 抗锯齿,绘制质量等可参考RenderingHints类的文档！
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		offsetX = x;
		offsetY = y;
		if (owner != null) {
			offsetX = owner.x + x;
			offsetY = owner.y + y;
		}

		if (isOver) {
			if (bgrImage != null) {
				if (bgImage != null) {
					g2.drawImage(bgImage, offsetX, offsetY, width, height, null);
				}
				g2.drawImage(bgrImage, offsetX, offsetY, width, height, null);
			}
		} else {
			if (bgImage != null) {
				g2.drawImage(bgImage, offsetX, offsetY, width, height, null);
			}
		}

		if (fgImage != null) {
			g2.drawImage(fgImage, offsetX, offsetY, width, height, null);
		}

		if (isSelected) {
			if (lineImage == null) {
				lineImage = ImageTools.getImage(Define.Button.line_path);
			}
			g2.drawImage(lineImage, offsetX, offsetY, width, height, null);
		}

		super.render(g2);
	}

	@Override
	public String toString() {
		return name + " - " + hashCode();
	}
}
