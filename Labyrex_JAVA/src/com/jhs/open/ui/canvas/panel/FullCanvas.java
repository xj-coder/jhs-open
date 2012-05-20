package com.jhs.open.ui.canvas.panel;

import java.awt.Image;

import com.jhs.open.Define;
import com.jhs.open.control.event.FullCanvasDragEvent;
import com.jhs.open.tool.ImageTools;
import com.jhs.open.ui.Canvas;
import com.jhs.open.ui.shape.EXButton;

public class FullCanvas extends Canvas {

	private EXButton closeButton;

	private Image buttonBgImage;
	private Image buttonBgrImage;

	public FullCanvas(int x, int y, int width, int height) {
		super(x, y, width, height);

		addMouseListener(new FullCanvasDragEvent());
	}

	@Override
	public void init() {
		super.init();

		buttonBgImage = ImageTools.resizeImage(ImageTools.getImage(Define.FullCanvas.close_bg_path),
				Define.FullCanvas.close_button_width, Define.FullCanvas.close_button_height);
		buttonBgrImage = ImageTools.resizeImage(ImageTools.getImage(Define.FullCanvas.close_bg_r_path),
				Define.FullCanvas.close_button_width, Define.FullCanvas.close_button_height);

		addCanvas(getCloseButton());
	}

	public EXButton getCloseButton() {
		if (closeButton == null) {
			closeButton = new EXButton("close", Define.FullCanvas.close_button_x, Define.FullCanvas.close_button_y,
					Define.FullCanvas.close_button_width, Define.FullCanvas.close_button_height);

			closeButton.setBgImage(buttonBgImage);
			closeButton.setBgrImage(buttonBgrImage);

		}
		return closeButton;
	}
}
