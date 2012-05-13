package com.jhs.open.ui.canvas.panel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import com.jhs.open.Define;
import com.jhs.open.control.GameControl;
import com.jhs.open.control.ListenerControl;
import com.jhs.open.control.MapControl;
import com.jhs.open.control.event.win.WinCanvasMouseClickedEvent;
import com.jhs.open.control.event.win.WinCanvasMouseMoveEvent;
import com.jhs.open.tool.GraphicsTools;
import com.jhs.open.ui.Canvas;


/**
 * 胜利界面
 * 
 * @author JHS
 * 
 */
public class WinPanel extends Canvas {

	private boolean isStop = false;

	public WinPanel() {
		super((Define.Main.width - Define.WinCanvas.width) / 2, 0, Define.WinCanvas.width, Define.WinCanvas.height);
	}

	@Override
	public void init() {
		addMouseListener(new WinCanvasMouseClickedEvent());
		addMouseListener(new WinCanvasMouseMoveEvent());
	}

	@Override
	public void render(Graphics g) {
		super.render(g);

		GraphicsTools.backupGraphics(g);

		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.gray);
		g2.fillRect(x, y, width, height);
		g2.setColor(Color.white);
		g2.setStroke(new BasicStroke(2));
		g2.drawRect(x, y, width, height);

		g2.setFont(new Font("Dialog", Font.BOLD, 24));
		int _x = x + (Define.WinCanvas.width - g2.getFontMetrics().stringWidth(Define.WinCanvas.win_text)) / 2;
		g2.drawString(Define.WinCanvas.win_text, _x, y + 40);

		g2.setFont(new Font("Dialog", Font.PLAIN, 18));
		_x = x + (Define.WinCanvas.width - g2.getFontMetrics().stringWidth(Define.WinCanvas.click_text)) / 2;
		g2.drawString(Define.WinCanvas.click_text, _x, y + Define.WinCanvas.height - 20);

		GraphicsTools.restoreGraphics(g);
	}

	@Override
	public void update(long l) {
		if (timer == 0) {
			Canvas canvas = GameControl.gi().getCurrCanvas();
			ListenerControl.gi().loopRemoveCanvasListener(canvas);
			MapControl.enableNext(GameControl.gi().getCurrMap());
		}
		if (y < (Define.Main.height - Define.WinCanvas.height) / 2) {
			y = y + Define.WinCanvas.move_speed;
		} else {
			if (!isStop) {
				ListenerControl.gi().loopRegistCanvasListener(this);
			}
			isStop = true;
		}
		super.update(l);
	}
}
