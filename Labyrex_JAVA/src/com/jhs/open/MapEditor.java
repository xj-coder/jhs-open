package com.jhs.open;

import com.jhs.open.ui.LabyrexMapEditorFrame;
import com.jidesoft.plaf.LookAndFeelFactory;

public class MapEditor {
	public static void main(String[] args) {
		LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
		LabyrexMapEditorFrame.gi().showFrame();
	}
}
