package com.jhs.open.control;

import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.jhs.open.tool.CanvasSearcher;
import com.jhs.open.ui.Canvas;
import com.jhs.open.ui.LabyrexFrame;

/**
 * 事件监听管理类
 * 
 * @author JHS
 * 
 */
public class ListenerControl {
	private final HashMap<Canvas, Vector<MouseAdapter>> mouseListenerMap = new HashMap<Canvas, Vector<MouseAdapter>>();
	private final HashMap<Canvas, Vector<KeyListener>> keyListenerMap = new HashMap<Canvas, Vector<KeyListener>>();

	private ReentrantReadWriteLock readWriteLock;

	private static ListenerControl gi;

	public static ListenerControl gi() {
		if (gi == null) {
			gi = new ListenerControl();
		}
		return gi;
	}

	private ListenerControl() {
	}

	public void begin() {

		LabyrexFrame.gi().addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				loopInvokeMouseEventMethod("mouseReleased", e);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				loopInvokeMouseEventMethod("mousePressed", e);
			}

			@Override
			public void mouseExited(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				loopInvokeMouseEventMethod("mouseClicked", e);
			}
		});
		LabyrexFrame.gi().addMouseMotionListener(new MouseMotionListener() {

			ArrayList<Canvas> enterCanvas = new ArrayList<Canvas>();

			@Override
			public void mouseMoved(MouseEvent e) {
				List<Canvas> canvasList = CanvasSearcher.findCanvas(GameControl.gi().getCurrCanvas(), e.getX(), e
						.getY());

				if (enterCanvas.size() > 0) {
					for (int i = 0; i < enterCanvas.size(); i++) {
						if (!canvasList.contains(enterCanvas.get(i))) {
							loopInvokeMouseEventMethod(enterCanvas.get(i), "mouseExited", e);
							enterCanvas.remove(i);
							i--;
						}
					}
				}
				if (canvasList != null && canvasList.size() > 0) {
					for (int i = 0; i < canvasList.size(); i++) {
						if (!enterCanvas.contains(canvasList.get(i))) {
							loopInvokeMouseEventMethod(canvasList.get(i), "mouseEntered", e);
							enterCanvas.add(canvasList.get(i));
						}
					}
				}
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				loopInvokeMouseEventMethod("mouseDragged", e);
			}
		});
		LabyrexFrame.gi().addMouseWheelListener(new MouseWheelListener() {

			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				loopInvokeMouseEventMethod("mouseWheelMoved", e);
			}
		});

	}

	private void loopInvokeMouseEventMethod(Canvas canvas, String methodName, MouseEvent e) {
		getReadWriteLock().writeLock().lock();
		try {
			Vector<MouseAdapter> adapters = mouseListenerMap.get(canvas);
			if (adapters != null) {
				for (int i = 0; i < adapters.size(); i++) {
					MouseAdapter adapter = adapters.get(i);
					try {
						Method method = adapter.getClass().getMethod(methodName, MouseEvent.class);
						method.invoke(adapter, e);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		} finally {
			getReadWriteLock().writeLock().unlock();
		}
	}

	private void loopInvokeMouseEventMethod(String methodName, MouseEvent e) {
		getReadWriteLock().writeLock().lock();
		try {

			Vector<Canvas> canvasVector = new Vector<Canvas>();

			Set<Canvas> keySet = mouseListenerMap.keySet();

			Iterator<Canvas> it = keySet.iterator();
			while (it.hasNext()) {
				Canvas key = it.next();
				if (key.isMouseIn(e.getX(), e.getY())) {
					canvasVector.add(key);
				}
			}

			for (int i = 0; i < canvasVector.size(); i++) {
				Vector<MouseAdapter> adapters = mouseListenerMap.get(canvasVector.get(i));
				if (adapters != null) {
					for (int j = 0; j < adapters.size(); j++) {
						MouseAdapter adapter = adapters.get(j);
						try {
							Method method = adapter.getClass().getMethod(methodName, MouseEvent.class);
							method.invoke(adapter, e);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		} finally {
			getReadWriteLock().writeLock().unlock();
		}
	}

	public void registMouseListener(Canvas owner, MouseAdapter listener) {
		getReadWriteLock().writeLock().lock();
		try {
			Vector<MouseAdapter> listeners = mouseListenerMap.get(owner);
			if (listeners == null) {
				listeners = new Vector<MouseAdapter>();
				mouseListenerMap.put(owner, listeners);
			}
			listeners.add(listener);
		} finally {
			getReadWriteLock().writeLock().unlock();
		}
	}

	public void removeMouseListener(Canvas owner) {
		getReadWriteLock().writeLock().lock();
		try {
			mouseListenerMap.remove(owner);
		} finally {
			getReadWriteLock().writeLock().unlock();
		}
	}

	public void registKeyListener(Canvas owner, KeyListener listener) {
		getReadWriteLock().writeLock().lock();
		try {
			Vector<KeyListener> listeners = keyListenerMap.get(owner);
			if (listeners == null) {
				listeners = new Vector<KeyListener>();
				keyListenerMap.put(owner, listeners);
			}
			listeners.add(listener);
		} finally {
			getReadWriteLock().writeLock().unlock();
		}
	}

	public void removeKeyListener(Canvas owner) {
		getReadWriteLock().writeLock().lock();
		try {
			keyListenerMap.remove(owner);
		} finally {
			getReadWriteLock().writeLock().unlock();
		}
	}

	public void loopRemoveCanvasListener(Canvas canvas) {
		ListenerControl.gi().removeMouseListener(canvas);
		ListenerControl.gi().removeKeyListener(canvas);

		for (int i = 0; i < canvas.getCanvasCount(); i++) {
			loopRemoveCanvasListener(canvas.getCanvas(i));
		}
	}

	public void loopRegistCanvasListener(Canvas canvas) {
		for (int i = 0; i < canvas.getMouseListeners().size(); i++) {
			ListenerControl.gi().registMouseListener(canvas, canvas.getMouseListeners().get(i));
		}
		for (int i = 0; i < canvas.getKeyListeners().size(); i++) {
			ListenerControl.gi().registKeyListener(canvas, canvas.getKeyListeners().get(i));
		}

		for (int i = 0; i < canvas.getCanvasCount(); i++) {
			loopRegistCanvasListener(canvas.getCanvas(i));
		}
	}

	public ReentrantReadWriteLock getReadWriteLock() {
		if (readWriteLock == null) {
			readWriteLock = new ReentrantReadWriteLock();
		}
		return readWriteLock;
	}
}
