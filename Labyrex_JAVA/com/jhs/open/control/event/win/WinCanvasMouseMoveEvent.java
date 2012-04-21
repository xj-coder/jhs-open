package com.jhs.open.control.event.win;


import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.jhs.open.ui.LabyrexFrame;

public class WinCanvasMouseMoveEvent extends MouseAdapter {

	@Override
	public void mouseEntered(MouseEvent e) {
		LabyrexFrame.gi().setCursor(new Cursor(Cursor.HAND_CURSOR));
	}

	@Override
	public void mouseExited(MouseEvent e) {
		LabyrexFrame.gi().setCursor(Cursor.getDefaultCursor());
	}
}
