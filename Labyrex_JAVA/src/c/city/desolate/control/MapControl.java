package c.city.desolate.control;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.dom.DOMElement;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.dom4j.tree.DefaultElement;

import c.city.desolate.Define;
import c.city.desolate.bean.EmitterBean;
import c.city.desolate.bean.MapBean;
import c.city.desolate.bean.MirrorBean;
import c.city.desolate.bean.ReceiverBean;

/**
 * 地图管理器
 * 
 * @author JHS
 * 
 */
public class MapControl {
	public final static String easy = "easy";
	public final static String moderate = "moderate";
	public final static String difficute = "difficute";
	public final static String absurd = "absurd";

	private static Map<String, ArrayList<MapBean>> maps;

	@SuppressWarnings("unchecked")
	public static Map<String, ArrayList<MapBean>> readMaps() {
		maps = new HashMap<String, ArrayList<MapBean>>();
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

			for (int i = 0; i < mapNodes.size(); i++) {
				DefaultElement mapNode = (DefaultElement) mapNodes.get(i);

				int width = Integer.parseInt(mapNode.attribute("width").getValue());
				int height = Integer.parseInt(mapNode.attribute("height").getValue());

				MapBean map = new MapBean();

				map.width = width;
				map.height = height;
				map.name = mapNode.attribute("name").getValue();
				map.isEnabled = mapNode.attribute("enabled").getValue().equals("true");

				List emitterNodes = mapNode.elements("emitter");
				EmitterBean[] emitterBeans = new EmitterBean[emitterNodes.size()];
				List receiverNodes = mapNode.elements("receiver");
				ReceiverBean[] receiverBeans = new ReceiverBean[receiverNodes.size()];
				List mirrorNodes = mapNode.elements("mirror");
				MirrorBean[] mirrorBeans = new MirrorBean[mirrorNodes.size()];

				for (int j = 0; j < emitterNodes.size(); j++) {
					DefaultElement emitter = (DefaultElement) emitterNodes.get(j);

					EmitterBean emitterBean = new EmitterBean();
					emitterBean.type = emitter.attribute("type").getValue().trim();
					emitterBean.x = Integer.parseInt(emitter.attribute("x").getValue());
					emitterBean.y = Integer.parseInt(emitter.attribute("y").getValue());

					emitterBeans[j] = emitterBean;
				}
				map.emitterList = emitterBeans;

				for (int j = 0; j < receiverNodes.size(); j++) {
					DefaultElement receiver = (DefaultElement) receiverNodes.get(j);

					ReceiverBean receiverBean = new ReceiverBean();
					receiverBean.type = receiver.attribute("type").getValue().trim();
					receiverBean.x = Integer.parseInt(receiver.attribute("x").getValue());
					receiverBean.y = Integer.parseInt(receiver.attribute("y").getValue());

					receiverBeans[j] = receiverBean;
				}
				map.receiverList = receiverBeans;

				for (int j = 0; j < mirrorNodes.size(); j++) {
					DefaultElement mirror = (DefaultElement) mirrorNodes.get(j);

					MirrorBean mirrorBean = new MirrorBean();
					mirrorBean.type = mirror.attribute("type").getValue().trim();
					mirrorBean.iniType = mirrorBean.type;
					mirrorBean.x = Integer.parseInt(mirror.attribute("x").getValue());
					mirrorBean.y = Integer.parseInt(mirror.attribute("y").getValue());

					mirrorBeans[j] = mirrorBean;
				}
				map.mirrorList = mirrorBeans;

				String category = map.name.substring(0, map.name.lastIndexOf("."));
				ArrayList<MapBean> list = maps.get(category);
				if (list == null) {
					list = new ArrayList<MapBean>();
					maps.put(category, list);
				}
				list.add(map);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return maps;
	}

	private static void addMap(Element mapsNode, MapBean map) {
		Element mapNode = new DOMElement("map");
		mapNode.addAttribute("name", map.name);
		mapNode.addAttribute("width", map.width + "");
		mapNode.addAttribute("height", map.height + "");
		mapNode.addAttribute("enabled", map.isEnabled + "");

		for (int i = 0; i < map.emitterList.length; i++) {
			EmitterBean bean = map.emitterList[i];
			Element node = new DOMElement("emitter");
			node.addAttribute("type", bean.type);
			node.addAttribute("x", bean.x + "");
			node.addAttribute("y", bean.y + "");
			mapNode.add(node);
		}
		for (int i = 0; i < map.mirrorList.length; i++) {
			MirrorBean bean = map.mirrorList[i];
			Element node = new DOMElement("mirror");
			node.addAttribute("type", bean.type);
			node.addAttribute("x", bean.x + "");
			node.addAttribute("y", bean.y + "");
			mapNode.add(node);
		}
		for (int i = 0; i < map.receiverList.length; i++) {
			ReceiverBean bean = map.receiverList[i];
			Element node = new DOMElement("receiver");
			node.addAttribute("type", bean.type);
			node.addAttribute("x", bean.x + "");
			node.addAttribute("y", bean.y + "");
			mapNode.add(node);
		}
		mapsNode.add(mapNode);
	}

	private static void modifyMap(Element mapsNode, Element element, MapBean map) {
		mapsNode.remove(element);
		addMap(mapsNode, map);
	}

	public static void saveMap(Map<String, ArrayList<MapBean>> maps) {
		Set<String> keySet = maps.keySet();
		Iterator<String> it = keySet.iterator();
		while (it.hasNext()) {
			String key = it.next();

			ArrayList<MapBean> mapList = maps.get(key);
			for (int i = 0; i < mapList.size(); i++) {
				MapBean map = mapList.get(i);
				saveMap(map);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static void saveMap(MapBean map) {
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new FileInputStream(new File(Define.MAP_PATH));
			SAXReader saxReader = new SAXReader();
			Document document = saxReader.read(in);
			// 将整个games-config.xml文件解析出来
			Element _root = document.getRootElement();

			// 提取XML中的map记录
			String xpath = "/maps";
			List<Element> mapsNode = _root.selectNodes(xpath);

			xpath = "//map[@name='" + map.name + "']";
			List<Element> mapNode = _root.selectNodes(xpath);
			if (mapNode != null && mapNode.size() > 0) {
				modifyMap(mapsNode.get(0), mapNode.get(0), map);
			} else {
				addMap(mapsNode.get(0), map);
			}

			out = new FileOutputStream(new File(Define.MAP_PATH));
			XMLWriter writer = new XMLWriter(out);
			writer.write(document);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void saveMap(String xpath, String value) {
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new FileInputStream(new File(Define.MAP_PATH));
			SAXReader saxReader = new SAXReader();
			Document document = saxReader.read(in);
			// 将整个games-config.xml文件解析出来
			Element _root = document.getRootElement();

			// 提取XML中的map记录
			Object obj = _root.selectObject(xpath);
			if (obj instanceof Attribute) {
				((Attribute) obj).setValue(value);
			}

			out = new FileOutputStream(new File(Define.MAP_PATH));
			XMLWriter writer = new XMLWriter(out);
			writer.write(document);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static ArrayList<MapBean> getEasyMap() {
		return maps.get(easy);
	}

	public static ArrayList<MapBean> getModerateMap() {
		return maps.get(moderate);
	}

	public static ArrayList<MapBean> getDifficuteMap() {
		return maps.get(difficute);
	}

	public static ArrayList<MapBean> getAbsurdMap() {
		return maps.get(absurd);
	}

	public static MapBean getMapByName(String name) {
		String category = name.substring(0, name.lastIndexOf("."));
		for (int i = 0; i < maps.get(category).size(); i++) {
			if (maps.get(category).get(i).name.equals(name)) {
				return maps.get(category).get(i);
			}
		}
		return null;
	}

	public static void enableNext(MapBean map) {
		ArrayList<MapBean> vector = maps.get(map.name.substring(0, map.name.lastIndexOf(".")));
		for (int i = 0; i < vector.size(); i++) {
			if (vector.get(i).name.equals(map.name)) {
				if (i + 1 < vector.size() && !vector.get(i + 1).isEnabled) {
					enableMap(vector.get(i + 1));
					break;
				}
			}
		}
	}

	public static void enableMap(MapBean map) {
		map.isEnabled = true;
		saveMap("//map[@name='" + map.name + "']/@enabled", "true");
	}
}
