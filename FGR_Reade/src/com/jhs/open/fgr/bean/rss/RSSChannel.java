package com.jhs.open.fgr.bean.rss;

import java.util.ArrayList;

public class RSSChannel {
	/**
	 * Ƶ������Ҫ������
	 */
	public String title;
	/**
	 * ���Ƶ�������� Web վ�����վ������� URL
	 */
	public String link;
	/**
	 * ��Ҫ���ܸ�Ƶ������ʲô�ġ�
	 */
	public String description;
	/**
	 * ����
	 */
	public String language;
	/**
	 * ��Ȩ
	 */
	public String copyright;
	/**
	 * 
	 */
	public String managingEditor;
	/**
	 * 
	 */
	public String webMaster;
	/**
	 * ����ʱ��
	 */
	public String pubDate;
	/**
	 * ������ʱ��
	 */
	public String lastBuildDate;
	/**
	 * ����
	 */
	public String category;
	/**
	 * 
	 */
	public String generator;
	/**
	 * 
	 */
	public String docs;
	/**
	 * �������ע��Ϊ��cloud����Ƶ������ʱ֪ͨ����Ϊ RSS ��Ҫʵ����һ���������ķ���-����Э�顣
	 */
	public String cloud;
	/**
	 * ���ʱ�� ��һ�����֣���ʾ��Ҫ��ˢ��֮ǰ����ķ�������
	 */
	public String ttl;
	/**
	 * ���ڸ�Ƶ���� PICS ���ۡ�
	 */
	public String rating;
	/**
	 * �������Ƶ��һ����ʾ�������
	 */
	public String textInput;
	/**
	 * ���߾ۼ�����ЩСʱ�ĸ��¿��Ժ��ԡ�
	 */
	public String skipHours;
	/**
	 * ���߾ۼ�����һ��ĸ��¿��Ժ��ԡ�
	 */
	public String skipDays;

	public ArrayList<RSSItem> itemBeans = new ArrayList<RSSItem>();

	public class Image {
		/**
		 * ��ʾ��Ƶ���� GIF��JPEG �� PNG ͼ��� URL��
		 */
		public String url;
		/**
		 * ͼ�����������Ƶ���� HTML ����ʱ������ HTML <image> ��ǩ�� ALT ���ԡ�
		 */
		public String title;
		/**
		 * վ��� URL�����Ƶ���� HTML ���֣���ͼ����Ϊ�����վ������ӡ�
		 */
		public String link;
		/**
		 * ���֣���ʾͼ������ؿ�ȣ����ֵ�� 188��Ĭ��ֵΪ 88��
		 */
		public int width = 88;
		/**
		 * ���֣���ʾͼ������ظ߶ȡ����ֵ�� 400��Ĭ��ֵΪ 31��
		 */
		public int height = 31;
		/**
		 * �����ı����ڳ���ʱ������ΪΧ���Ÿ�ͼ���γɵ�����Ԫ�ص� title ���ԡ�
		 */
		public String description;

	}
}
