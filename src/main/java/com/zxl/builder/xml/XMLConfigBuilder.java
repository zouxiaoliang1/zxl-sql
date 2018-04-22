package com.zxl.builder.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.zxl.builder.BaseBuilder;
import com.zxl.exception.BuilderException;
import com.zxl.io.Resources;
import com.zxl.session.Configuration;

/**
 * Created by zouxiaoliang on 2018/4/21.
 */
public class XMLConfigBuilder extends BaseBuilder {

	private boolean isParsed = false;

	private InputStream inputStream;

	private String environment;

	private Properties props;

	public XMLConfigBuilder(InputStream inputStream) {
		this(inputStream, null, null);
	}

	public XMLConfigBuilder(InputStream inputStream, String environment, Properties props) {
		this.inputStream = inputStream;
		this.environment = environment;
		this.props = new Properties();
		if (props != null) {
			this.props.putAll(props);
		}
	}

	public Configuration parse() {
		if (isParsed) {
			throw new BuilderException("配置文件已解析");
		}
		SAXReader saxReader = new SAXReader();
		Document document;
		try {
			document = saxReader.read(inputStream);
			Element root = document.getRootElement();
			Element prosElm = root.element("properties");
			List<Element> proElms = prosElm.elements();

			// 在 properties 元素体中指定的属性首先被读取
			for (Element proElm : proElms) {
				String name = proElm.attributeValue("name");
				String value = proElm.attributeValue("value");
				props.put(name, value);
			}
			// 从 properties元素的类路径resource可以覆盖已经指定的重复属性
			String resource = prosElm.attributeValue("resource");
			try {
				Properties resPros = Resources.getResourceAsProperties(resource);
				props.putAll(resPros);
			} catch (IOException e) {
				e.printStackTrace();
			}
			Configuration config = new Configuration(props);
			isParsed = true;
			this.configuration = config;
			return config;
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return null;
	}
}
