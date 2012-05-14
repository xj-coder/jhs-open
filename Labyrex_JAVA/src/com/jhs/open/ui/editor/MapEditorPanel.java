package com.jhs.open.ui.editor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import com.jhs.open.Define;
import com.jhs.open.bean.EmitterBean;
import com.jhs.open.bean.MapBean;
import com.jhs.open.bean.MirrorBean;
import com.jhs.open.bean.ReceiverBean;
import com.jhs.open.bean.Rect2D;
import com.jhs.open.control.event.editor.MapPanelMouseAdapter;
import com.jhs.open.tool.CanvasSearcher;
import com.jhs.open.tool.GraphicsTools;
import com.jhs.open.tool.ImageTools;
import com.jhs.open.tool.ImgSelector;
import com.jhs.open.ui.Canvas;
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
	private boolean isMouseDrag = false;

	private int mouseX;
	private int mouseY;

	private int draw_width;
	private int draw_height;
	private int draw_x;
	private int draw_y;

	private boolean isShowGrid = true;

	private Point currPoint = new Point();

	public MapEditorPanel() {
		setFocusable(true);

		initData();
		initEvent();
	}

	private void initEvent() {
		addMouseListener(new MapPanelMouseAdapter(this));
		addMouseMotionListener(new MapPanelMouseAdapter(this));
		addMouseWheelListener(new MapPanelMouseAdapter(this));

		// LabyrexMapEditorFrame.gi().
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.isControlDown()) {
					if (e.getKeyCode() == KeyEvent.VK_UP) {

					} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {

					}
				} else {
					if (e.getKeyCode() == KeyEvent.VK_DELETE) {
						Canvas selectCanvas = LabyrexMapEditorFrame.gi()
								.getCurrSelectedCanvas();

						if (selectCanvas instanceof MirrorShape) {
							LabyrexMapEditorFrame.gi().getCurrMapBean().mirrorList
									.remove(((MirrorShape) selectCanvas).bean);
						} else if (selectCanvas instanceof EmitterShape) {
							LabyrexMapEditorFrame.gi().getCurrMapBean().emitterList
									.remove(((EmitterShape) selectCanvas).bean);
						} else if (selectCanvas instanceof ReceiverShape) {
							LabyrexMapEditorFrame.gi().getCurrMapBean().receiverList
									.remove(((ReceiverShape) selectCanvas).bean);
						}

						LabyrexMapEditorFrame.gi().setCurrSelectedCanvas(null);
						LabyrexMapEditorFrame.gi().updateAttrField();
						initData();
						repaint();
						LabyrexMapEditorFrame.gi().getCurrMapBean().setSave(
								false);
					}
				}
			}
		});
	}

	public void initData() {
		MapBean map = LabyrexMapEditorFrame.gi().getCurrMapBean();

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

			Canvas owner = new Canvas(draw_x, draw_y, draw_width, draw_height);

			if (map.emitterList != null) {
				emitterList = new EmitterShape[map.emitterList.size()];
				for (int i = 0; i < map.emitterList.size(); i++) {
					EmitterShape emitterShape = new EmitterShape(
							map.emitterList.get(i));
					// EmitterShape emitterShape = new
					// EmitterShape(map.emitterList.get(i).x *
					// Define.Main.grid_size,
					// map.emitterList.get(i).y * Define.Main.grid_size,
					// map.emitterList.get(i));

					emitterShape.bgImage = ImgSelector.emitterSelector(
							emitterShape, new Rect2D(0, 0, draw_width,
									draw_height));
					emitterShape.owner = owner;
					emitterList[i] = emitterShape;
				}
			}
			if (map.receiverList != null) {
				receiverList = new ReceiverShape[map.receiverList.size()];
				for (int i = 0; i < map.receiverList.size(); i++) {
					ReceiverShape receiverShape = new ReceiverShape(
							map.receiverList.get(i));
					// ReceiverShape receiverShape = new
					// ReceiverShape(map.receiverList.get(i).x *
					// Define.Main.grid_size,
					// map.receiverList.get(i).y * Define.Main.grid_size,
					// map.receiverList.get(i));

					receiverShape.bgImage = ImgSelector.receiverSelector(
							receiverShape, new Rect2D(0, 0, draw_width,
									draw_height));

					receiverShape.owner = owner;
					receiverList[i] = receiverShape;
				}
			}
			if (map.mirrorList != null) {
				mirrorList = new MirrorShape[map.mirrorList.size()];
				for (int i = 0; i < map.mirrorList.size(); i++) {
					MirrorShape mirrorShape = new MirrorShape(map.mirrorList
							.get(i));
					// MirrorShape mirrorShape = new
					// MirrorShape(map.mirrorList.get(i).x *
					// Define.Main.grid_size,
					// map.mirrorList.get(i).y * Define.Main.grid_size,
					// map.mirrorList.get(i));
					mirrorShape.owner = owner;
					mirrorList[i] = mirrorShape;
				}
			}
		}
	}

	public boolean isMouseIn() {
		return isMouseIn;
	}

	public void setMouseIn(boolean isMouseIn) {
		this.isMouseIn = isMouseIn;
		if (LabyrexMapEditorFrame.gi().getCurrClickButton() != null) {
			repaint(getMouseX() - Define.Main.grid_size / 2, getMouseY()
					- Define.Main.grid_size / 2, Define.Main.grid_size,
					Define.Main.grid_size);
		}
	}

	public boolean isMouseDrag() {
		return isMouseDrag;
	}

	public void setMouseDrag(boolean isMousePressed) {
		this.isMouseDrag = isMousePressed;
	}

	public int getMouseX() {
		return mouseX;
	}

	public void setMouseX(int mouseX) {

		// if (LabyrexMapEditorFrame.gi().getCurrClickButton() != null) {
		// repaint(clacRectByCenterPoint(getMouseX(), getMouseY()));
		// }
		// if (LabyrexMapEditorFrame.gi().getCurrSelectedCanvas() != null
		// && isMouseDrag) {
		// repaint(LabyrexMapEditorFrame.gi().getCurrSelectedCanvas().offsetX,
		// LabyrexMapEditorFrame.gi().getCurrSelectedCanvas().offsetX,
		// LabyrexMapEditorFrame.gi().getCurrSelectedCanvas().width,
		// LabyrexMapEditorFrame.gi().getCurrSelectedCanvas().height);
		// }

		// if (LabyrexMapEditorFrame.gi().getCurrClickButton() != null
		// || (LabyrexMapEditorFrame.gi().getCurrSelectedCanvas() != null &&
		// isMouseDrag)) {
		// currPoint = clacPoint(MapEditorPanel.this.mouseX,
		// MapEditorPanel.this.mouseY);
		// repaint(clacRect(currPoint));
		// }

		this.mouseX = mouseX;
		currPoint = clacPoint(MapEditorPanel.this.mouseX,
				MapEditorPanel.this.mouseY);
		repaint();

		// if (LabyrexMapEditorFrame.gi().getCurrClickButton() != null
		// || (LabyrexMapEditorFrame.gi().getCurrSelectedCanvas() != null &&
		// isMouseDrag)) {
		// currPoint = clacPoint(MapEditorPanel.this.mouseX,
		// MapEditorPanel.this.mouseY);
		// repaint(clacRect(currPoint));
		// }

		// if (LabyrexMapEditorFrame.gi().getCurrClickButton() != null) {
		// repaint(clacRectByCenterPoint(getMouseX(), getMouseY()));
		// }
		//		
		// if (LabyrexMapEditorFrame.gi().getCurrSelectedCanvas() != null
		// && isMouseDrag) {
		// repaint(clacRectByCenterPoint(getMouseX(), getMouseY()));
		// }

	}

	public int getMouseY() {
		return mouseY;
	}

	public void setMouseY(int mouseY) {
		if (LabyrexMapEditorFrame.gi().getCurrClickButton() != null) {
			repaint(getMouseX() - Define.Main.grid_size / 2, getMouseY()
					- Define.Main.grid_size / 2, Define.Main.grid_size,
					Define.Main.grid_size);
		}
		if (LabyrexMapEditorFrame.gi().getCurrSelectedCanvas() != null
				&& isMouseDrag) {
			repaint(LabyrexMapEditorFrame.gi().getCurrSelectedCanvas().offsetX,
					LabyrexMapEditorFrame.gi().getCurrSelectedCanvas().offsetX,
					LabyrexMapEditorFrame.gi().getCurrSelectedCanvas().width,
					LabyrexMapEditorFrame.gi().getCurrSelectedCanvas().height);
		}
		this.mouseY = mouseY;
		if (LabyrexMapEditorFrame.gi().getCurrClickButton() != null) {
			repaint(getMouseX() - Define.Main.grid_size / 2, getMouseY()
					- Define.Main.grid_size / 2, Define.Main.grid_size,
					Define.Main.grid_size);
		}
		if (LabyrexMapEditorFrame.gi().getCurrSelectedCanvas() != null
				&& isMouseDrag) {
			repaint(getMouseX() - Define.Main.grid_size / 2, getMouseY()
					- Define.Main.grid_size / 2, Define.Main.grid_size,
					Define.Main.grid_size);
		}
	}

	private Point clacPoint(int mouseX, int mouseY) {
		Point point = new Point();

		point.x = (mouseX - draw_x) / Define.Main.grid_size;
		point.y = (mouseY - draw_y) / Define.Main.grid_size;

		return point;
	}

	private Rectangle clacRect(Point point) {
		Rectangle rect = new Rectangle();

		rect.x = draw_x + point.x * Define.Main.grid_size;
		rect.y = draw_y + point.y * Define.Main.grid_size;
		rect.width = Define.Main.grid_size;
		rect.height = Define.Main.grid_size;

		return rect;
	}

	public void mouseLeftClicked() {
		boolean isRepaint = false;

		if (LabyrexMapEditorFrame.gi().getCurrClickButton() != null) {

			if (getMouseX() > draw_x - Define.Main.grid_size
					&& getMouseX() < draw_x + draw_width
							+ Define.Main.grid_size
					&& getMouseY() > draw_y - Define.Main.grid_size
					&& getMouseY() < draw_y + draw_height
							+ Define.Main.grid_size) {

				EmitterShape emitterShape = CanvasSearcher.findEmitter(
						emitterList, getMouseX(), getMouseY());
				ReceiverShape receiverShape = CanvasSearcher.findReceiver(
						receiverList, getMouseX(), getMouseY());
				MirrorShape mirrorShape = CanvasSearcher.findMirror(mirrorList,
						getMouseX(), getMouseY());
				if (emitterShape != null || receiverShape != null
						|| mirrorShape != null) {
					return;
				}

				int _x = -100;
				int _y = -100;

				if (getMouseX() > draw_x && getMouseX() < draw_x + draw_width) {
					_x = (getMouseX() - draw_x) / Define.Main.grid_size;
				} else if (getMouseX() < draw_x
						&& getMouseX() > draw_x - Define.Main.grid_size) {
					_x = -1;
				} else if (getMouseX() < draw_x + draw_width
						+ Define.Main.grid_size
						&& getMouseX() > draw_x + draw_width) {
					_x = LabyrexMapEditorFrame.gi().getCurrMapBean().width;
				}
				if (getMouseY() > draw_y && getMouseY() < draw_y + draw_height) {
					_y = (getMouseY() - draw_y) / Define.Main.grid_size;
				} else if (getMouseY() < draw_y
						&& getMouseY() > draw_y - Define.Main.grid_size) {
					_y = -1;
				} else if (getMouseY() < draw_y + draw_height
						+ Define.Main.grid_size
						&& getMouseY() > draw_y + draw_height) {
					_y = LabyrexMapEditorFrame.gi().getCurrMapBean().height;
				}

				if ((_x != -100 && _y != -100)
						&& (_x == -1
								|| _x == LabyrexMapEditorFrame.gi()
										.getCurrMapBean().width || _y == -1 || _y == LabyrexMapEditorFrame
								.gi().getCurrMapBean().height)
						&& !(_x == -1 && _y == -1)
						&& !(_x == -1 && _y == LabyrexMapEditorFrame.gi()
								.getCurrMapBean().height)
						&& !(_x == LabyrexMapEditorFrame.gi().getCurrMapBean().width && _y == LabyrexMapEditorFrame
								.gi().getCurrMapBean().height)
						&& !(_x == LabyrexMapEditorFrame.gi().getCurrMapBean().width && _y == -1)) {

					if (LabyrexMapEditorFrame.gi().getCurrClickButton() == LabyrexMapEditorFrame
							.gi().getEmitterButton()) {
						EmitterBean bean = new EmitterBean();
						bean.type = LabyrexMapEditorFrame.gi()
								.getEmitterButton().getName();
						bean.x = _x;
						bean.y = _y;
						bean.backup();
						LabyrexMapEditorFrame.gi().getCurrMapBean().emitterList
								.add(bean);
						isRepaint = true;
					} else if (LabyrexMapEditorFrame.gi().getCurrClickButton() == LabyrexMapEditorFrame
							.gi().getReceiverButton()) {
						ReceiverBean bean = new ReceiverBean();
						bean.type = LabyrexMapEditorFrame.gi()
								.getReceiverButton().getName();
						bean.x = _x;
						bean.y = _y;
						bean.backup();
						LabyrexMapEditorFrame.gi().getCurrMapBean().receiverList
								.add(bean);
						isRepaint = true;
					} else {

					}
				} else if (_x != -100 && _y != -100) {
					if (LabyrexMapEditorFrame.gi().getCurrClickButton() == LabyrexMapEditorFrame
							.gi().getMirrorButton()) {
						MirrorBean bean = new MirrorBean();
						bean.type = LabyrexMapEditorFrame.gi()
								.getMirrorButton().getName();
						bean.x = _x;
						bean.y = _y;
						bean.backup();
						LabyrexMapEditorFrame.gi().getCurrMapBean().mirrorList
								.add(bean);
						isRepaint = true;
					} else {

					}
				}
				if (isRepaint) {
					LabyrexMapEditorFrame.gi().updateAttrField();
					initData();
					repaint();
					LabyrexMapEditorFrame.gi().getCurrMapBean().setSave(false);
				}
			}
		} else {
			if (getMouseX() > draw_x - Define.Main.grid_size
					&& getMouseX() < draw_x + draw_width
							+ Define.Main.grid_size
					&& getMouseY() > draw_y - Define.Main.grid_size
					&& getMouseY() < draw_y + draw_height
							+ Define.Main.grid_size) {

				EmitterShape emitterShape = CanvasSearcher.findEmitter(
						emitterList, getMouseX(), getMouseY());
				ReceiverShape receiverShape = CanvasSearcher.findReceiver(
						receiverList, getMouseX(), getMouseY());
				MirrorShape mirrorShape = CanvasSearcher.findMirror(mirrorList,
						getMouseX(), getMouseY());

				if (emitterShape != null) {
					if (emitterShape.isSelected()) {
						emitterShape.setSelected(false);
						LabyrexMapEditorFrame.gi().setCurrSelectedCanvas(null);
					} else {
						LabyrexMapEditorFrame.gi().setCurrSelectedCanvas(
								emitterShape);
						emitterShape.setSelected(true);
					}
					isRepaint = true;
				} else if (receiverShape != null) {
					if (receiverShape.isSelected()) {
						receiverShape.setSelected(false);
						LabyrexMapEditorFrame.gi().setCurrSelectedCanvas(null);
					} else {
						LabyrexMapEditorFrame.gi().setCurrSelectedCanvas(
								receiverShape);
						receiverShape.setSelected(true);
					}
					isRepaint = true;
				} else if (mirrorShape != null) {
					if (mirrorShape.isSelected()) {
						mirrorShape.setSelected(false);
						LabyrexMapEditorFrame.gi().setCurrSelectedCanvas(null);
					} else {
						LabyrexMapEditorFrame.gi().setCurrSelectedCanvas(
								mirrorShape);
						mirrorShape.setSelected(true);
					}
					isRepaint = true;
				}
			}
		}

		if (isRepaint) {
			int _x = (getMouseX() - draw_x)
					- ((getMouseX() - draw_x) % Define.Main.grid_size > 0 ? (getMouseX() - draw_x)
							% Define.Main.grid_size
							: Define.Main.grid_size + (getMouseX() - draw_x)
									% Define.Main.grid_size);
			int _y = (getMouseY() - draw_y)
					- ((getMouseY() - draw_y) % Define.Main.grid_size > 0 ? (getMouseY() - draw_y)
							% Define.Main.grid_size
							: Define.Main.grid_size + (getMouseY() - draw_y)
									% Define.Main.grid_size);

			repaint(draw_x + _x, draw_y + _y, Define.Main.grid_size,
					Define.Main.grid_size);
		}
	}

	public void mouseRightClicked() {
		if (LabyrexMapEditorFrame.gi().getCurrClickButton() != null) {
			repaint(getMouseX() - Define.Main.grid_size / 2, getMouseY()
					- Define.Main.grid_size / 2, Define.Main.grid_size,
					Define.Main.grid_size);

			LabyrexMapEditorFrame.gi().getCurrClickButton().setButtonSelected(
					false);
			LabyrexMapEditorFrame.gi().setCurrClickButton(null);
		}
	}

	public boolean isShowGrid() {
		return isShowGrid;
	}

	public void setShowGrid(boolean isShowGrid) {
		this.isShowGrid = isShowGrid;
	}

	public int getDraw_width() {
		return draw_width;
	}

	public void setDraw_width(int draw_width) {
		this.draw_width = draw_width;
	}

	public int getDraw_height() {
		return draw_height;
	}

	public void setDraw_height(int draw_height) {
		this.draw_height = draw_height;
	}

	public int getDraw_x() {
		return draw_x;
	}

	public void setDraw_x(int draw_x) {
		this.draw_x = draw_x;
	}

	public int getDraw_y() {
		return draw_y;
	}

	public void setDraw_y(int draw_y) {
		this.draw_y = draw_y;
	}

	@Override
	public void update(Graphics g) {
		MapBean map = LabyrexMapEditorFrame.gi().getCurrMapBean();

		Image screen = ImageTools.createImage(getWidth(), getHeight(), true);// 双缓冲

		// 自己的代码[begin]

		Graphics2D graphics = (Graphics2D) screen.getGraphics();
		GraphicsTools.backupGraphics(graphics);
		// 画背景
		graphics.setColor(Color.gray);
		graphics.fillRect(0, 0, getWidth(), getHeight());

		if (map != null) {

			// 绘制可设置区域
			graphics.setColor(Color.black);
			graphics.fillRect(draw_x - Define.Main.grid_size, draw_y
					- Define.Main.grid_size, Define.Main.grid_size * 2
					+ draw_width, Define.Main.grid_size * 2 + draw_height);

			// 绘制地图
			Image g_image = ImageTools.cut(Define.IMG_PATH + "grid.png", 0, 0,
					Define.Main.grid_size, Define.Main.grid_size, "png");
			for (int i = 0; i < draw_width / Define.Main.grid_size; i++) {
				for (int j = 0; j < draw_height / Define.Main.grid_size; j++) {
					graphics.drawImage(g_image, draw_x + j
							* Define.Main.grid_size, draw_y + i
							* Define.Main.grid_size, Define.Main.grid_size,
							Define.Main.grid_size, null);
				}
			}

			// 如果鼠标处于按下状态则表示在拖动
			if (isMouseDrag) {
				if (LabyrexMapEditorFrame.gi().getCurrSelectedCanvas() != null) {
					Canvas canvas = LabyrexMapEditorFrame.gi()
							.getCurrSelectedCanvas();

					if (canvas instanceof MirrorShape) {
						((MirrorShape) canvas).bean.x = currPoint.x;
						((MirrorShape) canvas).bean.y = currPoint.y;
						((MirrorShape) canvas).reset();
					} else if (canvas instanceof EmitterShape) {
						((EmitterShape) canvas).bean.x = currPoint.x;
						((EmitterShape) canvas).bean.y = currPoint.y;
						((EmitterShape) canvas).reset();
					} else if (canvas instanceof ReceiverShape) {
						((ReceiverShape) canvas).bean.x = currPoint.x;
						((ReceiverShape) canvas).bean.y = currPoint.y;
						((ReceiverShape) canvas).reset();
					}
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

			if (isMouseIn) {
				// 如果鼠标进入绘制区，并且点击了原始按钮，则在鼠标位置绘制元素图
				if (LabyrexMapEditorFrame.gi().getCurrClickButton() != null) {

					Image bgImage = null;
					if (LabyrexMapEditorFrame.gi().getCurrClickButton() == LabyrexMapEditorFrame
							.gi().getEmitterButton()) {
						bgImage = ImgSelector.emitterSelector(getMouseX(),
								getMouseY(), Define.Main.grid_size,
								Define.Main.grid_size, LabyrexMapEditorFrame
										.gi().getCurrClickButton().getName(),
								new Rect2D(draw_x, draw_y, draw_width,
										draw_height));
					} else if (LabyrexMapEditorFrame.gi().getCurrClickButton() == LabyrexMapEditorFrame
							.gi().getReceiverButton()) {
						bgImage = ImgSelector.receiverSelector(getMouseX(),
								getMouseY(), Define.Main.grid_size,
								Define.Main.grid_size, LabyrexMapEditorFrame
										.gi().getCurrClickButton().getName(),
								new Rect2D(draw_x, draw_y, draw_width,
										draw_height));
					} else {
						bgImage = ((ImageIcon) LabyrexMapEditorFrame.gi()
								.getCurrClickButton().getIcon()).getImage();
					}

					EmitterShape emitterShape = CanvasSearcher.findEmitter(
							emitterList, getMouseX(), getMouseY());
					ReceiverShape receiverShape = CanvasSearcher.findReceiver(
							receiverList, getMouseX(), getMouseY());
					MirrorShape mirrorShape = CanvasSearcher.findMirror(
							mirrorList, getMouseX(), getMouseY());
					if (emitterShape != null || receiverShape != null
							|| mirrorShape != null) {

					}

					int _x = -100;
					int _y = -100;

					if (getMouseX() > draw_x
							&& getMouseX() < draw_x + draw_width) {
						_x = (getMouseX() - draw_x) / Define.Main.grid_size;
					} else if (getMouseX() < draw_x
							&& getMouseX() > draw_x - Define.Main.grid_size) {
						_x = -1;
					} else if (getMouseX() < draw_x + draw_width
							+ Define.Main.grid_size
							&& getMouseX() > draw_x + draw_width) {
						_x = LabyrexMapEditorFrame.gi().getCurrMapBean().width;
					}
					if (getMouseY() > draw_y
							&& getMouseY() < draw_y + draw_height) {
						_y = (getMouseY() - draw_y) / Define.Main.grid_size;
					} else if (getMouseY() < draw_y
							&& getMouseY() > draw_y - Define.Main.grid_size) {
						_y = -1;
					} else if (getMouseY() < draw_y + draw_height
							+ Define.Main.grid_size
							&& getMouseY() > draw_y + draw_height) {
						_y = LabyrexMapEditorFrame.gi().getCurrMapBean().height;
					}

					BufferedImage img = ImageTools
							.getImage(Define.Editor.x_icon_path);
					if ((_x != -100 && _y != -100)
							&& (_x == -1
									|| _x == LabyrexMapEditorFrame.gi()
											.getCurrMapBean().width || _y == -1 || _y == LabyrexMapEditorFrame
									.gi().getCurrMapBean().height)
							&& !(_x == -1 && _y == -1)
							&& !(_x == -1 && _y == LabyrexMapEditorFrame.gi()
									.getCurrMapBean().height)
							&& !(_x == LabyrexMapEditorFrame.gi()
									.getCurrMapBean().width && _y == LabyrexMapEditorFrame
									.gi().getCurrMapBean().height)
							&& !(_x == LabyrexMapEditorFrame.gi()
									.getCurrMapBean().width && _y == -1)) {

						if (LabyrexMapEditorFrame.gi().getCurrClickButton() == LabyrexMapEditorFrame
								.gi().getMirrorButton()) {
							graphics.drawImage(img, getMouseX()
									- img.getWidth() / 2, getMouseY()
									- img.getHeight() / 2, null);
						} else {
							graphics.drawImage(bgImage, getMouseX()
									- Define.Main.grid_size / 2, getMouseY()
									- Define.Main.grid_size / 2,
									Define.Main.grid_size,
									Define.Main.grid_size, null);
						}
					} else if (_x != -100 && _y != -100 && getMouseX() > draw_x
							&& getMouseX() < draw_x + draw_width
							&& getMouseY() > draw_y
							&& getMouseY() < draw_y + draw_height) {
						if (LabyrexMapEditorFrame.gi().getCurrClickButton() == LabyrexMapEditorFrame
								.gi().getEmitterButton()) {
							graphics.drawImage(img, getMouseX()
									- img.getWidth() / 2, getMouseY()
									- img.getHeight() / 2, null);
						} else if (LabyrexMapEditorFrame.gi()
								.getCurrClickButton() == LabyrexMapEditorFrame
								.gi().getReceiverButton()) {
							graphics.drawImage(img, getMouseX()
									- img.getWidth() / 2, getMouseY()
									- img.getHeight() / 2, null);
						} else {
							graphics.drawImage(bgImage, getMouseX()
									- Define.Main.grid_size / 2, getMouseY()
									- Define.Main.grid_size / 2,
									Define.Main.grid_size,
									Define.Main.grid_size, null);
						}
					} else {
						graphics.drawImage(img, getMouseX() - img.getWidth()
								/ 2, getMouseY() - img.getHeight() / 2, null);
					}
				}
			}

			// 绘制网格
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
		}

		g.drawImage(screen, 0, 0, null);// 最后个参数一定要用null，这样可以防止drawImage调用update方法
		g.dispose();
	}

	@Override
	public void paint(Graphics g) {
		update(g);// 我们在paint方法中，直接调用update方法
	}
}
