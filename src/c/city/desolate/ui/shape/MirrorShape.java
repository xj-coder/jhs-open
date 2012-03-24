package c.city.desolate.ui.shape;

import c.city.desolate.Define;
import c.city.desolate.control.event.map.MirrorMouseClickedAdapter;
import c.city.desolate.control.event.map.MouseMoveOnMapAdapter;
import c.city.desolate.tool.GraphicsTools;
import c.city.desolate.tool.ImgSelector;
import c.city.desolate.ui.Canvas;

import java.awt.*;

/**
 * 挡板
 * 
 * @author JHS
 * 
 */
public class MirrorShape extends Canvas {
	public static final String LEFT = "L";
	public static final String RIGHT = "R";

	public String type;// 类型，左挡板和右挡板两种，以挡板头所指的方向为准

	public boolean isLock = false;

	public MirrorShape(int x, int y, int width, int height) {
		super(x, y, width, height);

		initEvent();
	}

	private void initEvent() {
		addMouseListener(new MouseMoveOnMapAdapter(this));
		addMouseListener(new MirrorMouseClickedAdapter(this));
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(ImgSelector.mirrorSelector(this), owner.x + x, owner.y + y, Define.Main.grid_size,
				Define.Main.grid_size, null);

		if (isLock) {
			GraphicsTools.backupGraphics(g);
			g.setColor(Color.white);
			g.fillOval(owner.x + x + Define.Main.grid_size / 4, owner.y + y + Define.Main.grid_size / 4,
					Define.Main.grid_size / 2, Define.Main.grid_size / 2);
			GraphicsTools.restoreGraphics(g);
		}

		super.render(g);
	}
}
