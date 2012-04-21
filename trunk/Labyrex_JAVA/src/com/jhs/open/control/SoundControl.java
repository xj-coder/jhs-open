package com.jhs.open.control;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
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
			AudioFormat audioFormat = null;
			try {
				File file = new File(srcPath);
				m_audioInputStream = AudioSystem.getAudioInputStream(file);
				audioFormat = m_audioInputStream.getFormat();

				if (audioFormat.getEncoding() != AudioFormat.Encoding.PCM_SIGNED) {
					AudioFormat newFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, audioFormat
							.getSampleRate(), 16, audioFormat.getChannels(), audioFormat.getChannels() * 2, audioFormat
							.getSampleRate(), false);
					AudioInputStream newStream = AudioSystem.getAudioInputStream(newFormat, m_audioInputStream);
					audioFormat = newFormat;
					m_audioInputStream = newStream;
				}

				mp3Date.audioFormat = audioFormat;

				// DataLine.Info info = new DataLine.Info(SourceDataLine.class, mp3Date.audioFormat);
				// SourceDataLine m_line = (SourceDataLine) AudioSystem.getLine(info);
				// mp3Date.dateLine = m_line;

				int bufferSize = (int) audioFormat.getSampleRate() * audioFormat.getFrameSize();
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
				System.out.println("Sound Thread running begin  " + srcPath);
				Mp3Date date = loadSound(srcPath);
				try {
					DataLine.Info info = new DataLine.Info(SourceDataLine.class, date.audioFormat);
					SourceDataLine m_line = (SourceDataLine) AudioSystem.getLine(info);
					date.dateLine = m_line;

					m_line.open(date.audioFormat, m_line.getBufferSize());
					m_line.start();
					date.loopTimes = loopTimes;
					date.isPlay = true;

					while (date.loopTimes != 0) {
						date.loopTimes--;

						for (int i = 0; i < date.date.size(); i++) {
							if (!date.isPlay) {
								m_line.flush();
								break;
							}
							m_line.write(date.date.get(i), 0, date.date.get(i).length);
						}
						m_line.drain();
					}

					m_line.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println("Sound Thread running end  " + srcPath);
			}
		}).start();
		System.out.println("Sound Thread Start  " + srcPath);
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
		Mp3Date date = loadSound(srcPath);
		if (date.dateLine != null) {
			date.loopTimes = 0;
			date.isPlay = false;
		}
	}

	/**
	 * 暂停播放
	 * 
	 * @param srcPath
	 *            音频文件
	 */
	public static void pause(String srcPath) {
		Mp3Date date = loadSound(srcPath);
		if (date.dateLine != null && date.dateLine.isRunning()) {
			date.dateLine.stop();
		}
	}

	/**
	 * 继续播放
	 * 
	 * @param srcPath
	 *            音频文件
	 */
	public static void go(String srcPath) {
		Mp3Date date = loadSound(srcPath);
		if (date.dateLine != null && !date.dateLine.isRunning()) {
			date.dateLine.start();
		}
	}
}

class Mp3Date {
	AudioFormat audioFormat;
	Vector<byte[]> date = new Vector<byte[]>();
	SourceDataLine dateLine;
	int loopTimes = 1;
	boolean isPlay = false;
}
