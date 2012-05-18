package com.jhs.open.control.event.group;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.jhs.open.control.GameControl;
import com.jhs.open.ui.canvas.panel.MenuCanvas;
import com.jhs.open.ui.shape.GroupShape;

public class GroupCardMouseClickedEvent extends MouseAdapter {
	private GroupShape group;

	public GroupCardMouseClickedEvent(GroupShape group) {
		this.group = group;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		MenuCanvas menuCanvas = (MenuCanvas) GameControl.gi().getCurrCanvas();

		menuCanvas.setCurrGroupShape(group);
	}
}
