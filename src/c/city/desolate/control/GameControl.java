package c.city.desolate.control;

import c.city.desolate.Define;
import c.city.desolate.ui.Canvas;
import c.city.desolate.ui.LabyrexFrame;
import c.city.desolate.ui.canvas.game.MapCanvas;
import c.city.desolate.ui.canvas.panel.GameCanvas;
import c.city.desolate.ui.canvas.panel.HelpCanvas;
import c.city.desolate.ui.canvas.panel.MainCanvas;
import c.city.desolate.ui.canvas.panel.MenuCanvas;

import java.awt.*;

/**
 * 游戏管理类
 * 
 * @author JHS
 * 
 */
public class GameControl {
	public static final int G_Null = -1;

	public static final int G_Main = 0;

	public static final int G_Menu = 1;

	public static final int G_Game = 2;

	public static final int G_Help = 3;

	private String currMapName;

	private int currCanvasIndex;
	private int preCanvasIndex;

	private Canvas currCanvas;

	private static GameControl instance;

	private boolean isSound = true;
	private boolean isMusic = true;

	private boolean lock = false;

	private GameControl() {
		setCurrCanvas(G_Main);
	}

	public int getCurrGameCanvasIndex() {
		return currCanvasIndex;
	}

	public void setCurrCanvas(int index) {
		if (currCanvasIndex != index) {
			if (currCanvas != null) {
				preCanvasIndex = currCanvasIndex;

				ListenerControl.gi().loopRemoveCanvasListener(currCanvas);
			}

			LabyrexFrame.gi().setCursor(Cursor.getDefaultCursor());

			currCanvasIndex = index;
			currCanvas = null;
		}
	}

	public int getPreCanvasIndex() {
		return preCanvasIndex;
	}

	public Canvas getCurrGameCanvas() {
		if (currCanvas == null) {
			switch (getCurrGameCanvasIndex()) {
			case G_Main:
				currCanvas = new MainCanvas();
				break;
			case G_Menu:
				currCanvas = new MenuCanvas();
				break;
			case G_Game:
				currCanvas = new GameCanvas();
				break;
			case G_Help:
				currCanvas = new HelpCanvas();
				break;
			}
			if (currCanvas != null) {
				for (int i = 0; i < currCanvas.getMouseListeners().size(); i++) {
					ListenerControl.gi().registMouseListener(currCanvas, currCanvas.getMouseListeners().get(i));
				}
			}
		}
		return currCanvas;
	}

	public void setCurrMapName(String currMapName) {
		this.currMapName = currMapName;
	}

	public String getCurrMapName() {
		return currMapName;
	}

	public void resetMap() {
		getLock();

		int index = getCurrGameCanvasIndex();
		setCurrCanvas(G_Null);
		MapControl.resetMap();
		setCurrCanvas(index);

		unlock();
	}

	public MapCanvas getCurrMap() {
		return MapControl.getMapByName(currMapName);
	}

	public boolean isMusic() {
		return isMusic;
	}

	public boolean isSound() {
		return isSound;
	}

	public void setSound(boolean isSound) {
		this.isSound = isSound;
	}

	public void setMusic(boolean isMusic) {
		this.isMusic = isMusic;
		if (isMusic) {
			SoundControl.go(Define.Sound.bg_sound);
		} else {
			SoundControl.pause(Define.Sound.bg_sound);
		}
	}

	public static GameControl gi() {
		if (instance == null) {
			instance = new GameControl();
		}
		return instance;
	}

	public synchronized void getLock() {
		if (lock) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		lock = true;
	}

	public synchronized void unlock() {
		lock = false;
		notify();
	}

}
