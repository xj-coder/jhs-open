package com.jhs.open.ui.canvas.game;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import com.jhs.open.Define;
import com.jhs.open.bean.MapBean;
import com.jhs.open.control.GameControl;
import com.jhs.open.control.event.map.EmitterMouseClickedAdapter;
import com.jhs.open.control.event.map.MirrorMouseClickedAdapter;
import com.jhs.open.control.event.map.MouseMoveOnMapAdapter;
import com.jhs.open.control.event.map.ReceiverMouseClickedAdapter;
import com.jhs.open.tool.ImageTools;
import com.jhs.open.ui.Canvas;
import com.jhs.open.ui.shape.EmitterShape;
import com.jhs.open.ui.shape.MirrorShape;
import com.jhs.open.ui.shape.PathShape;
import com.jhs.open.ui.shape.ReceiverShape;

public class MapCanvas extends Canvas {

	private MapBean map;// 地图数据

	private ArrayList<PathShape> pathList;// 路线集合

	public EmitterShape[] emitterList;// 发射器集合
	public ReceiverShape[] receiverList;// 接收器集合
	public MirrorShape[] mirrorList;// 挡板集合

	public MapCanvas(MapBean map) {
		super();
		width = map.width * Define.Main.grid_size;
		height = map.height * Define.Main.grid_size;
		x = (Define.Main.width - width) / 2;
		y = (Define.Main.height - height) / 2 - 20;
		this.map = map;
	}

	@Override
	public void init() {
		GameControl.gi().getReadWriteLock().writeLock().lock();
		try {
			map.restore();

			removeAllCanvas();

			pathList = new ArrayList<PathShape>();

			if (map.emitterList != null) {
				emitterList = new EmitterShape[map.emitterList.size()];
				for (int i = 0; i < map.emitterList.size(); i++) {
					EmitterShape emitterShape = new EmitterShape(map.emitterList.get(i));
					// EmitterShape emitterShape = new EmitterShape(map.emitterList.get(i).x * Define.Main.grid_size,
					// map.emitterList.get(i).y * Define.Main.grid_size, map.emitterList.get(i));

					// emitterShape.bgImage = ImgSelector.emitterSelector(emitterShape, new Rect2D(0, 0, width,
					// height));
					emitterShape.addMouseListener(new MouseMoveOnMapAdapter(emitterShape));
					emitterShape.addMouseListener(new EmitterMouseClickedAdapter(emitterShape));

					emitterList[i] = emitterShape;
					addCanvas(emitterShape);
				}
			}
			if (map.receiverList != null) {
				receiverList = new ReceiverShape[map.receiverList.size()];
				for (int i = 0; i < map.receiverList.size(); i++) {
					ReceiverShape receiverShape = new ReceiverShape(map.receiverList.get(i));
					// ReceiverShape receiverShape = new ReceiverShape(map.receiverList.get(i).x *
					// Define.Main.grid_size,
					// map.receiverList.get(i).y * Define.Main.grid_size, map.receiverList.get(i));

					// receiverShape.bgImage = ImgSelector
					// .receiverSelector(receiverShape, new Rect2D(0, 0, width, height));
					receiverShape.addMouseListener(new MouseMoveOnMapAdapter(receiverShape));
					receiverShape.addMouseListener(new ReceiverMouseClickedAdapter(receiverShape));

					receiverList[i] = receiverShape;
					addCanvas(receiverShape);
				}
			}
			if (map.mirrorList != null) {
				mirrorList = new MirrorShape[map.mirrorList.size()];
				for (int i = 0; i < map.mirrorList.size(); i++) {
					MirrorShape mirrorShape = new MirrorShape(map.mirrorList.get(i));
					// MirrorShape mirrorShape = new MirrorShape(map.mirrorList.get(i).x * Define.Main.grid_size,
					// map.mirrorList.get(i).y * Define.Main.grid_size, map.mirrorList.get(i));

					mirrorShape.addMouseListener(new MouseMoveOnMapAdapter(mirrorShape));
					mirrorShape.addMouseListener(new MirrorMouseClickedAdapter(mirrorShape));

					mirrorList[i] = mirrorShape;
					addCanvas(mirrorShape);
				}
			}
		} finally {
			GameControl.gi().getReadWriteLock().writeLock().unlock();
		}
	}

	public void addPath(PathShape path) {
		pathList.add(path);
		addCanvas(path);
	}

	public void removePath(PathShape path) {
		pathList.remove(path);
		removeCanvas(path);
	}

	public PathShape getPath(int index) {
		return pathList.get(index);
	}

	public int getPathCount() {
		return pathList.size();
	}

	public MapBean getMap() {
		return map;
	}

	public void setMap(MapBean map) {
		this.map = map;
	}

	@Override
	public void render(Graphics g) {
		// 画地图表格[begin]
		Image g_image = ImageTools.cut(Define.IMG_PATH + "grid.png", 0, 0, Define.Main.grid_size,
				Define.Main.grid_size, "png");
		for (int i = 0; i < height / Define.Main.grid_size; i++) {
			for (int j = 0; j < width / Define.Main.grid_size; j++) {
				g.drawImage(g_image, x + j * Define.Main.grid_size, y + i * Define.Main.grid_size,
						Define.Main.grid_size, Define.Main.grid_size, null);
			}
		}
		// 画地图表格[end]

		super.render(g);
	}
}
