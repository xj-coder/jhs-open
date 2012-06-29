package com.jhs.open.filter;

import java.awt.image.BufferedImage;

public abstract class Filter {
	private int total;
	private int process;

	public void filter(final BufferedImage bufferedImage) {
		// new Thread(new Runnable() {
		// @Override
		// public void run() {
		dofilter(bufferedImage);
		// }
		// }).start();
	}

	protected abstract void dofilter(BufferedImage bufferedImage);

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getProcess() {
		return process;
	}

	public void setProcess(int process) {
		this.process = process;
	}
}
