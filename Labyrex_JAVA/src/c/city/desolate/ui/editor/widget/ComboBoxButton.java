package c.city.desolate.ui.editor.widget;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

/**
 * 下拉按钮
 * 
 * @author JHS
 * 
 */
public class ComboBoxButton extends JPanel {

	private static final long serialVersionUID = 6919132140496015101L;

	private ArrayList<JToggleButton> items = new ArrayList<JToggleButton>();
	private JButton arrowButton;

	private JToggleButton selectedButton;

	public ComboBoxButton() {
		setLayout(new BorderLayout());

		add(getArrowButton(), BorderLayout.EAST);
	}

	public ComboBoxButton(ArrayList<JToggleButton> items) {
		this.items = items;
		setLayout(new BorderLayout());

		if (items.size() > 0) {
			add(items.get(0), BorderLayout.CENTER);
		}
		add(getArrowButton(), BorderLayout.EAST);
	}

	public JToggleButton getSelectedButton() {
		return selectedButton;
	}

	public void setSelectedButton(JToggleButton selectedButton) {
		if (this.selectedButton != null) {
			selectedButton.setSelected(false);
			remove(this.selectedButton);
		}
		this.selectedButton = selectedButton;
		this.selectedButton.setSelected(true);
	}

	public void addItem(JToggleButton item) {
		if (items.size() == 0) {
			add(item, BorderLayout.CENTER);
		}
		items.add(item);
	}

	public JButton getArrowButton() {
		if (arrowButton == null) {
			arrowButton = new JButton("▼");

			arrowButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {

				}
			});
		}
		return arrowButton;
	}
}
