package com.jhs.open.base.bean;

import java.util.ArrayList;

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
	private int userNum;// 当前玩家数

	private int state;// 房间状态

	private User createUser;// 创建者

	private ArrayList<GamePack> packList = new ArrayList<GamePack>();// 游戏使用的包
	private ArrayList<User> userList = new ArrayList<User>();// 用户列表

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

	public int getUserNum() {
		return userNum;
	}

	public void setUserNum(int userNum) {
		this.userNum = userNum;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public User getCreateUser() {
		return createUser;
	}

	public void setCreateUser(User createUser) {
		this.createUser = createUser;
	}

	public int getUserCount() {
		return userList.size();
	}

	public User getUser(int index) {
		return userList.get(index);
	}

	public User getUser(String name) {
		for (int i = 0; i < getUserCount(); i++) {
			if (getUser(i).getName().equals(name)) {
				return getUser(i);
			}
		}
		return null;
	}

	public int indexOfUser(User user) {
		return userList.indexOf(user);
	}

	public int indexOfUser(String name) {
		for (int i = 0; i < getUserCount(); i++) {
			if (getUser(i).getName().equals(name)) {
				return i;
			}
		}
		return -1;
	}

	public boolean removeUser(User user) {
		return userList.remove(user);
	}

	public User removeUser(int index) {
		return userList.remove(index);
	}

	public User removeUser(String name) {
		User user = getUser(name);
		userList.remove(user);
		return user;
	}

	public void clearUser() {
		userList.clear();
	}

	public int getGamePackCount() {
		return packList.size();
	}

	public GamePack getGamePack(int index) {
		return packList.get(index);
	}

	public GamePack getGamePack(String name) {
		for (int i = 0; i < getGamePackCount(); i++) {
			if (getGamePack(i).getName().equals(name)) {
				return getGamePack(i);
			}
		}
		return null;
	}

	public int indexOfGamePack(User user) {
		return packList.indexOf(user);
	}

	public int indexOfGamePack(String name) {
		for (int i = 0; i < getGamePackCount(); i++) {
			if (getGamePack(i).getName().equals(name)) {
				return i;
			}
		}
		return -1;
	}

	public boolean removeGamePack(GamePack pack) {
		return packList.remove(pack);
	}

	public GamePack removeGamePack(int index) {
		return packList.remove(index);
	}

	public GamePack removeGamePack(String name) {
		GamePack pack = getGamePack(name);
		packList.remove(pack);
		return pack;
	}

	public void clearGamePack() {
		packList.clear();
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
