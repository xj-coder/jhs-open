package c.city.desolate.ui.shape;

import c.city.desolate.Define;
import c.city.desolate.control.event.map.MouseMoveOnMapAdapter;
import c.city.desolate.control.event.map.ReceiverMouseClickedAdapter;
import c.city.desolate.tool.ImgSelector;
import c.city.desolate.ui.Canvas;

import java.awt.*;

/**
 * 接收器
 * 
 * @author JHS
 * 
 */
public class ReceiverShape extends Canvas {
	public String type;// 类型，不同颜色的发射器就是不同类型

	public ReceiverShape(int x, int y, int width, int height) {
		super(x, y, width, height);
		initEvent();
	}

	private void initEvent() {
		addMouseListener(new MouseMoveOnMapAdapter(this));
		addMouseListener(new ReceiverMouseClickedAdapter(this));
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(ImgSelector.receiverSelector(this), owner.x + x, owner.y + y, Define.Main.grid_size,
				Define.Main.grid_size, null);

		super.render(g);
	}
}
