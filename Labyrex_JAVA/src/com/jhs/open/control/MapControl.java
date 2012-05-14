package com.jhs.open.control;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.dom.DOMElement;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.dom4j.tree.DefaultElement;

import com.jhs.open.Define;
import com.jhs.open.bean.EmitterBean;
import com.jhs.open.bean.GroupBean;
import com.jhs.open.bean.MapBean;
import com.jhs.open.bean.MirrorBean;
import com.jhs.open.bean.ReceiverBean;

/**
 * 地图管理器
 * 
 * @author JHS
 * 
 */

@SuppressWarnings({ "unchecked"})
public class MapControl {
	public final static String easy = "easy";
	public final static String moderate = "moderate";
	public final static String difficute = "difficute";
	public final static String absurd = "absurd";

	private static ArrayList<GroupBean> groupList = new ArrayList<GroupBean>();

	/**
	 * 读取关卡XML文件
	 */
	public static void read() {
		clearGroup();

		InputStream in = null;
		try {
			in = new FileInputStream(new File(Define.MAP_PATH));
			SAXReader saxReader = new SAXReader();
			Document document = saxReader.read(in);
			// 将整个games-config.xml文件解析出来
			Element _root = document.getRootElement();

			// 提取XML中的group记录
			String xpath = "//group";
			List<Element> groupNodes = _root.selectNodes(xpath);
			for (int i = 0; i < groupNodes.size(); i++) {
				DefaultElement groupNode = (DefaultElement) groupNodes.get(i);

				GroupBean group = new GroupBean();
				group.sort = Integer.parseInt(groupNode.attribute("sort").getValue());
				group.name = groupNode.attribute("name").getValue();

				List mapNodes = groupNode.elements("map");

				for (int j = 0; j < mapNodes.size(); j++) {
					DefaultElement mapNode = (DefaultElement) mapNodes.get(j);

					MapBean map = new MapBean();

					map.width = Integer.parseInt(mapNode.attribute("width").getValue());
					map.height = Integer.parseInt(mapNode.attribute("height").getValue());
					map.sort = Integer.parseInt(mapNode.attribute("sort").getValue());
					map.name = mapNode.attribute("name").getValue();
					map.isEnabled = mapNode.attribute("enabled").getValue().equals("true");

					List emitterNodes = mapNode.elements("emitter");

					List receiverNodes = mapNode.elements("receiver");

					List mirrorNodes = mapNode.elements("mirror");

					for (int p = 0; p < emitterNodes.size(); p++) {
						DefaultElement emitter = (DefaultElement) emitterNodes.get(p);

						EmitterBean emitterBean = new EmitterBean();
						emitterBean.type = emitter.attribute("type").getValue().trim();
						emitterBean.x = Integer.parseInt(emitter.attribute("x").getValue());
						emitterBean.y = Integer.parseInt(emitter.attribute("y").getValue());
						emitterBean.backup();

						map.emitterList.add(emitterBean);
					}

					for (int p = 0; p < receiverNodes.size(); p++) {
						DefaultElement receiver = (DefaultElement) receiverNodes.get(p);

						ReceiverBean receiverBean = new ReceiverBean();
						receiverBean.type = receiver.attribute("type").getValue().trim();
						receiverBean.x = Integer.parseInt(receiver.attribute("x").getValue());
						receiverBean.y = Integer.parseInt(receiver.attribute("y").getValue());
						receiverBean.backup();

						map.receiverList.add(receiverBean);
					}

					for (int p = 0; p < mirrorNodes.size(); p++) {
						DefaultElement mirror = (DefaultElement) mirrorNodes.get(p);

						MirrorBean mirrorBean = new MirrorBean();
						mirrorBean.type = mirror.attribute("type").getValue().trim();
						mirrorBean.x = Integer.parseInt(mirror.attribute("x").getValue());
						mirrorBean.y = Integer.parseInt(mirror.attribute("y").getValue());
						mirrorBean.backup();

						map.mirrorList.add(mirrorBean);
					}

					group.addMap(map);
				}

				addGroup(group);
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
	}

	/**
	 * 保存关卡XML文件
	 */
	public static void save() {
		InputStream in = null;
		OutputStream out = null;

		try {
			in = new FileInputStream(new File(Define.MAP_PATH));
			SAXReader saxReader = new SAXReader();
			Document document = saxReader.read(in);
			// 将整个games-config.xml文件解析出来
			Element rootNode = document.getRootElement();

			// 删除XML中的maps根节点，创建新的根节点
			rootNode.clearContent();

			for (int i = 0; i < getGroupCount(); i++) {
				GroupBean group = getGroup(i);
				addGroup(rootNode, group);
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

	public static int getGroupCount() {
		return groupList.size();
	}

	public static GroupBean getGroup(int index) {
		return groupList.get(index);
	}

	public static GroupBean getGroup(String name) {
		for (int i = 0; i < groupList.size(); i++) {
			if (groupList.get(i).getName().equals(name)) {
				return groupList.get(i);
			}
		}
		return null;
	}

	public static int indexOf(GroupBean group) {
		for (int i = 0; i < getGroupCount(); i++) {
			GroupBean bean = getGroup(i);
			if (bean.equals(group)) {
				return i;
			}
		}

		return -1;
	}

	public static boolean addGroup(GroupBean group) {
		if (getGroup(group.getName()) == null) {

			// 如果列表中存在sort一样的元素。将自己的sort+1，然后将后面元素的sort都+1
			for (int i = 0; i < getGroupCount(); i++) {
				GroupBean bean1 = getGroup(i);
				if (bean1.sort == group.sort) {
					group.sort = group.sort + 1;
					for (int j = i + 1; j < getGroupCount(); j++) {
						GroupBean bean2 = getGroup(j);
						bean2.sort = bean2.sort + 1;
					}
					break;
				}
			}

			group.backup();

			groupList.add(group);

			Collections.sort(groupList);

			return true;
		} else {
			return false;
		}

	}

	public static GroupBean removeGroup(int index) {
		GroupBean group = getGroup(index);
		removeGroup(group);
		return group;
	}

	public static GroupBean removeGroup(String name) {
		GroupBean group = getGroup(name);
		removeGroup(group);
		return group;
	}

	public static boolean removeGroup(GroupBean group) {
		for (int i = indexOf(group); i != -1 && i < getGroupCount(); i++) {
			getGroup(i).sort = getGroup(i).sort - 1;
		}
		return groupList.remove(group);
	}

	public static void clearGroup() {
		groupList.clear();
	}

	public static void sort() {
		Collections.sort(groupList);
	}

	public static int getMaxSort() {
		if (getGroupCount() > 0) {
			return getGroup(getGroupCount() - 1).sort;
		}
		return 0;
	}

	public static GroupBean getEasyGroup() {
		return getGroup(easy);
	}

	public static GroupBean getModerateGroup() {
		return getGroup(moderate);
	}

	public static GroupBean getDifficuteGroup() {
		return getGroup(difficute);
	}

	public static GroupBean getAbsurdGroup() {
		return getGroup(absurd);
	}

	public static MapBean getMap(String groupName, String mapName) {
		GroupBean group = getGroup(groupName);
		if (group != null) {
			group.getMap(mapName);
		}
		return null;
	}

	/**
	 * 使下一关可见，该方法会自动保存XML文件
	 */
	public static boolean enableNext(MapBean map) {
		GroupBean group = map.getGroup();

		if (group != null) {
			int index = group.indexOf(map);
			if (index != -1 && index + 1 < group.getMapCount()) {
				return enableMap(group.getMap(index + 1));
			}
		}
		return false;
	}

	/**
	 * 使指定关卡可见，该方法会自动保存XML文件
	 */
	public static boolean enableMap(MapBean map) {
		map.isEnabled = true;
		GroupBean group = map.getGroup();

		if (group != null) {
			int index = group.indexOf(map);
			if (index != -1 && !map.isEnabled()) {
				map.setEnabled(true);
				save();
				return true;
			}
		}

		return false;
	}

	private static void addMap(Element mapsNode, MapBean map) {
		Element mapNode = new DOMElement("map");
		mapNode.addAttribute("name", map.name);
		mapNode.addAttribute("width", map.width + "");
		mapNode.addAttribute("height", map.height + "");
		mapNode.addAttribute("enabled", map.isEnabled + "");
		mapNode.addAttribute("sort", map.sort + "");

		for (int i = 0; i < map.emitterList.size(); i++) {
			EmitterBean bean = map.emitterList.get(i);
			Element node = new DOMElement("emitter");
			node.addAttribute("type", bean.type);
			node.addAttribute("x", bean.x + "");
			node.addAttribute("y", bean.y + "");
			mapNode.add(node);
		}
		for (int i = 0; i < map.mirrorList.size(); i++) {
			MirrorBean bean = map.mirrorList.get(i);
			Element node = new DOMElement("mirror");
			node.addAttribute("type", bean.type);
			node.addAttribute("x", bean.x + "");
			node.addAttribute("y", bean.y + "");
			mapNode.add(node);
		}
		for (int i = 0; i < map.receiverList.size(); i++) {
			ReceiverBean bean = map.receiverList.get(i);
			Element node = new DOMElement("receiver");
			node.addAttribute("type", bean.type);
			node.addAttribute("x", bean.x + "");
			node.addAttribute("y", bean.y + "");
			mapNode.add(node);
		}
		mapsNode.add(mapNode);
	}

	private static void addGroup(Element rootNode, GroupBean group) {
		Element groupNode = new DOMElement("group");
		groupNode.addAttribute("name", group.name);
		groupNode.addAttribute("sort", group.sort + "");

		for (int i = 0; i < group.getMapCount(); i++) {
			addMap(groupNode, group.getMap(i));
		}

		rootNode.add(groupNode);
	}
}
