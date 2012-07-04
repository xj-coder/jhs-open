package com.jhs.open.client.ui.ex.frame;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 扩展的JFrame，用在大厅、房间、游戏等窗体上
 * 
 * @author JHS
 * 
 */
public class EXFrame extends JFrame {

	private static final long serialVersionUID = -4978094874003804338L;
	private JPanel northPanel;
	private JPanel centerPanel;
	private JLabel headInfoLabel;

	public EXFrame() {
		initGUI();
	}

	private void initGUI() {
		getContentPane().setLayout(new BorderLayout(0, 0));
		getContentPane().add(getNorthPanel(), BorderLayout.NORTH);
		getContentPane().add(getCenterPanel(), BorderLayout.CENTER);
	}

	public JPanel getNorthPanel() {
		if (northPanel == null) {
			northPanel = new JPanel();
			northPanel.setLayout(new BorderLayout(0, 0));
			northPanel.add(getHeadInfoLabel(), BorderLayout.CENTER);
		}
		return northPanel;
	}

	public JPanel getCenterPanel() {
		if (centerPanel == null) {
			centerPanel = new JPanel();
			centerPanel.setLayout(null);
		}
		return centerPanel;
	}

	public JLabel getHeadInfoLabel() {
		if (headInfoLabel == null) {
			headInfoLabel = new JLabel("三国杀");
		}
		return headInfoLabel;
	}
}
