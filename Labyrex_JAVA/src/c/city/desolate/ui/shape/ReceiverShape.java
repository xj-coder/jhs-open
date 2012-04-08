package c.city.desolate.ui.shape;

import java.awt.Graphics;
import java.awt.Image;

import c.city.desolate.Define;
import c.city.desolate.bean.ReceiverBean;
import c.city.desolate.ui.Canvas;

/**
 * 接收器
 * 
 * @author JHS
 * 
 */
public class ReceiverShape extends Canvas {
	public ReceiverBean bean;

	public Image bgImage;

	public ReceiverShape(int x, int y, ReceiverBean bean) {
		super(x, y, Define.Main.grid_size, Define.Main.grid_size);
		this.bean = bean;
	}

	@Override
	public void init() {
	}

	@Override
	public void render(Graphics g) {
		if (bgImage != null) {
			g.drawImage(bgImage, owner.x + x, owner.y + y, Define.Main.grid_size, Define.Main.grid_size, null);
			// g.drawImage(ImgSelector.receiverSelector(this), owner.x + x, owner.y + y, Define.Main.grid_size,
			// Define.Main.grid_size, null);
		}

		super.render(g);
	}
}
