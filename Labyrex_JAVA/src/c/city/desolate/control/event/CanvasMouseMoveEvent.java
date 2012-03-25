package c.city.desolate.control.event;

import c.city.desolate.ui.Canvas;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
