package com.jhs.open.client.ui.ex.border;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.border.Border;

/**
 * 扩展的边框3
 * 
 * @author JHS
 * 
 */
public class EXBorder3 implements Border {

	@Override
	public Insets getBorderInsets(Component c) {
		return null;
	}

	@Override
	public boolean isBorderOpaque() {
		return false;
	}

	@Override
	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {

	}

}
