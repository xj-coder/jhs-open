package c.city.desolate.control.event.exbutton;

import c.city.desolate.Define;
import c.city.desolate.control.GameControl;
import c.city.desolate.control.SoundControl;
import c.city.desolate.ui.canvas.panel.GameCanvas;
import c.city.desolate.ui.shape.EXButton;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
			GameControl.gi().setCurrCanvas(GameControl.G_Menu);
		} else if (button.getName().equals("music")) {
			GameControl.gi().setMusic(!button.isSelected());
		} else if (button.getName().equals("sound")) {
			GameControl.gi().setSound(!button.isSelected());
		} else if (button.getName().equals("help")) {
			GameControl.gi().setCurrCanvas(GameControl.G_Help);
		} else if (button.getName().equals("back")) {
			GameControl.gi().setCurrCanvas(GameControl.G_Main);
		} else if (button.getName().equals("reset")) {
			GameControl.gi().resetMap();
		} else if (button.getName().equals("menu")) {
			GameControl.gi().setCurrCanvas(GameControl.G_Menu);
		} else if (button.getName().equals("lock")) {
			GameCanvas gameCanvas = (GameCanvas) GameControl.gi().getCurrGameCanvas();

			boolean lockSelected = gameCanvas.getToolCanvas().getLockButton().isSelected();
			if (lockSelected) {
				gameCanvas.getToolCanvas().getUnlockButton().setSelected(false);
			}
		} else if (button.getName().equals("unlock")) {
			GameCanvas gameCanvas = (GameCanvas) GameControl.gi().getCurrGameCanvas();

			boolean unlockSelected = gameCanvas.getToolCanvas().getUnlockButton().isSelected();
			if (unlockSelected) {
				gameCanvas.getToolCanvas().getLockButton().setSelected(false);
			}

		}
	}
}
