package c.city.desolate.control.event.map;

import c.city.desolate.Define;
import c.city.desolate.control.GameControl;
import c.city.desolate.control.MapControl;
import c.city.desolate.control.SoundControl;
import c.city.desolate.tool.ImgSelector;
import c.city.desolate.ui.canvas.game.MapCanvas;
import c.city.desolate.ui.canvas.panel.GameCanvas;
import c.city.desolate.ui.shape.MirrorShape;
import c.city.desolate.ui.shape.PathShape;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

public class MirrorMouseClickedAdapter extends MouseAdapter {
	private MirrorShape mirror;

	public MirrorMouseClickedAdapter(MirrorShape mirror) {
		this.mirror = mirror;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		MapCanvas map = MapControl.getMapByName(GameControl.gi().getCurrMapName());

		GameCanvas gameCanvas = (GameCanvas) GameControl.gi().getCurrGameCanvas();

		boolean lockSelected = gameCanvas.getToolCanvas().getLockButton().isSelected();
		boolean unlockSelected = gameCanvas.getToolCanvas().getUnlockButton().isSelected();

		if (lockSelected) {
			mirror.isLock = true;
		}

		if (!mirror.isLock) {
			Vector<PathShape> paths = ImgSelector.inPath(mirror);
			if (paths.size() > 0) {
				if (GameControl.gi().isSound()) {
					SoundControl.play(Define.Sound.path_bad_sound);
				}
				for (int i = 0; i < paths.size(); i++) {
					map.removePath(paths.get(i));
				}
			}

			if (mirror.type.equals(MirrorShape.LEFT)) {
				mirror.type = MirrorShape.RIGHT;
			} else {
				mirror.type = MirrorShape.LEFT;
			}

			if (GameControl.gi().isSound()) {
				SoundControl.play(Define.Sound.mirrot_change_sound);
			}
		}

		if (unlockSelected) {
			mirror.isLock = false;
		}
	}
}
