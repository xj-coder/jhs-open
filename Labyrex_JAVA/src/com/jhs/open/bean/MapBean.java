package com.jhs.open.bean;

/**
 * 地图Bean
 * 
 * @author JHS
 * 
 */

public class MapBean implements Comparable<MapBean> {
	public String name;// 地图名
	public boolean isEnabled = false;// 可见性标记

	public int width;
	public int height;

	public EmitterBean[] emitterList;// 发射器集合
	public ReceiverBean[] receiverList;// 接收器集合
	public MirrorBean[] mirrorList;// 挡板集合

	@Override
	public int compareTo(MapBean map) {
		return Integer.parseInt(name.substring(name.lastIndexOf(".") + 1, name.length()))
				- Integer.parseInt(map.name.substring(map.name.lastIndexOf(".") + 1, map.name.length()));
	}

	@Override
	public String toString() {
		return name.substring(name.lastIndexOf(".") + 1);
	}
}