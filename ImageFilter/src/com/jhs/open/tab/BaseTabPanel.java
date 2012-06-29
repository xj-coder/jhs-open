package com.jhs.open.tab;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import com.jhs.open.MainFrame;
import com.jhs.open.filter.SharpenFilter;
import com.jhs.open.filter.SmoothFilter;

/**
 * 
 * @author JHS
 * 
 */
public class BaseTabPanel extends JPanel {
	private static final long serialVersionUID = 2361902037236480426L;

	private JCheckBox carvingCheckBox;
	private JCheckBox pervasionCheckBox;
	private JCheckBox smoothCheckBox;
	private JCheckBox sharpenCheckBox;
	private JTextField carvingRatioTextField;
	private JTextField pervasionRatioTextField;
	private JTextField smoothRatioTextField;
	private JTextField sharpenRatioTextField;

	private SharpenFilter sharpenFilter = new SharpenFilter();
	private SmoothFilter smoothFilter = new SmoothFilter();

	private Action action = new Action();

	public BaseTabPanel() {
		super();
		setName("1");
		setLayout(new GridLayout(0, 1));

		final JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new TitledBorder(null, "锐化", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
				null, null));
		add(panel);

		panel.add(getSharpenCheckBox());
		panel.add(getSharpenRatioTextField());

		final JLabel label = new JLabel();
		label.setText("系数：");
		label.setBounds(75, 25, 39, 26);
		panel.add(label);

		final JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "柔滑", TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, null, null));
		panel_1.setLayout(null);
		add(panel_1);

		panel_1.add(getSmoothCheckBox());
		panel_1.add(getSmoothRatioTextField());

		final JLabel label_1 = new JLabel();
		label_1.setText("系数：");
		label_1.setBounds(75, 25, 39, 26);
		panel_1.add(label_1);

		final JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "扩散", TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, null, null));
		panel_2.setLayout(null);
		add(panel_2);

		panel_2.add(getPervasionCheckBox());
		panel_2.add(getPervasionRatioTextField());

		final JLabel label_2 = new JLabel();
		label_2.setText("系数：");
		label_2.setBounds(75, 25, 39, 26);
		panel_2.add(label_2);

		final JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(null, "雕刻", TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, null, null));
		panel_3.setLayout(null);
		add(panel_3);

		panel_3.add(getCarvingCheckBox());
		panel_3.add(getCarvingRatioTextField());

		final JLabel label_3 = new JLabel();
		label_3.setText("系数：");
		label_3.setBounds(75, 25, 39, 26);
		panel_3.add(label_3);
	}

	public JCheckBox getSharpenCheckBox() {
		if (sharpenCheckBox == null) {
			sharpenCheckBox = new JCheckBox();
			sharpenCheckBox.setBounds(10, 25, 21, 26);
			sharpenCheckBox.setActionCommand("sharpenCheckBox");

			sharpenCheckBox.addActionListener(action);
		}
		return sharpenCheckBox;
	}

	public JTextField getSharpenRatioTextField() {
		if (sharpenRatioTextField == null) {
			sharpenRatioTextField = new JTextField();
			sharpenRatioTextField.setBounds(120, 29, 87, 22);
			sharpenRatioTextField.setActionCommand("sharpenRatioTextField");

			sharpenRatioTextField.addActionListener(action);
		}
		return sharpenRatioTextField;
	}

	public JCheckBox getSmoothCheckBox() {
		if (smoothCheckBox == null) {
			smoothCheckBox = new JCheckBox();
			smoothCheckBox.setBounds(10, 25, 21, 26);
			smoothCheckBox.setActionCommand("smoothCheckBox");

			smoothCheckBox.addActionListener(action);
		}
		return smoothCheckBox;
	}

	public JTextField getSmoothRatioTextField() {
		if (smoothRatioTextField == null) {
			smoothRatioTextField = new JTextField();
			smoothRatioTextField.setBounds(120, 29, 87, 22);
			smoothRatioTextField.setActionCommand("smoothRatioTextField");

			smoothRatioTextField.addActionListener(action);
		}
		return smoothRatioTextField;
	}

	public JCheckBox getPervasionCheckBox() {
		if (pervasionCheckBox == null) {
			pervasionCheckBox = new JCheckBox();
			pervasionCheckBox.setBounds(10, 25, 21, 26);
			pervasionCheckBox.setActionCommand("pervasionCheckBox");

			pervasionCheckBox.addActionListener(action);
		}
		return pervasionCheckBox;
	}

	public JTextField getPervasionRatioTextField() {
		if (pervasionRatioTextField == null) {
			pervasionRatioTextField = new JTextField();
			pervasionRatioTextField.setBounds(120, 29, 87, 22);
			pervasionRatioTextField.setActionCommand("pervasionRatioTextField");

			pervasionRatioTextField.addActionListener(action);
		}
		return pervasionRatioTextField;
	}

	public JCheckBox getCarvingCheckBox() {
		if (carvingCheckBox == null) {
			carvingCheckBox = new JCheckBox();
			carvingCheckBox.setBounds(10, 25, 21, 26);
			carvingCheckBox.setActionCommand("carvingCheckBox");

			carvingCheckBox.addActionListener(action);
		}
		return carvingCheckBox;
	}

	public JTextField getCarvingRatioTextField() {
		if (carvingRatioTextField == null) {
			carvingRatioTextField = new JTextField();
			carvingRatioTextField.setBounds(120, 29, 87, 22);
			carvingRatioTextField.setActionCommand("carvingRatioTextField");

			carvingRatioTextField.addActionListener(action);
		}
		return carvingRatioTextField;
	}

	class Action implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String command = e.getActionCommand();

			BufferedImage targetImage = MainFrame.gi().getTargetImage();
			if (targetImage != null) {

				if (command.equals("sharpenCheckBox")) {
					if (getSharpenCheckBox().isSelected()) {
						sharpenFilter.ratio = Double.parseDouble(getSharpenRatioTextField().getText());
						MainFrame.gi().addFilter(sharpenFilter);
					} else {
						MainFrame.gi().removeFilter(sharpenFilter);
					}
				} else if (command.equals("sharpenRatioTextField")) {
					sharpenFilter.ratio = Double.parseDouble(getSharpenRatioTextField().getText());
					MainFrame.gi().removeFilter(sharpenFilter);
					MainFrame.gi().addFilter(sharpenFilter);
				} else if (command.equals("smoothCheckBox")) {
					if (getSmoothCheckBox().isSelected()) {
						smoothFilter.ratio = Double.parseDouble(getSmoothRatioTextField().getText());
						MainFrame.gi().addFilter(smoothFilter);
					} else {
						MainFrame.gi().removeFilter(smoothFilter);
					}
				} else if (command.equals("smoothRatioTextField")) {
					smoothFilter.ratio = Double.parseDouble(getSmoothRatioTextField().getText());
					MainFrame.gi().removeFilter(smoothFilter);
					MainFrame.gi().addFilter(smoothFilter);
				} else if (command.equals("pervasionCheckBox")) {
				} else if (command.equals("pervasionRatioTextField")) {
				} else if (command.equals("carvingCheckBox")) {
				} else if (command.equals("carvingRatioTextField")) {
				}
			}
		}
	}

}
