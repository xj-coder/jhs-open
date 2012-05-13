package com.jhs.open.bean;

public class Person {
	public String name;

	@Override
	public boolean equals(Object obj) {
		return name.equals(((Person) obj).name);
	}
}
