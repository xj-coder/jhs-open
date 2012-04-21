package com.jhs.open.control.event.map;


import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.jhs.open.ui.shape.ReceiverShape;

public class ReceiverMouseClickedAdapter extends MouseAdapter {
	@SuppressWarnings("unused")
	private ReceiverShape receiver;

	public ReceiverMouseClickedAdapter(ReceiverShape receiver) {
		this.receiver = receiver;
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}
}
