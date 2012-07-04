package com.jhs.open.client;

import com.jhs.open.client.control.GameControl;
import com.jhs.open.client.ui.LoginFrame;

/**
 * 三国杀游戏启动类
 * 
 * @author JHS
 * 
 */
public class SGSGame {
	public static void main(String[] args) {
		GameControl.gi().begin();
		LoginFrame.gi().showUI();
	}
}
