package com.jhs.open.ui.editor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JPanel;

import com.jhs.open.tool.GraphicsTools;
import com.jhs.open.tool.ImageTools;

/**
 * 分组编辑界面
 * 
 * @author JHS
 * 
 */
public class GroupEditorPanel extends JPanel {

	private static final long serialVersionUID = 1978558975643151921L;

	@Override
	public void update(Graphics g) {
		Image screen = ImageTools.createImage(getWidth(), getHeight(), true);// 双缓冲

		// 自己的代码[begin]
		Graphics2D graphics = (Graphics2D) screen.getGraphics();
		GraphicsTools.backupGraphics(graphics);
		// 画背景
		graphics.setColor(Color.BLACK);
		graphics.fillRect(0, 0, getWidth(), getHeight());

		GraphicsTools.restoreGraphics(graphics);
		// 自己的代码[end]

		g.drawImage(screen, 0, 0, null);// 最后个参数一定要用null，这样可以防止drawImage调用update方法
		g.dispose();
	}

	@Override
	public void paint(Graphics g) {
		update(g);// 我们在paint方法中，直接调用update方法
	}
}
