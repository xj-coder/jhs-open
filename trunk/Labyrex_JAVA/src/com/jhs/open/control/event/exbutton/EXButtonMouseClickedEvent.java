package com.jhs.open.control.event.exbutton;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.jhs.open.Define;
import com.jhs.open.control.GameControl;
import com.jhs.open.control.SoundControl;
import com.jhs.open.ui.canvas.panel.GameCanvas;
import com.jhs.open.ui.shape.EXButton;

public class EXButtonMouseClickedEvent extends MouseAdapter {

	private EXButton button;

	public EXButtonMouseClickedEvent(EXButton button) {
		this.button = button;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("button click");
		if (GameControl.gi().isSound()) {
			SoundControl.play(Define.Sound.button_click_sound);
		}
		if (button.isToggle()) {
			button.setSelected(!button.isSelected());
		}
		if (button.getName().equals("start")) {
			GameControl.gi().setCurrCanvasIndex(GameControl.G_Menu);
		} else if (button.getName().equals("music")) {
			GameControl.gi().setMusic(!button.isSelected());
		} else if (button.getName().equals("sound")) {
			GameControl.gi().setSound(!button.isSelected());
		} else if (button.getName().equals("help")) {
			GameControl.gi().setCurrCanvasIndex(GameControl.G_Help);
		} else if (button.getName().equals("back")) {
			GameControl.gi().setCurrCanvasIndex(GameControl.G_Title);
		} else if (button.getName().equals("reset")) {
			((GameCanvas) GameControl.gi().getCurrCanvas()).getMapCanvas().init();
		} else if (button.getName().equals("menu")) {
			GameControl.gi().setCurrCanvasIndex(GameControl.G_Menu);
		} else if (button.getName().equals("lock")) {
			GameCanvas gameCanvas = (GameCanvas) GameControl.gi().getCurrCanvas();

			boolean lockSelected = gameCanvas.getToolCanvas().getLockButton().isSelected();
			if (lockSelected) {
				gameCanvas.getToolCanvas().getUnlockButton().setSelected(false);
			}
		} else if (button.getName().equals("unlock")) {
			GameCanvas gameCanvas = (GameCanvas) GameControl.gi().getCurrCanvas();

			boolean unlockSelected = gameCanvas.getToolCanvas().getUnlockButton().isSelected();
			if (unlockSelected) {
				gameCanvas.getToolCanvas().getLockButton().setSelected(false);
			}

		}
	}
}
