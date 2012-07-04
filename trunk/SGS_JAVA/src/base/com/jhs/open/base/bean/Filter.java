package com.jhs.open.base.bean;

import java.lang.reflect.Method;

public class Filter<T> {

	private String param;
	private Object value;

	public Filter(String param, Object value) {
		this.param = param;
		this.value = value;
	}

	@SuppressWarnings("unchecked")
	public boolean filter(T t) {

		Class<T> c = (Class<T>) t.getClass();

		String methodName = "get" + param.substring(0, 1).toUpperCase() + param.substring(1, param.length());
		Method method = null;
		try {
			method = c.getMethod(methodName);
		} catch (SecurityException e) {
			e.printStackTrace();
			return true;
		} catch (NoSuchMethodException e) {
			methodName = "is" + param.substring(0, 1).toUpperCase() + param.substring(1, param.length());
			try {
				method = c.getMethod(methodName);
			} catch (SecurityException e1) {
				e1.printStackTrace();
				return true;
			} catch (NoSuchMethodException e1) {
				e1.printStackTrace();
				return true;
			}
		}

		try {
			Object result = method.invoke(t);
			if (result.equals(value)) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return true;
		}
	}
}
