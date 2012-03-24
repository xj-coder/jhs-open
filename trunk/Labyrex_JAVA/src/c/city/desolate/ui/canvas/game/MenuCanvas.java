package c.city.desolate.ui.canvas.game;

import c.city.desolate.Define;
import c.city.desolate.control.GameControl;
import c.city.desolate.tool.GraphicsTools;
import c.city.desolate.tool.ImageResLoader;
import c.city.desolate.ui.Canvas;
import c.city.desolate.ui.shape.EXButton;

import java.awt.*;

public class MenuCanvas extends Canvas {
	private static final long serialVersionUID = -5451216572057652277L;

	private EXButton resetButton;
	private EXButton musicButton;
	private EXButton soundButton;
	private EXButton menuButton;

	public MenuCanvas(int x, int y, int width, int height) {
		super(x, y, width, height);

		addCanvas(getMenuButton());
		addCanvas(getMusicButton());
		addCanvas(getSoundButton());
		addCanvas(getResetButton());
	}

	public EXButton getResetButton() {
		if (resetButton == null) {
			resetButton = new EXButton("reset", Define.GamePanel.Menu.reset_button_x,
					Define.GamePanel.Menu.reset_button_y, Define.GamePanel.Menu.reset_button_width,
					Define.GamePanel.Menu.reset_button_height);

			resetButton.setFgImage(ImageResLoader.getImage(Define.Button.reset_bg_path));
		}
		return resetButton;
	}

	public EXButton getMusicButton() {
		if (musicButton == null) {
			musicButton = new EXButton("music", true, Define.GamePanel.Menu.music_button_x,
					Define.GamePanel.Menu.music_button_y, Define.GamePanel.Menu.music_button_width,
					Define.GamePanel.Menu.music_button_height);

			musicButton.setFgImage(ImageResLoader.getImage(Define.Button.music_bg_path));

			musicButton.setSelected(!GameControl.gi().isMusic());
		}
		return musicButton;
	}

	public EXButton getSoundButton() {
		if (soundButton == null) {
			soundButton = new EXButton("sound", true, Define.GamePanel.Menu.sound_button_x,
					Define.GamePanel.Menu.sound_button_y, Define.GamePanel.Menu.sound_button_width,
					Define.GamePanel.Menu.sound_button_height);

			soundButton.setFgImage(ImageResLoader.getImage(Define.Button.sound_bg_path));
			soundButton.setSelected(!GameControl.gi().isSound());
		}
		return soundButton;
	}

	public EXButton getMenuButton() {
		if (menuButton == null) {
			menuButton = new EXButton("menu", Define.GamePanel.Menu.menu_button_x, Define.GamePanel.Menu.menu_button_y,
					Define.GamePanel.Menu.menu_button_width, Define.GamePanel.Menu.menu_button_height);

			menuButton.setFgImage(ImageResLoader.getImage(Define.Button.menu_bg_path));
		}
		return menuButton;
	}

	@Override
	public void render(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		GraphicsTools.backupGraphics(g2);

		g2.setColor(Color.gray);
		g2.fillRect(x, y, width, height);
		g2.setColor(Color.white);
		g2.setStroke(new BasicStroke(3));
		g2.drawRect(x, y, width, height);

		GraphicsTools.restoreGraphics(g2);
		super.render(g);
	}
}
