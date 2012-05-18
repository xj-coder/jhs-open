package com.jhs.open.control.event.cross;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.jhs.open.bean.MapBean;
import com.jhs.open.control.GameControl;

public class CrossCardMouseClickedEvent extends MouseAdapter {
	private MapBean map;

	public CrossCardMouseClickedEvent(MapBean map) {
		this.map = map;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (map.isEnabled) {
			GameControl.gi().setCurrMap(map);
			GameControl.gi().setCurrCanvasIndex(GameControl.G_Game);
		}
	}
}
