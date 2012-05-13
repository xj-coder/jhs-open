package com.jhs.open.control.event.win;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.jhs.open.control.GameControl;


public class WinCanvasMouseClickedEvent extends MouseAdapter {

	@Override
	public void mouseClicked(MouseEvent e) {
		GameControl.gi().setCurrCanvasIndex(GameControl.G_Menu);
	}
}
