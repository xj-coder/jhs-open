package com.jhs.open;

import java.io.File;
import java.util.HashMap;

public class Define {
	public static String PRJ_PATH = new File("").getAbsolutePath() + File.separator;
	public static String RES_PATH = PRJ_PATH + "res" + File.separator;
	public static String MAP_PATH = RES_PATH + "map" + File.separator + "map.xml";
	public static String IMG_PATH = RES_PATH + "pic" + File.separator;
	public static String SOUND_PATH = RES_PATH + "music" + File.separator;

	// static {
	// PRJ_PATH = new File("").getAbsolutePath() + File.separator;
	// RES_PATH = PRJ_PATH + "res" + File.separator;
	// MAP_PATH = RES_PATH + "map" + File.separator + "map.xml";
	// }

	public static boolean FPS = true;
	public static boolean DEBUG = true;

	public static final class Main {
		public static final int width = 640;
		public static final int height = 480;

		public static final int grid_size = 30;
		public static final float path_size = 3.5f;

	}

	public static final class MainPanel {
		public static final String bg_image_path = IMG_PATH + "bg.png";

		public static final int help_button_height = 55;
		public static final int help_button_width = 120;
		public static final int help_button_x = 270;
		public static final int help_button_y = 270;

		public static final int music_button_height = 55;
		public static final int music_button_width = 120;
		public static final int music_button_x = 200;
		public static final int music_button_y = 180;

		public static final int sound_button_height = 55;
		public static final int sound_button_width = 120;
		public static final int sound_button_x = 350;
		public static final int sound_button_y = 180;

		public static final int start_button_height = 55;
		public static final int start_button_width = 120;
		public static final int start_button_x = 270;
		public static final int start_button_y = 90;
	}

	public static final class MenuPanel {
		public static final String bg_image_path = IMG_PATH + "menuBG.png";
		public static final String card_white_image_path = IMG_PATH + "card1.png";
		public static final String card_black_image_path = IMG_PATH + "card2.png";

		public static final int border_size = 2;
		public static final int offset_size = 3;
		public static final int card_size = 20;
		public static final int card_col = 10;

		public static final int back_button_height = 55;
		public static final int back_button_width = 120;
		public static final int back_button_x = 500;
		public static final int back_button_y = 10;
	}

	public static final class GamePanel {
		public static final String bg_image_path = IMG_PATH + "gameBG.png";

		public static final int menu_canvas_x = 0;
		public static final int menu_canvas_y = (Define.Main.height - Define.GamePanel.menu_canvas_height) / 2;
		public static final int menu_canvas_width = 100;
		public static final int menu_canvas_height = 200;

		public static final int info_canvas_x = 0;
		public static final int info_canvas_y = 0;
		public static final int info_canvas_width = Main.width;
		public static final int info_canvas_height = 100;

		public static final int tool_canvas_width = 100;
		public static final int tool_canvas_height = 200;
		public static final int tool_canvas_x = Define.Main.width - Define.GamePanel.tool_canvas_width;
		public static final int tool_canvas_y = (Define.Main.height - Define.GamePanel.menu_canvas_height) / 2;

		public static final int map_canvas_width = Main.width;
		public static final int map_canvas_height = Main.height;

		public static final class Tool {
			public static final int lock_button_x = 20;
			public static final int lock_button_y = 15;
			public static final int lock_button_height = 60;
			public static final int lock_button_width = 60;

			public static final int unlock_button_x = 20;
			public static final int unlock_button_y = lock_button_height + 65;
			public static final int unlock_button_height = 60;
			public static final int unlock_button_width = 60;

		}

		public static final class Menu {
			public static final int reset_button_height = 30;
			public static final int reset_button_width = 60;
			public static final int reset_button_x = 20;
			public static final int reset_button_y = 15;

			public static final int music_button_height = 30;
			public static final int music_button_width = 60;
			public static final int music_button_x = 20;
			public static final int music_button_y = reset_button_height + 30;

			public static final int sound_button_height = 30;
			public static final int sound_button_width = 60;
			public static final int sound_button_x = 20;
			public static final int sound_button_y = reset_button_height + music_button_height + 45;

			public static final int menu_button_height = 30;
			public static final int menu_button_width = 60;
			public static final int menu_button_x = 20;
			public static final int menu_button_y = reset_button_height + music_button_height + sound_button_height
					+ 60;
		}
	}

	public static final class Receiver {
		public static final HashMap<String, String> typeMap = new HashMap<String, String>();

		static {
			typeMap.put("red", "receiver_red");
			typeMap.put("green", "receiver_green");
			typeMap.put("blue", "receiver_blue");
			typeMap.put("yellow", "receiver_yellow");
		}

	}

	public static final class Emitter {
		public static final HashMap<String, String> typeMap = new HashMap<String, String>();

		static {
			typeMap.put("red", "emitter_red");
			typeMap.put("green", "emitter_green");
			typeMap.put("blue", "emitter_blue");
			typeMap.put("yellow", "emitter_yellow");
		}
	}

	public static final class Ball {
		public static final HashMap<String, String> typeMap = new HashMap<String, String>();

		static {
			typeMap.put("red", "ball_red");
			typeMap.put("green", "ball_green");
			typeMap.put("blue", "ball_blue");
			typeMap.put("yellow", "ball_yellow");
		}
	}

	public static final class Line {
		public static final int width = 15;
		public static final int height = 15;

		public static final HashMap<String, String> typeMap = new HashMap<String, String>();

		static {
			typeMap.put("red", "line_red");
			typeMap.put("green", "line_green");
			typeMap.put("blue", "line_blue");
			typeMap.put("yellow", "line_yellow");
		}
	}

	public static final class Mirror {
		public static final HashMap<String, String> typeMap = new HashMap<String, String>();

		static {
			typeMap.put("L", "mirror_l");
			typeMap.put("R", "mirror_r");
		}
	}

	public static final class Button {
		public static final String lock_bg_path = IMG_PATH + "lock_bg.png";
		public static final String unlock_bg_path = IMG_PATH + "unlock_bg.png";
		public static final String lock_line_path = IMG_PATH + "lock_line.png";
		public static final String unlock_line_path = IMG_PATH + "unlock_line.png";

		public static final String bg_path = IMG_PATH + "button_bg.png";
		public static final String bg_r_path = IMG_PATH + "button_bg_r.png";
		public static final String line_path = IMG_PATH + "button_line.png";

		public static final String menu_bg_path = IMG_PATH + "button_bg_menu.png";
		public static final String music_bg_path = IMG_PATH + "button_bg_music.png";
		public static final String sound_bg_path = IMG_PATH + "button_bg_sound.png";
		public static final String reset_bg_path = IMG_PATH + "button_bg_reset.png";

		public static final String music_fg_path = IMG_PATH + "button_fg_music.png";
		public static final String sound_fg_path = IMG_PATH + "button_fg_sound.png";
		public static final String back_fg_path = IMG_PATH + "button_fg_back.png";
		public static final String help_fg_path = IMG_PATH + "button_fg_help.png";
		public static final String start_fg_path = IMG_PATH + "button_fg_start.png";
	}

	public static final class Sound {
		public static final String bg_sound = SOUND_PATH + "bg_music.mp3";
		public static final String ball_out_sound = SOUND_PATH + "ball_out.mp3";
		public static final String button_click_sound = SOUND_PATH + "button_click.mp3";
		public static final String emitter_ball_sound = SOUND_PATH + "emitter_ball.mp3";
		public static final String mirrot_change_sound = SOUND_PATH + "mirror_change.mp3";
		public static final String path_bad_sound = SOUND_PATH + "path_bad.mp3";
		public static final String path_right_sound = SOUND_PATH + "path_right.mp3";
		public static final String touch_mirror_sound = SOUND_PATH + "touch_mirror.mp3";
		public static final String win_sound = SOUND_PATH + "win.mp3";
	}

	public static final class WinCanvas {
		public static final String win_text = "胜利";
		public static final String click_text = "点击继续";

		public static final int move_speed = 10;

		public static final int width = 300;
		public static final int height = 120;
	}
}
