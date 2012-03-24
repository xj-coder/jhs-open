package c.city.desolate.test;

import c.city.desolate.Define;
import c.city.desolate.control.SoundControl;

public class TestMp3 {
	public static void main(String[] args) {
		SoundControl.play(Define.SOUND_PATH + "ad.mp3", -1);

		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				SoundControl.go(Define.SOUND_PATH + "ad.mp3");
				SoundControl.pause(Define.SOUND_PATH + "ad.mp3");
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				SoundControl.pause(Define.SOUND_PATH + "ad.mp3");
				SoundControl.go(Define.SOUND_PATH + "ad.mp3");
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				SoundControl.stop(Define.SOUND_PATH + "ad.mp3");
			}
		}).start();
	}
}
