package com.jhs.open.bean;


/**
 * 挡板Bean
 * 
 * @author JHS
 * 
 */
public class MirrorBean {
	public static final String LEFT = "L";
	public static final String RIGHT = "R";

	public String iniType;
	public String type;// 类型，左挡板和右挡板两种，以挡板头所指的方向为准
	public int x;
	public int y;
	public boolean isLock = false;
}
