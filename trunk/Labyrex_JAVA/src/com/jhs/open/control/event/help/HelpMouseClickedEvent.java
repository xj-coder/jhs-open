package com.jhs.open.control.event.help;


import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.jhs.open.control.GameControl;

public class HelpMouseClickedEvent extends MouseAdapter {

	@Override
	public void mouseClicked(MouseEvent e) {
		GameControl.gi().setCurrCanvasIndex(GameControl.gi().getPreCanvasIndex());
	}
}
