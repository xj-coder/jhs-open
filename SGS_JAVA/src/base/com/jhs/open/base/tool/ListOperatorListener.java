package com.jhs.open.base.tool;

public interface ListOperatorListener<T> {

	public boolean afterListChange(ListOperatorEvent<T> event);

	public void listChanged(ListOperatorEvent<T> event);
}
