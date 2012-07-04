package com.jhs.open.client.test;

import com.jhs.open.base.bean.Room;

public class TestClass {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		Room room = new Room();
		Class<Room> c = (Class<Room>) room.getClass();

		try {
			c.getMethod("setNumber", int.class).invoke(room, 2);
			System.out.println(c.getMethod("getNumber").invoke(room));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
