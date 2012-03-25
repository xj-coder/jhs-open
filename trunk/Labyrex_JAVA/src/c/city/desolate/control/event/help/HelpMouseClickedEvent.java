package c.city.desolate.control.event.help;

import c.city.desolate.control.GameControl;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HelpMouseClickedEvent extends MouseAdapter {

	@Override
	public void mouseClicked(MouseEvent e) {
		GameControl.gi().setCurrCanvas(GameControl.gi().getPreCanvasIndex());
	}
}
