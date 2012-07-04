package com.jhs.open.base.bean;

import java.util.ArrayList;

import com.jhs.open.base.tool.ListOperator;

public class Room {
	public static final String ROOM_STATE_BEGIN = "游戏中";
	public static final String ROOM_STATE_WAIT = "等待中";
	public static final String ROOM_STATE_LOCK = "锁定中";

	private int number;// 房间编号（唯一）
	private String name;// 房间名（唯一）
	private GameModel model;// 游戏模式

	private boolean isLook;// 是否支持旁观
	private boolean isChat;// 是否支持聊天
	private int waitTime;// 出手等待时间，单位秒

	private int maxUserNum;// 最大玩家数

	private String state;// 房间状态

	private User createUser;// 创建者

	private ListOperator<GamePack> packList = new ListOperator<GamePack>(new ArrayList<GamePack>());// 游戏使用的包
	private ListOperator<User> userList = new ListOperator<User>(new ArrayList<User>());// 用户列表
	private ListOperator<User> lookerList = new ListOperator<User>(new ArrayList<User>());// 旁观者列表，仅当isLook=true的时候有效

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public GameModel getModel() {
		return model;
	}

	public void setModel(GameModel model) {
		this.model = model;
	}

	public boolean isLook() {
		return isLook;
	}

	public void setLook(boolean isLook) {
		this.isLook = isLook;
	}

	public boolean isChat() {
		return isChat;
	}

	public void setChat(boolean isChat) {
		this.isChat = isChat;
	}

	public int getWaitTime() {
		return waitTime;
	}

	public void setWaitTime(int waitTime) {
		this.waitTime = waitTime;
	}

	public int getMaxUserNum() {
		return maxUserNum;
	}

	public void setMaxUserNum(int maxUserNum) {
		this.maxUserNum = maxUserNum;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public User getCreateUser() {
		return createUser;
	}

	public void setCreateUser(User createUser) {
		this.createUser = createUser;
	}

	public ListOperator<GamePack> getPackList() {
		return packList;
	}

	public ListOperator<User> getUserList() {
		return userList;
	}

	public ListOperator<User> getLookerList() {
		return lookerList;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Room) {
			return name.equals(((Room) obj).getName()) && number == ((Room) obj).getNumber();
		}
		return false;
	}

	@Override
	public String toString() {
		return name;
	}
}
