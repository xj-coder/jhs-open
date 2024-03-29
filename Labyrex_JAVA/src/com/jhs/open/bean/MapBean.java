package com.jhs.open.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.jhs.open.bean.impl.IBackupable;

/**
 * 地图Bean
 * 
 * @author JHS
 * 
 */

public class MapBean implements IBackupable<MapBean>, Comparable<MapBean> {
	public String name;// 地图名
	public boolean isEnabled = false;// 可见性标记

	public int width = 0;
	public int height = 0;
	public int sort = Integer.MAX_VALUE;

	public ArrayList<EmitterBean> emitterList = new ArrayList<EmitterBean>();// 发射器集合
	public ArrayList<ReceiverBean> receiverList = new ArrayList<ReceiverBean>();// 接收器集合
	public ArrayList<MirrorBean> mirrorList = new ArrayList<MirrorBean>();// 挡板集合

	public boolean isSave = true;

	public GroupBean group;

	public int getEmitterCount(String type) {
		int count = 0;
		for (int i = 0; i < emitterList.size(); i++) {
			if (emitterList.get(i).type.equals(type)) {
				count++;
			}
		}
		return count;
	}

	public int getReceiverCount(String type) {
		int count = 0;
		for (int i = 0; i < receiverList.size(); i++) {
			if (receiverList.get(i).type.equals(type)) {
				count++;
			}
		}
		return count;
	}

	public int getMirrorCount(String type) {
		int count = 0;
		for (int i = 0; i < mirrorList.size(); i++) {
			if (mirrorList.get(i).type.equals(type)) {
				count++;
			}
		}
		return count;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public ArrayList<EmitterBean> getEmitterList() {
		return emitterList;
	}

	public void setEmitterList(ArrayList<EmitterBean> emitterList) {
		this.emitterList = emitterList;
	}

	public ArrayList<ReceiverBean> getReceiverList() {
		return receiverList;
	}

	public void setReceiverList(ArrayList<ReceiverBean> receiverList) {
		this.receiverList = receiverList;
	}

	public ArrayList<MirrorBean> getMirrorList() {
		return mirrorList;
	}

	public void setMirrorList(ArrayList<MirrorBean> mirrorList) {
		this.mirrorList = mirrorList;
	}

	public boolean isSave() {
		return isSave;
	}

	public void setSave(boolean isSave) {
		this.isSave = isSave;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public GroupBean getGroup() {
		return group;
	}

	public void setGroup(GroupBean group) {
		this.group = group;
	}

	public void doChange() {
		if (isSave) {
			isSave = false;
		}
	}

	@Override
	public int compareTo(MapBean map) {
		int result = sort - map.sort;

		if (result == 0) {
			System.err.println("ERR : map[" + map + "].sort == map[" + this + "]");
		}

		return result;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof MapBean) {
			MapBean map = (MapBean) obj;

			if (!map.name.equals(name)) {
				return false;
			}
			if (!map.group.equals(group)) {
				return false;
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	public MapBean copy() {
		MapBean map = new MapBean();

		map.name = name;
		map.isEnabled = isEnabled;
		map.width = width;
		map.height = height;
		map.sort = sort;
		map.group = group;

		for (int i = 0; i < receiverList.size(); i++) {
			map.receiverList.add(receiverList.get(i).copy());
		}
		for (int i = 0; i < emitterList.size(); i++) {
			map.emitterList.add(emitterList.get(i).copy());
		}
		for (int i = 0; i < mirrorList.size(); i++) {
			map.mirrorList.add(mirrorList.get(i).copy());
		}

		return map;
	}

	private Map<String, MapBean> map = new HashMap<String, MapBean>();

	@Override
	public void backup() {
		backup("");
	}

	@Override
	public MapBean restore() {
		return restore("");
	}

	@Override
	public void backup(String key) {
		map.remove(key);

		for (int i = 0; i < receiverList.size(); i++) {
			receiverList.get(i).backup(key);
		}
		for (int i = 0; i < emitterList.size(); i++) {
			emitterList.get(i).backup(key);
		}
		for (int i = 0; i < mirrorList.size(); i++) {
			mirrorList.get(i).backup(key);
		}

		map.put(key, copy());
	}

	@Override
	public MapBean restore(String key) {
		MapBean backup = map.get(key);

		if (backup != null) {
			name = backup.name;
			isEnabled = backup.isEnabled;
			width = backup.width;
			height = backup.height;
			sort = backup.sort;
			isSave = backup.isSave;
			group = backup.group;

		}
		for (int i = 0; i < receiverList.size(); i++) {
			receiverList.get(i).restore(key);
		}
		for (int i = 0; i < emitterList.size(); i++) {
			emitterList.get(i).restore(key);
		}
		for (int i = 0; i < backup.mirrorList.size(); i++) {
			mirrorList.get(i).restore(key);
		}

		backup(key);
		return backup;
	}

}