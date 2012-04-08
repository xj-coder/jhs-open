package c.city.desolate.control;

import java.awt.Cursor;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import c.city.desolate.Define;
import c.city.desolate.bean.MapBean;
import c.city.desolate.ui.Canvas;
import c.city.desolate.ui.LabyrexFrame;
import c.city.desolate.ui.canvas.panel.GameCanvas;
import c.city.desolate.ui.canvas.panel.HelpCanvas;
import c.city.desolate.ui.canvas.panel.MainCanvas;
import c.city.desolate.ui.canvas.panel.MenuCanvas;

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

	private static GameControl gi;

	private boolean isSound = true;
	private boolean isMusic = true;

	private boolean lock = false;

	private ReentrantReadWriteLock readWriteLock;

	public static GameControl gi() {
		if (gi == null) {
			gi = new GameControl();
			gi.initCanvas();
		}
		return gi;
	}

	private GameControl() {
	}

	private void initCanvas() {
		gi.setCurrCanvas(G_Main);
	}

	public int getCurrGameCanvasIndex() {
		return currCanvasIndex;
	}

	public void setCurrCanvas(int index) {
		if (currCanvasIndex != index) {
			getReadWriteLock().writeLock().lock();
			try {
				if (currCanvas != null) {
					preCanvasIndex = currCanvasIndex;

					ListenerControl.gi().loopRemoveCanvasListener(currCanvas);
				}

				LabyrexFrame.gi().setCursor(Cursor.getDefaultCursor());

				currCanvasIndex = index;
				currCanvas = null;
			} finally {
				getReadWriteLock().writeLock().unlock();
			}
		}
	}

	public int getPreCanvasIndex() {
		return preCanvasIndex;
	}

	public Canvas getCurrGameCanvas() {
		if (currCanvas == null) {
			getReadWriteLock().readLock().lock();
			try {
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
				// if (currCanvas != null) {
				// ListenerControl.gi().loopRegistCanvasListener(currCanvas);
				// }
				currCanvas.init();
			} finally {
				getReadWriteLock().readLock().unlock();
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

	// public void resetMap() {
	// // getLock();
	// getReadWriteLock().writeLock().lock();
	//
	// int index = getCurrGameCanvasIndex();
	// setCurrCanvas(G_Null);
	// MapControl.resetMap();
	// setCurrCanvas(index);
	//
	// getReadWriteLock().writeLock().unlock();
	// // unlock();
	// }

	public MapBean getCurrMap() {
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

	/**
	 * 该方法过期，由ReentrantReadWriteLock替代
	 */
	@Deprecated
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

	/**
	 * 该方法过期，由ReentrantReadWriteLock替代
	 */
	@Deprecated
	public synchronized void unlock() {
		lock = false;
		notify();
	}

	public ReentrantReadWriteLock getReadWriteLock() {
		if (readWriteLock == null) {
			readWriteLock = new ReentrantReadWriteLock();
		}
		return readWriteLock;
	}
}
