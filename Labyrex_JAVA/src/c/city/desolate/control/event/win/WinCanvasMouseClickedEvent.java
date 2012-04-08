package c.city.desolate.control.event.win;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import c.city.desolate.control.GameControl;

public class WinCanvasMouseClickedEvent extends MouseAdapter {

	@Override
	public void mouseClicked(MouseEvent e) {
		GameControl.gi().setCurrCanvas(GameControl.G_Menu);
	}
}
