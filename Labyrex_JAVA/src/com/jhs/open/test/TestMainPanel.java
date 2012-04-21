package com.jhs.open.test;

import com.jhs.open.control.GameControl;
import com.jhs.open.ui.LabyrexFrame;


public class TestMainPanel {
	public static void main(String[] args) {
		LabyrexFrame.gi().setVisible(true);
		GameControl.gi().setCurrMapName("difficute.1");
		GameControl.gi().setCurrCanvas(GameControl.G_Game);
		LabyrexFrame.gi().start();
	}
}
