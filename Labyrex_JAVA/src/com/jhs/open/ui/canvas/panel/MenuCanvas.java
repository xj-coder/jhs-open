package com.jhs.open.ui.canvas.panel;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;

import com.jhs.open.Define;
import com.jhs.open.bean.GroupBean;
import com.jhs.open.control.MapControl;
import com.jhs.open.tool.ImageTools;
import com.jhs.open.ui.Canvas;
import com.jhs.open.ui.shape.CardShape;
import com.jhs.open.ui.shape.EXButton;

/**
 * 游戏关卡选择界面
 * 
 * @author JHS
 * 
 */
public class MenuCanvas extends Canvas {
	private Image bgImage;

	private int bgColorR = 0xFF;
	private int bgColorG = 0xFF;
	private int bgColorB = 0xFF;

	private EXButton backButton;

	public MenuCanvas() {
		super(0, 0, Define.Main.width, Define.Main.height);
	}

	@Override
	public void init() {

		bgImage = ImageTools.getImage(Define.MenuPanel.bg_image_path);

		GroupBean easyGroup = MapControl.getEasyGroup();
		int begin_x = 35;
		int begin_y = 170;
		if (easyGroup != null) {
			for (int i = 0; i < easyGroup.getMapCount(); i++) {
				CardShape card = new CardShape(begin_x + i % Define.MenuPanel.card_col * Define.MenuPanel.card_size
						+ Define.MenuPanel.offset_size * i, begin_y + i / Define.MenuPanel.card_col
						* Define.MenuPanel.card_size, easyGroup.getMap(i));
				addCanvas(card);
			}
		}
		begin_x = 385;
		begin_y = 170;
		GroupBean moderateGroup = MapControl.getModerateGroup();
		if (moderateGroup != null) {
			for (int i = 0; i < moderateGroup.getMapCount(); i++) {
				CardShape card = new CardShape(begin_x + i % Define.MenuPanel.card_col * Define.MenuPanel.card_size
						+ Define.MenuPanel.offset_size * i, begin_y + i / Define.MenuPanel.card_col
						* Define.MenuPanel.card_size, moderateGroup.getMap(i));
				addCanvas(card);
			}
		}
		begin_x = 35;
		begin_y = 360;
		GroupBean difficuteGroup = MapControl.getDifficuteGroup();
		if (difficuteGroup != null) {
			for (int i = 0; i < difficuteGroup.getMapCount(); i++) {
				CardShape card = new CardShape(begin_x + i % Define.MenuPanel.card_col * Define.MenuPanel.card_size
						+ Define.MenuPanel.offset_size * i, begin_y + i / Define.MenuPanel.card_col
						* Define.MenuPanel.card_size, difficuteGroup.getMap(i));
				addCanvas(card);
			}
		}
		begin_x = 385;
		begin_y = 360;
		GroupBean absurdGroup = MapControl.getAbsurdGroup();
		if (absurdGroup != null) {
			for (int i = 0; i < absurdGroup.getMapCount(); i++) {
				CardShape card = new CardShape(begin_x + i % Define.MenuPanel.card_col * Define.MenuPanel.card_size
						+ Define.MenuPanel.offset_size * i, begin_y + i / Define.MenuPanel.card_col
						* Define.MenuPanel.card_size, absurdGroup.getMap(i));
				addCanvas(card);
			}
		}

		addCanvas(getBackButton());
	}

	public EXButton getBackButton() {
		if (backButton == null) {
			backButton = new EXButton("back", Define.MenuPanel.back_button_x, Define.MenuPanel.back_button_y,
					Define.MenuPanel.back_button_width, Define.MenuPanel.back_button_height);

			backButton.setFgImage(ImageTools.getImage(Define.Button.back_fg_path));
			backButton.setBgImage(ImageTools.getImage(Define.Button.bg_path));
			backButton.setBgrImage(ImageTools.getImage(Define.Button.bg_r_path));
		}
		return backButton;
	}

	@Override
	public void render() {
		Graphics2D g2 = getGraphics();

		Color c = g2.getColor();
		g2.setColor(new Color(bgColorR, bgColorG, bgColorB));
		g2.fillRect(x, y, width, height);
		g2.setColor(c);

		g2.drawImage(bgImage, x, y, width, height, null);

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
