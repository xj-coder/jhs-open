package com.jhs.open.client.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import com.jhs.open.base.bean.User;
import com.jhs.open.base.tool.ScreenTools;
import com.jhs.open.client.Define;
import com.jhs.open.client.control.GameControl;
import com.jhs.open.client.control.SocketControl;
import com.jhs.open.client.ui.dialog.ConnectDialog;
import com.jhs.open.client.ui.dialog.DialogFactory;
import com.jhs.open.client.ui.ex.frame.EXFrame;

/**
 * 登入界面
 * 
 * @author JHS
 * 
 */
public class LoginFrame extends EXFrame {

	private static final long serialVersionUID = 4497418206389876652L;
	private JTabbedPane tabbedPane;
	private JPanel buttonPanel;
	private JPanel panel_1;
	private JPanel panel_2;
	private JButton connectSetButton;
	private JButton loginButton;
	private JLabel lblNewLabel;
	private JTextField showNameTextField;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JTextField userNameTextField;
	private JPasswordField passwordField;
	private JCheckBox memoryCheckBox;

	private static LoginFrame gi;

	public static LoginFrame gi() {
		if (gi == null) {
			gi = new LoginFrame();
		}
		return gi;
	}

	public void showUI() {
		setVisible(true);
	}

	private LoginFrame() {
		super();
		initGUI();
		initEvent();
	}

	private void initEvent() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				GameControl.gi().exit();
			}
		});
	}

	private void initGUI() {
		setSize(Define.LoginFrame.WIDTH, Define.LoginFrame.HEIGHT);
		ScreenTools.moveToConter(this);
		setResizable(false);
		setTitle("三国杀-登入");

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		getContentPane().add(getTabbedPane(), BorderLayout.CENTER);
		getContentPane().add(getButtonPanel(), BorderLayout.SOUTH);
	}

	public JTabbedPane getTabbedPane() {
		if (tabbedPane == null) {
			tabbedPane = new JTabbedPane(JTabbedPane.TOP);
			tabbedPane.addTab("游客模式", null, getPanel_1(), null);
			tabbedPane.setEnabledAt(0, true);
			tabbedPane.addTab("用户模式", null, getPanel_2(), null);
		}
		return tabbedPane;
	}

	public JPanel getButtonPanel() {
		if (buttonPanel == null) {
			buttonPanel = new JPanel();
			buttonPanel.setAlignmentY(10.0f);
			buttonPanel.setAlignmentX(10.0f);
			buttonPanel.setLayout(new BorderLayout(10, 10));
			buttonPanel.add(getConnectSetButton(), BorderLayout.WEST);
			buttonPanel.add(getLoginButton(), BorderLayout.EAST);
		}
		return buttonPanel;
	}

	public JPanel getPanel_1() {
		if (panel_1 == null) {
			panel_1 = new JPanel();
			panel_1.setLayout(null);
			panel_1.add(getLblNewLabel());
			panel_1.add(getShowNameTextField());
		}
		return panel_1;
	}

	public JPanel getPanel_2() {
		if (panel_2 == null) {
			panel_2 = new JPanel();
			panel_2.setLayout(null);
			panel_2.add(getLblNewLabel_1());
			panel_2.add(getLblNewLabel_2());
			panel_2.add(getUserNameTextField());
			panel_2.add(getPasswordField());
			panel_2.add(getMemoryCheckBox());
		}
		return panel_2;
	}

	public JButton getConnectSetButton() {
		if (connectSetButton == null) {
			connectSetButton = new JButton("连接设置");
			connectSetButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					new ConnectDialog().setVisible(true);
				}
			});
		}
		return connectSetButton;
	}

	public JButton getLoginButton() {
		if (loginButton == null) {
			loginButton = new JButton("登入");
			loginButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					int selectTabIndex = getTabbedPane().getSelectedIndex();
					switch (selectTabIndex) {
					case 0:
						String result = checkShowName();
						if (result.equals("")) {
							String name = getShowNameTextField().getText();

							User user = SocketControl.gi().visitorLogin(name);
							if (user != null) {
								GameControl.gi().setUser(user);
								setVisible(false);
								HallFrame.gi().showUI();
							}
						} else {
							DialogFactory.showMessage(null, result);
						}
						break;
					case 1:

						break;
					}
				}
			});
		}
		return loginButton;
	}

	public JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("昵    称：");
			lblNewLabel.setBounds(10, 61, 76, 15);
		}
		return lblNewLabel;
	}

	public JTextField getShowNameTextField() {
		if (showNameTextField == null) {
			showNameTextField = new JTextField();
			showNameTextField.setBounds(96, 58, 204, 21);
			showNameTextField.setColumns(10);
		}
		return showNameTextField;
	}

	public JLabel getLblNewLabel_1() {
		if (lblNewLabel_1 == null) {
			lblNewLabel_1 = new JLabel("用户名：");
			lblNewLabel_1.setBounds(10, 29, 54, 15);
		}
		return lblNewLabel_1;
	}

	public JLabel getLblNewLabel_2() {
		if (lblNewLabel_2 == null) {
			lblNewLabel_2 = new JLabel("密   码：");
			lblNewLabel_2.setBounds(10, 73, 54, 15);
		}
		return lblNewLabel_2;
	}

	public JTextField getUserNameTextField() {
		if (userNameTextField == null) {
			userNameTextField = new JTextField();
			userNameTextField.setBounds(74, 26, 226, 21);
			userNameTextField.setColumns(10);
		}
		return userNameTextField;
	}

	public JPasswordField getPasswordField() {
		if (passwordField == null) {
			passwordField = new JPasswordField();
			passwordField.setBounds(74, 70, 226, 21);
		}
		return passwordField;
	}

	public JCheckBox getMemoryCheckBox() {
		if (memoryCheckBox == null) {
			memoryCheckBox = new JCheckBox("记住密码");
			memoryCheckBox.setBounds(227, 102, 73, 23);
		}
		return memoryCheckBox;
	}

	private String checkShowName() {
		String showName = getShowNameTextField().getText();
		if (showName == null || showName.trim().equals("")) {
			return "昵称不能为空";
		}
		return "";
	}
}
