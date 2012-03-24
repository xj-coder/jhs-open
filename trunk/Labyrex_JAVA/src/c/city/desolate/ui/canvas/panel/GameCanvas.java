package c.city.desolate.ui.canvas.panel;

import c.city.desolate.Define;
import c.city.desolate.control.GameControl;
import c.city.desolate.tool.ImageResLoader;
import c.city.desolate.ui.Canvas;
import c.city.desolate.ui.canvas.game.InfoCanvas;
import c.city.desolate.ui.canvas.game.MenuCanvas;
import c.city.desolate.ui.canvas.game.ToolCanvas;

import java.awt.*;

/**
 * 游戏界面
 * 
 * @author JHS
 * 
 */
public class GameCanvas extends Canvas {
	private InfoCanvas infoCanvas;
	private MenuCanvas menuCanvas;
	private ToolCanvas toolCanvas;

	private int bgColorR = 0xFF;
	private int bgColorG = 0xFF;
	private int bgColorB = 0xFF;

	private Image bgImage;

	public GameCanvas() {
		super(0, 0, Define.Main.width, Define.Main.height);

		bgImage = ImageResLoader.getImage(Define.GamePanel.bg_image_path);

		GameControl.gi().getCurrMap().init();

		addCanvas(GameControl.gi().getCurrMap());
		addCanvas(getInfoCanvas());
		addCanvas(getMenuCanvas());
		addCanvas(getToolCanvas());
	}

	public InfoCanvas getInfoCanvas() {
		if (infoCanvas == null) {
			infoCanvas = new InfoCanvas(0, 0, Define.GamePanel.info_canvas_width, Define.GamePanel.info_canvas_height);
		}
		return infoCanvas;
	}

	public MenuCanvas getMenuCanvas() {
		if (menuCanvas == null) {
			menuCanvas = new MenuCanvas(Define.GamePanel.menu_canvas_x, Define.GamePanel.menu_canvas_y,
					Define.GamePanel.menu_canvas_width, Define.GamePanel.menu_canvas_height);
		}
		return menuCanvas;
	}

	public ToolCanvas getToolCanvas() {
		if (toolCanvas == null) {
			toolCanvas = new ToolCanvas(Define.GamePanel.tool_canvas_x, Define.GamePanel.tool_canvas_y,
					Define.GamePanel.tool_canvas_width, Define.GamePanel.tool_canvas_height);
		}
		return toolCanvas;
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