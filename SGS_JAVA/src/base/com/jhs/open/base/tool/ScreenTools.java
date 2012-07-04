package com.jhs.open.base.tool;

import java.awt.Component;
import java.awt.Toolkit;

public class ScreenTools {
	public static void moveToConter(Component comp) {
		int width = comp.getWidth();
		int height = comp.getHeight();
		int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
		int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;

		comp.setLocation(screenWidth / 2 - width / 2, screenHeight / 2 - height / 2);
	}

	public static void moveToConter(Component parentComp, Component comp) {
		int width = comp.getWidth();
		int height = comp.getHeight();
		int screenWidth = parentComp.getWidth();
		int screenHeight = parentComp.getHeight();
		int x = parentComp.getX();
		int y = parentComp.getY();

		comp.setLocation(x + screenWidth / 2 - width / 2, y + screenHeight / 2 - height / 2);
	}
}
