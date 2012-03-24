package c.city.desolate.ui.shape;

import c.city.desolate.Define;
import c.city.desolate.control.event.card.CardMouseClickedEvent;
import c.city.desolate.control.event.card.CardMouseMoveEvent;
import c.city.desolate.tool.ImageResLoader;
import c.city.desolate.ui.Canvas;
import c.city.desolate.ui.canvas.game.MapCanvas;

import java.awt.*;

public class CardShape extends Canvas {

	private Image cardWImage;
	private Image cardBImage;

	private MapCanvas mapCanvas;

	public CardShape(int x, int y, MapCanvas mapCanvas) {
		super(x, y, Define.MenuPanel.card_size, Define.MenuPanel.card_size);
		this.mapCanvas = mapCanvas;

		cardWImage = ImageResLoader.getImage(Define.MenuPanel.card_white_image_path);
		cardBImage = ImageResLoader.getImage(Define.MenuPanel.card_black_image_path);

		addMouseListener(new CardMouseClickedEvent(this));
		addMouseListener(new CardMouseMoveEvent());
	}

	public MapCanvas getMapCanvas() {
		return mapCanvas;
	}

	public void setMapCanvas(MapCanvas mapCanvas) {
		this.mapCanvas = mapCanvas;
	}

	@Override
	public void render(Graphics g) {
		super.render(g);

		Graphics2D g2 = (Graphics2D) g;
		if (mapCanvas.isEnabled) {
			g2.drawImage(cardWImage, x, y, width, height, null);

			if (isOver) {
				Stroke s = g2.getStroke();
				Color c = g2.getColor();

				g2.setStroke(new BasicStroke(Define.MenuPanel.border_size * 3 / 2));
				g2.setColor(new Color(0xff, 0xff, 0xff, 200));
				g2.drawRect(x - Define.MenuPanel.border_size, y - Define.MenuPanel.border_size, width
						+ Define.MenuPanel.border_size, height + Define.MenuPanel.border_size);

				g2.setStroke(s);
				g2.setColor(c);
			}
		} else {
			g2.drawImage(cardBImage, x, y, Define.MenuPanel.card_size, Define.MenuPanel.card_size, null);
		}
	}
}
