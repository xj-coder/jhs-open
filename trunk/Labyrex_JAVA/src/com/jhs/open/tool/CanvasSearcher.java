package com.jhs.open.tool;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import com.jhs.open.bean.EmitterBean;
import com.jhs.open.bean.MapBean;
import com.jhs.open.bean.MirrorBean;
import com.jhs.open.bean.ReceiverBean;
import com.jhs.open.control.GameControl;
import com.jhs.open.ui.Canvas;
import com.jhs.open.ui.LabyrexMapEditorFrame;
import com.jhs.open.ui.canvas.game.MapCanvas;
import com.jhs.open.ui.canvas.panel.GameCanvas;
import com.jhs.open.ui.shape.BallShape;
import com.jhs.open.ui.shape.EmitterShape;
import com.jhs.open.ui.shape.MirrorShape;
import com.jhs.open.ui.shape.ReceiverShape;

public class CanvasSearcher {
	public static EmitterShape findEmitter(EmitterShape[] emitterList, int x, int y) {

		for (int i = 0; i < emitterList.length; i++) {
			EmitterShape emitter = emitterList[i];

			Rectangle rec = new Rectangle(emitter.owner.x + emitter.x, emitter.owner.y + emitter.y, emitter.width,
					emitter.height);

			if (rec.contains(x, y)) {
				return emitter;
			}
		}
		return null;
	}

	public static EmitterBean findEmitter(EmitterBean[] emitterList, int x, int y) {

		for (int i = 0; i < emitterList.length; i++) {
			EmitterBean emitter = emitterList[i];

			if (emitter.x == x && emitter.y == y) {
				return emitter;
			}
		}
		return null;
	}

	public static ReceiverShape findReceiver(ReceiverShape[] receiverList, int x, int y) {
		for (int i = 0; i < receiverList.length; i++) {
			ReceiverShape receiver = receiverList[i];

			Rectangle rec = new Rectangle(receiver.owner.x + receiver.x, receiver.owner.y + receiver.y, receiver.width,
					receiver.height);

			if (rec.contains(x, y)) {
				return receiver;
			}
		}
		return null;
	}

	public static ReceiverBean findReceiver(ReceiverBean[] receiverList, int x, int y) {
		for (int i = 0; i < receiverList.length; i++) {
			ReceiverBean receiver = receiverList[i];

			if (receiver.x == x && receiver.y == y) {
				return receiver;
			}
		}
		return null;
	}

	public static MirrorShape findMirror(MirrorShape[] mirrorList, int x, int y) {

		for (int i = 0; i < mirrorList.length; i++) {
			MirrorShape mirror = mirrorList[i];

			Rectangle rec = new Rectangle(mirror.owner.x + mirror.x, mirror.owner.y + mirror.y, mirror.width,
					mirror.height);

			if (rec.contains(x, y)) {
				return mirror;
			}
		}
		return null;
	}

	public static MirrorBean findMirror(MirrorBean[] mirrorList, int x, int y) {

		for (int i = 0; i < mirrorList.length; i++) {
			MirrorBean mirror = mirrorList[i];

			if (mirror.x == x && mirror.y == y) {
				return mirror;
			}
		}
		return null;
	}

	public static EmitterShape findEmitter(int x, int y) {
		MapCanvas map = ((GameCanvas) GameControl.gi().getCurrCanvas()).getMapCanvas();

		return findEmitter(map.emitterList, x, y);
	}

	public static ReceiverShape findReceiver(int x, int y) {
		MapCanvas map = ((GameCanvas) GameControl.gi().getCurrCanvas()).getMapCanvas();

		return findReceiver(map.receiverList, x, y);
	}

	public static MirrorShape findMirror(int x, int y) {
		MapCanvas map = ((GameCanvas) GameControl.gi().getCurrCanvas()).getMapCanvas();

		return findMirror(map.mirrorList, x, y);
	}

	public static MirrorShape findBallInMirror(int x, int y) {
		MapCanvas map = ((GameCanvas) GameControl.gi().getCurrCanvas()).getMapCanvas();

		for (int i = 0; i < map.mirrorList.length; i++) {
			MirrorShape mirror = map.mirrorList[i];

			Rectangle rec = new Rectangle(mirror.owner.x + mirror.x + mirror.width / 2 - BallShape.speed / 2,
					mirror.owner.y + mirror.y + mirror.height / 2 - BallShape.speed / 2, BallShape.speed,
					BallShape.speed);

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
		MapCanvas map = ((GameCanvas) GameControl.gi().getCurrCanvas()).getMapCanvas();

		Rectangle rec = new Rectangle(map.owner.x + map.x, map.owner.y + map.y, map.width, map.height);

		if (rec.contains(x, y)) {
			return false;
		} else {
			return true;
		}
	}

	public static EmitterBean hasEmitterBean(MapBean map, int x, int y) {
		for (int i = 0; i < map.emitterList.size(); i++) {
			if (map.emitterList.get(i).x == x && map.emitterList.get(i).y == y) {
				return map.emitterList.get(i);
			}
		}

		return null;
	}

	public static MirrorBean hasMirrorBean(MapBean map, int x, int y) {
		for (int i = 0; i < map.mirrorList.size(); i++) {
			if (map.mirrorList.get(i).x == x && map.mirrorList.get(i).y == y) {
				return map.mirrorList.get(i);
			}
		}

		return null;
	}

	public static ReceiverBean hasReceiverBean(MapBean map, int x, int y) {
		for (int i = 0; i < map.receiverList.size(); i++) {
			if (map.receiverList.get(i).x == x && map.receiverList.get(i).y == y) {
				return map.receiverList.get(i);
			}
		}

		return null;
	}

	public static boolean hasBean(MapBean map, int x, int y) {
		return hasMirrorBean(map, x, y) != null || hasEmitterBean(map, x, y) != null
				|| hasReceiverBean(map, x, y) != null;
	}

	public static boolean inEmitterRange(int x, int y) {
		if ((x == -1 || x == LabyrexMapEditorFrame.gi().getCurrMapBean().width) && y >= 0
				&& y < LabyrexMapEditorFrame.gi().getCurrMapBean().height) {
			return true;
		}
		if ((y == -1 || y == LabyrexMapEditorFrame.gi().getCurrMapBean().height) && x >= 0
				&& x < LabyrexMapEditorFrame.gi().getCurrMapBean().width) {
			return true;
		}
		return false;
	}

	public static boolean inReceiverRange(int x, int y) {
		return inEmitterRange(x, y);
	}

	public static boolean inMirrorRange(int x, int y) {
		if (x >= 0 && x < LabyrexMapEditorFrame.gi().getCurrMapBean().width && y >= 0
				&& y < LabyrexMapEditorFrame.gi().getCurrMapBean().height) {
			return true;
		}
		return false;
	}

	public static boolean inDrawableRange(int x, int y) {
		return inMirrorRange(x, y) || inReceiverRange(x, y) || inEmitterRange(x, y);
	}
}
