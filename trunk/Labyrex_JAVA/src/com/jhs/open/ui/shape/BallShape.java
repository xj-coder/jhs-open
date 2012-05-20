package com.jhs.open.ui.shape;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import com.jhs.open.Define;
import com.jhs.open.bean.MapBean;
import com.jhs.open.bean.MirrorBean;
import com.jhs.open.control.GameControl;
import com.jhs.open.control.ListenerControl;
import com.jhs.open.control.SoundControl;
import com.jhs.open.tool.CanvasSearcher;
import com.jhs.open.tool.GameTools;
import com.jhs.open.tool.GraphicsTools;
import com.jhs.open.tool.ImgSelector;
import com.jhs.open.ui.Canvas;
import com.jhs.open.ui.canvas.game.MapCanvas;
import com.jhs.open.ui.canvas.panel.GameCanvas;
import com.jhs.open.ui.canvas.panel.WinPanel;

/**
 * 球
 * 
 * @author JHS
 * 
 */
public class BallShape extends Canvas {
	public static int speed = 8;// 速度，每个时间单位移动的距离
	public static final int minParticelNum = 3;
	public static final int maxParticelNum = 5;

	public String type;// 类型，不同颜色的发射器就是不同类型
	public EDir dir;// 方向

	public PathShape path;

	public boolean isBomb = false;

	private long bombTimeNum = 500l;
	private long bombTimer = -1l;

	public double radius = 14;// 直径

	private ArrayList<ParticleShape> particleShapeList = new ArrayList<ParticleShape>();

	private ArrayList<Point> tails = new ArrayList<Point>();

	public BallShape(int x, int y, int width, int height) {
		super(x, y, width, height);

		MapBean map = GameControl.gi().getCurrMap();
		path = new PathShape(0, 0, map.width, map.height);
	}

	@Override
	public void render(Graphics g) {
		super.render(g);

		MapCanvas map = ((GameCanvas) GameControl.gi().getCurrCanvas()).getMapCanvas();

		if (isBomb) {
			if (bombTimer < bombTimeNum) {
				for (int i = 0; i < particleShapeList.size(); i++) {
					particleShapeList.get(i).render(g);
				}
			} else {
				map.removeCanvas(this);

				if (!GameTools.isWin()) {
					ListenerControl.gi().loopRegistCanvasListener(map);
				}
			}
		} else {
			// 画尾巴
			Graphics2D g2 = (Graphics2D) g;
			GraphicsTools.backupGraphics(g2);
			Color c = Define.Particle.typeMap.get(type);
			int a = 100;
			for (int i = 0; i < tails.size() && i < radius; i++) {
				Point p = tails.get(i);

				a = (int) (a - 100 / radius);
				a = a < 0 ? 1 : a;

				float r = (float) ((radius - i) / 2);
				float[] dist = { 0.5f, 1.0f };
				Color[] colors = { new Color(c.getRed(), c.getGreen(), c.getBlue(), a),
						new Color(c.getRed(), c.getGreen(), c.getBlue(), 20) };
				RadialGradientPaint paint = new RadialGradientPaint(p, r, dist, colors);

				g2.setPaint(paint);
				Ellipse2D ellipse = new Ellipse2D.Double(p.x - (radius - i) / 2, p.y - (radius - i) / 2, radius - i,
						radius - i);
				g2.fill(ellipse);
			}
			GraphicsTools.restoreGraphics(g2);

			g.drawImage(ImgSelector.ballSelector(this), owner.x + x, owner.y + y, Define.Main.grid_size,
					Define.Main.grid_size, null);
		}
	}

	@Override
	public void update(long l) {
		super.update(l);

		if (isBomb) {
			if (bombTimer == -1) {
				tails.clear();

				int num = (int) (Math.random() * (maxParticelNum - minParticelNum)) + minParticelNum;
				for (int i = 0; i < num; i++) {
					double _x = owner.x + x + width / 2;
					double _y = owner.y + y + height / 2;
					double _xV = 3.5;
					double _yV = 3.5;

					if (_x > owner.x + owner.width) {
						_x = owner.x + owner.width;
					}
					if (_x < owner.x) {
						_x = owner.x;
					}
					if (_y > owner.y + owner.height) {
						_y = owner.y + owner.height;
					}
					if (_y < owner.y) {
						_y = owner.y;
					}

					switch (dir) {
					case DIR_UP:
						_xV = Math.random();
						_xV = _xV * ((int) (Math.random() * 2) % 2 == 0 ? 1 : -1);
						break;
					case DIR_DOWN:
						_xV = Math.random();
						_xV = _xV * ((int) (Math.random() * 2) % 2 == 0 ? 1 : -1);
						_yV = _yV * -1;
						break;
					case DIR_LEFT:
						_yV = Math.random();
						_yV = _yV * ((int) (Math.random() * 2) % 2 == 0 ? 1 : -1);
						break;
					case DIR_RIGHT:
						_yV = Math.random();
						_yV = _yV * ((int) (Math.random() * 2) % 2 == 0 ? 1 : -1);
						_xV = _xV * -1;
						break;
					}

					ParticleShape particle = new ParticleShape(_x, _y, _xV, _yV, Define.Particle.typeMap.get(type), 1);

					particleShapeList.add(particle);
				}
			} else {
				if (bombTimer > bombTimeNum) {
					particleShapeList.clear();
				} else {
					for (int i = 0; i < particleShapeList.size(); i++) {
						particleShapeList.get(i).update(l);
					}
				}
			}
			bombTimer += l;
			return;
		}

		MapCanvas map = ((GameCanvas) GameControl.gi().getCurrCanvas()).getMapCanvas();

		// 移动球
		switch (dir) {
		case DIR_UP:
			y = y - speed;
			break;
		case DIR_DOWN:
			y = y + speed;
			break;
		case DIR_LEFT:
			x = x - speed;
			break;
		case DIR_RIGHT:
			x = x + speed;
			break;
		}

		tails.add(0, new Point(owner.x + x + width / 2, owner.y + y + height / 2));

		MirrorShape mirror = CanvasSearcher.findBallInMirror(owner.x + x + width / 2, owner.y + y + height / 2);

		// 球碰到镜面后转向
		if (mirror != null) {

			if (GameControl.gi().isSound()) {
				SoundControl.play(Define.Sound.touch_mirror_sound);
			}

			path.bean.mirrors.add(mirror.bean);

			if (mirror.bean.type.equals(MirrorBean.LEFT)) {
				switch (dir) {
				case DIR_UP:
					dir = EDir.DIR_LEFT;
					break;
				case DIR_DOWN:
					dir = EDir.DIR_RIGHT;
					break;
				case DIR_LEFT:
					dir = EDir.DIR_UP;
					break;
				case DIR_RIGHT:
					dir = EDir.DIR_DOWN;
					break;
				}
			} else {
				switch (dir) {
				case DIR_UP:
					dir = EDir.DIR_RIGHT;
					break;
				case DIR_DOWN:
					dir = EDir.DIR_LEFT;
					break;
				case DIR_LEFT:
					dir = EDir.DIR_DOWN;
					break;
				case DIR_RIGHT:
					dir = EDir.DIR_UP;
					break;
				}
			}
		}

		// 碰到接收器
		ReceiverShape receiver = CanvasSearcher.findReceiver(owner.x + x + width / 2, owner.y + y + height / 2);
		if (receiver != null) {
			if (receiver.bean.type.equals(type)) {

				if (GameControl.gi().isSound()) {
					SoundControl.play(Define.Sound.path_right_sound);
				}

				path.bean.receiver = receiver.bean;
				map.addPath(path);

				if (GameTools.isWin()) {
					SoundControl.play(Define.Sound.win_sound);

					Canvas canvas = GameControl.gi().getCurrCanvas();
					canvas.addCanvas(new WinPanel());
				}
			}

		}

		// 出界
		if (CanvasSearcher.outMap(owner.x + x + width / 2, owner.y + y + height / 2)) {
			if (GameControl.gi().isSound()) {
				SoundControl.play(Define.Sound.ball_out_sound);
			}

			path = null;
			isBomb = true;
		}
	}
}
