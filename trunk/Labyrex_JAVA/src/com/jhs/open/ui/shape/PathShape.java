package com.jhs.open.ui.shape;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import com.jhs.open.bean.BodyBean;
import com.jhs.open.bean.PathBean;
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

		Graphics2D g2 = (Graphics2D) g;

		Color c = g2.getColor();
		g2.setColor(Color.red);

		BodyBean body1 = bean.emitter;
		BodyBean body2 = null;

		for (int i = 0; i < bean.mirrors.size() + 1; i++) {
			if (i < bean.mirrors.size()) {
				body2 = bean.mirrors.get(i);
			} else {
				body2 = bean.receiver;
			}

			int begin_x = 0;
			int begin_y = 0;
			int end_x = 0;
			int end_y = 0;

			begin_x = owner.x + body1.x * body1.width + body1.width / 2;
			begin_y = owner.y + body1.y * body1.height + body1.height / 2;
			end_x = owner.x + body2.x * body2.width + body2.width / 2;
			end_y = owner.y + body2.y * body2.height + body2.height / 2;

			// begin_x、begin_y值校正，保证线画在地图内
			if (begin_x < owner.x) {
				begin_x = owner.x;
			} else if (begin_x > owner.x + owner.width) {
				begin_x = owner.x + owner.width;
			}
			if (begin_y > owner.y + owner.height) {
				begin_y = owner.y + owner.height;
			} else if (begin_y < owner.y) {
				begin_y = owner.y;

			}
			if (end_x < owner.x) {
				end_x = owner.x;
			} else if (end_x > owner.x + owner.width) {
				end_x = owner.x + owner.width;
			}
			if (end_y > owner.y + owner.height) {
				end_y = owner.y + owner.height;
			} else if (end_y < owner.y) {
				end_y = owner.y;
			}

			// 普通画线法
			if (isThick) {
				g2.setStroke(new BasicStroke(3.8f));
			} else {
				g2.setStroke(new BasicStroke(1));
			}
			g2.drawLine(begin_x, begin_y, end_x, end_y);

			if (i < bean.mirrors.size()) {
				body1 = bean.mirrors.get(i);
			}
		}
		g2.setColor(c);
		g2.setStroke(new BasicStroke(1));

	}
}
