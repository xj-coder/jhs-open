package com.jhs.open.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.jhs.open.Define;

public class TestMapXML {

	public static void main(String[] args) {

		InputStream in = null;
		try {
			in = new FileInputStream(new File(Define.MAP_PATH));
			SAXReader saxReader = new SAXReader();
			Document document = saxReader.read(in);
			// 将整个games-config.xml文件解析出来
			Element _root = document.getRootElement();

			System.out.println(_root.selectObject("//group[@name='easy']//map[@name='2']"));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
