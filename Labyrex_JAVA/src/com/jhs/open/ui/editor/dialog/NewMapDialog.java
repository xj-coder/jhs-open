package com.jhs.open.ui.editor.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import com.jhs.open.Define;
import com.jhs.open.bean.GroupBean;
import com.jhs.open.bean.MapBean;
import com.jhs.open.control.MapControl;
import com.jhs.open.ui.LabyrexMapEditorFrame;

public class NewMapDialog extends JDialog {
	private static final long serialVersionUID = 8696397227856713392L;

	private JTextField nameTextField;
	private JTextField widthTextField;
	private JTextField heightTextField;
	private JComboBox groupComboBox;
	private JButton okButton;
	private JButton cancelButton;
	private JCheckBox visibleCheckBox;

	public NewMapDialog(JFrame parent) {
		super(parent);
		initUI();
	}

	private void initUI() {
		setModal(true);
		setTitle("新建关卡");
		setSize(325, 270);

		int _x = getParent().getX() + (getParent().getWidth() - getWidth()) / 2;
		int _y = getParent().getY() + (getParent().getHeight() - getHeight()) / 2;

		setLocation(_x, _y);

		getContentPane().setLayout(new BorderLayout(10, 10));

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "", TitledBorder.LEADING,
				TitledBorder.TOP, null, new Color(0, 0, 0)));
		getContentPane().add(panel);
		panel.setLayout(new GridLayout(0, 2, 10, 10));

		JLabel label = new JLabel("关卡名：");
		panel.add(label);

		panel.add(getNameTextField());

		JLabel label_1 = new JLabel("分类：");
		panel.add(label_1);

		panel.add(getGroupComboBox());

		JLabel label_2 = new JLabel("宽度：");
		panel.add(label_2);

		panel.add(getWidthTextField());

		JLabel label_3 = new JLabel("高度：");
		panel.add(label_3);

		panel.add(getHeightTextField());

		JLabel label_8 = new JLabel("");
		panel.add(label_8);

		panel.add(getVisibleCheckBox());
		panel.add(getCancelButton());
		panel.add(getOkButton());
	}

	public JTextField getNameTextField() {
		if (nameTextField == null) {
			nameTextField = new JTextField();
		}
		return nameTextField;
	}

	public JTextField getHeightTextField() {
		if (heightTextField == null) {
			heightTextField = new JTextField();
		}
		return heightTextField;
	}

	public JTextField getWidthTextField() {
		if (widthTextField == null) {
			widthTextField = new JTextField();
		}
		return widthTextField;
	}

	public JButton getOkButton() {
		if (okButton == null) {
			okButton = new JButton("确定");

			okButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					String result = checkValid();
					if (!result.equals("")) {
						JOptionPane.showMessageDialog(null, result);
					} else {
						GroupBean group = (GroupBean) getGroupComboBox().getSelectedItem();

						MapBean mapBean = new MapBean();
						mapBean.name = getNameTextField().getText();
						mapBean.width = Integer.parseInt(getWidthTextField().getText());
						mapBean.height = Integer.parseInt(getWidthTextField().getText());
						mapBean.isEnabled = getVisibleCheckBox().isSelected();
						mapBean.sort = group.getMaxSort() + 1;
						mapBean.doChange();

						group.addMap(mapBean);
						group.doChange();

						DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode) LabyrexMapEditorFrame.gi().getTree()
								.getModel().getRoot();
						for (int i = 0; i < rootNode.getChildCount(); i++) {
							DefaultMutableTreeNode groupNode = (DefaultMutableTreeNode) rootNode.getChildAt(i);

							if (groupNode.getUserObject().equals(group)) {
								int j = 0;
								for (; j < groupNode.getChildCount(); j++) {
									DefaultMutableTreeNode mapNode = (DefaultMutableTreeNode) groupNode.getChildAt(j);
									MapBean map = (MapBean) mapNode.getUserObject();
									if (map.sort > mapBean.sort) {
										DefaultMutableTreeNode newMapNode = new DefaultMutableTreeNode(mapBean);
										groupNode.insert(newMapNode, j);

										LabyrexMapEditorFrame.gi().getTree().setSelectionPath(
												new TreePath(newMapNode.getPath()));
										break;
									}
								}
								if (j >= groupNode.getChildCount()) {
									DefaultMutableTreeNode newMapNode = new DefaultMutableTreeNode(mapBean);
									groupNode.add(newMapNode);

									LabyrexMapEditorFrame.gi().getTree().setSelectionPath(
											new TreePath(newMapNode.getPath()));
								}
								break;
							}
						}
						LabyrexMapEditorFrame.gi().getTree().updateUI();

						setVisible(false);
					}
				}
			});
		}
		return okButton;
	}

	public JButton getCancelButton() {
		if (cancelButton == null) {
			cancelButton = new JButton("取消");
			cancelButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					setVisible(false);
				}
			});
		}
		return cancelButton;
	}

	public JCheckBox getVisibleCheckBox() {
		if (visibleCheckBox == null) {
			visibleCheckBox = new JCheckBox("初始可见性");
		}
		return visibleCheckBox;
	}

	public JComboBox getGroupComboBox() {
		if (groupComboBox == null) {
			groupComboBox = new JComboBox();

			for (int i = 0; i < MapControl.getGroupCount(); i++) {
				GroupBean key = MapControl.getGroup(i);
				groupComboBox.addItem(key);
			}

			if (LabyrexMapEditorFrame.gi().getTree().getSelectionPath() != null) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) LabyrexMapEditorFrame.gi().getTree()
						.getSelectionPath().getLastPathComponent();
				Object obj = node.getUserObject();
				if (obj instanceof GroupBean) {
					getGroupComboBox().setSelectedItem(obj);
				} else if (obj instanceof MapBean) {
					MapBean map = (MapBean) obj;
					getGroupComboBox().setSelectedItem(map.getGroup());
				}
			} else {
				getGroupComboBox().setSelectedIndex(0);
			}
		}
		return groupComboBox;
	}

	private String checkValid() {
		return nameCheckValid() + wAhCheckValid();
	}

	private String nameCheckValid() {
		String name = getNameTextField().getText();
		if (name == null || name.trim().equals("")) {
			return "关卡名不能为空\n";
		}

		GroupBean group = (GroupBean) getGroupComboBox().getSelectedItem();

		MapBean map = group.getMap(name);

		if (map != null) {
			return "关卡名已存在\n";
		}

		return "";
	}

	private String wAhCheckValid() {

		String width = getWidthTextField().getText();
		String height = getHeightTextField().getText();

		if (width == null || width.trim().equals("")) {
			return "宽度不能为空\n";
		}

		try {
			if (Integer.parseInt(width) < Define.Editor.min_map_width
					|| Integer.parseInt(width) > Define.Editor.max_map_width) {
				return "宽度值必须在 " + Define.Editor.min_map_width + " - " + Define.Editor.max_map_width + " 之间\n";
			}
		} catch (Exception e) {
			return "宽度值必须为数字\n";
		}
		try {
			if (Integer.parseInt(height) < Define.Editor.min_map_height
					|| Integer.parseInt(height) > Define.Editor.max_map_height) {
				return "高度值必须在 " + Define.Editor.min_map_height + " - " + Define.Editor.max_map_height + " 之间\n";
			}
		} catch (Exception e) {
			return "高度值必须为数字\n";
		}

		if (height == null || height.trim().equals("")) {
			return "高度不能为空\n";
		}

		return "";
	}
}
