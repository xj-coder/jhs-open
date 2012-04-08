package c.city.desolate.ui.shape;

import java.awt.Graphics;

import c.city.desolate.Define;
import c.city.desolate.bean.MapBean;
import c.city.desolate.bean.MirrorBean;
import c.city.desolate.control.GameControl;
import c.city.desolate.control.ListenerControl;
import c.city.desolate.control.SoundControl;
import c.city.desolate.tool.CanvasSearcher;
import c.city.desolate.tool.GameTools;
import c.city.desolate.tool.ImgSelector;
import c.city.desolate.ui.Canvas;
import c.city.desolate.ui.canvas.game.MapCanvas;
import c.city.desolate.ui.canvas.panel.GameCanvas;
import c.city.desolate.ui.canvas.panel.WinPanel;

/**
 * 球
 * 
 * @author JHS
 * 
 */
public class BallShape extends Canvas {
	public static int speed = 8;// 速度，每个时间单位移动的距离

	public String type;// 类型，不同颜色的发射器就是不同类型
	public EDir dir;// 方向

	public PathShape path;

	public boolean isBomb = false;

	private long bombTimeNum = 50l;
	private long bombTimer = -1l;

	public BallShape(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	@Override
	public void init() {
		MapBean map = GameControl.gi().getCurrMap();
		path = new PathShape(0, 0, map.width, map.height);
	}

	@Override
	public void render(Graphics g) {
		super.render(g);

		MapCanvas map = ((GameCanvas) GameControl.gi().getCurrGameCanvas()).getMapCanvas();

		if (isBomb) {
			if (bombTimer < bombTimeNum) {
				g.drawImage(ImgSelector.ballSelector(this), owner.x + x, owner.y + y, Define.Main.grid_size,
						Define.Main.grid_size, null);
			} else {
				map.removeCanvas(this);

				if (!GameTools.isWin()) {
					ListenerControl.gi().loopRegistCanvasListener(map);
				}
				// ListenerControl.gi().openAllMapListener();
			}
		} else {
			g.drawImage(ImgSelector.ballSelector(this), owner.x + x, owner.y + y, Define.Main.grid_size,
					Define.Main.grid_size, null);
		}
	}

	@Override
	public void update(long l) {
		super.update(l);

		if (isBomb && bombTimer < bombTimeNum) {
			bombTimer += l;

			return;
		}

		MapCanvas map = ((GameCanvas) GameControl.gi().getCurrGameCanvas()).getMapCanvas();

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

		MirrorShape mirror = CanvasSearcher.findBallInMirror(owner.x + x + width / 2, owner.y + y + height / 2);

		// 球碰到镜面后转向
		if (mirror != null) {
			System.out.println("碰到镜面前 ： " + dir);

			if (GameControl.gi().isSound()) {
				SoundControl.play(Define.Sound.touch_mirror_sound);
			}

			path.mirrors.add(mirror);

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
			System.out.println("碰到镜面后 ： " + dir);
		}

		// 碰到接收器
		ReceiverShape receiver = CanvasSearcher.findReceiver(owner.x + x + width / 2, owner.y + y + height / 2);
		if (receiver != null) {
			if (receiver.bean.type.equals(type)) {

				if (GameControl.gi().isSound()) {
					SoundControl.play(Define.Sound.path_right_sound);
				}

				path.receiver = receiver;
				map.addPath(path);

				if (GameTools.isWin()) {
					SoundControl.play(Define.Sound.win_sound);

					Canvas canvas = GameControl.gi().getCurrGameCanvas();
					canvas.addCanvas(new WinPanel());
				}
				System.out.println("碰到对应的接收器");
			} else {
				System.out.println("碰到错误的接收器");
			}

		}

		// 碰到发射器
		EmitterShape emitter = CanvasSearcher.findEmitter(owner.x + x + width / 2, owner.y + y + height / 2);
		if (emitter != null) {
			System.out.println("碰到发射器");
		}

		// 出界
		if (CanvasSearcher.outMap(owner.x + x + width / 2, owner.y + y + height / 2)) {
			System.out.println("球出界");

			if (GameControl.gi().isSound()) {
				SoundControl.play(Define.Sound.ball_out_sound);
			}

			path = null;
			isBomb = true;
			bombTimer = 0;
		}
	}
}
