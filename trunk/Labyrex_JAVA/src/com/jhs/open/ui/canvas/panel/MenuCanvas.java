package com.jhs.open.ui.canvas.panel;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;

import com.jhs.open.Define;
import com.jhs.open.bean.MapBean;
import com.jhs.open.control.MapControl;
import com.jhs.open.tool.GraphicsTools;
import com.jhs.open.tool.ImageTools;
import com.jhs.open.ui.shape.CrossShape;
import com.jhs.open.ui.shape.EXButton;
import com.jhs.open.ui.shape.GroupShape;

/**
 * 游戏关卡选择界面
 * 
 * @author JHS
 * 
 */
public class MenuCanvas extends FullCanvas {

	private Image bgImage;

	private int bgColorR = 0xFF;
	private int bgColorG = 0xFF;
	private int bgColorB = 0xFF;

	private EXButton backButton;

	private ArrayList<GroupShape> groupList = new ArrayList<GroupShape>();
	private ArrayList<CrossShape> crossList = new ArrayList<CrossShape>();

	private GroupShape currGroupShape;

	public MenuCanvas() {
		super(0, 0, Define.Main.width, Define.Main.height);
		isAnimation = true;
	}

	@Override
	public void init() {
		super.init();

		groupList.clear();

		bgImage = ImageTools.getImage(Define.MenuPanel.bg_image_path);
		addCanvas(getBackButton());

		// 添加分组按钮
		int begin_x = (getWidth() - MapControl.getGroupCount()
				* (Define.MenuPanel.group_card_size + Define.MenuPanel.group_card_offset)) / 2;
		int begin_y = 420;
		for (int i = 0; i < MapControl.getGroupCount(); i++) {
			GroupShape card = new GroupShape(begin_x
					+ (Define.MenuPanel.group_card_size + Define.MenuPanel.group_card_offset) * i, begin_y, MapControl
					.getGroup(i));
			addCanvas(card);
			groupList.add(card);

			if (i == 0) {
				card.isAvtivity = true;
				setCurrGroupShape(card);
			}
		}
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

	public ArrayList<GroupShape> getGroupList() {
		return groupList;
	}

	public void setCurrGroupShape(GroupShape currGroupShape) {
		if (this.currGroupShape != null) {
			this.currGroupShape.isAvtivity = false;
		}
		this.currGroupShape = currGroupShape;

		while (crossList.size() > 0) {
			removeCanvas(crossList.remove(0));
		}

		this.currGroupShape.isAvtivity = true;
		int begin_x = 65;
		int begin_y = 70;
		for (int j = 0; j < this.currGroupShape.getGroup().getMapCount(); j++) {
			MapBean map = this.currGroupShape.getGroup().getMap(j);

			CrossShape cross = new CrossShape(begin_x + j % Define.MenuPanel.cross_card_col
					* (Define.MenuPanel.cross_card_size + Define.MenuPanel.cross_card_offset), begin_y + j
					/ Define.MenuPanel.cross_card_col
					* (Define.MenuPanel.cross_card_offset + Define.MenuPanel.cross_card_size), map);

			addCanvas(cross);
			crossList.add(cross);
		}
	}

	@Override
	public void render() {
		Graphics2D g2 = getGraphics();

		GraphicsTools.backupGraphics(g2);

		Color c = g2.getColor();
		g2.setColor(new Color(bgColorR, bgColorG, bgColorB));
		g2.fillRect(x, y, width, height);
		g2.setColor(c);

		g2.drawImage(bgImage, x, y, width, height, null);

		GraphicsTools.restoreGraphics(g2);

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
