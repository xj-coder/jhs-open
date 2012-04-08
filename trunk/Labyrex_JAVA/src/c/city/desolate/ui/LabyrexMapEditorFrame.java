package c.city.desolate.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.border.TitledBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import c.city.desolate.Define;
import c.city.desolate.bean.MapBean;
import c.city.desolate.control.MapControl;
import c.city.desolate.tool.ImgSelector;
import c.city.desolate.tool.ScreenTools;
import c.city.desolate.ui.editor.MapEditorPanel;

import com.jidesoft.swing.JideToggleSplitButton;

/**
 * 地图编辑器
 * 
 * @author JHS
 * 
 */
public class LabyrexMapEditorFrame extends JFrame {

	private static final long serialVersionUID = 1733539651012368750L;

	public static int WIDTH = 950;
	public static int HEIGHT = 600;

	private JTree tree;

	private MapEditorPanel mapPanel;

	private JPanel toolPanel;

	private JPanel mapInfoPanel;
	private JTextField nameField;
	private JComboBox categoryComboBox;
	private JTextField widthField;
	private JTextField heightField;
	private JCheckBox enableCheckBox;

	private JPanel bodysPanel;
	private JideToggleSplitButton mirrorButton;
	private JideToggleSplitButton receiverButton;
	private JideToggleSplitButton emmiterButton;

	private JPanel buttonPanel;
	private JButton randomButton;
	private JButton checkButton;
	private JButton saveButton;

	private JideToggleSplitButton currClickButton;
	private MapBean currMapBean;

	private static LabyrexMapEditorFrame gi;

	public static LabyrexMapEditorFrame gi() {
		if (gi == null) {
			gi = new LabyrexMapEditorFrame();
		}
		return gi;
	}

	private LabyrexMapEditorFrame() {
		initUI();
		initData();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void initData() {
		// load tree data
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) getTree().getModel().getRoot();
		Map<String, ArrayList<MapBean>> masp = MapControl.readMaps();

		Set<String> keySet = masp.keySet();
		Iterator<String> it = keySet.iterator();
		while (it.hasNext()) {
			String key = it.next();

			DefaultMutableTreeNode cNode = new DefaultMutableTreeNode(key);

			ArrayList<MapBean> mapList = masp.get(key);
			for (int i = 0; i < mapList.size(); i++) {
				MapBean bean = mapList.get(i);
				DefaultMutableTreeNode mapNode = new DefaultMutableTreeNode(bean);
				cNode.add(mapNode);
			}
			root.add(cNode);
			getTree().expandPath(new TreePath(cNode.getPath()));
		}
		getTree().updateUI();
	}

	private void initUI() {
		setSize(WIDTH, HEIGHT);
		setLayout(new BorderLayout());
		setResizable(false);
		ScreenTools.moveToConter(this);

		JSplitPane splitPane1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPane1.setDividerLocation(150);
		splitPane1.setLeftComponent(getTree());
		splitPane1.setRightComponent(getMapPanel());
		splitPane1.setEnabled(false);

		JSplitPane splitPane2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPane2.setDividerLocation(700);
		splitPane2.setLeftComponent(splitPane1);
		splitPane2.setRightComponent(getToolPanel());
		splitPane2.setEnabled(false);

		add(splitPane2, BorderLayout.CENTER);
	}

	public void setCurrMapBean(MapBean currMapBean) {
		this.currMapBean = currMapBean;
		reset();
	}

	public JideToggleSplitButton getCurrClickButton() {
		return currClickButton;
	}

	public MapBean getCurrMapBean() {
		return currMapBean;
	}

	private void reset() {
		if (currMapBean != null) {
			getMapPanel().reset();
			getNameField().setText(currMapBean.name.substring(currMapBean.name.lastIndexOf(".") + 1));
			getWidthField().setText(currMapBean.width + "");
			getHeightField().setText(currMapBean.height + "");
			getEnableCheckBox().setSelected(currMapBean.isEnabled);
			getCategoryComboBox().setSelectedItem(currMapBean.name.substring(0, currMapBean.name.lastIndexOf(".")));
		}
	}

	public JTree getTree() {
		if (tree == null) {
			tree = new JTree(new DefaultMutableTreeNode("地图"));

			tree.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (e.getButton() == MouseEvent.BUTTON1) {
						DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getSelectionPath()
								.getLastPathComponent();
						Object obj = selectedNode.getUserObject();
						if (obj instanceof MapBean) {
							setCurrMapBean((MapBean) obj);
						}
					} else if (e.getButton() == MouseEvent.BUTTON3) {

					}
				}
			});
		}
		return tree;
	}

	public MapEditorPanel getMapPanel() {
		if (mapPanel == null) {
			mapPanel = new MapEditorPanel(this);
		}
		return mapPanel;
	}

	public JPanel getToolPanel() {
		if (toolPanel == null) {
			toolPanel = new JPanel();
			// toolPanel.setLayout(new BoxLayout(toolPanel, BoxLayout.Y_AXIS));
			// toolPanel.setLayout(new FlowLayout());
			toolPanel.setLayout(new BorderLayout());

			toolPanel.add(getMapInfoPanel(), BorderLayout.NORTH);
			toolPanel.add(getBodysPanel(), BorderLayout.CENTER);
			toolPanel.add(getButtonPanel(), BorderLayout.SOUTH);
		}
		return toolPanel;
	}

	public JPanel getMapInfoPanel() {
		if (mapInfoPanel == null) {
			mapInfoPanel = new JPanel();
			mapInfoPanel.setBorder(new TitledBorder("地图基本信息"));
			GridLayout layout = new GridLayout(5, 2);
			layout.setHgap(10);
			layout.setVgap(10);
			mapInfoPanel.setLayout(layout);

			mapInfoPanel.add(new JLabel("地图名："));
			mapInfoPanel.add(getNameField());

			mapInfoPanel.add(new JLabel("分类："));
			mapInfoPanel.add(getCategoryComboBox());

			mapInfoPanel.add(new JLabel("宽度："));
			mapInfoPanel.add(getWidthField());

			mapInfoPanel.add(new JLabel("高度："));
			mapInfoPanel.add(getHeightField());

			mapInfoPanel.add(new JLabel());
			mapInfoPanel.add(getEnableCheckBox());
		}
		return mapInfoPanel;
	}

	public JTextField getNameField() {
		if (nameField == null) {
			nameField = new JTextField();
		}
		return nameField;
	}

	public JComboBox getCategoryComboBox() {
		if (categoryComboBox == null) {
			categoryComboBox = new JComboBox(new String[] { MapControl.easy, MapControl.moderate, MapControl.difficute,
					MapControl.absurd });
		}
		return categoryComboBox;
	}

	public JTextField getWidthField() {
		if (widthField == null) {
			widthField = new JTextField();
		}
		return widthField;
	}

	public JTextField getHeightField() {
		if (heightField == null) {
			heightField = new JTextField();
		}
		return heightField;
	}

	public JCheckBox getEnableCheckBox() {
		if (enableCheckBox == null) {
			enableCheckBox = new JCheckBox("初始可见性", false);
		}
		return enableCheckBox;
	}

	public JPanel getBodysPanel() {
		if (bodysPanel == null) {
			bodysPanel = new JPanel();
			bodysPanel.setBorder(new TitledBorder(""));
			bodysPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 5));

			bodysPanel.add(getMirrorButton());
			bodysPanel.add(getEmmiterButton());
			bodysPanel.add(getReceiverButton());
		}
		return bodysPanel;
	}

	public JideToggleSplitButton getMirrorButton() {
		if (mirrorButton == null) {
			mirrorButton = new JideToggleSplitButton(new ImageIcon(ImgSelector.mirrorSelector("L")));
			mirrorButton.setName("L");

			Set<String> keySet = Define.Mirror.typeMap.keySet();
			Iterator<String> it = keySet.iterator();
			while (it.hasNext()) {
				String key = it.next();

				final JMenuItem item = new JMenuItem(key, new ImageIcon(ImgSelector.mirrorSelector(key)));
				item.setName(key);
				item.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						mirrorButton.setIcon(item.getIcon());
						mirrorButton.setName(item.getName());
					}
				});

				mirrorButton.add(item);
			}

			mirrorButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (currClickButton != null && currClickButton != getMirrorButton()) {
						currClickButton.setButtonSelected(false);
					}
					if (mirrorButton.isSelected()) {
						currClickButton = null;
					} else {
						currClickButton = getMirrorButton();
					}
				}
			});
		}
		return mirrorButton;
	}

	public JideToggleSplitButton getReceiverButton() {
		if (receiverButton == null) {
			receiverButton = new JideToggleSplitButton(new ImageIcon(ImgSelector.receiverSelector("red")));
			receiverButton.setName("red");

			Set<String> keySet = Define.Receiver.typeMap.keySet();
			Iterator<String> it = keySet.iterator();
			while (it.hasNext()) {
				String key = it.next();

				final JMenuItem item = new JMenuItem(key, new ImageIcon(ImgSelector.receiverSelector(key)));
				item.setName(key);
				item.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						receiverButton.setIcon(item.getIcon());
						receiverButton.setName(item.getName());
					}
				});

				receiverButton.add(item);
			}

			receiverButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (currClickButton != null && currClickButton != getReceiverButton()) {
						currClickButton.setButtonSelected(false);
					}
					if (receiverButton.isSelected()) {
						currClickButton = null;
					} else {
						currClickButton = getReceiverButton();
					}
				}
			});
		}
		return receiverButton;
	}

	public JideToggleSplitButton getEmmiterButton() {
		if (emmiterButton == null) {
			emmiterButton = new JideToggleSplitButton(new ImageIcon(ImgSelector.emitterSelector("red")));
			emmiterButton.setName("red");

			Set<String> keySet = Define.Emitter.typeMap.keySet();
			Iterator<String> it = keySet.iterator();
			while (it.hasNext()) {
				String key = it.next();

				final JMenuItem item = new JMenuItem(key, new ImageIcon(ImgSelector.emitterSelector(key)));
				item.setName(key);
				item.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						emmiterButton.setIcon(item.getIcon());
						emmiterButton.setName(item.getName());
					}
				});

				emmiterButton.add(item);
			}

			emmiterButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (currClickButton != null && currClickButton != getEmmiterButton()) {
						currClickButton.setButtonSelected(false);
					}
					if (emmiterButton.isSelected()) {
						currClickButton = null;
					} else {
						currClickButton = getEmmiterButton();
					}
				}
			});
		}
		return emmiterButton;
	}

	public JPanel getButtonPanel() {
		if (buttonPanel == null) {
			buttonPanel = new JPanel();
			buttonPanel.setBorder(new TitledBorder(""));
			buttonPanel.setLayout(new GridLayout(3, 1, 5, 10));

			buttonPanel.add(getRandomButton());
			buttonPanel.add(getCheckButton());
			buttonPanel.add(getSaveButton());
		}
		return buttonPanel;
	}

	public JButton getRandomButton() {
		if (randomButton == null) {
			randomButton = new JButton("随即生成");
		}
		return randomButton;
	}

	public JButton getCheckButton() {
		if (checkButton == null) {
			checkButton = new JButton("验证可行性");
		}
		return checkButton;
	}

	public JButton getSaveButton() {
		if (saveButton == null) {
			saveButton = new JButton("保存");
		}
		return saveButton;
	}
}
