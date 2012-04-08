package c.city.desolate.ui.shape;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Stroke;

import c.city.desolate.Define;
import c.city.desolate.bean.MapBean;
import c.city.desolate.control.event.card.CardMouseClickedEvent;
import c.city.desolate.control.event.card.CardMouseMoveEvent;
import c.city.desolate.tool.ImageResLoader;
import c.city.desolate.ui.Canvas;

public class CardShape extends Canvas {

	private Image cardWImage;
	private Image cardBImage;

	private MapBean map;

	public CardShape(int x, int y, MapBean map) {
		super(x, y, Define.MenuPanel.card_size, Define.MenuPanel.card_size);
		this.map = map;
	}

	@Override
	public void init() {
		cardWImage = ImageResLoader.getImage(Define.MenuPanel.card_white_image_path);
		cardBImage = ImageResLoader.getImage(Define.MenuPanel.card_black_image_path);

		addMouseListener(new CardMouseClickedEvent(this));
		addMouseListener(new CardMouseMoveEvent());
	}

	public MapBean getMapBean() {
		return map;
	}

	public void setMapBean(MapBean map) {
		this.map = map;
	}

	@Override
	public void render(Graphics g) {
		super.render(g);

		Graphics2D g2 = (Graphics2D) g;
		if (map.isEnabled) {
			g2.drawImage(cardWImage, owner.x + x, owner.y + y, width, height, null);

			if (isOver) {
				Stroke s = g2.getStroke();
				Color c = g2.getColor();

				g2.setStroke(new BasicStroke(Define.MenuPanel.border_size * 3 / 2));
				g2.setColor(new Color(0xff, 0xff, 0xff, 200));
				g2.drawRect(owner.x + x - Define.MenuPanel.border_size, owner.y + y - Define.MenuPanel.border_size,
						width + Define.MenuPanel.border_size, height + Define.MenuPanel.border_size);

				g2.setStroke(s);
				g2.setColor(c);
			}
		} else {
			g2.drawImage(cardBImage, owner.x + x, owner.y + y, Define.MenuPanel.card_size, Define.MenuPanel.card_size,
					null);
		}
	}
}
