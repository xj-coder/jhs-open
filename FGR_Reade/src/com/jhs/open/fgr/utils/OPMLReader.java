package com.jhs.open.fgr.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.dom.DOMElement;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.jhs.open.fgr.bean.opml.OPML;
import com.jhs.open.fgr.bean.opml.Outline;

public class OPMLReader {

	@SuppressWarnings("unchecked")
	public static OPML readOPML(String path) throws Exception {
		OPML opml = new OPML();

		SAXReader reader = new SAXReader();
		Document doc = reader.read(new File(path));
		Element _root = doc.getRootElement();

		String xpath = "/opml/head/title";
		Element titleNode = (Element) _root.selectSingleNode(xpath);
		String title = titleNode.getText();
		opml.title = title;

		xpath = "//outline";
		List<Element> outlineNodes = _root.selectNodes(xpath);
		for (Element outlineNode : outlineNodes) {
			Outline outline = new Outline();

			outline.title = outlineNode.attributeValue("title");
			outline.text = outlineNode.attributeValue("text");
			outline.type = outlineNode.attributeValue("type");
			outline.xmlUrl = outlineNode.attributeValue("xmlUrl");
			outline.htmlUrl = outlineNode.attributeValue("htmlUrl");

			opml.outlines.add(outline);
		}

		return opml;
	}

	public static void writeOPML(OPML opml, String path) throws Exception {
		InputStream in = null;
		OutputStream out = null;

		in = new FileInputStream(new File(path));
		SAXReader saxReader = new SAXReader();
		Document document = saxReader.read(in);
		// 将整个games-config.xml文件解析出来
		Element rootNode = document.getRootElement();

		// 删除XML中的根节点，创建新的根节点
		rootNode.clearContent();

		addHeadNode(opml, rootNode);
		addBodyNode(opml, rootNode);

		out = new FileOutputStream(new File(path));
		XMLWriter writer = new XMLWriter(out);
		writer.write(document);
	}

	private static void addBodyNode(OPML opml, Element rootNode) {
		Element bodyNode = new DOMElement("body");

		for (int i = 0; i < opml.outlines.size(); i++) {
			Outline outline = opml.outlines.get(i);
			Element outlineNode = new DOMElement("outline");

			if (outline.text != null)
				outlineNode.addAttribute("text", outline.text);
			if (outline.title != null)
				outlineNode.addAttribute("title", outline.title);
			if (outline.type != null)
				outlineNode.addAttribute("type", outline.type);
			if (outline.xmlUrl != null)
				outlineNode.addAttribute("xmlUrl", outline.xmlUrl);
			if (outline.htmlUrl != null)
				outlineNode.addAttribute("htmlUrl", outline.htmlUrl);

			bodyNode.add(outlineNode);
		}

		rootNode.add(bodyNode);
	}

	private static void addHeadNode(OPML opml, Element rootNode) {
		Element headNode = new DOMElement("head");

		Element titleNode = new DOMElement("title");
		titleNode.setText(opml.title);

		headNode.add(titleNode);

		rootNode.add(headNode);
	}
}
