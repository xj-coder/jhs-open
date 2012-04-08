package c.city.desolate.tool;

import c.city.desolate.control.GameControl;
import c.city.desolate.ui.canvas.game.MapCanvas;
import c.city.desolate.ui.canvas.panel.GameCanvas;

public class GameTools {

	public static boolean isWin() {
		MapCanvas map = ((GameCanvas) GameControl.gi().getCurrGameCanvas()).getMapCanvas();

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
