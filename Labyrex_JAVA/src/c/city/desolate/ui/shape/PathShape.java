package c.city.desolate.ui.shape;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Vector;

import c.city.desolate.ui.Canvas;

public class PathShape extends Canvas {
	public EmitterShape emitter;
	public ReceiverShape receiver;

	public Vector<MirrorShape> mirrors = new Vector<MirrorShape>();

	public boolean isThick = false;

	public PathShape(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	@Override
	public void render(Graphics g) {
		super.render(g);

		Graphics2D g2 = (Graphics2D) g;

		Color c = g2.getColor();
		g2.setColor(Color.red);

		Canvas c1 = emitter;
		Canvas c2 = null;

		for (int i = 0; i < mirrors.size() + 1; i++) {
			if (i < mirrors.size()) {
				c2 = mirrors.get(i);
			} else {
				c2 = receiver;
			}

			int begin_x = 0;
			int begin_y = 0;
			int end_x = 0;
			int end_y = 0;

			begin_x = c1.owner.x + c1.x + c1.width / 2;
			begin_y = c1.owner.y + c1.y + c1.height / 2;
			end_x = c2.owner.x + c2.x + c2.width / 2;
			end_y = c2.owner.y + c2.y + c2.height / 2;

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

			// 图片画线法
			// if (begin_x != end_x) {
			// int _dir = (begin_x - end_x) / Math.abs(begin_x - end_x);
			//
			// if (_dir < 0) {
			// Image img = ImgSelector.lineSelector(emitter.type, isThick, EDir.DIR_LEFT);
			// for (int j = begin_x; j < end_x; j -= Define.Line.width / 2 * _dir) {
			// g.drawImage(img, j - Define.Line.width / 2, begin_y - Define.Line.height / 2,
			// Define.Line.width, Define.Line.height, null);
			// }
			// } else {
			// Image img = ImgSelector.lineSelector(emitter.type, isThick, EDir.DIR_RIGHT);
			// for (int j = end_x; j < begin_x; j += Define.Line.width / 2 * _dir) {
			// g.drawImage(img, j - Define.Line.width / 2, begin_y - Define.Line.height / 2,
			// Define.Line.width, Define.Line.height, null);
			// }
			// }
			// } else if (begin_y != end_y) {
			// int _dir = (begin_y - end_y) / Math.abs(begin_y - end_y);
			// if (_dir < 0) {
			// Image img = ImgSelector.lineSelector(emitter.type, isThick, EDir.DIR_DOWN);
			// for (int j = begin_y; j < end_y; j -= Define.Line.height / 2 * _dir) {
			// g.drawImage(img, begin_x - Define.Line.width / 2, j - Define.Line.height / 2,
			// Define.Line.width, Define.Line.height, null);
			// }
			// } else {
			// Image img = ImgSelector.lineSelector(emitter.type, isThick, EDir.DIR_DOWN);
			// for (int j = end_y; j < begin_y; j += Define.Line.height / 2 * _dir) {
			// g.drawImage(img, begin_x - Define.Line.width / 2, j - Define.Line.height / 2,
			// Define.Line.width, Define.Line.height, null);
			// }
			// }
			// }

			// 普通画线法
			if (isThick) {
				g2.setStroke(new BasicStroke(3.8f));
			} else {
				g2.setStroke(new BasicStroke(1));
			}
			g2.drawLine(begin_x, begin_y, end_x, end_y);

			if (i < mirrors.size()) {
				c1 = mirrors.get(i);
			}
		}
		g2.setColor(c);
		g2.setStroke(new BasicStroke(1));

	}
}
