package c.city.desolate.ui.canvas.panel;

import c.city.desolate.Define;
import c.city.desolate.control.MapControl;
import c.city.desolate.tool.ImageResLoader;
import c.city.desolate.ui.Canvas;
import c.city.desolate.ui.canvas.game.MapCanvas;
import c.city.desolate.ui.shape.CardShape;
import c.city.desolate.ui.shape.EXButton;

import java.awt.*;
import java.util.Vector;

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

		bgImage = ImageResLoader.getImage(Define.MenuPanel.bg_image_path);

		Vector<MapCanvas> easyList = MapControl.getEasyMap();
		int begin_x = 35;
		int begin_y = 170;
		if (easyList != null) {
			for (int i = 0; i < easyList.size(); i++) {
				CardShape card = new CardShape(begin_x + i % Define.MenuPanel.card_col * Define.MenuPanel.card_size
						+ Define.MenuPanel.offset_size * i, begin_y + i / Define.MenuPanel.card_col
						* Define.MenuPanel.card_size, easyList.get(i));
				addCanvas(card);
			}
		}
		begin_x = 385;
		begin_y = 170;
		Vector<MapCanvas> moderateList = MapControl.getModerateMap();
		if (moderateList != null) {
			for (int i = 0; i < moderateList.size(); i++) {
				CardShape card = new CardShape(begin_x + i % Define.MenuPanel.card_col * Define.MenuPanel.card_size
						+ Define.MenuPanel.offset_size * i, begin_y + i / Define.MenuPanel.card_col
						* Define.MenuPanel.card_size, moderateList.get(i));
				addCanvas(card);
			}
		}
		begin_x = 35;
		begin_y = 360;
		Vector<MapCanvas> difficuteList = MapControl.getDifficuteMap();
		if (difficuteList != null) {
			for (int i = 0; i < difficuteList.size(); i++) {
				CardShape card = new CardShape(begin_x + i % Define.MenuPanel.card_col * Define.MenuPanel.card_size
						+ Define.MenuPanel.offset_size * i, begin_y + i / Define.MenuPanel.card_col
						* Define.MenuPanel.card_size, difficuteList.get(i));
				addCanvas(card);
			}
		}
		begin_x = 385;
		begin_y = 360;
		Vector<MapCanvas> absurdList = MapControl.getAbsurdMap();
		if (absurdList != null) {
			for (int i = 0; i < absurdList.size(); i++) {
				CardShape card = new CardShape(begin_x + i % Define.MenuPanel.card_col * Define.MenuPanel.card_size
						+ Define.MenuPanel.offset_size * i, begin_y + i / Define.MenuPanel.card_col
						* Define.MenuPanel.card_size, absurdList.get(i));
				addCanvas(card);
			}
		}

		addCanvas(getBackButton());
	}

	public EXButton getBackButton() {
		if (backButton == null) {
			backButton = new EXButton("back", Define.MenuPanel.back_button_x, Define.MenuPanel.back_button_y,
					Define.MenuPanel.back_button_width, Define.MenuPanel.back_button_height);

			backButton.setFgImage(ImageResLoader.getImage(Define.Button.back_fg_path));
			backButton.setBgImage(ImageResLoader.getImage(Define.Button.bg_path));
			backButton.setBgrImage(ImageResLoader.getImage(Define.Button.bg_r_path));
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
