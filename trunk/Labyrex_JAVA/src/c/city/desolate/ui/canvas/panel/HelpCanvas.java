package c.city.desolate.ui.canvas.panel;

import c.city.desolate.Define;
import c.city.desolate.control.event.help.HelpMouseClickedEvent;
import c.city.desolate.ui.Canvas;

public class HelpCanvas extends Canvas {

	public HelpCanvas() {
		super(0, 0, Define.Main.width, Define.Main.height);

	}

	@Override
	public void init() {
		addMouseListener(new HelpMouseClickedEvent());
	}

}
