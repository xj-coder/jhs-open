package c.city.desolate.control.event.map;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import c.city.desolate.Define;
import c.city.desolate.bean.Rect2D;
import c.city.desolate.control.GameControl;
import c.city.desolate.control.ListenerControl;
import c.city.desolate.control.SoundControl;
import c.city.desolate.tool.ImgSelector;
import c.city.desolate.ui.canvas.game.MapCanvas;
import c.city.desolate.ui.canvas.panel.GameCanvas;
import c.city.desolate.ui.shape.BallShape;
import c.city.desolate.ui.shape.EDir;
import c.city.desolate.ui.shape.EmitterShape;

public class EmitterMouseClickedAdapter extends MouseAdapter {

	private EmitterShape emitter;

	public EmitterMouseClickedAdapter(EmitterShape emitter) {
		this.emitter = emitter;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// 如果该发射器已经有连线则点击无效
		if (ImgSelector.inPath(emitter).size() != 0) {
			return;
		}

		if (GameControl.gi().isSound()) {
			SoundControl.play(Define.Sound.emitter_ball_sound);
		}

		MapCanvas map = ((GameCanvas) GameControl.gi().getCurrGameCanvas()).getMapCanvas();

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
		map.addCanvas(ball);

		ball.dir = dir;
		ball.type = emitter.bean.type;
		ball.path.emitter = emitter;

		ListenerControl.gi().loopRemoveCanvasListener(map);
		// ListenerControl.gi().closeAllMapListener();

	}

}
