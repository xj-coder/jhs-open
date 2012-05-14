package com.jhs.open.control.event.editor;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.jhs.open.ui.editor.MapEditorPanel;

public class MapPanelMouseAdapter extends MouseAdapter {

	private final MapEditorPanel editorPanel;

	public MapPanelMouseAdapter(MapEditorPanel editorPanel) {
		this.editorPanel = editorPanel;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		editorPanel.setMouseDrag(true);

		editorPanel.setMouseX(e.getX());
		editorPanel.setMouseY(e.getY());
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		editorPanel.setMouseDrag(false);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			editorPanel.mouseLeftClicked();
		} else if (e.getButton() == MouseEvent.BUTTON3) {
			editorPanel.mouseRightClicked();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		editorPanel.setMouseIn(true);
		editorPanel.requestFocus();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		editorPanel.setMouseIn(false);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		editorPanel.setMouseX(e.getX());
		editorPanel.setMouseY(e.getY());
	}
}
