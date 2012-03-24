package c.city.desolate.ui.canvas.game;

import c.city.desolate.Define;
import c.city.desolate.tool.ImageTools;
import c.city.desolate.ui.Canvas;
import c.city.desolate.ui.shape.EmitterShape;
import c.city.desolate.ui.shape.MirrorShape;
import c.city.desolate.ui.shape.PathShape;
import c.city.desolate.ui.shape.ReceiverShape;

import java.awt.*;
import java.util.Vector;

public class MapCanvas extends Canvas implements Comparable<MapCanvas> {
	public String name;// 地图名
	public boolean isEnabled = false;

	public EmitterShape[] emitterList;// 发射器集合
	public ReceiverShape[] receiverList;// 接收器集合
	public MirrorShape[] mirrorList;// 挡板集合

	private Vector<PathShape> pathVector;// 路线集合

	public MapCanvas(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	public void init() {
		pathVector = new Vector<PathShape>();

		x = (Define.Main.width - width) / 2;
		y = (Define.Main.height - height) / 2 - 20;

		if (emitterList != null) {
			for (int i = 0; i < emitterList.length; i++) {
				addCanvas(emitterList[i]);
			}
		}
		if (receiverList != null) {
			for (int i = 0; i < receiverList.length; i++) {
				addCanvas(receiverList[i]);
			}
		}
		if (mirrorList != null) {
			for (int i = 0; i < mirrorList.length; i++) {
				addCanvas(mirrorList[i]);
			}
		}
	}

	public void addPath(PathShape path) {
		pathVector.add(path);
		addCanvas(path);
	}

	public void removePath(PathShape path) {
		pathVector.remove(path);
		removeCanvas(path);
	}

	public PathShape getPath(int index) {
		return pathVector.get(index);
	}

	public int getPathCount() {
		return pathVector.size();
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

	@Override
	public int compareTo(MapCanvas map) {
		return Integer.parseInt(this.name.substring(this.name.lastIndexOf(".") + 1, this.name.length()))
				- Integer.parseInt(map.name.substring(map.name.lastIndexOf(".") + 1, map.name.length()));
	}
}
