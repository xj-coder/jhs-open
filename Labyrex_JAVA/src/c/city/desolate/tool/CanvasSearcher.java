package c.city.desolate.tool;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import c.city.desolate.control.GameControl;
import c.city.desolate.ui.Canvas;
import c.city.desolate.ui.canvas.game.MapCanvas;
import c.city.desolate.ui.canvas.panel.GameCanvas;
import c.city.desolate.ui.shape.BallShape;
import c.city.desolate.ui.shape.EmitterShape;
import c.city.desolate.ui.shape.MirrorShape;
import c.city.desolate.ui.shape.ReceiverShape;

public class CanvasSearcher {
	public static EmitterShape findEmitter(int x, int y) {
		MapCanvas map = ((GameCanvas) GameControl.gi().getCurrGameCanvas()).getMapCanvas();

		for (int i = 0; i < map.emitterList.length; i++) {
			EmitterShape emitter = map.emitterList[i];

			Rectangle rec = new Rectangle(emitter.owner.x + emitter.x, emitter.owner.y + emitter.y, emitter.width,
					emitter.height);

			if (rec.contains(x, y)) {
				return emitter;
			}
		}
		return null;
	}

	public static ReceiverShape findReceiver(int x, int y) {
		MapCanvas map = ((GameCanvas) GameControl.gi().getCurrGameCanvas()).getMapCanvas();

		for (int i = 0; i < map.receiverList.length; i++) {
			ReceiverShape receiver = map.receiverList[i];

			Rectangle rec = new Rectangle(receiver.owner.x + receiver.x, receiver.owner.y + receiver.y, receiver.width,
					receiver.height);

			if (rec.contains(x, y)) {
				return receiver;
			}
		}
		return null;
	}

	public static MirrorShape findMirror(int x, int y) {
		MapCanvas map = ((GameCanvas) GameControl.gi().getCurrGameCanvas()).getMapCanvas();

		for (int i = 0; i < map.mirrorList.length; i++) {
			MirrorShape mirror = map.mirrorList[i];

			Rectangle rec = new Rectangle(mirror.owner.x + mirror.x, mirror.owner.y + mirror.y, mirror.width,
					mirror.height);

			if (rec.contains(x, y)) {
				return mirror;
			}
		}
		return null;
	}

	public static MirrorShape findBallInMirror(int x, int y) {
		MapCanvas map = ((GameCanvas) GameControl.gi().getCurrGameCanvas()).getMapCanvas();

		for (int i = 0; i < map.mirrorList.length; i++) {
			MirrorShape mirror = map.mirrorList[i];

			Rectangle rec = new Rectangle(mirror.owner.x + mirror.x + mirror.width / 2 - BallShape.speed / 2,
					mirror.owner.y + mirror.y + mirror.height / 2 - BallShape.speed / 2, BallShape.speed,
					BallShape.speed);

			// Rectangle rec = new Rectangle(mirror.owner.x + mirror.x, mirror.owner.y + mirror.y, mirror.width,
			// mirror.height);

			if (rec.contains(x, y)) {
				return mirror;
			}
		}
		return null;
	}

	public static List<Canvas> findCanvas(Canvas canvas, int x, int y) {
		ArrayList<Canvas> canvasList = new ArrayList<Canvas>();

		for (int i = 0; i < canvas.getCanvasCount(); i++) {
			Canvas c = canvas.getCanvas(i);

			Rectangle rec = new Rectangle(c.owner.x + c.x, c.owner.y + c.y, c.width, c.height);

			if (rec.contains(x, y)) {
				canvasList.add(c);
			}
			if (c.getCanvasCount() > 0) {
				canvasList.addAll(findCanvas(c, x, y));
			}
		}
		return canvasList;
	}

	public static boolean outMap(int x, int y) {
		MapCanvas map = ((GameCanvas) GameControl.gi().getCurrGameCanvas()).getMapCanvas();

		Rectangle rec = new Rectangle(map.owner.x + map.x, map.owner.y + map.y, map.width, map.height);

		if (rec.contains(x, y)) {
			return false;
		} else {
			return true;
		}
	}
}
