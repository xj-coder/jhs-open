package c.city.desolate.control.event.map;

import c.city.desolate.ui.shape.ReceiverShape;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
