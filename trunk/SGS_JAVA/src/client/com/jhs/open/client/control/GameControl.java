package com.jhs.open.client.control;

import com.jhs.open.base.bean.User;

/**
 * 游戏控制器
 * 
 * @author JHS
 * 
 */
public class GameControl {
	private static GameControl gi;

	private User user;

	public static GameControl gi() {
		if (gi == null) {
			gi = new GameControl();
		}
		return gi;
	}

	public void exit() {
		System.exit(0);
	}

	public void begin() {
		LookAndFeelControl.installDefaultLookAndFeel();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
