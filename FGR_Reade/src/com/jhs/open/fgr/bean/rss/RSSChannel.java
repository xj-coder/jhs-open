package com.jhs.open.fgr.bean.rss;

import java.util.ArrayList;

public class RSSChannel {
	/**
	 * 频道或提要的名称
	 */
	public String title;
	/**
	 * 与该频道关联的 Web 站点或者站点区域的 URL
	 */
	public String link;
	/**
	 * 简要介绍该频道是做什么的。
	 */
	public String description;
	/**
	 * 语言
	 */
	public String language;
	/**
	 * 版权
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
	 * 发布时间
	 */
	public String pubDate;
	/**
	 * 最后编译时间
	 */
	public String lastBuildDate;
	/**
	 * 分类
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
	 * 允许进程注册为“cloud”，频道更新时通知它，为 RSS 提要实现了一种轻量级的发布-订阅协议。
	 */
	public String cloud;
	/**
	 * 存活时间 是一个数字，表示提要在刷新之前缓冲的分钟数。
	 */
	public String ttl;
	/**
	 * 关于该频道的 PICS 评价。
	 */
	public String rating;
	/**
	 * 定义可与频道一起显示的输入框。
	 */
	public String textInput;
	/**
	 * 告诉聚集器哪些小时的更新可以忽略。
	 */
	public String skipHours;
	/**
	 * 告诉聚集器那一天的更新可以忽略。
	 */
	public String skipDays;

	public ArrayList<RSSItem> itemBeans = new ArrayList<RSSItem>();

	public class Image {
		/**
		 * 表示该频道的 GIF、JPEG 或 PNG 图像的 URL。
		 */
		public String url;
		/**
		 * 图象的描述。当频道以 HTML 呈现时，用作 HTML <image> 标签的 ALT 属性。
		 */
		public String title;
		/**
		 * 站点的 URL。如果频道以 HTML 呈现，该图像作为到这个站点的链接。
		 */
		public String link;
		/**
		 * 数字，表示图象的像素宽度，最大值是 188，默认值为 88。
		 */
		public int width = 88;
		/**
		 * 数字，表示图象的像素高度。最大值是 400，默认值为 31。
		 */
		public int height = 31;
		/**
		 * 包含文本，在呈现时可以作为围绕着该图像形成的链接元素的 title 属性。
		 */
		public String description;

	}
}
