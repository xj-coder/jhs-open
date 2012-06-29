package com.jhs.open;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.jhs.open.filter.Filter;
import com.jhs.open.tab.BaseTabPanel;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = -6010578755113014772L;

	public static ArrayList<JPanel> tabPanels = new ArrayList<JPanel>();

	private JPanel sourceImagePanel;
	private JPanel targetImagePanel;

	private JTabbedPane toolPanel;

	private BufferedImage sourceImage;
	private BufferedImage targetImage;

	private ArrayList<Filter> filterList = new ArrayList<Filter>();

	private static MainFrame gi;

	public static MainFrame gi() {
		if (gi == null) {
			gi = new MainFrame();
		}
		return gi;
	}

	private MainFrame() {
		tabPanels.add(new BaseTabPanel());

		setSize(700, 680);
		setLayout(new BorderLayout());

		final JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		splitPane.setTopComponent(getSourceImagePanel());
		splitPane.setBottomComponent(getTargetImagePanel());

		final JSplitPane splitPane2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPane2.setLeftComponent(splitPane);
		splitPane2.setRightComponent(getToolPanel());

		add(splitPane2, BorderLayout.CENTER);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				splitPane.setDividerLocation(0.5d);
				splitPane2.setDividerLocation(getWidth() - 300);
			}
		});
	}

	public JPanel getSourceImagePanel() {
		if (sourceImagePanel == null) {
			sourceImagePanel = new JPanel() {
				private static final long serialVersionUID = 7092008634875142888L;

				@Override
				public void paint(Graphics g) {
					super.paint(g);
					if (sourceImage != null) {
						g.drawImage(sourceImage, 0, 0, null);
					}
				}
			};

			sourceImagePanel.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					JFileChooser chooser = new JFileChooser();

					FileNameExtensionFilter extensionFilter = new FileNameExtensionFilter("Í¼Æ¬ÎÄ¼þ", "png", "jpg");
					chooser.setFileFilter(extensionFilter);

					int result = chooser.showOpenDialog(null);

					if (result == JFileChooser.APPROVE_OPTION) {
						File file = chooser.getSelectedFile();
						try {
							sourceImage = ImageIO.read(file);

							if (sourceImage != null) {
								targetImage = new BufferedImage(sourceImage.getWidth(), sourceImage.getHeight(),
										sourceImage.getType());
								targetImage.setData(sourceImage.copyData(null));

								for (int i = 0; i < getFilterCount(); i++) {
									final Filter f = getFilter(i);
									f.filter(targetImage);

									new Thread(new Runnable() {

										@Override
										public void run() {
											while (true) {
												System.out.println(f.getProcess() + "/" + f.getTotal());
												if (f.getProcess() == f.getTotal()) {
													return;
												}
											}
										}
									}).start();

								}
							}

							getSourceImagePanel().repaint();
							getTargetImagePanel().repaint();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				}
			});
		}
		return sourceImagePanel;
	}

	public JPanel getTargetImagePanel() {
		if (targetImagePanel == null) {
			targetImagePanel = new JPanel() {
				private static final long serialVersionUID = 464811652284704107L;

				@Override
				public void paint(Graphics g) {
					super.paint(g);
					if (targetImage != null) {
						g.drawImage(targetImage, 0, 0, null);
					}
				}
			};
		}
		return targetImagePanel;
	}

	public JTabbedPane getToolPanel() {
		if (toolPanel == null) {
			toolPanel = new JTabbedPane();
			toolPanel.setTabPlacement(JTabbedPane.RIGHT);
			toolPanel.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

			for (int i = 0; i < tabPanels.size(); i++) {
				toolPanel.addTab(tabPanels.get(i).getName(), new JScrollPane(tabPanels.get(i)));
			}
		}
		return toolPanel;
	}

	public BufferedImage getSourceImage() {
		return sourceImage;
	}

	public BufferedImage getTargetImage() {
		return targetImage;
	}

	public void addFilter(Filter filter) {
		filterList.add(filter);

		if (sourceImage != null) {
			targetImage = new BufferedImage(sourceImage.getWidth(), sourceImage.getHeight(), sourceImage.getType());
			targetImage.setData(sourceImage.copyData(null));

			for (int i = 0; i < getFilterCount(); i++) {
				final Filter f = getFilter(i);
				f.filter(targetImage);
			}
		}

		getTargetImagePanel().repaint();
	}

	public int getFilterCount() {
		return filterList.size();
	}

	public Filter getFilter(int index) {
		return filterList.get(index);
	}

	public Filter removeFilter(int index) {
		Filter filter = filterList.remove(index);
		removeFilter(filter);
		return filter;
	}

	public boolean removeFilter(Filter filter) {
		boolean remove = filterList.remove(filter);
		if (sourceImage != null) {
			targetImage = new BufferedImage(sourceImage.getWidth(), sourceImage.getHeight(), sourceImage.getType());
			targetImage.setData(sourceImage.copyData(null));

			for (int i = 0; i < getFilterCount(); i++) {
				final Filter f = getFilter(i);
				f.filter(targetImage);
			}
		}
		getTargetImagePanel().repaint();
		return remove;
	}

	public void clearFilter() {
		filterList.clear();
		getTargetImagePanel().repaint();
	}

	public static void main(String[] args) {
		MainFrame.gi().setVisible(true);
	}
}
