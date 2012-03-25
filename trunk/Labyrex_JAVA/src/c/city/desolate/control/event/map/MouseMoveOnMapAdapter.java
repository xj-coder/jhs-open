package c.city.desolate.control.event.map;

import c.city.desolate.tool.ImgSelector;
import c.city.desolate.ui.Canvas;
import c.city.desolate.ui.LabyrexFrame;
import c.city.desolate.ui.shape.EmitterShape;
import c.city.desolate.ui.shape.MirrorShape;
import c.city.desolate.ui.shape.PathShape;
import c.city.desolate.ui.shape.ReceiverShape;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

public class MouseMoveOnMapAdapter extends MouseAdapter {
	private Canvas canvas;

	public MouseMoveOnMapAdapter(Canvas canvas) {
		this.canvas = canvas;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		Vector<PathShape> thickPaths = null;

		if (canvas instanceof EmitterShape) {
			thickPaths = ImgSelector.inPath((EmitterShape) canvas);
		} else if (canvas instanceof ReceiverShape) {
			thickPaths = ImgSelector.inPath((ReceiverShape) canvas);
		} else if (canvas instanceof MirrorShape) {
			thickPaths = ImgSelector.inPath((MirrorShape) canvas);
			LabyrexFrame.gi().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		}

		if (thickPaths != null && thickPaths.size() > 0) {
			for (int i = 0; i < thickPaths.size(); i++) {
				thickPaths.get(i).isThick = true;
			}
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		LabyrexFrame.gi().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

		Vector<PathShape> thickPaths = null;

		if (canvas instanceof EmitterShape) {
			thickPaths = ImgSelector.inPath((EmitterShape) canvas);
		} else if (canvas instanceof ReceiverShape) {
			thickPaths = ImgSelector.inPath((ReceiverShape) canvas);
		} else if (canvas instanceof MirrorShape) {
			thickPaths = ImgSelector.inPath((MirrorShape) canvas);
		}

		if (thickPaths != null) {
			for (int i = 0; i < thickPaths.size(); i++) {
				thickPaths.get(i).isThick = false;
			}
		}
	}
}
