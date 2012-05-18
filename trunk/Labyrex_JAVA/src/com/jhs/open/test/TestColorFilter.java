package com.jhs.open.test;

import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.jhs.open.Define;

public class TestColorFilter extends JFrame {
	private static final long serialVersionUID = 3597975049169151826L;

	BufferedImage bi;
	File f = new File(Define.IMG_PATH + "emitter_full.png");

	public static void main(String[] args) throws IOException {
		new TestColorFilter().setVisible(true);
	}

	public TestColorFilter() throws IOException {
		bi = ImageIO.read(f);
		setLayout(new FlowLayout());

		Action action = new Action();
		JButton a1 = new JButton("+");
		a1.addActionListener(action);
		a1.setActionCommand("a+");
		JButton a2 = new JButton("-");
		a2.addActionListener(action);
		a2.setActionCommand("a-");
		JButton r1 = new JButton("+");
		r1.addActionListener(action);
		r1.setActionCommand("r+");
		JButton r2 = new JButton("-");
		r2.addActionListener(action);
		r2.setActionCommand("r-");
		JButton g1 = new JButton("+");
		g1.addActionListener(action);
		g1.setActionCommand("g+");
		JButton g2 = new JButton("-");
		g2.addActionListener(action);
		g2.setActionCommand("g-");
		JButton b1 = new JButton("+");
		b1.addActionListener(action);
		b1.setActionCommand("b+");
		JButton b2 = new JButton("-");
		b2.addActionListener(action);
		b2.setActionCommand("b-");
		JButton back = new JButton("back");
		back.addActionListener(action);
		back.setActionCommand("back");

		add(a1);
		add(a2);
		add(new JLabel(" | "));
		add(r1);
		add(r2);
		add(new JLabel(" | "));
		add(g1);
		add(g2);
		add(new JLabel(" | "));
		add(b1);
		add(b2);
		add(new JLabel(" | "));
		add(back);

		setSize(500, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		int size = 200;
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.drawImage(bi, 100, 100, size, size, null);
	}

	class Action implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String action = e.getActionCommand();

			if (action.equals("back")) {
				try {
					bi = ImageIO.read(f);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				TestColorFilter.this.repaint();
				return;
			}

			DataBufferByte bufferByte = (DataBufferByte) bi.getRaster().getDataBuffer();

			for (int i = 0; i < bi.getRaster().getHeight(); i++) {
				for (int j = 0; j < bi.getRaster().getWidth(); j++) {
					int offset = (i * bi.getRaster().getWidth() + j) * 4;
					if (bufferByte.getElem(offset) == 0 && bufferByte.getElem(offset + 1) == 0
							&& bufferByte.getElem(offset + 2) == 0 && bufferByte.getElem(offset + 3) == 0) {
						continue;
					}
					if (action.equals("a+")) {
						bufferByte.setElem(offset, bufferByte.getElem(offset) + 1);
					} else if (action.equals("a-")) {
						bufferByte.setElem(offset, bufferByte.getElem(offset) - 1);
					} else if (action.equals("r+")) {
						bufferByte.setElem(offset + 1, bufferByte.getElem(offset + 1) + 1);
					} else if (action.equals("r-")) {
						bufferByte.setElem(offset + 1, bufferByte.getElem(offset + 1) - 1);
					} else if (action.equals("g+")) {
						bufferByte.setElem(offset + 2, bufferByte.getElem(offset + 2) + 1);
					} else if (action.equals("g-")) {
						bufferByte.setElem(offset + 2, bufferByte.getElem(offset + 2) - 1);
					} else if (action.equals("b+")) {
						bufferByte.setElem(offset + 3, bufferByte.getElem(offset + 3) + 1);
					} else if (action.equals("b-")) {
						bufferByte.setElem(offset + 3, bufferByte.getElem(offset + 3) - 1);
					}
				}
			}

			TestColorFilter.this.repaint();
		}
	}
}
