package c.city.desolate;

import c.city.desolate.ui.LabyrexMapEditorFrame;

import com.jidesoft.plaf.LookAndFeelFactory;

public class MapEditor {
	public static void main(String[] args) {
		LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
		LabyrexMapEditorFrame.gi().setVisible(true);
	}
}
