package utils;

import java.util.HashMap;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * XML工具类
 * @author travler
 *
 */
public class XmlUtil {
	private static SAXReader read =new SAXReader();
	/**
	 * 获取数据库配置信息
	 * @param dbName
	 * @return
	 */
	public static HashMap<String, String> getDBConfig(String dbName) {
		HashMap<String, String> dbConfig = new HashMap<>();
		try {
			Document doc = read.read(XmlUtil.class.getClassLoader().getResourceAsStream("db.xml"));
			Element root = doc.getRootElement();
			List<Element> ls = root.selectNodes("//conns/conn");
			Element ele = null;
			for (Element element : ls) {
				ele = element.element("dbName");
				if (ele.getTextTrim().equals(dbName)) {
					dbConfig.put("dbName", dbName);
					ele = element.element("desc");
					dbConfig.put("desc", ele.getTextTrim());
					ele = element.element("url");
					dbConfig.put("url", ele.getTextTrim());
					ele = element.element("classDrive");
					dbConfig.put("classDrive", ele.getTextTrim());
					ele = element.element("uname");
					dbConfig.put("uname", ele.getTextTrim());
					ele = element.element("pwd");
					dbConfig.put("pwd", ele.getTextTrim());
					ele = element.element("init");
					dbConfig.put("init", ele.getTextTrim());
					ele = element.element("max");
					dbConfig.put("max", ele.getTextTrim());
					break;
				}
				continue;
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		return dbConfig;
	}
}
