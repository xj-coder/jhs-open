package com.jhs.open.client.ui;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.border.LineBorder;

import com.jhs.open.base.tool.ScreenTools;
import com.jhs.open.client.Define;
import com.jhs.open.client.control.GameControl;
import com.jhs.open.client.ui.chat.SimpleChatPanel;
import com.jhs.open.client.ui.ex.frame.EXFrame;
import com.jhs.open.client.ui.game.FunctionPanel;
import com.jhs.open.client.ui.game.GamePanel;
import com.jhs.open.client.ui.game.HeadPanel;
import com.jhs.open.client.ui.game.InfoPanel;

/**
 * 游戏界面
 * 
 * @author JHS
 * 
 */
public class GameFrame extends EXFrame {

	private static final long serialVersionUID = 1021454778704542073L;

	private static GameFrame gi;
	private GamePanel gamePanel;
	private InfoPanel infoPanel;
	private SimpleChatPanel simpleChatPanel;
	private HeadPanel headPanel;
	private FunctionPanel functionPanel;

	public static GameFrame gi() {
		if (gi == null) {
			gi = new GameFrame();
		}
		return gi;
	}

	public void showUI() {
		setVisible(true);
	}

	private GameFrame() {
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

		setSize(Define.HallFrame.WIDTH, Define.HallFrame.HEIGHT);
		ScreenTools.moveToConter(this);

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		getCenterPanel().add(getGamePanel());
		getCenterPanel().add(getInfoPanel());
		getCenterPanel().add(getSimpleChatPanel());
		getCenterPanel().add(getHeadPanel());
		getCenterPanel().add(getFunctionPanel());
	}

	public GamePanel getGamePanel() {
		if (gamePanel == null) {
			gamePanel = new GamePanel();
			gamePanel.setBorder(new LineBorder(new Color(0, 0, 0)));
			gamePanel.setBounds(10, 10, 800, 560);
		}
		return gamePanel;
	}

	public InfoPanel getInfoPanel() {
		if (infoPanel == null) {
			infoPanel = new InfoPanel();
			infoPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
			infoPanel.setBounds(820, 10, 188, 397);
		}
		return infoPanel;
	}

	public SimpleChatPanel getSimpleChatPanel() {
		if (simpleChatPanel == null) {
			simpleChatPanel = new SimpleChatPanel();
			simpleChatPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
			simpleChatPanel.setBounds(820, 417, 188, 153);
		}
		return simpleChatPanel;
	}

	public HeadPanel getHeadPanel() {
		if (headPanel == null) {
			headPanel = new HeadPanel();
			headPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
			headPanel.setBounds(820, 576, 188, 151);
		}
		return headPanel;
	}

	public FunctionPanel getFunctionPanel() {
		if (functionPanel == null) {
			functionPanel = new FunctionPanel();
			functionPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
			functionPanel.setBounds(10, 580, 800, 147);
		}
		return functionPanel;
	}
}
