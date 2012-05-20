package com.jhs.open.ui.shape;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.MultipleGradientPaint;

import com.jhs.open.Define;
import com.jhs.open.bean.BodyBean;
import com.jhs.open.bean.PathBean;
import com.jhs.open.tool.GraphicsTools;
import com.jhs.open.ui.Canvas;

public class PathShape extends Canvas {
	public PathBean bean;

	public boolean isThick = false;

	public PathShape(int x, int y, int width, int height) {
		super(x, y, width, height);
		bean = new PathBean();
	}

	@Override
	public void render(Graphics g) {
		super.render(g);

		BodyBean body1 = bean.emitter;
		BodyBean body2 = null;

		for (int i = 0; i < bean.mirrors.size() + 1; i++) {
			if (i < bean.mirrors.size()) {
				body2 = bean.mirrors.get(i);
			} else {
				body2 = bean.receiver;
			}

			int x1 = 0;
			int y1 = 0;
			int x2 = 0;
			int y2 = 0;

			x1 = owner.x + body1.x * body1.width + body1.width / 2;
			y1 = owner.y + body1.y * body1.height + body1.height / 2;
			x2 = owner.x + body2.x * body2.width + body2.width / 2;
			y2 = owner.y + body2.y * body2.height + body2.height / 2;

			// begin_x、begin_y值校正，保证线画在地图内
			if (x1 < owner.x) {
				x1 = owner.x;
			} else if (x1 > owner.x + owner.width) {
				x1 = owner.x + owner.width;
			}
			if (y1 > owner.y + owner.height) {
				y1 = owner.y + owner.height;
			} else if (y1 < owner.y) {
				y1 = owner.y;

			}
			if (x2 < owner.x) {
				x2 = owner.x;
			} else if (x2 > owner.x + owner.width) {
				x2 = owner.x + owner.width;
			}
			if (y2 > owner.y + owner.height) {
				y2 = owner.y + owner.height;
			} else if (y2 < owner.y) {
				y2 = owner.y;
			}

			// 普通画线法
			Graphics2D g2 = (Graphics2D) g;

			GraphicsTools.backupGraphics(g2);
			Color c = Define.Particle.typeMap.get(bean.emitter.type);
			if (isThick) {

				Color c1 = new Color(c.getRed(), c.getGreen(), c.getBlue(), 1);
				Color c2 = new Color(c.getRed(), c.getGreen(), c.getBlue(), 50);
				Color c3 = new Color(c.getRed(), c.getGreen(), c.getBlue(), 100);
				int stroke = 10;

				int _x1 = 0;
				int _y1 = 0;
				int _x2 = 0;
				int _y2 = 0;

				if (x1 == x2) {
					_x1 = x1 - stroke;
					_y1 = y1;
					_x2 = x1;
					_y2 = y1;
				} else if (y1 == y2) {
					_x1 = x1;
					_y1 = y1 - stroke;
					_x2 = x1;
					_y2 = y1;
				}

				LinearGradientPaint gradient = new LinearGradientPaint(_x1, _y1, _x2, _y2, new float[] { 0.4f, 0.7f,
						1.0f }, new Color[] { c1, c2, c3 }, MultipleGradientPaint.CycleMethod.REFLECT);

				g2.setPaint(gradient);
				g2.setStroke(new BasicStroke(stroke));
			} else {
				g2.setColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), 70));
				g2.setStroke(new BasicStroke(2f));
			}
			g2.drawLine(x1, y1, x2, y2);

			if (i < bean.mirrors.size()) {
				body1 = bean.mirrors.get(i);
			}
			GraphicsTools.restoreGraphics(g2);
		}

	}
}
