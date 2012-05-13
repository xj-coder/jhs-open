package com.jhs.open.tool;

import java.awt.Image;
import java.util.Vector;

import com.jhs.open.Define;
import com.jhs.open.bean.Rect2D;
import com.jhs.open.control.GameControl;
import com.jhs.open.ui.Canvas;
import com.jhs.open.ui.canvas.game.MapCanvas;
import com.jhs.open.ui.canvas.panel.GameCanvas;
import com.jhs.open.ui.shape.BallShape;
import com.jhs.open.ui.shape.EDir;
import com.jhs.open.ui.shape.EmitterShape;
import com.jhs.open.ui.shape.MirrorShape;
import com.jhs.open.ui.shape.PathShape;
import com.jhs.open.ui.shape.ReceiverShape;

public class ImgSelector {

	public static Image ballSelector(BallShape ball) {
		Image image = null;
		image = ImageTools.cut(Define.IMG_PATH + Define.Ball.typeMap.get(ball.type) + ".png", 0, 0, ball.width,
				ball.height, "png");
		return image;
	}

	public static Image lineSelector(String type, boolean isLight, EDir dir) {
		Image image = null;

		int degree = 0;// 选择角度

		switch (dir) {
		case DIR_LEFT:
			degree = 90;
			break;
		case DIR_RIGHT:
			degree = 270;
			break;
		case DIR_DOWN:
			degree = 180;
			break;
		}

		image = ImageTools.cut(Define.IMG_PATH + Define.Line.typeMap.get(type) + (isLight ? "_r" : "") + ".png", 0, 0,
				Define.Line.width, Define.Line.height, "png");
		image = ImageTools.rotateImage(image, degree);
		return image;
	}

	public static Image emitterSelector(EmitterShape emitter, Rect2D rec) {
		Image image = null;

		boolean inPath = inPath(emitter).size() == 0 ? false : true;

		int degree = 0;// 选择角度

		switch (getDir(emitter, rec)) {
		case DIR_DOWN:
			degree = 180;
			break;
		case DIR_LEFT:
			degree = 270;
			break;
		case DIR_RIGHT:
			degree = 90;
			break;
		}

		image = ImageTools.cut(Define.IMG_PATH + Define.Emitter.typeMap.get(emitter.bean.type) + (inPath ? "_r" : "")
				+ ".png", 0, 0, emitter.width, emitter.height, "png");
		image = ImageTools.rotateImage(image, degree);
		return image;
	}

	public static Image emitterSelector(int x, int y, int width, int height, String type, Rect2D rec) {
		Image image = null;

		int degree = 0;// 选择角度

		switch (getDir(x, y, rec)) {
		case DIR_DOWN:
			degree = 180;
			break;
		case DIR_LEFT:
			degree = 270;
			break;
		case DIR_RIGHT:
			degree = 90;
			break;
		}

		image = ImageTools.cut(Define.IMG_PATH + Define.Emitter.typeMap.get(type) + ".png", 0, 0, width, height, "png");
		image = ImageTools.rotateImage(image, degree);
		return image;
	}

	public static Image emitterSelector(String type) {
		Image image = null;

		image = ImageTools.cut(Define.IMG_PATH + Define.Emitter.typeMap.get(type) + ".png", 0, 0,
				Define.Main.grid_size, Define.Main.grid_size, "png");
		return image;
	}

	public static Image receiverSelector(ReceiverShape receiver, Rect2D rec) {
		Image image = null;

		boolean inPath = inPath(receiver).size() == 0 ? false : true;

		int degree = 0;
		switch (getDir(receiver, rec)) {
		case DIR_DOWN:
			degree = 180;
			break;
		case DIR_LEFT:
			degree = 270;
			break;
		case DIR_RIGHT:
			degree = 90;
			break;
		}

		image = ImageTools.cut(Define.IMG_PATH + Define.Receiver.typeMap.get(receiver.bean.type) + (inPath ? "_r" : "")
				+ ".png", 0, 0, receiver.width, receiver.height, "png");
		image = ImageTools.rotateImage(image, degree);
		return image;
	}

	public static Image receiverSelector(int x, int y, int width, int height, String type, Rect2D rec) {
		Image image = null;

		int degree = 0;
		switch (getDir(x, y, rec)) {
		case DIR_DOWN:
			degree = 180;
			break;
		case DIR_LEFT:
			degree = 270;
			break;
		case DIR_RIGHT:
			degree = 90;
			break;
		}

		image = ImageTools
				.cut(Define.IMG_PATH + Define.Receiver.typeMap.get(type) + ".png", 0, 0, width, height, "png");
		image = ImageTools.rotateImage(image, degree);
		return image;
	}

	public static Image receiverSelector(String type) {
		Image image = null;

		image = ImageTools.cut(Define.IMG_PATH + Define.Receiver.typeMap.get(type) + ".png", 0, 0,
				Define.Main.grid_size, Define.Main.grid_size, "png");
		return image;
	}

	public static Image mirrorSelector(MirrorShape mirror) {
		Image image = null;

		boolean inPath = inPath(mirror).size() == 0 ? false : true;

		image = ImageTools.cut(Define.IMG_PATH + Define.Mirror.typeMap.get(mirror.bean.type) + (inPath ? "_r" : "")
				+ ".png", 0, 0, mirror.width, mirror.height, "png");

		return image;
	}

	public static Image mirrorSelector(String type) {
		Image image = null;

		image = ImageTools.cut(Define.IMG_PATH + Define.Mirror.typeMap.get(type) + ".png", 0, 0, Define.Main.grid_size,
				Define.Main.grid_size, "png");

		return image;
	}

	/**
	 * 根据Shape的x、y值判断Shape所在的方向。
	 */
	public static EDir getDir(Canvas canvas, Rect2D rec) {
		if (canvas.x < rec.x) {
			return EDir.DIR_LEFT;
		} else if (canvas.y < rec.y) {
			return EDir.DIR_UP;
		} else if (canvas.x >= rec.x + rec.w) {
			return EDir.DIR_RIGHT;
		} else if (canvas.y >= rec.y + rec.h) {
			return EDir.DIR_DOWN;
		}

		return EDir.DIR_LEFT;
	}

	/**
	 * 根据Shape的x、y值判断Shape所在的方向。
	 */
	public static EDir getDir(int x, int y, Rect2D rec) {
		if (x < rec.x) {
			return EDir.DIR_LEFT;
		} else if (y < rec.y) {
			return EDir.DIR_UP;
		} else if (x >= rec.x + rec.w) {
			return EDir.DIR_RIGHT;
		} else if (y >= rec.y + rec.h) {
			return EDir.DIR_DOWN;
		}

		return EDir.DIR_LEFT;
	}

	public static Vector<PathShape> inPath(EmitterShape shape) {
		Vector<PathShape> paths = new Vector<PathShape>();
		if (GameControl.gi().getCurrCanvas() instanceof GameCanvas) {
			MapCanvas map = ((GameCanvas) GameControl.gi().getCurrCanvas()).getMapCanvas();

			if (map != null) {
				for (int i = 0; i < map.getPathCount(); i++) {
					PathShape path = map.getPath(i);
					if (path.emitter == shape) {
						paths.add(path);
					}
				}
			}
		}
		return paths;
	}

	public static Vector<PathShape> inPath(ReceiverShape shape) {
		Vector<PathShape> paths = new Vector<PathShape>();
		if (GameControl.gi().getCurrCanvas() instanceof GameCanvas) {
			MapCanvas map = ((GameCanvas) GameControl.gi().getCurrCanvas()).getMapCanvas();

			if (map != null) {
				for (int i = 0; i < map.getPathCount(); i++) {
					PathShape path = map.getPath(i);
					if (path.receiver == shape) {
						paths.add(path);
					}
				}
			}
		}
		return paths;
	}

	public static Vector<PathShape> inPath(MirrorShape shape) {
		Vector<PathShape> paths = new Vector<PathShape>();
		if (GameControl.gi().getCurrCanvas() instanceof GameCanvas) {
			MapCanvas map = ((GameCanvas) GameControl.gi().getCurrCanvas()).getMapCanvas();

			if (map != null) {
				for (int i = 0; i < map.getPathCount(); i++) {
					PathShape path = map.getPath(i);
					for (int j = 0; j < path.mirrors.size(); j++) {
						if (path.mirrors.get(j) == shape) {
							paths.add(path);
						}
					}
				}
			}
		}
		return paths;
	}
}
