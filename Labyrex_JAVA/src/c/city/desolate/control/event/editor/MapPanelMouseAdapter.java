package c.city.desolate.control.event.editor;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import c.city.desolate.ui.editor.MapEditorPanel;

public class MapPanelMouseAdapter extends MouseAdapter {

	private MapEditorPanel editorPanel;

	public MapPanelMouseAdapter(MapEditorPanel editorPanel) {
		this.editorPanel = editorPanel;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		editorPanel.setMouseIn(true);
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
