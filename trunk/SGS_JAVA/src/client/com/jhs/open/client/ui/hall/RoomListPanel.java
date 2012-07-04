package com.jhs.open.client.ui.hall;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import com.jhs.open.base.bean.GamePack;
import com.jhs.open.base.bean.Room;
import com.jhs.open.base.tool.ListOperator;
import com.jhs.open.client.Define;
import com.jhs.open.client.ui.ex.button.HeadButton;

/**
 * 房间列表Panel
 * 
 * @author JHS
 * 
 */

@SuppressWarnings("unchecked")
public class RoomListPanel extends JPanel {
	private static final long serialVersionUID = -9152980016463276741L;

	private ListOperator<Room> roomList = new ListOperator<Room>(new ArrayList<Room>());// 房间列表

	private JPanel toolPanel;
	private JPanel headPanel;
	private JPanel listPanel;

	private int currPage = 0;

	public RoomListPanel() {
		super();
		initGUI();
	}

	private void initGUI() {
		setBounds(Define.HallFrame.room_list_x, Define.HallFrame.room_list_y, Define.HallFrame.room_list_width,
				Define.HallFrame.room_list_height);
		setBorder(new LineBorder(new Color(0, 0, 0)));
		setLayout(null);
		setOpaque(false);

		add(getHeadPanel());
		add(getListPanel());
		add(getToolPanel());
	}

	public JPanel getHeadPanel() {
		if (headPanel == null) {
			headPanel = new JPanel();
			headPanel.setLayout(null);
			headPanel.setOpaque(false);

			headPanel.setBounds(Define.HallFrame.room_list_row_alig_x, Define.HallFrame.room_list_row_alig_y,
					getWidth(), Define.HallFrame.room_list_row_height);

			int x = 0;
			int y = 0;
			for (int i = 0; i < getCol(); i++) {
				HeadButton head = new HeadButton();

				head.setText(getHead(i));
				head.setSize(getColWidth(i), Define.HallFrame.room_list_row_height
						- Define.HallFrame.room_list_row_offset_y - Define.HallFrame.room_list_row_alig_y);
				head.setLocation(x, y);
				x = x + head.getWidth() + Define.HallFrame.room_list_row_offset_x;

				headPanel.add(head);
			}
		}
		return headPanel;
	}

	public JPanel getListPanel() {
		if (listPanel == null) {
			listPanel = new JPanel();

			listPanel.setOpaque(false);
			listPanel.setLayout(null);
			listPanel.setBounds(Define.HallFrame.room_list_row_alig_x, Define.HallFrame.room_list_row_alig_y
					+ Define.HallFrame.room_list_row_height, getWidth(), Define.HallFrame.room_list_row_height * 10);
		}
		return listPanel;
	}

	public JPanel getToolPanel() {
		if (toolPanel == null) {
			toolPanel = new JPanel();

			toolPanel.setOpaque(false);
			toolPanel.setLayout(null);
			toolPanel.setBounds(Define.HallFrame.room_list_row_alig_x, Define.HallFrame.room_list_row_alig_y
					+ Define.HallFrame.room_list_row_height * 11, getWidth(), Define.HallFrame.room_list_row_height);
		}
		return toolPanel;
	}

	public int getCol() {
		return 9;
	}

	public int getColWidth(int index) {
		switch (index) {
		case 0:
			return 60;
		case 1:
			return 100;
		case 2:
			return 60;
		case 3:
			return 60;
		case 4:
			return 90;
		case 5:
			return 80;
		case 6:
			return 60;
		case 7:
			return 60;
		case 8:
			return 60;
		default:
			return 0;
		}
	}

	public String getHead(int index) {
		switch (index) {
		case 0:
			return "房号";
		case 1:
			return "房间名";
		case 2:
			return "模式";
		case 3:
			return "聊天";
		case 4:
			return "扩展包";
		case 5:
			return "出手时间";
		case 6:
			return "人数";
		case 7:
			return "状态";
		case 8:
			return "信息";
		default:
			return "房号";
		}
	}

	public Object getValue(int row, int col) {
		Room room = roomList.get(row);

		switch (col) {
		case 0:
			return room.getNumber();
		case 1:
			return room.getName();
		case 2:
			return room.getModel();
		case 3:
			return room.isChat();
		case 4:
			return room.getPackList();
		case 5:
			return room.getWaitTime();
		case 6:
			return room.getUserList().getCount() + "/" + room.getMaxUserNum();
		case 7:
			return room.getState();
		case 8:
			return room;
		default:
			return "";
		}
	}

	public Component getCellRender(Object value, int index) {
		Component cell = null;
		switch (index) {
		case 0:
			cell = new JLabel(value.toString());
			return cell;
		case 1:
			cell = new JLabel(value.toString());
			return cell;
		case 2:
			cell = new JLabel(value.toString());
			return cell;
		case 3:
			cell = new JLabel(Boolean.parseBoolean(value.toString()) ? "支持" : "不支持");
			return cell;
		case 4:
			ListOperator<GamePack> packs = (ListOperator<GamePack>) value;
			if (packs.getCount() > 0) {
				String tmp = "";
				for (int i = 0; i < packs.getCount(); i++) {
					if (i > 0) {
						tmp = tmp + ",";
					}
					tmp = tmp + packs.get(i).getName();
				}
				cell = new JLabel("无");
			} else {
				cell = new JLabel("无");
			}
			return cell;
		case 5:
			cell = new JLabel(value.toString() + "秒");
			return cell;
		case 6:
			cell = new JLabel(value.toString());
			return cell;
		case 7:
			cell = new JLabel(value.toString());
			return cell;
		case 8:
			cell = new JButton(" - ");
			return cell;
		default:
			cell = new JLabel();
			return cell;
		}

	}

	public ListOperator<Room> getRoomList() {
		return roomList;
	}
}
