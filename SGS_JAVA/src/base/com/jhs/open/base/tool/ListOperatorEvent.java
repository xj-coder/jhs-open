package com.jhs.open.base.tool;

import java.util.List;

public class ListOperatorEvent<T> {
	public static final int ADD = 0x0;
	public static final int REMOVE = 0x1;

	private T source;
	private List<T> list;
	private int operaType;

	public int getOperaType() {
		return operaType;
	}

	public void setOperaType(int operaType) {
		this.operaType = operaType;
	}

	public T getSource() {
		return source;
	}

	public void setSource(T source) {
		this.source = source;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

}
