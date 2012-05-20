package com.jhs.open.control.event;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.jhs.open.ui.LabyrexFrame;

public class FullCanvasDragEvent extends MouseAdapter {

	private int pressX;
	private int pressY;

	public FullCanvasDragEvent() {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		pressX = e.getX();
		pressY = e.getY();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		int _x = LabyrexFrame.gi().getX() + e.getX() - pressX;
		int _y = LabyrexFrame.gi().getY() + e.getY() - pressY;
		LabyrexFrame.gi().setLocation(_x, _y);
	}
}
