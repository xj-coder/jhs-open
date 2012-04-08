package c.city.desolate.ui.shape;

import java.awt.Color;
import java.awt.Graphics;

import c.city.desolate.Define;
import c.city.desolate.bean.MirrorBean;
import c.city.desolate.tool.GraphicsTools;
import c.city.desolate.tool.ImgSelector;
import c.city.desolate.ui.Canvas;

/**
 * 挡板
 * 
 * @author JHS
 * 
 */
public class MirrorShape extends Canvas {
	public MirrorBean bean;

	public MirrorShape(int x, int y, MirrorBean bean) {
		super(x, y, Define.Main.grid_size, Define.Main.grid_size);
		this.bean = bean;
	}

	@Override
	public void init() {
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(ImgSelector.mirrorSelector(this), owner.x + x, owner.y + y, Define.Main.grid_size,
				Define.Main.grid_size, null);

		if (bean.isLock) {
			GraphicsTools.backupGraphics(g);
			g.setColor(Color.white);
			g.fillOval(owner.x + x + Define.Main.grid_size / 4, owner.y + y + Define.Main.grid_size / 4,
					Define.Main.grid_size / 2, Define.Main.grid_size / 2);
			GraphicsTools.restoreGraphics(g);
		}

		super.render(g);
	}
}
