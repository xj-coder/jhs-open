package com.jhs.open.control;

import java.awt.Cursor;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.jhs.open.Define;
import com.jhs.open.bean.MapBean;
import com.jhs.open.ui.Canvas;
import com.jhs.open.ui.LabyrexFrame;
import com.jhs.open.ui.canvas.panel.GameCanvas;
import com.jhs.open.ui.canvas.panel.HelpCanvas;
import com.jhs.open.ui.canvas.panel.MainCanvas;
import com.jhs.open.ui.canvas.panel.MenuCanvas;

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
		gi.setCurrCanvasIndex(G_Title);
	}

	public int getCurrCanvasIndex() {
		return currCanvasIndex;
	}

	public void setCurrCanvasIndex(int index) {
		if (currCanvasIndex != index) {
			getReadWriteLock().writeLock().lock();
			try {
				if (currCanvas != null) {
					preCanvasIndex = currCanvasIndex;

					ListenerControl.gi().loopRemoveCanvasListener(currCanvas);
					currCanvas.removeAllCanvas();
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

	public Canvas getCurrCanvas() {
		if (currCanvas == null) {
			getReadWriteLock().readLock().lock();// 获得读锁
			try {
				switch (getCurrCanvasIndex()) {
				case G_Title:
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
				currCanvas.init();
			} finally {
				getReadWriteLock().readLock().unlock();// 释放读锁
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

	public ReentrantReadWriteLock getReadWriteLock() {
		if (readWriteLock == null) {
			readWriteLock = new ReentrantReadWriteLock();
		}
		return readWriteLock;
	}
}
