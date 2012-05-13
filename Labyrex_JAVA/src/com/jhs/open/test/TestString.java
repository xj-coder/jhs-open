package com.jhs.open.test;

import java.util.HashMap;
import java.util.Map;

public class TestString {
	public static void main(String[] args) {
		Map<String, String> map = new HashMap<String, String>();

		map.put("", "111");

		System.out.println(map.get(""));
	}
}
