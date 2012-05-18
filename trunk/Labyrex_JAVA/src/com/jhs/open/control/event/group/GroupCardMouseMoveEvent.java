package com.jhs.open.control.event.group;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.jhs.open.ui.LabyrexFrame;

public class GroupCardMouseMoveEvent extends MouseAdapter {

	@Override
	public void mouseEntered(MouseEvent e) {
		LabyrexFrame.gi().setCursor(new Cursor(Cursor.HAND_CURSOR));
	}

	@Override
	public void mouseExited(MouseEvent e) {
		LabyrexFrame.gi().setCursor(Cursor.getDefaultCursor());
	}
}
