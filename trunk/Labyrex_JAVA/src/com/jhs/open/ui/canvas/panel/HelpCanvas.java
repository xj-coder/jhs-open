package com.jhs.open.ui.canvas.panel;

import com.jhs.open.Define;
import com.jhs.open.control.event.help.HelpMouseClickedEvent;
import com.jhs.open.ui.Canvas;


public class HelpCanvas extends Canvas {

	public HelpCanvas() {
		super(0, 0, Define.Main.width, Define.Main.height);

	}

	@Override
	public void init() {
		addMouseListener(new HelpMouseClickedEvent());
	}

}
