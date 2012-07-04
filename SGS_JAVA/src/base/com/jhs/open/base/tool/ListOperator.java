package com.jhs.open.base.tool;

import java.util.ArrayList;
import java.util.List;

public class ListOperator<T> {

	private List<T> list;

	private ArrayList<ListOperatorListener<T>> listeners = new ArrayList<ListOperatorListener<T>>();

	public ListOperator(List<T> list) {
		this.list = list;
	}

	public ArrayList<ListOperatorListener<T>> getListeners() {
		return listeners;
	}

	public void setListeners(ArrayList<ListOperatorListener<T>> listeners) {
		this.listeners = listeners;
	}

	public int getCount() {
		return list.size();
	}

	public T get(int index) {
		return list.get(index);
	}

	public int indexOf(T t) {
		return list.indexOf(t);
	}

	public boolean remove(T t) {
		ListOperatorEvent<T> event = new ListOperatorEvent<T>();
		event.setList(list);
		event.setOperaType(ListOperatorEvent.REMOVE);
		event.setSource(t);
		for (int i = 0; i < listeners.size(); i++) {
			if (!listeners.get(i).afterListChange(event)) {
				return false;
			}
		}
		boolean result = list.remove(t);
		for (int i = 0; i < listeners.size(); i++) {
			listeners.get(i).listChanged(event);
		}
		return result;
	}

	public T remove(int index) {
		T t = list.get(index);
		if (remove(t)) {
			return t;
		} else {
			return null;
		}
	}

	public boolean add(int index, T t) {
		ListOperatorEvent<T> event = new ListOperatorEvent<T>();
		event.setList(list);
		event.setOperaType(ListOperatorEvent.ADD);
		event.setSource(t);
		for (int i = 0; i < listeners.size(); i++) {
			if (!listeners.get(i).afterListChange(event)) {
				return false;
			}
		}
		list.add(index, t);
		for (int i = 0; i < listeners.size(); i++) {
			listeners.get(i).listChanged(event);
		}
		return true;
	}

	public boolean add(T t) {
		ListOperatorEvent<T> event = new ListOperatorEvent<T>();
		event.setList(list);
		event.setOperaType(ListOperatorEvent.ADD);
		event.setSource(t);
		for (int i = 0; i < listeners.size(); i++) {
			if (!listeners.get(i).afterListChange(event)) {
				return false;
			}
		}
		list.add(t);
		for (int i = 0; i < listeners.size(); i++) {
			listeners.get(i).listChanged(event);
		}
		return true;
	}

	public void clearLooker() {
		list.clear();
	}
}
