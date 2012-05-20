package com.jhs.open.control.event.map;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.jhs.open.Define;
import com.jhs.open.bean.Rect2D;
import com.jhs.open.control.GameControl;
import com.jhs.open.control.ListenerControl;
import com.jhs.open.control.SoundControl;
import com.jhs.open.tool.ImgSelector;
import com.jhs.open.ui.canvas.game.MapCanvas;
import com.jhs.open.ui.canvas.panel.GameCanvas;
import com.jhs.open.ui.shape.BallShape;
import com.jhs.open.ui.shape.EDir;
import com.jhs.open.ui.shape.EmitterShape;

public class EmitterMouseClickedAdapter extends MouseAdapter {

	private EmitterShape emitter;

	public EmitterMouseClickedAdapter(EmitterShape emitter) {
		this.emitter = emitter;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// 如果该发射器已经有连线则点击无效
		if (ImgSelector.inPath(emitter.bean).size() != 0) {
			return;
		}

		if (GameControl.gi().isSound()) {
			SoundControl.play(Define.Sound.emitter_ball_sound);
		}

		MapCanvas map = ((GameCanvas) GameControl.gi().getCurrCanvas()).getMapCanvas();

		// 计算球的初始位置
		int y = +emitter.y;
		int x = +emitter.x;
		EDir dir = EDir.DIR_UP;

		Rect2D rect = new Rect2D(0, 0, map.width, map.height);

		switch (ImgSelector.getDir(emitter, rect)) {
		case DIR_UP:
			y = y + emitter.height / 2;
			dir = EDir.DIR_DOWN;
			break;
		case DIR_DOWN:
			y = y - emitter.height / 2;
			dir = EDir.DIR_UP;
			break;
		case DIR_LEFT:
			x = x + emitter.width / 2;
			dir = EDir.DIR_RIGHT;
			break;
		case DIR_RIGHT:
			x = x - emitter.width / 2;
			dir = EDir.DIR_LEFT;
			break;
		}

		BallShape ball = new BallShape(x, y, Define.Main.grid_size, Define.Main.grid_size);

		ball.dir = dir;
		ball.type = emitter.bean.type;
		ball.path.bean.emitter = emitter.bean;

		map.addCanvas(ball);

		// 关闭所有事件,当球消失后重新开启
		ListenerControl.gi().loopRemoveCanvasListener(map);
		// ListenerControl.gi().closeAllMapListener();

	}

}
