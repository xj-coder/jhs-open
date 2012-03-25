package c.city.desolate.control.event.win;

import c.city.desolate.control.GameControl;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class WinCanvasMouseClickedEvent extends MouseAdapter {

	@Override
	public void mouseClicked(MouseEvent e) {
		GameControl.gi().setCurrCanvas(GameControl.G_Menu);
		GameControl.gi().resetMap();
	}
}
