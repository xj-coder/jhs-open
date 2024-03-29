package com.jhs.open.control;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.SourceDataLine;

import com.jhs.open.Define;

/**
 * 声音管理器
 * 
 * @author JHS
 * 
 */
public class SoundControl {

	private static HashMap<String, Mp3Date> soundMap = new HashMap<String, Mp3Date>();

	private static ReentrantReadWriteLock readWriteLock;

	public static float voiceNum = 0;

	static {
		loadSound(Define.Sound.bg_sound);
		loadSound(Define.Sound.ball_out_sound);
		loadSound(Define.Sound.button_click_sound);
		loadSound(Define.Sound.emitter_ball_sound);
		loadSound(Define.Sound.mirrot_change_sound);
		loadSound(Define.Sound.path_bad_sound);
		loadSound(Define.Sound.path_right_sound);
		loadSound(Define.Sound.touch_mirror_sound);
		loadSound(Define.Sound.win_sound);
	}

	private static Mp3Date loadSound(String srcPath) {
		Mp3Date mp3Date = soundMap.get(srcPath);

		if (mp3Date == null) {
			mp3Date = new Mp3Date();
			soundMap.put(srcPath, mp3Date);

			AudioInputStream m_audioInputStream = null;
			AudioFormat sourceFormat = null;
			try {
				File file = new File(srcPath);
				m_audioInputStream = AudioSystem.getAudioInputStream(file);
				sourceFormat = m_audioInputStream.getFormat();

				if (sourceFormat.getEncoding() != AudioFormat.Encoding.PCM_SIGNED) {
					AudioFormat newFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
							sourceFormat.getSampleRate(), 16, sourceFormat.getChannels(),
							sourceFormat.getChannels() * 2, sourceFormat.getSampleRate(), false);
					AudioInputStream newStream = AudioSystem.getAudioInputStream(newFormat, m_audioInputStream);
					sourceFormat = newFormat;
					m_audioInputStream = newStream;
				}

				mp3Date.audioFormat = sourceFormat;

				int bufferSize = (int) sourceFormat.getSampleRate() * sourceFormat.getFrameSize();
				byte[] buffer = new byte[bufferSize];

				int bytesRead = 0;
				while (bytesRead >= 0) {
					bytesRead = m_audioInputStream.read(buffer, 0, buffer.length);
					if (bytesRead >= 0) {
						mp3Date.date.add(Arrays.copyOfRange(buffer, 0, bytesRead));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return mp3Date;
	}

	/**
	 * 播放音频文件
	 * 
	 * @param srcPath
	 *            音频文件
	 * @param loopTimes
	 *            循环播放次数,-1表示无限循环
	 */
	public static void play(final String srcPath, final int loopTimes) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				writeLock();
				Mp3Date date = loadSound(srcPath);
				try {
					DataLine.Info info = new DataLine.Info(SourceDataLine.class, date.audioFormat);

					FloatControl volctrl = null;
					for (int i = 0; i < AudioSystem.getMixerInfo().length; i++) {
						if (AudioSystem.getMixer(AudioSystem.getMixerInfo()[i]).isLineSupported(info)
								&& AudioSystem.getMixer(AudioSystem.getMixerInfo()[i]).getLine(info)
										.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
							date.dateLine = (SourceDataLine) AudioSystem.getMixer(AudioSystem.getMixerInfo()[i])
									.getLine(info);
							volctrl = (FloatControl) date.dateLine.getControl(FloatControl.Type.MASTER_GAIN);
							break;
						}
					}

					date.dateLine.open(date.audioFormat, date.dateLine.getBufferSize());
					date.dateLine.start();

					if (voiceNum > volctrl.getMaximum()) {
						voiceNum = volctrl.getMaximum();
					}
					if (voiceNum < volctrl.getMinimum()) {
						voiceNum = volctrl.getMinimum();
					}
					System.out.println(voiceNum);
					volctrl.setValue(voiceNum);

					date.loopTimes = loopTimes;
					date.isPlay = true;

					writeUnlock();

					while (date.loopTimes != 0) {
						date.loopTimes--;

						for (int i = 0; i < date.date.size(); i++) {
							if (!date.isPlay) {
								date.dateLine.flush();
								break;
							}
							date.dateLine.write(date.date.get(i), 0, date.date.get(i).length);
						}
						date.dateLine.drain();
					}

					date.dateLine.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	public static void incVoice() {
		voiceNum += 1f;
	}

	public static void decVoice() {
		voiceNum -= 1f;
	}

	/**
	 * 播放音频文件
	 * 
	 * @param srcPath
	 *            音频文件
	 */
	public static void play(String srcPath) {
		play(srcPath, 1);
	}

	/**
	 * 停止播放
	 * 
	 * @param srcPath
	 *            音频文件
	 */
	public static void stop(String srcPath) {
		writeLock();
		Mp3Date date = loadSound(srcPath);
		if (date.dateLine != null) {
			date.loopTimes = 0;
			date.isPlay = false;
		}
		writeUnlock();
	}

	/**
	 * 暂停播放
	 * 
	 * @param srcPath
	 *            音频文件
	 */
	public static void pause(String srcPath) {
		writeLock();
		Mp3Date date = loadSound(srcPath);
		if (date.dateLine != null && date.isPlay) {
			// if (date.dateLine != null && date.dateLine.isRunning()) {
			date.dateLine.stop();
			date.isPlay = false;
		}
		writeUnlock();
	}

	/**
	 * 继续播放
	 * 
	 * @param srcPath
	 *            音频文件
	 */
	public static void go(String srcPath) {
		writeLock();
		Mp3Date date = loadSound(srcPath);
		if (date.dateLine != null && !date.isPlay) {
			// if (date.dateLine != null && !date.dateLine.isRunning()) {
			date.dateLine.start();
			date.isPlay = true;
		}
		writeUnlock();
	}

	private static void writeLock() {
		getReadWriteLock().writeLock().lock();
	}

	private static void writeUnlock() {
		getReadWriteLock().writeLock().unlock();
	}

	private static ReentrantReadWriteLock getReadWriteLock() {
		if (readWriteLock == null) {
			readWriteLock = new ReentrantReadWriteLock(true);
		}
		return readWriteLock;
	}
}

class Mp3Date {
	AudioFormat audioFormat;
	Vector<byte[]> date = new Vector<byte[]>();
	SourceDataLine dateLine;
	int loopTimes = 1;
	boolean isPlay = false;
}
