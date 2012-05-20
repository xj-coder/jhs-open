package com.jhs.open.base.bean;

/**
 * 用户Bean
 * 
 * @author JHS
 * 
 */
public class User {
	private String name;// 用户名（唯一）

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof User) {
			return name.equals(((User) obj).getName());
		}
		return false;
	}

	@Override
	public String toString() {
		return name;
	}
}
