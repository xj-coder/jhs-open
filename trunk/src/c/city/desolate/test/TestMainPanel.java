package c.city.desolate.test;

import c.city.desolate.control.GameControl;
import c.city.desolate.ui.LabyrexFrame;

public class TestMainPanel {
	public static void main(String[] args) {
		LabyrexFrame.gi().setVisible(true);
		GameControl.gi().setCurrMapName("difficute.1");
		GameControl.gi().setCurrCanvas(GameControl.G_Game);
		LabyrexFrame.gi().start();
	}
}
