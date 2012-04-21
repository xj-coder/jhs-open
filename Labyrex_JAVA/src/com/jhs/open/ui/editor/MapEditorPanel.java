package com.jhs.open.ui.editor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import com.jhs.open.Define;
import com.jhs.open.bean.MapBean;
import com.jhs.open.tool.GraphicsTools;
import com.jhs.open.tool.ImageTools;
import com.jhs.open.ui.LabyrexMapEditorFrame;
import com.jhs.open.ui.shape.EmitterShape;
import com.jhs.open.ui.shape.MirrorShape;
import com.jhs.open.ui.shape.ReceiverShape;


/**
 * 地图编辑面板
 * 
 * @author JHS
 * 
 */
public class MapEditorPanel extends JPanel {

	private static final long serialVersionUID = -7879585521403488655L;

	public EmitterShape[] emitterList;// 发射器集合
	public ReceiverShape[] receiverList;// 接收器集合
	public MirrorShape[] mirrorList;// 挡板集合

	private boolean isMouseIn = false;
	private int mouseX;
	private int mouseY;

	private int draw_width;
	private int draw_height;
	private int draw_x;
	private int draw_y;

	private boolean isShowGrid = true;

	private LabyrexMapEditorFrame editorFrame;

	public MapEditorPanel(LabyrexMapEditorFrame editorFrame) {
		this.editorFrame = editorFrame;

		initData();
		initEvent();
	}

	private void initEvent() {

	}

	private void initData() {
		MapBean map = editorFrame.getCurrMapBean();

		// for (int i = 0; i < map.mirrorList.length; i++) {
		// map.mirrorList[i].type = map.mirrorList[i].iniType;
		// }

		if (map != null) {
			if (map.emitterList != null) {
				emitterList = new EmitterShape[map.emitterList.length];
				for (int i = 0; i < map.emitterList.length; i++) {
					EmitterShape emitterShape = new EmitterShape(map.emitterList[i].x * Define.Main.grid_size,
							map.emitterList[i].y * Define.Main.grid_size, map.emitterList[i]);
					emitterList[i] = emitterShape;
				}
			}
			if (map.receiverList != null) {
				receiverList = new ReceiverShape[map.receiverList.length];
				for (int i = 0; i < map.receiverList.length; i++) {
					ReceiverShape receiverShape = new ReceiverShape(map.receiverList[i].x * Define.Main.grid_size,
							map.receiverList[i].y * Define.Main.grid_size, map.receiverList[i]);
					receiverList[i] = receiverShape;
				}
			}
			if (map.mirrorList != null) {
				mirrorList = new MirrorShape[map.mirrorList.length];
				for (int i = 0; i < map.mirrorList.length; i++) {
					MirrorShape mirrorShape = new MirrorShape(map.mirrorList[i].x * Define.Main.grid_size,
							map.mirrorList[i].y * Define.Main.grid_size, map.mirrorList[i]);
					mirrorList[i] = mirrorShape;
				}
			}
		}
	}

	public void reset() {
		initData();
		repaint();
	}

	public boolean isMouseIn() {
		return isMouseIn;
	}

	public void setMouseIn(boolean isMouseIn) {
		this.isMouseIn = isMouseIn;
		if (LabyrexMapEditorFrame.gi().getCurrMapBean() != null) {
			repaint();
		}
	}

	public int getMouseX() {
		return mouseX;
	}

	public void setMouseX(int mouseX) {
		this.mouseX = mouseX;
		if (LabyrexMapEditorFrame.gi().getCurrMapBean() != null) {
			repaint();
		}
	}

	public int getMouseY() {
		return mouseY;
	}

	public void setMouseY(int mouseY) {
		this.mouseY = mouseY;
		if (LabyrexMapEditorFrame.gi().getCurrMapBean() != null) {
			repaint();
		}
	}

	public boolean isShowGrid() {
		return isShowGrid;
	}

	public void setShowGrid(boolean isShowGrid) {
		this.isShowGrid = isShowGrid;
	}

	@Override
	public void update(Graphics g) {
		Image screen = createImage(getWidth(), getHeight(), true);// 双缓冲

		Graphics2D graphics = (Graphics2D) screen.getGraphics();

		// 自己的代码[begin]
		// 画背景
		GraphicsTools.backupGraphics(graphics);
		graphics.setColor(Color.gray);
		graphics.fillRect(0, 0, getWidth(), getHeight());

		MapBean map = editorFrame.getCurrMapBean();
		if (map != null) {
			// 计算地图绘制数据
			draw_width = map.width * Define.Main.grid_size;
			draw_height = map.height * Define.Main.grid_size;
			draw_x = (getWidth() - draw_width) / 2;
			draw_y = (getHeight() - draw_height) / 2;

			if (draw_x % Define.Main.grid_size != 0) {
				draw_x = draw_x - draw_x % Define.Main.grid_size;
			}
			if (draw_y % Define.Main.grid_size != 0) {
				draw_y = draw_y - draw_y % Define.Main.grid_size;
			}

			// 绘制可设置区域
			graphics.setColor(Color.black);
			graphics.fillRect(draw_x - Define.Main.grid_size, draw_y - Define.Main.grid_size, Define.Main.grid_size * 2
					+ draw_width, Define.Main.grid_size * 2 + draw_height);

			// 绘制地图
			Image g_image = ImageTools.cut(Define.IMG_PATH + "grid.png", 0, 0, Define.Main.grid_size,
					Define.Main.grid_size, "png");
			for (int i = 0; i < draw_width / Define.Main.grid_size; i++) {
				for (int j = 0; j < draw_height / Define.Main.grid_size; j++) {
					graphics.drawImage(g_image, draw_x + j * Define.Main.grid_size, draw_y + i * Define.Main.grid_size,
							Define.Main.grid_size, Define.Main.grid_size, null);
				}
			}

			if (emitterList != null) {
				for (int i = 0; i < emitterList.length; i++) {
					emitterList[i].render(graphics);
				}
			}
			if (receiverList != null) {
				for (int i = 0; i < receiverList.length; i++) {
					receiverList[i].render(graphics);
				}
			}
			if (mirrorList != null) {
				for (int i = 0; i < mirrorList.length; i++) {
					mirrorList[i].render(graphics);
				}
			}

		}

		if (isShowGrid()) {
			graphics.setColor(Color.gray);
			for (int i = 0; i < getWidth(); i += Define.Main.grid_size) {
				graphics.drawLine(i, 0, i, getHeight());
			}
			for (int i = 0; i < getHeight(); i += Define.Main.grid_size) {
				graphics.drawLine(0, i, getWidth(), i);
			}
		}

		GraphicsTools.restoreGraphics(graphics);
		// 自己的代码[end]

		g.drawImage(screen, 0, 0, null);// 最后个参数一定要用null，这样可以防止drawImage调用update方法
		g.dispose();
	}

	@Override
	public void paint(Graphics g) {
		update(g);// 我们在paint方法中，直接调用update方法
	}

	final static public BufferedImage createImage(int width, int height, boolean flag) {
		if (flag) {
			return new BufferedImage(width, height, 2);
		} else {
			return new BufferedImage(width, height, 1);
		}
	}
}
