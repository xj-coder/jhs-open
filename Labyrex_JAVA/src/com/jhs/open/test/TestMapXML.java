package com.jhs.open.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.jhs.open.bean.MapBean;
import com.jhs.open.control.MapControl;


public class TestMapXML {

	public static void main(String[] args) {
		Map<String, ArrayList<MapBean>> maps = MapControl.readMaps();

		Set<String> keySet = maps.keySet();
		Iterator<String> it = keySet.iterator();

		while (it.hasNext()) {
			String key = it.next();
			ArrayList<MapBean> mapList = maps.get(key);
			for (int i = 0; i < mapList.size(); i++) {
				mapList.get(i).isEnabled = false;
			}
		}
		MapControl.saveMap(maps);
	}
}
