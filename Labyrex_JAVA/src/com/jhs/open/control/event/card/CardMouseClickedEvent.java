package com.jhs.open.control.event.card;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.jhs.open.control.GameControl;
import com.jhs.open.ui.shape.CardShape;

public class CardMouseClickedEvent extends MouseAdapter {
	private CardShape card;

	public CardMouseClickedEvent(CardShape card) {
		this.card = card;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (card.getMapBean().isEnabled) {
			GameControl.gi().setCurrMap(card.getMapBean());
			GameControl.gi().setCurrCanvasIndex(GameControl.G_Game);
		}
	}
}
