package com.jhs.open.bean;

import java.util.HashMap;
import java.util.Map;

import com.jhs.open.bean.impl.IBackupable;

/**
 * 发射器Bean
 * 
 * @author JHS
 * 
 */
public class EmitterBean implements IBackupable<EmitterBean> {
	public String type;// 类型，不同颜色的发射器就是不同类型
	public int x;
	public int y;

	@Override
	public EmitterBean copy() {
		EmitterBean emitter = new EmitterBean();

		emitter.type = type;
		emitter.x = x;
		emitter.y = y;

		return emitter;
	}

	private Map<String, EmitterBean> map = new HashMap<String, EmitterBean>();

	@Override
	public void backup() {
		backup("");
	}

	@Override
	public EmitterBean restore() {
		return restore("");
	}

	@Override
	public void backup(String key) {
		map.remove(key);
		map.put(key, copy());
	}

	@Override
	public EmitterBean restore(String key) {
		EmitterBean backup = map.get(key);

		if (backup != null) {
			type = backup.type;
			x = backup.x;
			y = backup.y;
		}

		return backup;
	}

}
