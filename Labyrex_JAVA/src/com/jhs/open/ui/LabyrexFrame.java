package com.jhs.open.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.jhs.open.Define;
import com.jhs.open.control.GameControl;
import com.jhs.open.control.ListenerControl;
import com.jhs.open.control.MapControl;
import com.jhs.open.control.SoundControl;
import com.jhs.open.tool.ScreenTools;

/**
 * 游戏主界面
 * 
 * @author JHS
 * 
 */
public class LabyrexFrame extends JFrame implements Runnable {

	private static final long serialVersionUID = -3215657325431795628L;

	private Thread gameLoop = null;

	private static LabyrexFrame gi;

	private JPanel panel;

	private Graphics graphics;

	public static LabyrexFrame gi() {
		if (gi == null) {
			gi = new LabyrexFrame();
		}
		return gi;
	}

	private LabyrexFrame() {
		setSize(Define.Main.width, Define.Main.height);
		ScreenTools.moveToConter(this);

		panel = new JPanel();

		setLayout(new BorderLayout());
		add(panel, BorderLayout.CENTER);

		setResizable(false);
		setUndecorated(true);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				start();
			}
		});
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		graphics = panel.getGraphics();
	}

	@Override
	public void run() {
		long l = System.currentTimeMillis();
		int i = 0;
		int j = 0;
		int fps = 0;

		long step = 25l;
		long sleep = 10l;
		while (true) {
			// 帧刷新频率矫正，确保每秒刷新的帧数最大值为固定的，最快每隔(step-sleep)毫秒刷新一帧,即每秒的帧数为1000/(step-sleep)
			long l3 = System.currentTimeMillis();
			long l2 = System.currentTimeMillis();
			long l1;
			for (l1 = l2 - l; l1 < step; l1 = l2 - l) {
				l2 = System.currentTimeMillis();
			}
			l = l2;

			// 游戏渲染开始
			Canvas gamestate = GameControl.gi().getCurrCanvas();
			gamestate.update(l1);
			gamestate.render();
			if (Define.FPS) {
				gamestate.drawText(new StringBuilder().append("FPS : ").append(fps).toString(), 400, 25, Color.red,
						new Font("Dialog", Font.PLAIN, 18));
			}
			gamestate.flip(graphics);
			// 游戏渲染结束

			// 每秒刷新帧数统计
			long l4 = System.currentTimeMillis();
			j = (int) (j + (l4 - l3));
			if (j >= 1000) {
				fps = i;
				i = 0;
				j = 0;
			} else {
				i++;
			}

			try {
				// 线程休眠
				Thread.sleep(sleep);
			} catch (InterruptedException e) {
				if (Define.DEBUG) {
					e.printStackTrace();
				}
			}
		}
	}

	private void start() {
		System.out.println("游戏线程启动");
		gameLoop = new Thread(this);
		gameLoop.start();
	}

	/**
	 * 游戏开发方法，初始化关卡控制器、声音控制器、游戏控制器，该方法应该有且只能调用一次
	 */
	public void begin() {
		ListenerControl.gi().begin();// 打开事件控制器
		MapControl.read();// 读入地图数据
		SoundControl.play(Define.Sound.bg_sound, -1);// 启动声音管理器并播放背景音乐
		GameControl.gi().setCurrCanvasIndex(GameControl.G_Title);// 启动游戏管理器并设置当前画面
		if (!GameControl.gi().isMusic()) {
			SoundControl.pause(Define.Sound.bg_sound);
		}
	}
}
