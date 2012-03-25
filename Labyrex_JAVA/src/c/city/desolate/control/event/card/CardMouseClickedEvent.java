package c.city.desolate.control.event.card;

import c.city.desolate.control.GameControl;
import c.city.desolate.ui.shape.CardShape;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CardMouseClickedEvent extends MouseAdapter {
	private CardShape card;

	public CardMouseClickedEvent(CardShape card) {
		this.card = card;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		GameControl.gi().setCurrCanvas(GameControl.G_Game);
		GameControl.gi().setCurrMapName(card.getMapCanvas().name);
		GameControl.gi().resetMap();
	}
}
