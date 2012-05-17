package com.jhs.open.bean;

import java.util.HashMap;
import java.util.Map;

import com.jhs.open.bean.impl.IBackupable;

/**
 * 挡板Bean
 * 
 * @author JHS
 * 
 */
public class MirrorBean extends BodyBean implements IBackupable<MirrorBean> {
	public static final String LEFT = "L";
	public static final String RIGHT = "R";

	public String type;// 类型，左挡板和右挡板两种，以挡板头所指的方向为准
	public boolean isLock = false;

	@Override
	public MirrorBean copy() {
		MirrorBean mirror = new MirrorBean();

		mirror.type = type;
		mirror.x = x;
		mirror.y = y;
		mirror.isLock = isLock;

		return mirror;
	}

	private Map<String, MirrorBean> map = new HashMap<String, MirrorBean>();

	@Override
	public void backup() {
		backup("");
	}

	@Override
	public MirrorBean restore() {
		return restore("");
	}

	@Override
	public void backup(String key) {
		map.remove(key);
		map.put(key, copy());
	}

	@Override
	public MirrorBean restore(String key) {
		MirrorBean backup = map.get(key);

		if (backup != null) {
			type = backup.type;
			x = backup.x;
			y = backup.y;
			isLock = backup.isLock;
		}

		return backup;
	}
}
