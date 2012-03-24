package c.city.desolate.control.event.exbutton;

import c.city.desolate.ui.LabyrexFrame;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class EXButtonMouseMoveEvent extends MouseAdapter {

	@Override
	public void mouseEntered(MouseEvent e) {
		LabyrexFrame.gi().setCursor(new Cursor(Cursor.HAND_CURSOR));
	}

	@Override
	public void mouseExited(MouseEvent e) {
		LabyrexFrame.gi().setCursor(Cursor.getDefaultCursor());
	}
}