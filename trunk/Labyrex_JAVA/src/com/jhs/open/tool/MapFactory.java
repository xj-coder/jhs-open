package com.jhs.open.tool;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.jhs.open.Define;
import com.jhs.open.bean.EmitterBean;
import com.jhs.open.bean.MapBean;
import com.jhs.open.bean.MirrorBean;
import com.jhs.open.bean.PathBean;
import com.jhs.open.bean.ReceiverBean;

/**
 * 关卡生成器，用来随即生成关卡、验证关卡可行性
 * 
 * @author JHS
 * 
 */
public class MapFactory {

	/**
	 * 随即生成关卡
	 * 
	 * @param width
	 *            关卡宽度
	 * @param height
	 *            关卡高度
	 * @param emitterNum
	 *            发射器数量
	 * @param receiverNum
	 *            接收器数量
	 * @param ratio
	 *            难度系数
	 * @return
	 */
	private static MapBean findMap = null;
	private static int nextCount = 1;
	private static boolean threadRun = false;;

	public static MapBean getRandomMap(int width, int height, int emitterNum, int ratio) {
		findMap = null;
		final MapBean map = new MapBean();
		map.width = width;
		map.height = height;

		for (int i = 0; i < emitterNum; i++) {
			EmitterBean emitter = getRandomEmitter(map);
			ReceiverBean receiver = getRandomReceiver(map);

			String[] keys = Define.Emitter.typeMap.keySet().toArray(new String[] {});
			int index = (int) (keys.length * Math.random());
			emitter.type = keys[index];
			receiver.type = keys[index];

			map.emitterList.add(emitter);
			map.receiverList.add(receiver);
		}

		int mirrorCount = emitterNum / 2 == 0 ? 1 : emitterNum / 2;

		for (int i = 0; i < width * height; i++) {
			System.out.println("测试 挡板数量为 : " + mirrorCount + " 的情况");
			ArrayList<MirrorBean> mirrorList = new ArrayList<MirrorBean>();
			for (int j = 0; j < mirrorCount; j++) {
				MirrorBean bean = new MirrorBean();

				int r = (int) (Math.random() * 10) % 2;
				if (r == 0) {
					bean.type = MirrorBean.LEFT;
				} else {
					bean.type = MirrorBean.RIGHT;
				}

				mirrorList.add(bean);
			}
			map.mirrorList = mirrorList;

			int threadCount = 1;
			System.out.println("创建线程数 ： " + threadCount);
			nextCount = 1;
			threadRun = true;

			for (int j = 0; j < threadCount; j++) {
				new Thread(new Runnable() {

					@Override
					public void run() {
						if (findMap != null) {
							return;
						}
						MapBean bean = nextMap(map);
						while (bean != null) {
							if (findMap != null) {
								return;
							}
							nextCount++;
							if (checkMap(bean).equals("")) {
								if (findMap != null) {
									return;
								}
								findMap = bean;
								threadRun = false;
							}
							bean = nextMap(map);
						}
						threadRun = false;
					}
				}).start();
			}
			while (threadRun) {
				try {
					Thread.sleep(0);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			if (findMap != null) {
				return findMap;
			}

			System.out.println("测试了 " + nextCount + " 种挡板排列组合");

			mirrorCount++;
		}
		return null;
	}

	private static synchronized MapBean nextMap(MapBean map) {
		ArrayList<MirrorBean> mirrorList = map.mirrorList;

		int mirrorIndex = 0;
		for (int i = mirrorList.size() - 1; i >= 0; i--) {
			if (mirrorList.get(i).x != map.width - 1 - mirrorIndex % map.height
					|| mirrorList.get(i).y != map.height - 1 - mirrorIndex / map.height) {
				MirrorBean mirror = mirrorList.get(i);
				if (mirror.x != map.width - 1) {
					mirror.x = mirror.x + 1;
				} else {
					mirror.x = 0;
					mirror.y = mirror.y + 1;
				}

				for (int j = i + 1; j < mirrorList.size(); j++) {
					MirrorBean m = mirrorList.get(j);
					int _x = mirror.x + (j - i);
					int _y = mirror.y;
					if (_x / map.width >= 0) {
						_y = _y + _x / map.width;
						_x = 0;
					}
					m.x = _x;
					m.y = _y;
				}

				return map.copy();
			}
			mirrorIndex++;
		}

		return null;
	}

	private static EmitterBean getRandomEmitter(MapBean map) {
		EmitterBean emitter = new EmitterBean();

		ArrayList<Point> pointList = getEARPointList(map);
		int index = (int) (Math.random() * pointList.size());
		Point point = pointList.get(index);
		emitter.x = point.x;
		emitter.y = point.y;

		return emitter;
	}

	private static ReceiverBean getRandomReceiver(MapBean map) {
		ReceiverBean receiver = new ReceiverBean();

		ArrayList<Point> pointList = getEARPointList(map);
		int index = (int) (Math.random() * pointList.size());
		Point point = pointList.get(index);
		receiver.x = point.x;
		receiver.y = point.y;

		return receiver;
	}

	private static ArrayList<Point> getEARPointList(MapBean map) {
		ArrayList<Point> list = new ArrayList<Point>();

		for (int i = 0; i < map.width; i++) {
			Point p1 = new Point();
			p1.x = i;
			p1.y = -1;
			if (!CanvasSearcher.hasBean(map, p1.x, p1.y)) {
				list.add(p1);
			}
			Point p2 = new Point();
			p2.x = i;
			p2.y = map.height;
			if (!CanvasSearcher.hasBean(map, p2.x, p2.y)) {
				list.add(p2);
			}
		}
		for (int i = 0; i < map.height; i++) {
			Point p1 = new Point();
			p1.x = -1;
			p1.y = i;
			if (!CanvasSearcher.hasBean(map, p1.x, p1.y)) {
				list.add(p1);
			}
			Point p2 = new Point();
			p2.x = map.width;
			p2.y = i;
			if (!CanvasSearcher.hasBean(map, p2.x, p2.y)) {
				list.add(p2);
			}
		}

		return list;
	}

	/**
	 * 验证地图可行性
	 * 
	 * @param map
	 * @return
	 */
	public static String checkMap(MapBean map) {
		map.backup("checkMap");

		if (map.mirrorList.size() == 0) {
			return "挡板数量不能为0";
		}
		if (map.emitterList.size() == 0) {
			return "发射器数量不能为0";
		}
		if (map.receiverList.size() == 0) {
			return "接收器数量不能为0";
		}
		if (map.emitterList.size() != map.receiverList.size()) {
			return "发射器的数量必须和接收器的数量相当";
		}

		Map<String, ArrayList<EmitterBean>> emitterMap = sortEmitterList(map.emitterList);
		Map<String, ArrayList<ReceiverBean>> receiverMap = sortReceiverList(map.receiverList);
		if (emitterMap.keySet().size() != receiverMap.keySet().size()) {
			return "发射器的类型数量与接收器的类型数量不同";

		}
		for (String key : emitterMap.keySet()) {
			ArrayList<ReceiverBean> receiverList = receiverMap.get(key);
			ArrayList<EmitterBean> emitterList = emitterMap.get(key);
			if (receiverList.size() != emitterList.size()) {
				return key + "类型的发射器和" + key + "类型的发射器数量不同";
			}
		}

		for (int i = 0; i < map.mirrorList.size(); i++) {
			MirrorBean mirror = map.mirrorList.get(i);
			mirror.type = MirrorBean.LEFT;
		}

		while (true) {
			if (check(map)) {
				map.restore("checkMap");
				return "";
			} else {
				if (nextMirror(map) == null) {
					break;
				}
			}
		}

		map.restore("checkMap");
		return "无法成功连接所有发射器、接收器和挡板";
	}

	/**
	 * 翻动挡板以达到遍历所有挡板的所有状态组合。<BR>
	 * 实现原理<BR>
	 * 挡板列表必须全部初始化成LEFT状态<BR>
	 * 从最后一个开始，翻动LEFT状态的挡板，每次只翻动一个挡板，翻动后将后面的所有挡板重新翻回到LEFT状态<BR>
	 * 放回被翻动的挡板，如果放回为NULL说明所有挡板的所有状态都翻完了。
	 */
	private static MirrorBean nextMirror(MapBean map) {
		for (int i = map.mirrorList.size() - 1; i >= 0; i--) {
			MirrorBean m = map.mirrorList.get(i);
			if (m.type.equals(MirrorBean.LEFT)) {
				m.type = MirrorBean.RIGHT;
				for (int j = i + 1; j < map.mirrorList.size(); j++) {
					map.mirrorList.get(j).type = MirrorBean.LEFT;
				}
				return m;
			}
		}
		return null;
	}

	private static boolean check(MapBean map) {
		ArrayList<PathBean> pathList = new ArrayList<PathBean>();

		for (int i = 0; i < map.emitterList.size(); i++) {
			EmitterBean emitter = map.emitterList.get(i);

			int x = emitter.x;
			int y = emitter.y;
			int x_dir = x == -1 ? 1 : x == map.width ? -1 : 0;
			int y_dir = y == -1 ? 1 : y == map.height ? -1 : 0;

			PathBean path = new PathBean();
			path.emitter = emitter;
			while (true) {
				x = x + x_dir;
				y = y + y_dir;

				if (!CanvasSearcher.inDrawableRange(x, y)) {
					break;
				}

				EmitterBean emitterBean = CanvasSearcher.hasEmitterBean(map, x, y);
				MirrorBean mirrorBean = CanvasSearcher.hasMirrorBean(map, x, y);
				ReceiverBean receiverBean = CanvasSearcher.hasReceiverBean(map, x, y);

				if (emitterBean != null) {
					break;
				}
				if (mirrorBean != null) {
					if (mirrorBean.type.equals(MirrorBean.LEFT)) {
						if (x_dir == 0) {
							if (y_dir == 1) {
								x_dir = 1;
							} else {
								x_dir = -1;
							}
							y_dir = 0;
						} else {
							if (x_dir == 1) {
								y_dir = 1;
							} else {
								y_dir = -1;
							}
							x_dir = 0;
						}
					} else {
						if (x_dir == 0) {
							if (y_dir == 1) {
								x_dir = -1;
							} else {
								x_dir = 1;
							}
							y_dir = 0;
						} else {
							if (x_dir == 1) {
								y_dir = -1;
							} else {
								y_dir = 1;
							}
							x_dir = 0;
						}
					}
					path.mirrors.add(mirrorBean);
				}
				if (receiverBean != null && receiverBean.type.equals(path.emitter.type)) {
					path.receiver = receiverBean;
					pathList.add(path);
					break;
				}
			}
		}

		if (pathList.size() != map.emitterList.size()) {
			return false;
		}

		for (int i = 0; i < map.mirrorList.size(); i++) {
			boolean in = false;
			for (int j = 0; j < pathList.size(); j++) {
				if (pathList.get(j).mirrors.indexOf(map.mirrorList.get(i)) != -1) {
					in = true;
				}
			}
			if (!in) {
				return false;
			}
		}

		return true;
	}

	private static Map<String, ArrayList<EmitterBean>> sortEmitterList(ArrayList<EmitterBean> emitterList) {
		Map<String, ArrayList<EmitterBean>> map = new HashMap<String, ArrayList<EmitterBean>>();

		for (int i = 0; i < emitterList.size(); i++) {
			EmitterBean bean = emitterList.get(i);

			ArrayList<EmitterBean> list = map.get(bean.type);
			if (list == null) {
				list = new ArrayList<EmitterBean>();
				map.put(bean.type, list);
			}
			list.add(bean);
		}

		return map;
	}

	private static Map<String, ArrayList<ReceiverBean>> sortReceiverList(ArrayList<ReceiverBean> receiverList) {
		Map<String, ArrayList<ReceiverBean>> map = new HashMap<String, ArrayList<ReceiverBean>>();

		for (int i = 0; i < receiverList.size(); i++) {
			ReceiverBean bean = receiverList.get(i);

			ArrayList<ReceiverBean> list = map.get(bean.type);
			if (list == null) {
				list = new ArrayList<ReceiverBean>();
				map.put(bean.type, list);
			}
			list.add(bean);
		}

		return map;
	}
}
