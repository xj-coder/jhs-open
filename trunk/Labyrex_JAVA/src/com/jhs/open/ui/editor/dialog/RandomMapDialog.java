package com.jhs.open.ui.editor.dialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.jhs.open.Define;
import com.jhs.open.bean.MapBean;
import com.jhs.open.tool.MapFactory;

public class RandomMapDialog extends JDialog {
	private static final long serialVersionUID = -1690484169982980378L;

	private JTextField heightTextField;
	private JTextField widthTextField;
	private JTextField emitterNumTextField;
	private JTextField ratioTextField;
	private JButton okButton;
	private JButton cancelButton;

	private MapBean map;

	public RandomMapDialog(JFrame parent) {
		super(parent);
		initUI();
	}

	private void initUI() {
		setTitle("随即地图参数设置");
		setBounds(100, 100, 290, 235);
		setModal(true);

		int _x = getParent().getX() + (getParent().getWidth() - getWidth()) / 2;
		int _y = getParent().getY() + (getParent().getHeight() - getHeight()) / 2;

		setLocation(_x, _y);

		getContentPane().setLayout(new BorderLayout());

		JPanel contentPanel = new JPanel();
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setLayout(null);

		JLabel label = new JLabel("宽度：");
		label.setBounds(10, 10, 90, 28);
		contentPanel.add(label);
		contentPanel.add(getWidthTextField());

		JLabel label_1 = new JLabel("高度：");
		label_1.setBounds(10, 48, 90, 28);
		contentPanel.add(label_1);
		contentPanel.add(getHeightTextField());

		JLabel label_2 = new JLabel("发射器数量：");
		label_2.setBounds(10, 86, 90, 28);
		contentPanel.add(label_2);
		contentPanel.add(getEmitterNumTextField());

		JLabel label_3 = new JLabel("难度系数：");
		label_3.setBounds(10, 124, 90, 28);
		contentPanel.add(label_3);
		contentPanel.add(getRatioTextField());

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		buttonPane.add(getOkButton());
		getRootPane().setDefaultButton(getOkButton());

		buttonPane.add(getCancelButton());

		getContentPane().add(contentPanel, BorderLayout.CENTER);
	}

	public JTextField getHeightTextField() {
		if (heightTextField == null) {
			heightTextField = new JTextField();
			heightTextField.setBounds(110, 49, 143, 28);
		}
		return heightTextField;
	}

	public JTextField getWidthTextField() {
		if (widthTextField == null) {
			widthTextField = new JTextField();
			widthTextField.setBounds(110, 11, 143, 28);
		}
		return widthTextField;
	}

	public JTextField getEmitterNumTextField() {
		if (emitterNumTextField == null) {
			emitterNumTextField = new JTextField();
			emitterNumTextField.setBounds(110, 87, 143, 27);
			emitterNumTextField.setColumns(10);
		}
		return emitterNumTextField;
	}

	public JTextField getRatioTextField() {
		if (ratioTextField == null) {
			ratioTextField = new JTextField();
			ratioTextField.setBounds(110, 124, 143, 28);
			ratioTextField.setColumns(10);
		}
		return ratioTextField;
	}

	public JButton getOkButton() {
		if (okButton == null) {
			okButton = new JButton("OK");

			okButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					String result = checkVaild();
					if (result.equals("")) {
						int width = Integer.parseInt(getWidthTextField().getText());
						int height = Integer.parseInt(getHeightTextField().getText());
						int emitterNum = Integer.parseInt(getEmitterNumTextField().getText());
						// int ratio = Integer.parseInt(getRatioTextField().getText());
						MapBean map = MapFactory.getRandomMap(width, height, emitterNum, 0);
						if (map == null) {
							JOptionPane.showMessageDialog(null, "无法生成关卡");
						} else {
							JOptionPane.showMessageDialog(null, "生成成功");
							setMap(map);
							setVisible(false);
						}
					} else {
						JOptionPane.showMessageDialog(null, result);
					}
				}
			});
		}
		return okButton;
	}

	public JButton getCancelButton() {
		if (cancelButton == null) {
			cancelButton = new JButton("Cancel");

			cancelButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					setVisible(false);
				}
			});
		}
		return cancelButton;
	}

	public MapBean getMap() {
		return map;
	}

	public void setMap(MapBean map) {
		this.map = map;
	}

	private String checkVaild() {
		return emitterNumCheckValid() + wAhCheckValid();
	}

	private String emitterNumCheckValid() {

		String emitterNum = getEmitterNumTextField().getText();

		if (emitterNum == null || emitterNum.trim().equals("")) {
			return "发射器数量不能为空\n";
		}

		try {
			if (Integer.parseInt(emitterNum) < 1) {
				return "发射器数量必须大于1\n";
			}
		} catch (Exception e) {
			return "发射器数量必须为数字\n";
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
