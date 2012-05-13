package com.jhs.open.bean.impl;

public interface IBackupable<T> extends ICopyable<T> {
	// public void backup(String key, T t) {
	// map.remove(key);
	// map.put(key, t.copy());
	// }
	//
	// public T restore(String key) {
	// T t = map.get(key);
	// if (t != null) {
	// t.backup(key, t);
	// }
	// return t;
	// }

	public void backup();

	public void backup(String key);

	public T restore();

	public T restore(String key);
}
