package com.jhs.open.control.event.map;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import com.jhs.open.tool.ImgSelector;
import com.jhs.open.ui.Canvas;
import com.jhs.open.ui.LabyrexFrame;
import com.jhs.open.ui.shape.EmitterShape;
import com.jhs.open.ui.shape.MirrorShape;
import com.jhs.open.ui.shape.PathShape;
import com.jhs.open.ui.shape.ReceiverShape;

public class MouseMoveOnMapAdapter extends MouseAdapter {
	private Canvas canvas;

	public MouseMoveOnMapAdapter(Canvas canvas) {
		this.canvas = canvas;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		Vector<PathShape> thickPaths = null;

		if (canvas instanceof EmitterShape) {
			thickPaths = ImgSelector.inPath(((EmitterShape) canvas).bean);
		} else if (canvas instanceof ReceiverShape) {
			thickPaths = ImgSelector.inPath(((ReceiverShape) canvas).bean);
		} else if (canvas instanceof MirrorShape) {
			thickPaths = ImgSelector.inPath(((MirrorShape) canvas).bean);
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
			thickPaths = ImgSelector.inPath(((EmitterShape) canvas).bean);
		} else if (canvas instanceof ReceiverShape) {
			thickPaths = ImgSelector.inPath(((ReceiverShape) canvas).bean);
		} else if (canvas instanceof MirrorShape) {
			thickPaths = ImgSelector.inPath(((MirrorShape) canvas).bean);
		}

		if (thickPaths != null) {
			for (int i = 0; i < thickPaths.size(); i++) {
				thickPaths.get(i).isThick = false;
			}
		}
	}
}
