package com.jhs.open.control;

import java.awt.Cursor;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.jhs.open.Define;
import com.jhs.open.bean.MapBean;
import com.jhs.open.ui.Canvas;
import com.jhs.open.ui.LabyrexFrame;
import com.jhs.open.ui.canvas.panel.GameCanvas;
import com.jhs.open.ui.canvas.panel.HelpCanvas;
import com.jhs.open.ui.canvas.panel.MenuCanvas;
import com.jhs.open.ui.canvas.panel.TitleCanvas;

/**
 * 游戏管理类
 * 
 * @author JHS
 * 
 */
public class GameControl {
	public static final int G_Null = -1;

	public static final int G_Title = 0;

	public static final int G_Menu = 1;

	public static final int G_Game = 2;

	public static final int G_Help = 3;

	private MapBean currMap;

	private int currCanvasIndex = G_Null;
	private int preCanvasIndex = G_Null;

	private Canvas currCanvas;

	private static GameControl gi;

	private boolean isSound = true;
	private boolean isMusic = false;

	private boolean lock = false;

	private ReentrantReadWriteLock readWriteLock;

	public static GameControl gi() {
		if (gi == null) {
			gi = new GameControl();
		}
		return gi;
	}

	private GameControl() {
	}

	public int getCurrCanvasIndex() {
		return currCanvasIndex;
	}

	public void setCurrCanvasIndex(int index) {
		if (currCanvasIndex != index) {
			writeLock();
			try {
				if (currCanvas != null) {
					preCanvasIndex = currCanvasIndex;

					ListenerControl.gi().loopRemoveCanvasListener(currCanvas);
					currCanvas.close();
				}

				LabyrexFrame.gi().setCursor(Cursor.getDefaultCursor());

				currCanvasIndex = index;
			} finally {
				writeUnlock();
			}
		}
	}

	public int getPreCanvasIndex() {
		return preCanvasIndex;
	}

	public Canvas getCurrCanvas() {
		if (currCanvas == null || currCanvas.isClose) {
			writeLock();
			try {
				switch (getCurrCanvasIndex()) {
				case G_Title:
					currCanvas = new TitleCanvas();
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
					currCanvas.init();

					for (int i = 0; i < currCanvas.getMouseListeners().size(); i++) {
						ListenerControl.gi().registMouseListener(currCanvas, currCanvas.getMouseListeners().get(i));
					}
				}
			} finally {
				writeUnlock();
			}
		}
		return currCanvas;
	}

	public MapBean getCurrMap() {
		return currMap;
	}

	public void setCurrMap(MapBean currMap) {
		this.currMap = currMap;
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

	public void writeLock() {
		getReadWriteLock().writeLock().lock();
	}

	public void writeUnlock() {
		getReadWriteLock().writeLock().unlock();
	}

	public void readLock() {
		getReadWriteLock().readLock().lock();
	}

	public void ReadUnlock() {
		getReadWriteLock().readLock().unlock();
	}

	private ReentrantReadWriteLock getReadWriteLock() {
		if (readWriteLock == null) {
			readWriteLock = new ReentrantReadWriteLock(true);
		}
		return readWriteLock;
	}
}
