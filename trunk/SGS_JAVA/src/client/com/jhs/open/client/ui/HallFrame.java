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
import com.jhs.open.client.ui.hall.FunctionPanel;
import com.jhs.open.client.ui.hall.InfoPanel;
import com.jhs.open.client.ui.hall.RoomListPanel;
import com.jhs.open.client.ui.hall.UserListPanel;

/**
 * 大厅界面
 * 
 * @author JHS
 * 
 */
public class HallFrame extends EXFrame {

	private static final long serialVersionUID = 1936691624909048784L;

	private static HallFrame gi;

	private ADPanel adPanel;
	private RoomListPanel roomListPanel;
	private MultiChatPanel multiChatPanel;
	private FunctionPanel functionPanel;
	private InfoPanel infoPanel;
	private UserListPanel userListPanel;

	public static HallFrame gi() {
		if (gi == null) {
			gi = new HallFrame();
		}
		return gi;
	}

	public void showUI() {
		setVisible(true);
	}

	private HallFrame() {
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
		setTitle("三国杀-游戏大厅");

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		setSize(Define.HallFrame.WIDTH, Define.HallFrame.HEIGHT);
		ScreenTools.moveToConter(this);
		getCenterPanel().add(getAdPanel());
		getCenterPanel().add(getRoomListPanel());
		getCenterPanel().add(getMultiChatPanel());
		getCenterPanel().add(getFunctionPanel());
		getCenterPanel().add(getInfoPanel());
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

	public RoomListPanel getRoomListPanel() {
		if (roomListPanel == null) {
			roomListPanel = new RoomListPanel();
		}
		return roomListPanel;
	}

	public MultiChatPanel getMultiChatPanel() {
		if (multiChatPanel == null) {
			multiChatPanel = new MultiChatPanel();
			multiChatPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
			multiChatPanel.setBounds(170, 520, 340, 190);
		}
		return multiChatPanel;
	}

	public FunctionPanel getFunctionPanel() {
		if (functionPanel == null) {
			functionPanel = new FunctionPanel();
			functionPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
			functionPanel.setBounds(520, 520, 310, 190);
		}
		return functionPanel;
	}

	public InfoPanel getInfoPanel() {
		if (infoPanel == null) {
			infoPanel = new InfoPanel();
			infoPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
			infoPanel.setBounds(840, 10, 168, 300);
		}
		return infoPanel;
	}

	public UserListPanel getUserListPanel() {
		if (userListPanel == null) {
			userListPanel = new UserListPanel();
			userListPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
			userListPanel.setBounds(840, 320, 168, 390);
		}
		return userListPanel;
	}
}
