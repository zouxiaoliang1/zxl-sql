package com.zxl.xml;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.zxl.session.Configuration;

/**
 * Created by zouxiaoliang on 2018/4/21.
 */
public class XMLConfigBuilder {

	private boolean isParsed = false;

	private InputStream inputStream;

	public XMLConfigBuilder(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public Configuration parse() {
		SAXReader saxReader = new SAXReader();
		Document document;
		try {
			document = saxReader.read(inputStream);
			Element root = document.getRootElement();
			Element prosElm = root.element("properties");
			List<Element> proElms = prosElm.elements();
			Configuration config = new Configuration();
			Properties properties = new Properties();
			config.setProperties(properties);
			for (Element proElm : proElms) {
				String name = proElm.attributeValue("name");
				String value = proElm.attributeValue("value");
				properties.put(name, value);
			}
			isParsed = true;
			return config;
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return null;
	}
}
