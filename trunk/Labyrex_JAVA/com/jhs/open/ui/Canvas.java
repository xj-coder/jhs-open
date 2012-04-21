package com.jhs.open.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Vector;

import com.jhs.open.Define;
import com.jhs.open.control.ListenerControl;
import com.jhs.open.control.event.CanvasMouseMoveEvent;
import com.jhs.open.tool.GraphicsTools;


public abstract class Canvas {
	protected BufferedImage backbuffer;// 画布缓存

	private Graphics2D g;// 画笔

	protected long timer = 0l;// 计时器

	protected ArrayList<Canvas> canvasList = new ArrayList<Canvas>();

	public final Vector<MouseAdapter> mouseListeners = new Vector<MouseAdapter>();
	public final Vector<KeyListener> keyListeners = new Vector<KeyListener>();

	public int x;// x轴值，像素单位
	public int y;// y轴值，像素单位
	public int width;// 宽度值，像素单位
	public int height;// 高度值，像素单位

	public Canvas owner;

	protected boolean isOver = false;

	public boolean isMouseIn(int x, int y) {
		if (owner != null) {
			if (x >= owner.x + this.x && x <= owner.x + this.x + width && y >= owner.y + this.y
					&& y <= owner.y + this.y + height) {
				return true;
			} else {
				return false;
			}
		} else {
			if (x >= this.x && x <= this.x + width && y >= this.y && y <= this.y + height) {
				return true;
			} else {
				return false;
			}
		}
	}

	public Canvas() {
	}

	public Canvas(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;

		addMouseListener(new CanvasMouseMoveEvent(this));
	}

	public void init() {

	}

	public void addCanvas(Canvas canvas) {
		canvas.owner = this;
		canvasList.add(canvas);
		canvas.init();

		for (int i = 0; i < canvas.getMouseListeners().size(); i++) {
			ListenerControl.gi().registMouseListener(canvas, canvas.getMouseListeners().get(i));
		}
	}

	public boolean removeCanvas(Canvas shape) {
		shape.owner = null;

		ListenerControl.gi().removeMouseListener(shape);

		return canvasList.remove(shape);
	}

	public void removeAllCanvas() {
		while (getCanvasCount() > 0) {
			System.out.println(getCanvas(0) + "  " + removeCanvas(getCanvas(0)));
		}
	}

	public int getCanvasCount() {
		return canvasList.size();
	}

	public Canvas getCanvas(int index) {
		return canvasList.get(index);
	}

	public Graphics2D getGraphics() {
		if (g == null) {
			initGraphics();
		}
		return g;
	}

	/**
	 * 重画，由覆盖全部界面的画板使用
	 */

	public void render() {
		render(getGraphics());
	}

	/**
	 * 重画，由小部件使用
	 */
	public void render(Graphics g) {
		for (int i = 0; i < canvasList.size(); i++) {
			canvasList.get(i).render(g);
		}
	}

	/**
	 * 更新计时器
	 */

	public void update(long l) {
		timer += l;
		for (int i = 0; i < canvasList.size(); i++) {
			canvasList.get(i).update(l);
		}
	}

	/**
	 * 填充
	 */

	public void flip(Graphics g) {
		g.drawImage(backbuffer, 0, 0, null);
	}

	public void drawText(String s, int i, int j, Color c, Font f) {
		GraphicsTools.backupGraphics(g);
		g.setColor(c);
		g.setFont(f);
		g.drawString(s, i, j);
		GraphicsTools.restoreGraphics(g);
	}

	public boolean isOver() {
		return isOver;
	}

	public void setOver(boolean isOver) {
		this.isOver = isOver;
	}

	public void initGraphics() {
		backbuffer = new BufferedImage(Define.Main.width, Define.Main.height, 1);
		g = backbuffer.createGraphics();
	}

	public Vector<MouseAdapter> getMouseListeners() {
		return mouseListeners;
	}

	public void addMouseListener(MouseAdapter mouseListener) {
		mouseListeners.add(mouseListener);
	}

	public void removeMouseListener(MouseAdapter mouseListener) {
		mouseListeners.remove(mouseListeners);
	}

	public void removeMouseListener(int index) {
		mouseListeners.remove(index);
	}

	public Vector<KeyListener> getKeyListeners() {
		return keyListeners;
	}

	public void addKeyListener(KeyListener keyListener) {
		keyListeners.add(keyListener);
	}

	public void removeKeyListener(KeyListener keyListener) {
		keyListeners.remove(keyListeners);
	}

	public void removeKeyListener(int index) {
		keyListeners.remove(index);
	}

}
