package com.jhs.open.control.event.map;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import com.jhs.open.Define;
import com.jhs.open.bean.MirrorBean;
import com.jhs.open.control.GameControl;
import com.jhs.open.control.SoundControl;
import com.jhs.open.tool.ImgSelector;
import com.jhs.open.ui.canvas.game.MapCanvas;
import com.jhs.open.ui.canvas.panel.GameCanvas;
import com.jhs.open.ui.shape.MirrorShape;
import com.jhs.open.ui.shape.PathShape;

public class MirrorMouseClickedAdapter extends MouseAdapter {
	private MirrorShape mirror;

	public MirrorMouseClickedAdapter(MirrorShape mirror) {
		this.mirror = mirror;
	}

	@Override
	public void mouseClicked(MouseEvent e) {

		GameCanvas gameCanvas = (GameCanvas) GameControl.gi().getCurrCanvas();
		MapCanvas map = gameCanvas.getMapCanvas();

		boolean lockSelected = gameCanvas.getToolCanvas().getLockButton().isSelected();
		boolean unlockSelected = gameCanvas.getToolCanvas().getUnlockButton().isSelected();

		if (lockSelected) {
			mirror.bean.isLock = true;
		}

		if (!mirror.bean.isLock) {
			Vector<PathShape> paths = ImgSelector.inPath(mirror.bean);
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
