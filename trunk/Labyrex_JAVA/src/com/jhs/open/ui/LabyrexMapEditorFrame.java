package com.jhs.open.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import com.jhs.open.Define;
import com.jhs.open.bean.EmitterBean;
import com.jhs.open.bean.GroupBean;
import com.jhs.open.bean.MapBean;
import com.jhs.open.bean.MirrorBean;
import com.jhs.open.bean.ReceiverBean;
import com.jhs.open.control.MapControl;
import com.jhs.open.tool.CanvasSearcher;
import com.jhs.open.tool.ImgSelector;
import com.jhs.open.tool.ScreenTools;
import com.jhs.open.ui.editor.GroupEditorPanel;
import com.jhs.open.ui.editor.MapEditorPanel;
import com.jhs.open.ui.editor.dialog.NewMapDialog;
import com.jhs.open.ui.shape.EmitterShape;
import com.jhs.open.ui.shape.MirrorShape;
import com.jhs.open.ui.shape.ReceiverShape;
import com.jidesoft.swing.JideScrollPane;
import com.jidesoft.swing.JideToggleSplitButton;

/**
 * 地图编辑器
 * 
 * @author JHS
 * 
 */
public class LabyrexMapEditorFrame extends JFrame {

	private static final long serialVersionUID = 1733539651012368750L;

	private JSplitPane splitPane1;
	private JSplitPane splitPane2;

	private JTree tree;

	private MapEditorPanel mapPanel;
	private GroupEditorPanel groupPanel;

	private JPanel centerPanel;

	private JPanel toolPanel;
	private JCheckBox showGridCheckBox;
	private JCheckBox showRulerCheckBox;
	private JCheckBox showAssistCheckBox;

	private JPanel operatePanel;

	private JPanel mapInfoPanel;
	private JTextField nameField;
	private JComboBox groupComboBox;
	private JTextField widthField;
	private JTextField heightField;
	private JTextField emitterNumField;
	private JTextField receiverNumField;
	private JTextField mirrorNumField;
	private JCheckBox enableCheckBox;

	private JPanel bodysPanel;
	private JPanel bodyButtonPanel;
	private JideToggleSplitButton mirrorButton;
	private JideToggleSplitButton receiverButton;
	private JideToggleSplitButton emitterButton;
	private JPanel bodyInfoPanel;
	private JComboBox bodyTypeComboBox;
	private JComboBox bodyXComboBox;
	private JComboBox bodyYComboBox;
	private JPanel bodyDescPanel;
	private JTextArea bodyDescArea;

	private JPanel buttonPanel;
	private JButton randomButton;
	private JButton checkButton;
	private JButton restoreButton;
	private JButton saveButton;

	private JPopupMenu popupMenu;
	private JMenuItem newMapItem;
	private JMenuItem newGroupItem;
	private JMenuItem copyItem;
	private JMenuItem delItem;
	private JMenuItem upMoveItem;
	private JMenuItem downMoveItem;

	private JideToggleSplitButton currClickButton;
	private MapBean currMapBean;
	private Canvas currSelectedCanvas;

	private static LabyrexMapEditorFrame gi;

	private BodyXComboBoxAction bodyXComboBoxAction = new BodyXComboBoxAction();
	private BodyValueComboBoxAction bodyValueComboBoxAction = new BodyValueComboBoxAction();

	public static LabyrexMapEditorFrame gi() {
		if (gi == null) {
			gi = new LabyrexMapEditorFrame();
		}
		return gi;
	}

	private LabyrexMapEditorFrame() {
		setTitle("关卡编辑器");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void showFrame() {

		MapControl.read();// 读入地图数据

		initUI();
		initData();
		initEvent();

		setVisible(true);
	}

	private void initEvent() {
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.isControlDown()) {
					if (e.getKeyCode() == KeyEvent.VK_UP) {

					} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {

					}
				} else {
					if (e.getKeyCode() == KeyEvent.VK_DELETE) {

					}
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
	}

	private void initData() {
		// load tree data
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) getTree().getModel().getRoot();

		for (int i = 0; i < MapControl.getGroupCount(); i++) {
			GroupBean group = MapControl.getGroup(i);

			DefaultMutableTreeNode cNode = new DefaultMutableTreeNode(group);

			for (int j = 0; j < group.getMapCount(); j++) {
				MapBean bean = group.getMap(j);
				DefaultMutableTreeNode mapNode = new DefaultMutableTreeNode(bean);
				cNode.add(mapNode);
			}
			root.add(cNode);
			getTree().expandPath(new TreePath(cNode.getPath()));
		}
		getTree().updateUI();
		getTree().setSelectionRow(0);
	}

	private void initUI() {
		setSize(Define.Editor.WIDTH, Define.Editor.HEIGHT);
		setLayout(new BorderLayout());
		setResizable(false);
		ScreenTools.moveToConter(this);

		add(getSplitPane2(), BorderLayout.CENTER);
	}

	public JSplitPane getSplitPane1() {
		if (splitPane1 == null) {
			splitPane1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

			splitPane1.setLeftComponent(new JideScrollPane(getTree()));
			splitPane1.setRightComponent(getCenterPanel());
		}
		return splitPane1;
	}

	public JSplitPane getSplitPane2() {
		if (splitPane2 == null) {
			splitPane2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
			splitPane2.setLeftComponent(getSplitPane1());
			splitPane2.setRightComponent(getOperatePanel());
		}
		return splitPane2;
	}

	public JPanel getCenterPanel() {
		if (centerPanel == null) {
			centerPanel = new JPanel();
			centerPanel.setLayout(new BorderLayout());

			centerPanel.add(getToolPanel(), BorderLayout.NORTH);
			centerPanel.add(getMapPanel(), BorderLayout.CENTER);
		}
		return centerPanel;
	}

	public JideToggleSplitButton getCurrClickButton() {
		return currClickButton;
	}

	public void setCurrClickButton(JideToggleSplitButton currClickButton) {
		this.currClickButton = currClickButton;

		updateBodyDescPanel();
	}

	public Canvas getCurrSelectedCanvas() {
		return currSelectedCanvas;
	}

	public void setCurrSelectedCanvas(Canvas currSelectedCanvas) {
		if (getCurrSelectedCanvas() != null && !getCurrSelectedCanvas().equals(currSelectedCanvas)) {
			getCurrSelectedCanvas().setSelected(false);
			getMapPanel().repaint(getCurrSelectedCanvas().offsetX, getCurrSelectedCanvas().offsetY,
					getCurrSelectedCanvas().width, getCurrSelectedCanvas().height);
		}
		this.currSelectedCanvas = currSelectedCanvas;

		updateBodyInfoPanel();
	}

	public MapBean getCurrMapBean() {
		return currMapBean;
	}

	public void setCurrMapBean(MapBean currMapBean) {
		this.currMapBean = currMapBean;

		resetData();

		setCurrClickButton(null);
		setCurrSelectedCanvas(null);
	}

	private void resetData() {
		if (currMapBean != null) {
			getSplitPane1().setRightComponent(getCenterPanel());
			getOperatePanel().setVisible(true);
		} else {
			getSplitPane1().setRightComponent(getGroupPanel());
			getOperatePanel().setVisible(false);
		}
		getSplitPane1().setDividerLocation(160);
		getSplitPane2().setDividerLocation(760);
		updateAttrField();

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				getMapPanel().initData();
				getMapPanel().repaint();
			}
		});

	}

	public void updateAttrField() {
		if (currMapBean != null) {
			getNameField().setText(currMapBean.name);
			getWidthField().setText(currMapBean.width + "");
			getHeightField().setText(currMapBean.height + "");
			getEmitterNumField().setText(currMapBean.emitterList.size() + "");
			getReceiverNumField().setText(currMapBean.receiverList.size() + "");
			getMirrorNumField().setText(currMapBean.mirrorList.size() + "");
			getEnableCheckBox().setSelected(currMapBean.isEnabled);
			getGroupComboBox().setSelectedItem(currMapBean.getGroup());

			for (int i = 0; i < getEmitterButton().getItemCount(); i++) {
				getEmitterButton().getItem(i).setText(
						getEmitterButton().getName() + " - "
								+ currMapBean.getEmitterCount(getEmitterButton().getItem(i).getName()));
			}
			for (int i = 0; i < getReceiverButton().getItemCount(); i++) {
				getReceiverButton().getItem(i).setText(
						getReceiverButton().getName() + " - "
								+ currMapBean.getReceiverCount(getReceiverButton().getItem(i).getName()));
			}
			for (int i = 0; i < getMirrorButton().getItemCount(); i++) {
				getMirrorButton().getItem(i).setText(
						getMirrorButton().getName() + " - "
								+ currMapBean.getMirrorCount(getMirrorButton().getItem(i).getName()));
			}
		}
	}

	public JTree getTree() {
		if (tree == null) {
			tree = new JTree(new DefaultMutableTreeNode("地图"));

			tree.addTreeSelectionListener(new TreeSelectionListener() {

				@Override
				public void valueChanged(TreeSelectionEvent e) {
					DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getSelectionPath()
							.getLastPathComponent();
					Object obj = selectedNode.getUserObject();

					if (obj instanceof MapBean) {
						setCurrMapBean((MapBean) obj);
					} else {
						setCurrMapBean(null);
					}
				}
			});

			tree.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {

					TreePath path = tree.getPathForLocation(e.getX(), e.getY());
					if (path != null) {
						tree.setSelectionPath(path);
					}

					if (tree.getSelectionPath() != null) {
						DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getSelectionPath()
								.getLastPathComponent();
						DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) selectedNode.getParent();
						Object obj = selectedNode.getUserObject();

						if (e.getButton() == MouseEvent.BUTTON3) {
							getPopupMenu().removeAll();

							getPopupMenu().add(getNewMapItem());
							getPopupMenu().add(getNewGroupItem());

							if (obj instanceof MapBean || obj instanceof GroupBean) {
								getPopupMenu().addSeparator();

								getPopupMenu().add(getCopyItem());
								getPopupMenu().add(getDelItem());

								getPopupMenu().addSeparator();

								getPopupMenu().add(getUpMoveItem());
								getPopupMenu().add(getDownMoveItem());

								int index = parentNode.getIndex(selectedNode);
								if (index == 0) {
									getUpMoveItem().setEnabled(false);
								} else {
									getUpMoveItem().setEnabled(true);
								}
								if (index + 1 == parentNode.getChildCount()) {
									getDownMoveItem().setEnabled(false);
								} else {
									getDownMoveItem().setEnabled(true);
								}
							}

							getPopupMenu().show(getTree(), e.getX(), e.getY());
						}
					}
				}
			});
		}
		return tree;
	}

	public MapEditorPanel getMapPanel() {
		if (mapPanel == null) {
			mapPanel = new MapEditorPanel();
		}
		return mapPanel;
	}

	public GroupEditorPanel getGroupPanel() {
		if (groupPanel == null) {
			groupPanel = new GroupEditorPanel();
		}
		return groupPanel;
	}

	public JPanel getToolPanel() {
		if (toolPanel == null) {
			toolPanel = new JPanel();
			toolPanel.add(getShowGridCheckBox());
			toolPanel.add(getShowRulerCheckBox());
			toolPanel.add(getShowAssistCheckBox());
		}
		return toolPanel;
	}

	public JCheckBox getShowAssistCheckBox() {
		if (showAssistCheckBox == null) {
			showAssistCheckBox = new JCheckBox("显示位置提示");

			showAssistCheckBox.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					getMapPanel().setShowAssist(getShowAssistCheckBox().isSelected());
					getMapPanel().repaint();
				}
			});

			showAssistCheckBox.setSelected(true);
		}
		return showAssistCheckBox;
	}

	public JCheckBox getShowGridCheckBox() {
		if (showGridCheckBox == null) {
			showGridCheckBox = new JCheckBox("显示网格");

			showGridCheckBox.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					getMapPanel().setShowGrid(getShowGridCheckBox().isSelected());
					getMapPanel().repaint();
				}
			});

			showGridCheckBox.setSelected(true);
		}
		return showGridCheckBox;
	}

	public JCheckBox getShowRulerCheckBox() {
		if (showRulerCheckBox == null) {
			showRulerCheckBox = new JCheckBox("显示标尺");
			showRulerCheckBox.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					getMapPanel().setShowRuler(getShowRulerCheckBox().isSelected());
					getMapPanel().repaint();
				}
			});

			showRulerCheckBox.setSelected(true);
		}
		return showRulerCheckBox;
	}

	public JPanel getOperatePanel() {
		if (operatePanel == null) {
			operatePanel = new JPanel();
			operatePanel.setLayout(new BorderLayout());

			operatePanel.add(getMapInfoPanel(), BorderLayout.NORTH);
			operatePanel.add(getBodysPanel(), BorderLayout.CENTER);
			operatePanel.add(getButtonPanel(), BorderLayout.SOUTH);
		}
		return operatePanel;
	}

	public JPanel getMapInfoPanel() {
		if (mapInfoPanel == null) {
			mapInfoPanel = new JPanel();
			mapInfoPanel.setBorder(new TitledBorder("地图基本信息"));
			GridLayout layout = new GridLayout(8, 2);
			layout.setHgap(10);
			layout.setVgap(10);
			mapInfoPanel.setLayout(layout);

			mapInfoPanel.add(new JLabel("地图名："));
			mapInfoPanel.add(getNameField());

			mapInfoPanel.add(new JLabel("分类："));
			mapInfoPanel.add(getGroupComboBox());

			mapInfoPanel.add(new JLabel("宽度："));
			mapInfoPanel.add(getWidthField());

			mapInfoPanel.add(new JLabel("高度："));
			mapInfoPanel.add(getHeightField());

			mapInfoPanel.add(new JLabel("发射器数量："));
			mapInfoPanel.add(getEmitterNumField());

			mapInfoPanel.add(new JLabel("接收器数量："));
			mapInfoPanel.add(getReceiverNumField());

			mapInfoPanel.add(new JLabel("挡板数量："));
			mapInfoPanel.add(getMirrorNumField());

			mapInfoPanel.add(new JLabel());
			mapInfoPanel.add(getEnableCheckBox());
		}
		return mapInfoPanel;
	}

	public JTextField getNameField() {
		if (nameField == null) {
			nameField = new JTextField();

			NameFieldListener nameFieldListener = new NameFieldListener();
			nameField.addActionListener(nameFieldListener);
			nameField.addFocusListener(nameFieldListener);
		}
		return nameField;
	}

	public JComboBox getGroupComboBox() {
		if (groupComboBox == null) {
			groupComboBox = new JComboBox();

			for (int i = 0; i < MapControl.getGroupCount(); i++) {
				groupComboBox.addItem(MapControl.getGroup(i));
			}

			GroupFieldListener groupFieldListener = new GroupFieldListener();
			groupComboBox.addActionListener(groupFieldListener);
			groupComboBox.addFocusListener(groupFieldListener);
		}
		return groupComboBox;
	}

	public JTextField getWidthField() {
		if (widthField == null) {
			widthField = new JTextField();

			WidthFieldListener widthFieldListener = new WidthFieldListener();
			widthField.addActionListener(widthFieldListener);
			widthField.addFocusListener(widthFieldListener);
		}
		return widthField;
	}

	public JTextField getHeightField() {
		if (heightField == null) {
			heightField = new JTextField();

			HeightFieldListener heightFieldListener = new HeightFieldListener();
			heightField.addActionListener(heightFieldListener);
			heightField.addFocusListener(heightFieldListener);
		}
		return heightField;
	}

	public JTextField getEmitterNumField() {
		if (emitterNumField == null) {
			emitterNumField = new JTextField();
			emitterNumField.setEditable(false);
		}
		return emitterNumField;
	}

	public JTextField getReceiverNumField() {
		if (receiverNumField == null) {
			receiverNumField = new JTextField();
			receiverNumField.setEditable(false);
		}
		return receiverNumField;
	}

	public JTextField getMirrorNumField() {
		if (mirrorNumField == null) {
			mirrorNumField = new JTextField();
			mirrorNumField.setEditable(false);
		}
		return mirrorNumField;
	}

	public JCheckBox getEnableCheckBox() {
		if (enableCheckBox == null) {
			enableCheckBox = new JCheckBox("初始可见性", false);
		}
		return enableCheckBox;
	}

	public JPanel getBodyButtonPanel() {
		if (bodyButtonPanel == null) {
			bodyButtonPanel = new JPanel();
			bodyButtonPanel.setBorder(new TitledBorder(""));
			bodyButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 5));

			bodyButtonPanel.add(getMirrorButton());
			bodyButtonPanel.add(getEmitterButton());
			bodyButtonPanel.add(getReceiverButton());
		}
		return bodyButtonPanel;
	}

	public JPanel getBodysPanel() {
		if (bodysPanel == null) {
			bodysPanel = new JPanel();
			bodysPanel.setBorder(new TitledBorder(""));
			bodysPanel.setLayout(new BorderLayout());

			bodysPanel.add(getBodyButtonPanel(), BorderLayout.NORTH);
			bodysPanel.add(getBodyInfoPanel(), BorderLayout.CENTER);
			bodysPanel.add(getBodyDescPanel(), BorderLayout.SOUTH);
		}
		return bodysPanel;
	}

	public JideToggleSplitButton getMirrorButton() {
		if (mirrorButton == null) {
			mirrorButton = new JideToggleSplitButton(new ImageIcon(ImgSelector.mirrorSelector("L")));
			mirrorButton.setName("L");// 存类型
			mirrorButton.setActionCommand("挡板，当球碰到挡板后会发生90°转弯，方向受挡板类型影响");// 存说明

			Set<String> keySet = Define.Mirror.typeMap.keySet();
			Iterator<String> it = keySet.iterator();
			while (it.hasNext()) {
				String key = it.next();

				final JMenuItem item = new JMenuItem(key, new ImageIcon(ImgSelector.mirrorSelector(key)));
				item.setText(key + "- 0");
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
					if (mirrorButton.isButtonSelected()) {
						setCurrClickButton(getMirrorButton());
					} else {
						setCurrClickButton(null);
					}
				}
			});
		}
		return mirrorButton;
	}

	public JideToggleSplitButton getReceiverButton() {
		if (receiverButton == null) {
			receiverButton = new JideToggleSplitButton(new ImageIcon(ImgSelector.receiverSelector("red")));
			receiverButton.setName("red");// 存类型
			receiverButton.setActionCommand("接收器,接收于自己颜色相同的球");// 存说明

			Set<String> keySet = Define.Receiver.typeMap.keySet();
			Iterator<String> it = keySet.iterator();
			while (it.hasNext()) {
				String key = it.next();

				final JMenuItem item = new JMenuItem(key, new ImageIcon(ImgSelector.receiverSelector(key)));
				item.setText(key + "- 0");
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
					if (receiverButton.isButtonSelected()) {
						setCurrClickButton(getReceiverButton());
					} else {
						setCurrClickButton(null);
					}
				}
			});
		}
		return receiverButton;
	}

	public JideToggleSplitButton getEmitterButton() {
		if (emitterButton == null) {
			emitterButton = new JideToggleSplitButton(new ImageIcon(ImgSelector.emitterSelector("red")));
			emitterButton.setName("red");// 存类型
			emitterButton.setActionCommand("发射器，发射于自己颜色相同的球出去");// 存说明

			Set<String> keySet = Define.Emitter.typeMap.keySet();
			Iterator<String> it = keySet.iterator();
			while (it.hasNext()) {
				String key = it.next();

				final JMenuItem item = new JMenuItem(key, new ImageIcon(ImgSelector.emitterSelector(key)));
				item.setText(key + "- 0");
				item.setName(key);
				item.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						emitterButton.setIcon(item.getIcon());
						emitterButton.setName(item.getName());
					}
				});

				emitterButton.add(item);
			}

			emitterButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (currClickButton != null && currClickButton != getEmitterButton()) {
						currClickButton.setButtonSelected(false);
					}
					if (emitterButton.isButtonSelected()) {
						setCurrClickButton(getEmitterButton());
					} else {
						setCurrClickButton(null);
					}
				}
			});
		}
		return emitterButton;
	}

	public JPanel getBodyInfoPanel() {
		if (bodyInfoPanel == null) {
			bodyInfoPanel = new JPanel();
			bodyInfoPanel.setVisible(false);

			bodyInfoPanel.setBorder(new TitledBorder("元素参数设置"));
			GridLayout layout = new GridLayout(3, 2);
			layout.setHgap(10);
			layout.setVgap(10);
			bodyInfoPanel.setLayout(layout);

			bodyInfoPanel.add(new JLabel("类型："));
			bodyInfoPanel.add(getBodyTypeComboBox());

			bodyInfoPanel.add(new JLabel("X："));
			bodyInfoPanel.add(getBodyXComboBox());

			bodyInfoPanel.add(new JLabel("Y："));
			bodyInfoPanel.add(getBodyYComboBox());
		}
		return bodyInfoPanel;
	}

	public JComboBox getBodyTypeComboBox() {
		if (bodyTypeComboBox == null) {
			bodyTypeComboBox = new JComboBox();

			bodyTypeComboBox.setRenderer(new BodyTypeListCellRenderer());

			bodyTypeComboBox.addActionListener(bodyValueComboBoxAction);
		}
		return bodyTypeComboBox;
	}

	public JComboBox getBodyXComboBox() {
		if (bodyXComboBox == null) {
			bodyXComboBox = new JComboBox();

			bodyXComboBox.addActionListener(bodyXComboBoxAction);

			bodyXComboBox.addActionListener(bodyValueComboBoxAction);
		}
		return bodyXComboBox;
	}

	public JComboBox getBodyYComboBox() {
		if (bodyYComboBox == null) {
			bodyYComboBox = new JComboBox();

			bodyYComboBox.addActionListener(bodyValueComboBoxAction);
		}
		return bodyYComboBox;
	}

	public JPanel getBodyDescPanel() {
		if (bodyDescPanel == null) {
			bodyDescPanel = new JPanel();

			bodyDescPanel.setLayout(new BorderLayout());
			bodyDescPanel.add(getBodyDescArea(), BorderLayout.CENTER);
		}
		return bodyDescPanel;
	}

	public JTextArea getBodyDescArea() {
		if (bodyDescArea == null) {
			bodyDescArea = new JTextArea();

			bodyDescArea.setRows(2);
			bodyDescArea.setEditable(false);
			bodyDescArea.setLineWrap(true);
			bodyDescArea.setAutoscrolls(true);
		}
		return bodyDescArea;
	}

	public JPanel getButtonPanel() {
		if (buttonPanel == null) {
			buttonPanel = new JPanel();
			buttonPanel.setBorder(new TitledBorder(""));
			buttonPanel.setLayout(new GridLayout(4, 1, 5, 10));

			buttonPanel.add(getRandomButton());
			buttonPanel.add(getCheckButton());
			buttonPanel.add(getRestoreButton());
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

	public JButton getRestoreButton() {
		if (restoreButton == null) {
			restoreButton = new JButton("还原");
		}
		return restoreButton;
	}

	public JButton getSaveButton() {
		if (saveButton == null) {
			saveButton = new JButton("保存");
		}
		return saveButton;
	}

	public JPopupMenu getPopupMenu() {
		if (popupMenu == null) {
			popupMenu = new JPopupMenu();
		}
		return popupMenu;
	}

	public JMenuItem getNewMapItem() {
		if (newMapItem == null) {
			newMapItem = new JMenuItem("新建关卡");

			newMapItem.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					NewMapDialog dialog = new NewMapDialog(LabyrexMapEditorFrame.gi());
					dialog.setVisible(true);
				}
			});
		}
		return newMapItem;
	}

	public JMenuItem getNewGroupItem() {
		if (newGroupItem == null) {
			newGroupItem = new JMenuItem("新建分组");

			newGroupItem.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					String input = JOptionPane.showInputDialog("请输入分组名");
					if (input != null && !input.trim().equals("")) {
						GroupBean group = new GroupBean();
						group.setName(input);
						group.setSort(MapControl.getMaxSort() + 1);
						MapControl.addGroup(group);

						DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode) getTree().getModel().getRoot();
						int i = 0;
						for (; i < rootNode.getChildCount(); i++) {
							DefaultMutableTreeNode groupNode = (DefaultMutableTreeNode) rootNode.getChildAt(i);
							GroupBean groupBean = (GroupBean) groupNode.getUserObject();

							if (groupBean.sort > group.sort) {
								DefaultMutableTreeNode newGroupNode = new DefaultMutableTreeNode(group);
								rootNode.insert(newGroupNode, i);
								LabyrexMapEditorFrame.gi().getTree().setSelectionPath(
										new TreePath(newGroupNode.getPath()));
								break;
							}

						}
						if (i >= rootNode.getChildCount()) {
							DefaultMutableTreeNode newGroupNode = new DefaultMutableTreeNode(group);
							rootNode.add(newGroupNode);
							LabyrexMapEditorFrame.gi().getTree().setSelectionPath(new TreePath(newGroupNode.getPath()));
						}
						LabyrexMapEditorFrame.gi().getTree().updateUI();

					}
				}
			});
		}
		return newGroupItem;
	}

	public JMenuItem getCopyItem() {
		if (copyItem == null) {
			copyItem = new JMenuItem("复制");

			copyItem.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) getTree().getSelectionPath()
							.getLastPathComponent();
					DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) selectedNode.getParent();
					int index = parentNode.getIndex(selectedNode);

					Object obj = selectedNode.getUserObject();
					if (obj instanceof GroupBean) {
						GroupBean group = (GroupBean) obj;

						GroupBean groupClone = group.copy();
						groupClone.setSave(false);

						int tmp = 1;
						while (MapControl.indexOf(groupClone) != -1) {
							groupClone.setName(group.getName() + "_copy" + tmp);
							tmp++;
						}

						boolean result = MapControl.addGroup(groupClone);

						if (result) {
							DefaultMutableTreeNode groupNode = new DefaultMutableTreeNode(groupClone);

							for (int i = 0; i < groupClone.getMapCount(); i++) {
								groupNode.add(new DefaultMutableTreeNode(groupClone.getMap(i)));
							}

							parentNode.insert(groupNode, index + 1);
							LabyrexMapEditorFrame.gi().getTree().updateUI();

							getTree().setSelectionPath(new TreePath(groupNode.getPath()));
						}
					} else if (obj instanceof MapBean) {
						MapBean map = (MapBean) obj;

						MapBean mapClone = map.copy();
						mapClone.setSave(false);

						int tmp = 1;
						while (map.getGroup().indexOf(mapClone) != -1) {
							mapClone.setName(map.getName() + "_copy" + tmp);
							tmp++;
						}

						boolean result = map.getGroup().addMap(mapClone);

						if (result) {
							DefaultMutableTreeNode mapNode = new DefaultMutableTreeNode(mapClone);
							parentNode.insert(mapNode, index + 1);
							LabyrexMapEditorFrame.gi().getTree().updateUI();

							getTree().setSelectionPath(new TreePath(mapNode.getPath()));
						}
					}
				}
			});
		}
		return copyItem;
	}

	public JMenuItem getDelItem() {
		if (delItem == null) {
			delItem = new JMenuItem("删除");

			delItem.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					treeDeleteAction();
				}
			});
		}
		return delItem;
	}

	public JMenuItem getUpMoveItem() {
		if (upMoveItem == null) {
			upMoveItem = new JMenuItem("上移");

			upMoveItem.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					treeUpAction();
				}
			});
		}
		return upMoveItem;
	}

	public JMenuItem getDownMoveItem() {
		if (downMoveItem == null) {
			downMoveItem = new JMenuItem("下移");

			downMoveItem.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					treeDownAction();
				}
			});
		}
		return downMoveItem;
	}

	private void treeDownAction() {

		DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) getTree().getSelectionPath()
				.getLastPathComponent();
		DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) selectedNode.getParent();
		int index = parentNode.getIndex(selectedNode);

		Object obj = selectedNode.getUserObject();
		if (obj instanceof GroupBean) {
			GroupBean group = (GroupBean) obj;

			// 下移操作就是和下一个Bean交换sort
			GroupBean swapBean = MapControl.getGroup(MapControl.indexOf(group) + 1);
			int tmp = swapBean.getSort();
			swapBean.setSort(group.getSort());
			group.setSort(tmp);
			MapControl.sort();

			parentNode.insert(selectedNode, index + 1);
			LabyrexMapEditorFrame.gi().getTree().updateUI();
		} else if (obj instanceof MapBean) {
			MapBean map = (MapBean) obj;
			GroupBean group = map.getGroup();

			// 下移操作就是和下一个Bean交换sort
			MapBean swapBean = group.getMap(group.indexOf(map) + 1);
			int tmp = swapBean.getSort();
			swapBean.setSort(map.getSort());
			map.setSort(tmp);
			group.sort();

			parentNode.insert(selectedNode, index + 1);
			LabyrexMapEditorFrame.gi().getTree().updateUI();
		}

	}

	private void treeUpAction() {

		DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) getTree().getSelectionPath()
				.getLastPathComponent();
		DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) selectedNode.getParent();
		int index = parentNode.getIndex(selectedNode);

		Object obj = selectedNode.getUserObject();
		if (obj instanceof GroupBean) {
			GroupBean group = (GroupBean) obj;

			// 上移操作就是和上一个Bean交换sort
			GroupBean swapBean = MapControl.getGroup(MapControl.indexOf(group) - 1);
			int tmp = swapBean.getSort();
			swapBean.setSort(group.getSort());
			group.setSort(tmp);
			MapControl.sort();

			parentNode.insert(selectedNode, index - 1);
			LabyrexMapEditorFrame.gi().getTree().updateUI();
		} else if (obj instanceof MapBean) {
			MapBean map = (MapBean) obj;
			GroupBean group = map.getGroup();

			// 上移操作就是和上一个Bean交换sort
			MapBean swapBean = group.getMap(group.indexOf(map) - 1);
			int tmp = swapBean.getSort();
			swapBean.setSort(map.getSort());
			map.setSort(tmp);
			group.sort();

			parentNode.insert(selectedNode, index - 1);
			LabyrexMapEditorFrame.gi().getTree().updateUI();
		}

	}

	private void treeDeleteAction() {

		DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) getTree().getSelectionPath()
				.getLastPathComponent();

		Object obj = selectedNode.getUserObject();
		if (obj instanceof GroupBean) {
			GroupBean group = (GroupBean) obj;

			MapControl.removeGroup(group);

			selectedNode.removeFromParent();
			LabyrexMapEditorFrame.gi().getTree().updateUI();
		} else if (obj instanceof MapBean) {
			MapBean map = (MapBean) obj;

			map.getGroup().removeMap(map);

			selectedNode.removeFromParent();
			LabyrexMapEditorFrame.gi().getTree().updateUI();
		}

	}

	public void updateBodyDescPanel() {
		if (getCurrClickButton() != null) {
			getBodyDescArea().setText(getCurrClickButton().getActionCommand());
		} else {
			getBodyDescArea().setText("");
		}
	}

	public void updateBodyInfoPanel() {
		if (getCurrSelectedCanvas() != null) {

			Canvas selectCanvas = getCurrSelectedCanvas();

			ArrayList<JLabel> typeItems = new ArrayList<JLabel>();
			ArrayList<Integer> xItems = new ArrayList<Integer>();

			JLabel type = null;
			Integer x = 0;
			if (selectCanvas instanceof MirrorShape) {
				MirrorShape shape = (MirrorShape) selectCanvas;

				x = shape.bean.x;

				Set<String> keySet = Define.Mirror.typeMap.keySet();
				Iterator<String> it = keySet.iterator();
				while (it.hasNext()) {
					String key = it.next();

					JLabel label = new JLabel(key, new ImageIcon(ImgSelector.mirrorSelector(key)), 10);
					typeItems.add(label);

					if (key.equals(shape.bean.type)) {
						type = label;
					}
				}

				for (int i = 0; i < getCurrMapBean().width; i++) {
					MirrorBean bean = CanvasSearcher.findMirror(getCurrMapBean().mirrorList
							.toArray(new MirrorBean[] {}), i, shape.bean.y);
					if (bean == null || bean == shape.bean) {
						xItems.add(i);
					}
				}
			} else if (selectCanvas instanceof EmitterShape) {
				EmitterShape shape = (EmitterShape) selectCanvas;

				x = shape.bean.x;

				Set<String> keySet = Define.Emitter.typeMap.keySet();
				Iterator<String> it = keySet.iterator();
				while (it.hasNext()) {
					String key = it.next();

					JLabel label = new JLabel(key, new ImageIcon(ImgSelector.emitterSelector(key)), 10);
					typeItems.add(label);

					if (key.equals(shape.bean.type)) {
						type = label;
					}
				}
				if (shape.bean.y == -1 || shape.bean.y == getCurrMapBean().height) {
					for (int i = 0; i < getCurrMapBean().width; i++) {
						EmitterBean bean = CanvasSearcher.findEmitter(getCurrMapBean().emitterList
								.toArray(new EmitterBean[] {}), i, shape.bean.y);
						if (bean == null || bean == shape.bean) {
							xItems.add(i);
						}
					}
				} else {
					for (int i = -1; i <= getCurrMapBean().width; i++) {
						EmitterBean bean = CanvasSearcher.findEmitter(getCurrMapBean().emitterList
								.toArray(new EmitterBean[] {}), i, shape.bean.y);
						if (bean == null || bean == shape.bean) {
							xItems.add(i);
						}
					}
				}
			} else if (selectCanvas instanceof ReceiverShape) {
				ReceiverShape shape = (ReceiverShape) selectCanvas;

				x = shape.bean.x;

				Set<String> keySet = Define.Receiver.typeMap.keySet();
				Iterator<String> it = keySet.iterator();
				while (it.hasNext()) {
					String key = it.next();

					JLabel label = new JLabel(key, new ImageIcon(ImgSelector.receiverSelector(key)), 10);
					typeItems.add(label);

					if (key.equals(shape.bean.type)) {
						type = label;
					}
				}
				if (shape.bean.y == -1 || shape.bean.y == getCurrMapBean().height) {
					for (int i = 0; i < getCurrMapBean().width; i++) {
						ReceiverBean bean = CanvasSearcher.findReceiver(getCurrMapBean().receiverList
								.toArray(new ReceiverBean[] {}), i, shape.bean.y);
						if (bean == null || bean == shape.bean) {
							xItems.add(i);
						}
					}
				} else {
					for (int i = -1; i <= getCurrMapBean().width; i++) {
						ReceiverBean bean = CanvasSearcher.findReceiver(getCurrMapBean().receiverList
								.toArray(new ReceiverBean[] {}), i, shape.bean.y);
						if (bean == null || bean == shape.bean) {
							xItems.add(i);
						}
					}
				}
			}

			bodyValueComboBoxAction.setEnable(false);

			getBodyTypeComboBox().removeAllItems();
			for (int i = 0; i < typeItems.size(); i++) {
				getBodyTypeComboBox().addItem(typeItems.get(i));
			}

			getBodyXComboBox().removeActionListener(bodyXComboBoxAction);
			getBodyXComboBox().removeAllItems();
			for (int i = 0; i < xItems.size(); i++) {
				getBodyXComboBox().addItem(xItems.get(i));
			}
			getBodyXComboBox().addActionListener(bodyXComboBoxAction);

			getBodyTypeComboBox().setSelectedItem(type);
			getBodyXComboBox().setSelectedItem(x);

			getBodyInfoPanel().setVisible(true);

			bodyValueComboBoxAction.setEnable(true);
		} else {
			getBodyInfoPanel().setVisible(false);
		}
	}

	public void updateBodyInfoValue() {
		if (getCurrSelectedCanvas() != null) {

			Canvas selectCanvas = getCurrSelectedCanvas();

			JLabel type = (JLabel) getBodyTypeComboBox().getSelectedItem();
			Integer x = Integer.parseInt(getBodyXComboBox().getSelectedItem().toString());
			Integer y = Integer.parseInt(getBodyYComboBox().getSelectedItem().toString());
			if (selectCanvas instanceof MirrorShape) {
				MirrorShape shape = (MirrorShape) selectCanvas;

				shape.bean.x = x;
				shape.bean.y = y;
				shape.bean.type = type.getText();
				shape.reset();
			} else if (selectCanvas instanceof EmitterShape) {
				EmitterShape shape = (EmitterShape) selectCanvas;

				shape.bean.x = x;
				shape.bean.y = y;
				shape.bean.type = type.getText();
				shape.reset();
			} else if (selectCanvas instanceof ReceiverShape) {
				ReceiverShape shape = (ReceiverShape) selectCanvas;

				shape.bean.x = x;
				shape.bean.y = y;
				shape.bean.type = type.getText();
				shape.reset();
			}

			updateAttrField();
			updateBodyInfoPanel();

			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					getMapPanel().repaint();
				}
			});
		}

	}

	class BodyTypeListCellRenderer extends DefaultListCellRenderer {
		private static final long serialVersionUID = 1823755050373945023L;

		@Override
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
				boolean cellHasFocus) {
			JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
			label.setText(((JLabel) value).getText());
			label.setIcon(((JLabel) value).getIcon());
			return label;
		}
	}

	class BodyXComboBoxAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			Canvas selectCanvas = getCurrSelectedCanvas();

			int value = Integer.parseInt(getBodyXComboBox().getSelectedItem().toString());

			bodyValueComboBoxAction.setEnable(false);

			getBodyYComboBox().removeAllItems();

			if (selectCanvas instanceof EmitterShape) {
				EmitterShape shape = (EmitterShape) selectCanvas;

				if (value == -1 || value == getCurrMapBean().width) {
					for (int i = -1; i <= getCurrMapBean().height; i++) {
						EmitterBean bean = CanvasSearcher.findEmitter(getCurrMapBean().emitterList
								.toArray(new EmitterBean[] {}), value, i);
						if (bean == null || bean == shape.bean) {
							getBodyYComboBox().addItem(i);
						}
					}
				} else {
					EmitterBean bean1 = CanvasSearcher.findEmitter(getCurrMapBean().emitterList
							.toArray(new EmitterBean[] {}), value, -1);
					if (bean1 == null || bean1 == shape.bean) {
						getBodyYComboBox().addItem(-1);
					}
					EmitterBean bean2 = CanvasSearcher.findEmitter(getCurrMapBean().emitterList
							.toArray(new EmitterBean[] {}), value, getCurrMapBean().height);
					if (bean2 == null || bean2 == shape.bean) {
						getBodyYComboBox().addItem(getCurrMapBean().height);
					}
				}

				bodyValueComboBoxAction.setEnable(true);

				getBodyYComboBox().setSelectedItem(shape.bean.y);
			} else if (selectCanvas instanceof ReceiverShape) {
				ReceiverShape shape = (ReceiverShape) selectCanvas;
				if (value == -1 || value == getCurrMapBean().width) {
					for (int i = -1; i <= getCurrMapBean().height; i++) {
						ReceiverBean bean = CanvasSearcher.findReceiver(getCurrMapBean().receiverList
								.toArray(new ReceiverBean[] {}), value, i);
						if (bean == null || bean == shape.bean) {
							getBodyYComboBox().addItem(i);
						}
					}
				} else {
					ReceiverBean bean1 = CanvasSearcher.findReceiver(getCurrMapBean().receiverList
							.toArray(new ReceiverBean[] {}), value, -1);
					if (bean1 == null || bean1 == shape.bean) {
						getBodyYComboBox().addItem(-1);
					}
					ReceiverBean bean2 = CanvasSearcher.findReceiver(getCurrMapBean().receiverList
							.toArray(new ReceiverBean[] {}), value, getCurrMapBean().height);
					if (bean2 == null || bean2 == shape.bean) {
						getBodyYComboBox().addItem(getCurrMapBean().height);
					}
				}

				bodyValueComboBoxAction.setEnable(true);

				getBodyYComboBox().setSelectedItem(shape.bean.y);
			} else if (selectCanvas instanceof MirrorShape) {
				MirrorShape shape = (MirrorShape) selectCanvas;

				for (int i = 0; i < getCurrMapBean().height; i++) {
					MirrorBean bean = CanvasSearcher.findMirror(getCurrMapBean().mirrorList
							.toArray(new MirrorBean[] {}), value, i);
					if (bean == null || bean == shape.bean) {
						getBodyYComboBox().addItem(i);
					}
				}

				bodyValueComboBoxAction.setEnable(true);

				getBodyYComboBox().setSelectedItem(shape.bean.y);
			}
		}
	}

	class BodyValueComboBoxAction implements ActionListener {
		private boolean isEnable = true;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (isEnable) {
				updateBodyInfoValue();
			}
		}

		public boolean isEnable() {
			return isEnable;
		}

		public void setEnable(boolean isEnable) {
			this.isEnable = isEnable;
		}
	}

	class NameFieldListener implements ActionListener, FocusListener {
		private boolean isEnable = true;

		@Override
		public void actionPerformed(ActionEvent e) {
			setEnable(false);
			change();
			setEnable(true);
		}

		@Override
		public void focusGained(FocusEvent e) {
		}

		@Override
		public void focusLost(FocusEvent e) {
			if (isEnable) {
				change();
			}
		}

		private void change() {
			String name = getNameField().getText();

			if (name == null || name.trim().equals("")) {
				JOptionPane.showMessageDialog(getNameField(), "关卡名不能为空");
				getNameField().setText(LabyrexMapEditorFrame.gi.getCurrMapBean().getName());
				return;
			}

			GroupBean group = (GroupBean) getGroupComboBox().getSelectedItem();
			MapBean map = group.getMap(name);
			if (map != null && !map.equals(getCurrMapBean())) {
				JOptionPane.showMessageDialog(getNameField(), "关卡名已存在");
				getNameField().setText(LabyrexMapEditorFrame.gi.getCurrMapBean().getName());
				return;
			} else {
				LabyrexMapEditorFrame.gi.getCurrMapBean().setName(name);
				getTree().updateUI();
			}
		}

		public boolean isEnable() {
			return isEnable;
		}

		public void setEnable(boolean isEnable) {
			this.isEnable = isEnable;
		}
	}

	class GroupFieldListener implements ActionListener, FocusListener {
		private boolean isEnable = true;

		@Override
		public void actionPerformed(ActionEvent e) {
			setEnable(false);
			change();
			setEnable(true);
		}

		@Override
		public void focusGained(FocusEvent e) {
		}

		@Override
		public void focusLost(FocusEvent e) {
			if (isEnable) {
				change();
			}
		}

		private void change() {

		}

		public boolean isEnable() {
			return isEnable;
		}

		public void setEnable(boolean isEnable) {
			this.isEnable = isEnable;
		}
	}

	class HeightFieldListener implements ActionListener, FocusListener {
		private boolean isEnable = true;

		@Override
		public void actionPerformed(ActionEvent e) {
			setEnable(false);
			change();
			setEnable(true);
		}

		@Override
		public void focusGained(FocusEvent e) {
		}

		@Override
		public void focusLost(FocusEvent e) {
			if (isEnable) {
				change();
			}
		}

		private void change() {

		}

		public boolean isEnable() {
			return isEnable;
		}

		public void setEnable(boolean isEnable) {
			this.isEnable = isEnable;
		}
	}

	class WidthFieldListener implements ActionListener, FocusListener {
		private boolean isEnable = true;

		@Override
		public void actionPerformed(ActionEvent e) {
			setEnable(false);
			change();
			setEnable(true);
		}

		@Override
		public void focusGained(FocusEvent e) {
		}

		@Override
		public void focusLost(FocusEvent e) {
			if (isEnable) {
				change();
			}
		}

		private void change() {

		}

		public boolean isEnable() {
			return isEnable;
		}

		public void setEnable(boolean isEnable) {
			this.isEnable = isEnable;
		}
	}
}
