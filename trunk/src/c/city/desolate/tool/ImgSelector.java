package c.city.desolate.tool;

import c.city.desolate.Define;
import c.city.desolate.control.GameControl;
import c.city.desolate.control.MapControl;
import c.city.desolate.ui.Canvas;
import c.city.desolate.ui.canvas.game.MapCanvas;
import c.city.desolate.ui.shape.*;

import java.awt.*;
import java.util.Vector;

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

	public static Image emitterSelector(EmitterShape emitter) {
		Image image = null;

		boolean inPath = inPath(emitter).size() == 0 ? false : true;

		int degree = 0;// 选择角度

		switch (getDir(emitter)) {
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

		image = ImageTools.cut(Define.IMG_PATH + Define.Emitter.typeMap.get(emitter.type) + (inPath ? "_r" : "")
				+ ".png", 0, 0, emitter.width, emitter.height, "png");
		image = ImageTools.rotateImage(image, degree);
		return image;
	}

	public static Image receiverSelector(ReceiverShape receiver) {
		Image image = null;

		boolean inPath = inPath(receiver).size() == 0 ? false : true;

		int degree = 0;
		switch (getDir(receiver)) {
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

		image = ImageTools.cut(Define.IMG_PATH + Define.Receiver.typeMap.get(receiver.type) + (inPath ? "_r" : "")
				+ ".png", 0, 0, receiver.width, receiver.height, "png");
		image = ImageTools.rotateImage(image, degree);
		return image;
	}

	public static Image mirrorSelector(MirrorShape mirror) {
		Image image = null;

		boolean inPath = inPath(mirror).size() == 0 ? false : true;

		image = ImageTools.cut(
				Define.IMG_PATH + Define.Mirror.typeMap.get(mirror.type) + (inPath ? "_r" : "") + ".png", 0, 0,
				mirror.width, mirror.height, "png");

		return image;
	}

	/**
	 * 根据Shape的x、y值判断Shape所在的方向。
	 */
	public static EDir getDir(Canvas canvas) {

		MapCanvas map = MapControl.getMapByName(GameControl.gi().getCurrMapName());
		if (map != null) {
			if (canvas.x < 0) {
				return EDir.DIR_LEFT;
			} else if (canvas.y < 0) {
				return EDir.DIR_UP;
			} else if (canvas.x >= map.width) {
				return EDir.DIR_RIGHT;
			} else if (canvas.y >= map.height) {
				return EDir.DIR_DOWN;
			}
		}

		return EDir.DIR_LEFT;
	}

	public static Vector<PathShape> inPath(EmitterShape shape) {
		Vector<PathShape> paths = new Vector<PathShape>();
		MapCanvas map = MapControl.getMapByName(GameControl.gi().getCurrMapName());
		if (map != null) {
			for (int i = 0; i < map.getPathCount(); i++) {
				PathShape path = map.getPath(i);
				if (path.emitter == shape) {
					paths.add(path);
				}
			}
		}
		return paths;
	}

	public static Vector<PathShape> inPath(ReceiverShape shape) {
		Vector<PathShape> paths = new Vector<PathShape>();
		MapCanvas map = MapControl.getMapByName(GameControl.gi().getCurrMapName());
		if (map != null) {
			for (int i = 0; i < map.getPathCount(); i++) {
				PathShape path = map.getPath(i);
				if (path.receiver == shape) {
					paths.add(path);
				}
			}
		}
		return paths;
	}

	public static Vector<PathShape> inPath(MirrorShape shape) {
		Vector<PathShape> paths = new Vector<PathShape>();
		MapCanvas map = MapControl.getMapByName(GameControl.gi().getCurrMapName());
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
		return paths;
	}
}
