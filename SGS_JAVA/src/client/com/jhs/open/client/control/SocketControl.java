package com.jhs.open.client.control;

import java.util.Date;

import com.jhs.open.base.bean.User;

/**
 * 网络控制<br>
 * 初始化socket，提供socket操作API
 * 
 * @author JHS
 * 
 */
public class SocketControl {
	private static SocketControl gi;

	public static SocketControl gi() {
		if (gi == null) {
			gi = new SocketControl();
		}
		return gi;
	}

	// [begin] socket操作API
	public User visitorLogin(String name) {
		User user = new User();

		user.setShowName(name);
		user.setCheck(false);
		user.setName(new Date().getTime() + "");

		return user;
	}
	// [end] socket操作API

}
