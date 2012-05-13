package com.jhs.open.tool;

import com.jhs.open.control.GameControl;
import com.jhs.open.ui.canvas.game.MapCanvas;
import com.jhs.open.ui.canvas.panel.GameCanvas;


public class GameTools {

	public static boolean isWin() {
		MapCanvas map = ((GameCanvas) GameControl.gi().getCurrCanvas()).getMapCanvas();

		for (int i = 0; i < map.receiverList.length; i++) {
			if (ImgSelector.inPath(map.receiverList[i]).size() == 0) {
				return false;
			}
		}
		for (int i = 0; i < map.mirrorList.length; i++) {
			if (ImgSelector.inPath(map.mirrorList[i]).size() == 0) {
				return false;
			}
		}
		for (int i = 0; i < map.emitterList.length; i++) {
			if (ImgSelector.inPath(map.emitterList[i]).size() == 0) {
				return false;
			}
		}

		GameTools.class.getResource("");

		return true;
	}
}
