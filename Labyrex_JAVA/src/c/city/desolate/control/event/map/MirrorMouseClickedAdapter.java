package c.city.desolate.control.event.map;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import c.city.desolate.Define;
import c.city.desolate.bean.MirrorBean;
import c.city.desolate.control.GameControl;
import c.city.desolate.control.SoundControl;
import c.city.desolate.tool.ImgSelector;
import c.city.desolate.ui.canvas.game.MapCanvas;
import c.city.desolate.ui.canvas.panel.GameCanvas;
import c.city.desolate.ui.shape.MirrorShape;
import c.city.desolate.ui.shape.PathShape;

public class MirrorMouseClickedAdapter extends MouseAdapter {
	private MirrorShape mirror;

	public MirrorMouseClickedAdapter(MirrorShape mirror) {
		this.mirror = mirror;
	}

	@Override
	public void mouseClicked(MouseEvent e) {

		GameCanvas gameCanvas = (GameCanvas) GameControl.gi().getCurrGameCanvas();
		MapCanvas map = gameCanvas.getMapCanvas();

		boolean lockSelected = gameCanvas.getToolCanvas().getLockButton().isSelected();
		boolean unlockSelected = gameCanvas.getToolCanvas().getUnlockButton().isSelected();

		if (lockSelected) {
			mirror.bean.isLock = true;
		}

		if (!mirror.bean.isLock) {
			Vector<PathShape> paths = ImgSelector.inPath(mirror);
			if (paths.size() > 0) {
				if (GameControl.gi().isSound()) {
					SoundControl.play(Define.Sound.path_bad_sound);
				}
				for (int i = 0; i < paths.size(); i++) {
					map.removePath(paths.get(i));
				}
			}

			if (mirror.bean.type.equals(MirrorBean.LEFT)) {
				mirror.bean.type = MirrorBean.RIGHT;
			} else {
				mirror.bean.type = MirrorBean.LEFT;
			}

			if (GameControl.gi().isSound()) {
				SoundControl.play(Define.Sound.mirrot_change_sound);
			}
		}

		if (unlockSelected) {
			mirror.bean.isLock = false;
		}
	}
}
