package com.jhs.open.client.ui;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.border.LineBorder;

import com.jhs.open.base.tool.ScreenTools;
import com.jhs.open.client.Define;
import com.jhs.open.client.control.GameControl;
import com.jhs.open.client.ui.ad.ADPanel;
import com.jhs.open.client.ui.chat.MultiChatPanel;
import com.jhs.open.client.ui.ex.frame.EXFrame;
import com.jhs.open.client.ui.room.FunctionPanel;
import com.jhs.open.client.ui.room.UserListPanel;
import com.jhs.open.client.ui.room.UserPanel;

/**
 * 房间界面
 * 
 * @author JHS
 * 
 */
public class RoomFrame extends EXFrame {

	private static final long serialVersionUID = -640342680278070369L;

	private static RoomFrame gi;

	private ADPanel adPanel;
	private UserPanel userPanel;
	private MultiChatPanel multiChatPanel;
	private FunctionPanel functionPanel;
	private UserListPanel userListPanel;

	public static RoomFrame gi() {
		if (gi == null) {
			gi = new RoomFrame();
		}
		return gi;
	}

	private RoomFrame() {
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
		setResizable(false);
		setTitle("三国杀-游戏房间");
		setSize(Define.RoomFrame.WIDTH, Define.RoomFrame.HEIGHT);
		ScreenTools.moveToConter(this);

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		getCenterPanel().add(getAdPanel());
		getCenterPanel().add(getUserPanel());
		getCenterPanel().add(getMultiChatPanel());
		getCenterPanel().add(getFunctionPanel());
		getCenterPanel().add(getUserListPanel());
	}

	public ADPanel getAdPanel() {
		if (adPanel == null) {
			adPanel = new ADPanel();
			adPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
			adPanel.setBounds(10, 10, 150, 700);
		}
		return adPanel;
	}

	public UserPanel getUserPanel() {
		if (userPanel == null) {
			userPanel = new UserPanel();
			userPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
			userPanel.setBounds(170, 10, 660, 520);
		}
		return userPanel;
	}

	public MultiChatPanel getMultiChatPanel() {
		if (multiChatPanel == null) {
			multiChatPanel = new MultiChatPanel();
			multiChatPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
			multiChatPanel.setBounds(170, 540, 360, 170);
		}
		return multiChatPanel;
	}

	public FunctionPanel getFunctionPanel() {
		if (functionPanel == null) {
			functionPanel = new FunctionPanel();
			functionPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
			functionPanel.setBounds(540, 540, 468, 170);
		}
		return functionPanel;
	}

	public UserListPanel getUserListPanel() {
		if (userListPanel == null) {
			userListPanel = new UserListPanel();
			userListPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
			userListPanel.setBounds(840, 10, 168, 520);
		}
		return userListPanel;
	}
}
