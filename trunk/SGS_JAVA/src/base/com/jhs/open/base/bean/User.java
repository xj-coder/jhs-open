package com.jhs.open.base.bean;

/**
 * 用户Bean
 * 
 * @author JHS
 * 
 */
public class User {
	private String name;// 用户名（唯一）
	private String showName;// 用户显示名（昵称）
	private boolean isCheck = false;// 是否是验证用户（true表示验证用户，false表示游客）

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShowName() {
		return showName;
	}

	public void setShowName(String showName) {
		this.showName = showName;
	}

	public boolean isCheck() {
		return isCheck;
	}

	public void setCheck(boolean isCheck) {
		this.isCheck = isCheck;
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
