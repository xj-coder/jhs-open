package com.jhs.open.bean;

import java.util.HashMap;
import java.util.Map;

import com.jhs.open.bean.impl.IBackupable;

/**
 * 接收器Bean
 * 
 * @author JHS
 * 
 */
public class ReceiverBean implements IBackupable<ReceiverBean> {
	public String type;// 类型，不同颜色的发射器就是不同类型
	public int x;
	public int y;

	@Override
	public ReceiverBean copy() {
		ReceiverBean receiver = new ReceiverBean();

		receiver.type = type;
		receiver.x = x;
		receiver.y = y;

		return receiver;
	}

	private Map<String, ReceiverBean> map = new HashMap<String, ReceiverBean>();

	@Override
	public void backup() {
		backup("");
	}

	@Override
	public ReceiverBean restore() {
		return restore("");
	}

	@Override
	public void backup(String key) {
		map.remove(key);
		map.put(key, copy());
	}

	@Override
	public ReceiverBean restore(String key) {
		ReceiverBean backup = map.get(key);

		if (backup != null) {
			type = backup.type;
			x = backup.x;
			y = backup.y;
		}

		return backup;
	}
}
