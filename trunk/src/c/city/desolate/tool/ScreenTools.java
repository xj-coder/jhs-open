package c.city.desolate.tool;

import java.awt.*;

public class ScreenTools {
	public static void moveToConter(Component comp) {
		int width = comp.getWidth();
		int height = comp.getHeight();
		int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
		int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;

		comp.setLocation(screenWidth / 2 - width / 2, screenHeight / 2 - height
				/ 2);
	}
}
