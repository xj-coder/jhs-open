package com.jhs.open.base.bean;

/**
 * 游戏包Bean
 * 
 * @author JHS
 * 
 */
public class GamePack {

	private String name;// 游戏包名

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof GamePack) {
			return name.equals(((GamePack) obj).getName());
		}
		return false;
	}

	@Override
	public String toString() {
		return name;
	}
}
