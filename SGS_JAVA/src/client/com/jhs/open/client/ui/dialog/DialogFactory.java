package com.jhs.open.client.ui.dialog;

import java.awt.Component;

import javax.swing.JOptionPane;

public class DialogFactory {

	public static void showMessage(Component component, String message) {
		JOptionPane.showMessageDialog(component, message);
	}
}
