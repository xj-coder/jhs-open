package com.jhs.open.control.event;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.jhs.open.ui.Canvas;

public class CanvasMouseMoveEvent extends MouseAdapter {

	private Canvas canvas;

	public CanvasMouseMoveEvent(Canvas canvas) {
		this.canvas = canvas;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		canvas.setOver(true);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		canvas.setOver(false);
	}
}
