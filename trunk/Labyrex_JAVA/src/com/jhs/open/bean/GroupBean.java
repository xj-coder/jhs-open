package com.jhs.open.bean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.jhs.open.bean.impl.IBackupable;

public class GroupBean implements IBackupable<GroupBean>, Comparable<GroupBean> {
	public String name;
	public int sort;

	private ArrayList<MapBean> mapList = new ArrayList<MapBean>();

	public boolean isSave = true;

	public int getMapCount() {
		return mapList.size();
	}

	public MapBean getMap(int index) {
		return mapList.get(index);
	}

	public MapBean getMap(String name) {
		for (int i = 0; i < mapList.size(); i++) {
			if (mapList.get(i).getName().equals(name)) {
				return mapList.get(i);
			}
		}
		return null;
	}

	public boolean addMap(MapBean map) {
		if (getMap(map.getName()) == null) {
			map.setGroup(this);

			// 如果列表中存在sort一样的元素。将自己的sort+1，然后将后面元素的sort都+1
			for (int i = 0; i < getMapCount(); i++) {
				MapBean bean1 = getMap(i);
				if (bean1.sort == map.sort) {
					map.sort = map.sort + 1;
					for (int j = i + 1; j < getMapCount(); j++) {
						MapBean bean2 = getMap(j);
						bean2.sort = bean2.sort + 1;
					}
					break;
				}
			}

			map.backup();

			mapList.add(map);
			Collections.sort(mapList);

			return true;
		} else {
			return false;
		}
	}

	public MapBean removeMap(int index) {
		MapBean map = getMap(index);
		removeMap(map);
		return map;
	}

	public MapBean removeMap(String name) {
		MapBean map = getMap(name);
		removeMap(map);
		return map;
	}

	public boolean removeMap(MapBean map) {
		for (int i = indexOf(map); i != -1 && i < getMapCount(); i++) {
			getMap(i).sort = getMap(i).sort - 1;
		}
		return mapList.remove(map);
	}

	public void clearMap() {
		mapList.clear();
	}

	public int indexOf(MapBean map) {
		for (int i = 0; i < getMapCount(); i++) {
			MapBean bean = getMap(i);
			if (bean.equals(map)) {
				return i;
			}
		}

		return -1;
	}

	public void sort() {
		Collections.sort(mapList);
	}

	public int getMaxSort() {
		if (getMapCount() > 0) {
			return getMap(getMapCount() - 1).sort;
		}
		return -1;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public boolean isSave() {
		return isSave;
	}

	public void setSave(boolean isSave) {
		this.isSave = isSave;
	}

	public void doChange() {
		if (isSave) {
			isSave = false;
		}
	}

	@Override
	public int compareTo(GroupBean group) {
		int result = sort - group.sort;

		return result;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof GroupBean) {
			GroupBean group = (GroupBean) obj;

			if (!group.getName().equals(name)) {
				return false;
			}

			return true;
		} else {
			return false;
		}
	}

	@Override
	public GroupBean copy() {
		GroupBean group = new GroupBean();

		group.name = name;
		group.sort = sort;

		for (int i = 0; i < getMapCount(); i++) {
			group.addMap(getMap(i).copy());
		}

		return group;
	}

	private Map<String, GroupBean> map = new HashMap<String, GroupBean>();

	@Override
	public void backup() {
		backup("");
	}

	@Override
	public GroupBean restore() {
		return restore("");
	}

	@Override
	public void backup(String key) {
		map.remove(key);
		map.put(key, copy());

		for (int i = 0; i < mapList.size(); i++) {
			mapList.get(i).backup(key);
		}
	}

	@Override
	public GroupBean restore(String key) {
		GroupBean backup = map.get(key);

		if (backup != null) {
			name = backup.name;
			sort = backup.sort;
			isSave = backup.isSave;
			mapList = backup.mapList;
		}

		for (int i = 0; i < mapList.size(); i++) {
			mapList.get(i).restore(key);
		}

		backup(key);

		return backup;
	}

}
