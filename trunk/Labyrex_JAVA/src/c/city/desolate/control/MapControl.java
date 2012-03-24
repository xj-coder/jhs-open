package c.city.desolate.control;

import c.city.desolate.Define;
import c.city.desolate.ui.canvas.game.MapCanvas;
import c.city.desolate.ui.shape.EmitterShape;
import c.city.desolate.ui.shape.MirrorShape;
import c.city.desolate.ui.shape.ReceiverShape;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.tree.DefaultElement;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

public class MapControl {
	public final static String easy = "easy";
	public final static String moderate = "moderate";
	public final static String difficute = "difficute";
	public final static String absurd = "absurd";

	private static HashMap<String, Vector<MapCanvas>> map = new HashMap<String, Vector<MapCanvas>>();

	private static MapCanvas[] mapCanvas;

	@SuppressWarnings("unchecked")
	public static MapCanvas[] getMaps() {
		if (mapCanvas == null) {
			InputStream in = null;
			try {
				in = new FileInputStream(new File(Define.MAP_PATH));
				SAXReader saxReader = new SAXReader();
				Document document = saxReader.read(in);
				// 将整个games-config.xml文件解析出来
				Element _root = document.getRootElement();

				// 提取XML中的map记录
				String xpath = "//map";
				List<Element> mapNodes = _root.selectNodes(xpath);

				mapCanvas = new MapCanvas[mapNodes.size()];
				for (int i = 0; i < mapNodes.size(); i++) {
					DefaultElement mapNode = (DefaultElement) mapNodes.get(i);

					int width = Integer.parseInt(mapNode.attribute("width").getValue());
					int height = Integer.parseInt(mapNode.attribute("height").getValue());

					MapCanvas mapCanva = new MapCanvas(0, 0, width * Define.Main.grid_size, height
							* Define.Main.grid_size);

					mapCanva.name = mapNode.attribute("name").getValue();
					mapCanva.isEnabled = mapNode.attribute("enabled").getValue().equals("true");

					List emitterNodes = mapNode.elements("emitter");
					EmitterShape[] emitterBeans = new EmitterShape[emitterNodes.size()];
					List receiverNodes = mapNode.elements("receiver");
					ReceiverShape[] receiverBeans = new ReceiverShape[receiverNodes.size()];
					List mirrorNodes = mapNode.elements("mirror");
					MirrorShape[] mirrorBeans = new MirrorShape[mirrorNodes.size()];

					for (int j = 0; j < emitterNodes.size(); j++) {
						DefaultElement emitter = (DefaultElement) emitterNodes.get(j);

						int x = Integer.parseInt(emitter.attribute("x").getValue()) * Define.Main.grid_size;
						int y = Integer.parseInt(emitter.attribute("y").getValue()) * Define.Main.grid_size;

						EmitterShape emitterBean = new EmitterShape(x, y, Define.Main.grid_size, Define.Main.grid_size);
						emitterBean.type = emitter.attribute("type").getValue().trim();

						emitterBeans[j] = emitterBean;
					}
					mapCanva.emitterList = emitterBeans;

					for (int j = 0; j < receiverNodes.size(); j++) {
						DefaultElement receiver = (DefaultElement) receiverNodes.get(j);

						int x = Integer.parseInt(receiver.attribute("x").getValue()) * Define.Main.grid_size;
						int y = Integer.parseInt(receiver.attribute("y").getValue()) * Define.Main.grid_size;

						ReceiverShape receiverBean = new ReceiverShape(x, y, Define.Main.grid_size,
								Define.Main.grid_size);
						receiverBean.type = receiver.attribute("type").getValue().trim();

						receiverBeans[j] = receiverBean;
					}
					mapCanva.receiverList = receiverBeans;

					for (int j = 0; j < mirrorNodes.size(); j++) {
						DefaultElement mirror = (DefaultElement) mirrorNodes.get(j);

						int x = Integer.parseInt(mirror.attribute("x").getValue()) * Define.Main.grid_size;
						int y = Integer.parseInt(mirror.attribute("y").getValue()) * Define.Main.grid_size;

						MirrorShape mirrorBean = new MirrorShape(x, y, Define.Main.grid_size, Define.Main.grid_size);
						mirrorBean.type = mirror.attribute("type").getValue().trim();

						mirrorBeans[j] = mirrorBean;
					}
					mapCanva.mirrorList = mirrorBeans;

					mapCanvas[i] = mapCanva;
				}

				return mapCanvas;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		}
		return mapCanvas;
	}

	public static MapCanvas getMapByName(String name) {
		for (int i = 0; i < getMaps().length; i++) {
			if (getMaps()[i].name.equals(name)) {
				return getMaps()[i];
			}
		}
		return null;
	}

	public static void loadMap() {
		map.clear();

		for (int i = 0; i < getMaps().length; i++) {
			MapCanvas canvas = getMaps()[i];
			String key = canvas.name.substring(0, canvas.name.lastIndexOf("."));
			Vector<MapCanvas> vector = map.get(key);
			if (vector == null) {
				vector = new Vector<MapCanvas>();
				map.put(key, vector);
			}
			vector.add(canvas);
		}
		Set<String> keySet = map.keySet();
		Iterator<String> it = keySet.iterator();

		while (it.hasNext()) {
			Vector<MapCanvas> vector = map.get(it.next());
			Collections.sort(vector);
		}
	}

	public static Vector<MapCanvas> getEasyMap() {
		return map.get(easy);
	}

	public static Vector<MapCanvas> getModerateMap() {
		return map.get(moderate);
	}

	public static Vector<MapCanvas> getDifficuteMap() {
		return map.get(difficute);
	}

	public static Vector<MapCanvas> getAbsurdMap() {
		return map.get(absurd);
	}

	public static void enableNext(MapCanvas mapCanvas) {
		Vector<MapCanvas> vector = map.get(mapCanvas.name.substring(0, mapCanvas.name.lastIndexOf(".")));
		for (int i = 0; i < vector.size(); i++) {
			if (vector.get(i).name.equals(mapCanvas.name)) {
				if (i + 1 < vector.size() && !vector.get(i + 1).isEnabled) {
					vector.get(i + 1).isEnabled = true;
					saveMap();
					break;
				}
			}
		}
	}

	public static void resetMap() {
		mapCanvas = null;
		getMaps();
		loadMap();
	}

	public static void saveMap() {

	}
}
