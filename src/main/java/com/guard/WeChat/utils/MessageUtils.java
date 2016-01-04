package com.guard.WeChat.utils;

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

import com.guard.WeChat.responseMessage.Article;
import com.guard.WeChat.responseMessage.ArticlesMessageP;
import com.guard.WeChat.responseMessage.ImageMessageP;
import com.guard.WeChat.responseMessage.MusicMessageP;
import com.guard.WeChat.responseMessage.TextMessageP;
import com.guard.WeChat.responseMessage.VideoMessageP;
import com.guard.WeChat.responseMessage.VoiceMessageP;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

public class MessageUtils {
	// 请求消息类型：文本
	public static final String REQ_MESSAGE_TYPE_TEXT = "text";
	// 请求消息类型：图片
	public static final String REQ_MESSAGE_TYPE_IMAGE = "image";
	// 请求消息类型：语音
	public static final String REQ_MESSAGE_TYPE_VOICE = "voice";
	// 请求消息类型：视频
	public static final String REQ_MESSAGE_TYPE_VIDEO = "video";
	// 请求消息类型：地理位置
	public static final String REQ_MESSAGE_TYPE_LOCATION = "location";
	// 请求消息类型：链接
	public static final String REQ_MESSAGE_TYPE_LINK = "link";

	// 请求消息类型：事件推送
	public static final String REQ_MESSAGE_TYPE_EVENT = "event";

	// 事件类型：subscribe(订阅)
	public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";
	// 事件类型：unsubscribe(取消订阅)
	public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";
	// 事件类型：scan(关注用户扫描带参数二维码)
	public static final String EVENT_TYPE_SCAN = "SCAN";
	// 事件类型：LOCATION(上报地理位置)
	public static final String EVENT_TYPE_LOCATION = "LOCATION";
	// 事件类型：CLICK(自定义菜单:点击菜单拉取消息时的事件推送 )
	public static final String EVENT_TYPE_CLICK = "CLICK";
	// 事件类型：VIEW(自定义菜单:点击菜单跳转链接时的事件推送)
	public static final String EVENT_TYPE_VIEW = "VIEW";

	// 响应消息类型：文本
	public static final String RESP_MESSAGE_TYPE_TEXT = "text";
	// 响应消息类型：图片
	public static final String RESP_MESSAGE_TYPE_IMAGE = "image";
	// 响应消息类型：语音
	public static final String RESP_MESSAGE_TYPE_VOICE = "voice";
	// 响应消息类型：视频
	public static final String RESP_MESSAGE_TYPE_VIDEO = "video";
	// 响应消息类型：音乐
	public static final String RESP_MESSAGE_TYPE_MUSIC = "music";
	// 响应消息类型：图文
	public static final String RESP_MESSAGE_TYPE_NEWS = "news";
	

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
    public static String textMessageToXml(TextMessageP textMessage){
    	xstream.alias("xml", textMessage.getClass());
    	return xstream.toXML(textMessage);
    }
    
    /**
     * 语音消息对象转换成xml
     * @param voiceMessage
     * @return
     */
    public static String voiceMessageToXml(VoiceMessageP voiceMessage){
		xstream.alias("xml", voiceMessage.getClass());
		return xstream.toXML(voiceMessage);
	}
    
    /**
     * 图片消息对象转成xml
     * @param imageMessage
     * @return
     */
	public static String imageMessageToXml(ImageMessageP imageMessage){
		xstream.alias("xml", imageMessage.getClass());
		return xstream.toXML(imageMessage);
	}
	
	/**
	 * 音乐消息对象转成xml
	 * @param musicMessage
	 * @return
	 */
	public static String musicMessageToXml(MusicMessageP musicMessage){
		xstream.alias("xml", musicMessage.getClass());
		return xstream.toXML(musicMessage);
	}
	
	/**
	 * 视频消息对象转成xml
	 * @param videoMessage
	 * @return
	 */
	public static String videoMessageToXml(VideoMessageP videoMessage){
		xstream.alias("xml", videoMessage.getClass());
		return xstream.toXML(videoMessage);
	}
	
	/**
	 * 图文消息转换成xml
	 * @param articlesMessage
	 * @return
	 */
	public static String articlesMessageToXml(ArticlesMessageP articlesMessage){
		xstream.alias("xml", articlesMessage.getClass());
		xstream.alias("item", new Article().getClass());
		return xstream.toXML(articlesMessage);
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
