package com.jhs.open.ui.canvas.panel;

import java.awt.Color;
import java.awt.Image;

import com.jhs.open.Define;
import com.jhs.open.control.GameControl;
import com.jhs.open.tool.ImageTools;
import com.jhs.open.ui.Canvas;
import com.jhs.open.ui.shape.EXButton;

/**
 * 开始界面
 * 
 * @author JHS
 * 
 */
public class MainCanvas extends Canvas {
	private Image bgImage;
	private Image buttonBgImage;
	private Image buttonBgrImage;
	private int bgColorR = 0xFF;
	private int bgColorG = 0xFF;
	private int bgColorB = 0xFF;

	private EXButton startButton;
	private EXButton musicButton;
	private EXButton soundButton;
	private EXButton helpButton;

	public MainCanvas() {
		super(0, 0, Define.Main.width, Define.Main.height);
	}

	@Override
	public void init() {
		bgImage = ImageTools.cut(Define.MainPanel.bg_image_path, 0, 0, Define.Main.width, Define.Main.height, "png");

		buttonBgImage = ImageTools.resizeImage(ImageTools.getImage(Define.Button.bg_path),
				Define.MainPanel.start_button_width, Define.MainPanel.start_button_height);
		buttonBgrImage = ImageTools.resizeImage(ImageTools.getImage(Define.Button.bg_r_path),
				Define.MainPanel.start_button_width, Define.MainPanel.start_button_height);

		addCanvas(getStartButton());
		addCanvas(getMusicButton());
		addCanvas(getSoundButton());
		addCanvas(getHelpButton());
	}

	public EXButton getStartButton() {
		if (startButton == null) {
			startButton = new EXButton("start", Define.MainPanel.start_button_x, Define.MainPanel.start_button_y,
					Define.MainPanel.start_button_width, Define.MainPanel.start_button_height);

			startButton.setFgImage(ImageTools.getImage(Define.Button.start_fg_path));
			startButton.setBgImage(buttonBgImage);
			startButton.setBgrImage(buttonBgrImage);
		}
		return startButton;
	}

	public EXButton getMusicButton() {
		if (musicButton == null) {
			musicButton = new EXButton("music", true, Define.MainPanel.music_button_x, Define.MainPanel.music_button_y,
					Define.MainPanel.music_button_width, Define.MainPanel.music_button_height);

			musicButton.setFgImage(ImageTools.getImage(Define.Button.music_fg_path));
			musicButton.setBgImage(buttonBgImage);
			musicButton.setBgrImage(buttonBgrImage);

			musicButton.setSelected(!GameControl.gi().isMusic());
		}
		return musicButton;
	}

	public EXButton getSoundButton() {
		if (soundButton == null) {
			soundButton = new EXButton("sound", true, Define.MainPanel.sound_button_x, Define.MainPanel.sound_button_y,
					Define.MainPanel.sound_button_width, Define.MainPanel.sound_button_height);

			soundButton.setFgImage(ImageTools.getImage(Define.Button.sound_fg_path));
			soundButton.setBgImage(buttonBgImage);
			soundButton.setBgrImage(buttonBgrImage);

			soundButton.setSelected(!GameControl.gi().isSound());
		}
		return soundButton;
	}

	public EXButton getHelpButton() {
		if (helpButton == null) {
			helpButton = new EXButton("help", Define.MainPanel.help_button_x, Define.MainPanel.help_button_y,
					Define.MainPanel.help_button_width, Define.MainPanel.help_button_height);

			helpButton.setFgImage(ImageTools.getImage(Define.Button.help_fg_path));
			helpButton.setBgImage(buttonBgImage);
			helpButton.setBgrImage(buttonBgrImage);
		}
		return helpButton;
	}

	@Override
	public void render() {
		Color c = getGraphics().getColor();
		getGraphics().setColor(new Color(bgColorR, bgColorG, bgColorB));
		getGraphics().fillRect(x, y, width, height);
		getGraphics().setColor(c);

		getGraphics().drawImage(bgImage, 0, 0, null);

		super.render();
	}

	int dirR = 1;
	int dirG = 1;
	int dirB = 1;

	@Override
	public void update(long l) {
		super.update(l);
		int step = 15;

		bgColorR = bgColorR - step * dirR;
		if (bgColorR <= 0 || bgColorR > 255) {
			bgColorR = bgColorR <= 0 ? 0 : 255;
			dirR = dirR * -1;
			bgColorG = bgColorG - step * dirG;
			if (bgColorG <= 0 || bgColorG > 255) {
				bgColorG = bgColorG <= 0 ? 0 : 255;
				dirG = dirG * -1;
				bgColorB = bgColorB - step * dirB;
				if (bgColorB <= 0 || bgColorB > 255) {
					bgColorB = bgColorB <= 0 ? 0 : 255;
					dirB = dirB * -1;
				}
			}
		}
	}
}
