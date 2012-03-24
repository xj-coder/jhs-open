package c.city.desolate.ui.canvas.game;

import c.city.desolate.Define;
import c.city.desolate.tool.GraphicsTools;
import c.city.desolate.tool.ImageResLoader;
import c.city.desolate.ui.Canvas;
import c.city.desolate.ui.shape.EXButton;

import java.awt.*;

public class ToolCanvas extends Canvas {

	private static final long serialVersionUID = 1115121122104407235L;

	private EXButton lockButton;
	private EXButton unlockButton;

	public ToolCanvas(int x, int y, int width, int height) {
		super(x, y, width, height);

		addCanvas(getLockButton());
		addCanvas(getUnlockButton());
	}

	public EXButton getLockButton() {
		if (lockButton == null) {
			lockButton = new EXButton("lock", true, Define.GamePanel.Tool.lock_button_x,
					Define.GamePanel.Tool.lock_button_y, Define.GamePanel.Tool.lock_button_width,
					Define.GamePanel.Tool.lock_button_height);

			lockButton.setFgImage(ImageResLoader.getImage(Define.Button.lock_bg_path));
			lockButton.setLineImage(ImageResLoader.getImage(Define.Button.lock_line_path));
		}
		return lockButton;
	}

	public EXButton getUnlockButton() {
		if (unlockButton == null) {
			unlockButton = new EXButton("unlock", true, Define.GamePanel.Tool.unlock_button_x,
					Define.GamePanel.Tool.unlock_button_y, Define.GamePanel.Tool.unlock_button_width,
					Define.GamePanel.Tool.unlock_button_height);

			unlockButton.setFgImage(ImageResLoader.getImage(Define.Button.unlock_bg_path));
			unlockButton.setLineImage(ImageResLoader.getImage(Define.Button.unlock_line_path));
		}
		return unlockButton;
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
