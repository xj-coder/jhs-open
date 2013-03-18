package com.jhs.open.fgr.bean.rss;

public class RSSItem {
	/**
	 * 这是项的名称，在标准应用中被转换成 HTML 中的标题。
	 */
	public String title;
	/**
	 * 这是该项的 URL。title 通常作为一个链接，指向包含在 <link> 元素中的 URL。
	 */
	public String link;
	/**
	 * 通常作为 link 中所指向的 URL 的摘要或者补充。
	 */
	public String description;
	/**
	 * 作者的 e-mail 地址。
	 */
	public String author;
	/**
	 * 支持有组织的记录。
	 */
	public String category;
	/**
	 * 关于项的注释页的 URL。
	 */
	public String comments;
	/**
	 * 支持和该项有关的媒体对象。
	 */
	public String enclosure;
	/**
	 * 唯一与该项联系在一起的永久性链接。
	 */
	public String guid;
	/**
	 * 该项是什么时候发布的。
	 */
	public String pubDate;
	/**
	 * 该项来自哪个 RSS 频道，当把项聚合在一起时非常有用。
	 */
	public String source;
	/**
	 * 已读标记
	 */
	public boolean isRead = false;
	/**
	 * 收藏标记
	 */
	public boolean isStarred = false;
	/**
	 * 下载标记
	 */
	public boolean isDownload = false;
}
