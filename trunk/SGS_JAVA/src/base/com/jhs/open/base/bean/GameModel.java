package com.jhs.open.base.bean;

/**
 * 游戏模式Bean
 * 
 * @author JHS
 * 
 */
public class GameModel {

	private String name;// 模式名

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof GameModel) {
			return name.equals(((GameModel) obj).getName());
		}
		return false;
	}

	@Override
	public String toString() {
		return name;
	}
}
