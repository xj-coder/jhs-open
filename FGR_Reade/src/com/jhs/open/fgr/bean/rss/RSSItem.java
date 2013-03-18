package com.jhs.open.fgr.bean.rss;

public class RSSItem {
	/**
	 * ����������ƣ��ڱ�׼Ӧ���б�ת���� HTML �еı��⡣
	 */
	public String title;
	/**
	 * ���Ǹ���� URL��title ͨ����Ϊһ�����ӣ�ָ������� <link> Ԫ���е� URL��
	 */
	public String link;
	/**
	 * ͨ����Ϊ link ����ָ��� URL ��ժҪ���߲��䡣
	 */
	public String description;
	/**
	 * ���ߵ� e-mail ��ַ��
	 */
	public String author;
	/**
	 * ֧������֯�ļ�¼��
	 */
	public String category;
	/**
	 * �������ע��ҳ�� URL��
	 */
	public String comments;
	/**
	 * ֧�ֺ͸����йص�ý�����
	 */
	public String enclosure;
	/**
	 * Ψһ�������ϵ��һ������������ӡ�
	 */
	public String guid;
	/**
	 * ������ʲôʱ�򷢲��ġ�
	 */
	public String pubDate;
	/**
	 * ���������ĸ� RSS Ƶ����������ۺ���һ��ʱ�ǳ����á�
	 */
	public String source;
	/**
	 * �Ѷ����
	 */
	public boolean isRead = false;
	/**
	 * �ղر��
	 */
	public boolean isStarred = false;
	/**
	 * ���ر��
	 */
	public boolean isDownload = false;
}
