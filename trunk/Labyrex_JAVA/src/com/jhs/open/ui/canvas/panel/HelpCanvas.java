package com.jhs.open.ui.canvas.panel;

import java.awt.Graphics2D;
import java.awt.Image;

import com.jhs.open.Define;
import com.jhs.open.control.event.help.HelpMouseClickedEvent;
import com.jhs.open.tool.ImageTools;

public class HelpCanvas extends FullCanvas {
	private Image bgImage;

	public HelpCanvas() {
		super(0, 0, Define.Main.width, Define.Main.height);
		isAnimation = true;
	}

	@Override
	public void init() {
		super.init();

		bgImage = ImageTools.cut(Define.HelpPanel.bg_image_path, 0, 0, Define.Main.width, Define.Main.height, "png");

		addMouseListener(new HelpMouseClickedEvent());
	}

	@Override
	public void render() {
		Graphics2D g2 = getGraphics();

		g2.drawImage(bgImage, x, y, width, height, null);

		super.render();
	}
}
