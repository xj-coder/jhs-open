package c.city.desolate.control;

import c.city.desolate.tool.CanvasSearcher;
import c.city.desolate.ui.Canvas;
import c.city.desolate.ui.LabyrexFrame;

import java.awt.event.*;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 事件监听管理类
 * 
 * @author JHS
 * 
 */
public class ListenerControl {
	private final HashMap<Canvas, Vector<MouseAdapter>> mouseListenerMap = new HashMap<Canvas, Vector<MouseAdapter>>();
	private final HashMap<Canvas, Vector<KeyListener>> keyListenerMap = new HashMap<Canvas, Vector<KeyListener>>();

	private static ListenerControl gi;

	public static ListenerControl gi() {
		if (gi == null) {
			gi = new ListenerControl();
		}
		return gi;
	}

	private ListenerControl() {
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
				List<Canvas> canvasList = CanvasSearcher.findCanvas(GameControl.gi().getCurrGameCanvas(), e.getX(), e
						.getY());

				if (enterCanvas.size() > 0) {
					for (int i = 0; i < enterCanvas.size(); i++) {
						if (!canvasList.contains(enterCanvas.get(i))) {
							// System.out.println(enterCanvas.get(i) + "   mouseExited");
							loopInvokeMouseEventMethod(enterCanvas.get(i), "mouseExited", e);
							enterCanvas.remove(i);
							i--;
						}
					}
				}
				if (canvasList != null && canvasList.size() > 0) {
					for (int i = 0; i < canvasList.size(); i++) {
						if (!enterCanvas.contains(canvasList.get(i))) {
							// System.out.println(canvasList.get(i) + "   mouseEntered");
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
	}

	private void loopInvokeMouseEventMethod(String methodName, MouseEvent e) {
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
						System.out.println(canvasVector.get(i) + " -- " + methodName);
						Method method = adapter.getClass().getMethod(methodName, MouseEvent.class);
						method.invoke(adapter, e);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		}
	}

	public synchronized void registMouseListener(Canvas owner, MouseAdapter listener) {
		Vector<MouseAdapter> listeners = mouseListenerMap.get(owner);
		if (listeners == null) {
			listeners = new Vector<MouseAdapter>();
			mouseListenerMap.put(owner, listeners);
		}
		listeners.add(listener);
	}

	public synchronized void removeMouseListener(Canvas owner) {
		mouseListenerMap.remove(owner);
	}

	public synchronized void registKeyListener(Canvas owner, KeyListener listener) {
		Vector<KeyListener> listeners = keyListenerMap.get(owner);
		if (listeners == null) {
			listeners = new Vector<KeyListener>();
			keyListenerMap.put(owner, listeners);
		}
		listeners.add(listener);
	}

	public synchronized void removeKeyListener(Canvas owner) {
		keyListenerMap.remove(owner);
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
}
