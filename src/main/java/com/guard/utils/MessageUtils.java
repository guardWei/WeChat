package com.guard.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

public class MessageUtils {
	//微信端请求信息封装
    public static Map<String, String> parseXml(HttpServletRequest request) throws IOException, DocumentException{
    	//将解析结构存储在HashMap中
    	Map<String,String> map = new HashMap<String,String>();
    	//从request中获取输入流
    	InputStream in = request.getInputStream();
    	//读取输入流
    	SAXReader reader = new SAXReader();
    	Document document = reader.read(in);
    	//得到根节点
    	Element root = document.getRootElement();
    	//得到根节点下所有的子节点
    	List<Element> elementLists = root.elements();
    	
    	//循环遍历子节点
    	for(Element e:elementLists){
    		map.put(e.getName(), e.getText());
    	}
    	
    	//释放资源
    	in.close();
    	in = null;
    	return map;
    }
    
    /**
	 * 文本消息对象转换成xml
	 * @author guard
	 * @param textMessage 文本消息对象
	 * @return xml
	 * @version 2015年12月26日16:20:30
	 */
    public static String textMessageToXml(TextMessage textMessage){
    	xstream.alias("xml", textMessage.getClass());
    	return xstream.toXML(textMessage);
    }
    
    /**
	 * 扩展xstream，使其支持CDATA块
	 * @author guard
	 * @version　2015年12月26日16:12:57
	 */
	private static XStream xstream = new XStream(new XppDriver() {
		public HierarchicalStreamWriter createWriter(Writer out) {
			return new PrettyPrintWriter(out) {
				// 对所有xml节点的转换都增加CDATA标记
				boolean cdata = true;
				protected void writeText(QuickWriter writer, String text) {
					if (cdata) {
						writer.write("<![CDATA[");
						writer.write(text);
						writer.write("]]>");
					} else {
						writer.write(text);
					}
				}
			};
		}
	});
}
