package com.guard.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.guard.WeChat.responseMessage.Article;
import com.guard.WeChat.responseMessage.ArticlesMessageP;
import com.guard.WeChat.responseMessage.Image;
import com.guard.WeChat.responseMessage.ImageMessageP;
import com.guard.WeChat.responseMessage.Music;
import com.guard.WeChat.responseMessage.MusicMessageP;
import com.guard.WeChat.responseMessage.TextMessageP;
import com.guard.WeChat.responseMessage.Video;
import com.guard.WeChat.responseMessage.VideoMessageP;
import com.guard.WeChat.responseMessage.Voice;
import com.guard.WeChat.responseMessage.VoiceMessageP;
import com.guard.WeChat.utils.MessageUtils;
import com.guard.WeChat.utils.ValidationUtil;

/**
 * 微信后台controller
 * @author guard
 * @version 2015年12月28日16:06:29
 */
@RequestMapping("/WeChatApi")
@Controller
public class WeChatController {
	
   @RequestMapping("/do")
   public void handler(HttpServletRequest request,HttpServletResponse response) throws IOException{
	   if("GET".equals(request.getMethod())){ //微信服务器将验证信息转发到自有公网IP地址的服务器
		   String signature = request.getParameter("signature"); //微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
			String timestamp = request.getParameter("timestamp"); //时间戳
			String nonce = request.getParameter("nonce"); //随机数
			String echostr = request.getParameter("echostr");//随机字符串
			System.out.println("signature:"+signature+"timestamp:"+timestamp+"nonce:"+nonce+"echostr:"+echostr);
			
			PrintWriter out =response.getWriter();
			if(ValidationUtil.checkSignature(signature, timestamp, nonce)){
				out.print(echostr);
			}
			
			out.close();
			out = null;
	   }else if("POST".equals(request.getMethod())){
		    request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			
			try {
				Map<String,String> requestMap = MessageUtils.parseXml(request);
				//消息id
				String msgId = requestMap.get("MsgId");
				//发送方账号(open_id)
				String fromUserName = requestMap.get("FromUserName");
				// 公众帐号
			    String toUserName = requestMap.get("ToUserName");
				// 消息类型
				String msgType = requestMap.get("MsgType");
				//消息时间
				String createTime = requestMap.get("CreateTime");
				
				System.out.println("MsgId:"+msgId);
				System.out.println("FromUserName:"+fromUserName);
				System.out.println("ToUserName:"+toUserName);
				System.out.println("MsgType:"+msgType);
				System.out.println("CreateTime:"+createTime);
				
				if(MessageUtils.REQ_MESSAGE_TYPE_TEXT.equals(msgType)){
					//文本消息内容
					String content = requestMap.get("Content");
					
					System.out.println("Content:"+content);
					if("1".equals(content)){
						//xml格式的消息数据
						String responseXml = null;
						
						TextMessageP tx = new TextMessageP();
						tx.setFromUserName(toUserName);
						tx.setToUserName(fromUserName);
						tx.setCreateTime(new Date().getTime());
						tx.setMsgType(MessageUtils.RESP_MESSAGE_TYPE_TEXT);
						tx.setContent("欢迎你，你输入的是1"+"\n"+"对么");
						responseXml = MessageUtils.textMessageToXml(tx);
						out.print(responseXml);
						System.out.println("responseXml:"+responseXml);
					}else if("2".equals(content)){
						 //xml格式的消息数据
					    String responseXml = null;
					    
					    ArticlesMessageP ar = new ArticlesMessageP();
					    ar.setToUserName(fromUserName);
					    ar.setFromUserName(toUserName);
					    ar.setMsgType(MessageUtils.RESP_MESSAGE_TYPE_NEWS);
					    ar.setCreateTime(new Date().getTime());
					    
					    List<Article> articles = new ArrayList<Article>();
					    Article a = new Article();
					    a.setTitle("测试图文信息");
					    a.setDescription("图文信息的描述");
					    a.setPicUrl("http://mmbiz.qpic.cn/mmbiz/aKSlSgaQFZkVGOyVMflV1I2l3hRIsh4Ada7OjZekfichAI5HyibzTB3tSdLtyXhGsTu7kK38guXMweicoU6Xdh45Q/0");
					    a.setUrl("www.baidu.com");
					    articles.add(a);
					    
					    Article a1 = new Article();
					    a1.setTitle("杭州野生动物园");
					    a1.setDescription("野生动物园里面的照片，这只是其中一张");
					    a1.setPicUrl("http://mmbiz.qpic.cn/mmbiz/aKSlSgaQFZkVGOyVMflV1I2l3hRIsh4Ada7OjZekfichAI5HyibzTB3tSdLtyXhGsTu7kK38guXMweicoU6Xdh45Q/0");
					    a1.setUrl("www.taobao.com");
					    articles.add(a1);
					    
					    ar.setArticleCount(String.valueOf(articles.size()));
					    ar.setArticles(articles);
					    
					    responseXml = MessageUtils.articlesMessageToXml(ar);
					    out.print(responseXml);
					    System.out.println("responseXml:"+responseXml);
					}else if("音乐".equals(content)){
						//xml格式的消息数据
					    String responseXml = null;
					    
					    MusicMessageP mu = new MusicMessageP();
					    mu.setToUserName(fromUserName);
					    mu.setFromUserName(toUserName);
					    mu.setMsgType(MessageUtils.RESP_MESSAGE_TYPE_MUSIC);
					    mu.setCreateTime(new Date().getTime());
					    
					    Music m = new Music();
					    m.setTitle("我的音乐");
					    m.setDescription("测试发送音乐");
					    m.setMusicUrl("http://y.baidu.com/song/225313");
					    m.setHQMusicUrl("http://y.baidu.com/song/225313");
					    m.setThumbMediaId("");
					    mu.setMusic(m);
					    
					    responseXml = MessageUtils.musicMessageToXml(mu);
					    out.print(responseXml);
					    System.out.println("responseXml:"+responseXml);
					}
				}else if(MessageUtils.REQ_MESSAGE_TYPE_VOICE.equals(msgType)){
					    //语音消息媒体id，可以调用多媒体文件下载接口拉取数据。
					    String mediaId = requestMap.get("MediaId");
					    //语音格式，如amr，speex等
					    String format = requestMap.get("Format");
					    //语音识别结果，UTF8编码 
					    String recognition = requestMap.get("Recognition");
					    
					    System.out.println("MediaId:"+mediaId);
					    System.out.println("Format:"+format);
					    System.out.println("Recognition:"+recognition);
					    
					    //xml格式的消息数据
						String responseXml = null;
						
						/*TextMessageP tx = new TextMessageP();
						tx.setFromUserName(toUserName);
						tx.setToUserName(fromUserName);
						tx.setCreateTime(new Date().getTime());
						tx.setMsgType(MessageUtils.RESP_MESSAGE_TYPE_TEXT);
						tx.setContent(recognition);
						responseXml = MessageUtils.textMessageToXml(tx);
						out.print(responseXml);
						System.out.println("responseXml:"+responseXml);*/
						
						VoiceMessageP vo = new VoiceMessageP();
						vo.setFromUserName(toUserName);
						vo.setToUserName(fromUserName);
						vo.setCreateTime(new Date().getTime());
						vo.setMsgType(MessageUtils.RESP_MESSAGE_TYPE_VOICE);
						Voice v = new Voice();
						v.setMediaId("OlZ3pvKsyTgflms__fsueerd5bYeejoZHGUHqdsb3M88UY4sLfcoGop4E-trPb3A");
						vo.setVoice(v);
						responseXml = MessageUtils.voiceMessageToXml(vo);
						out.print(responseXml);
						System.out.println("responseXml:"+responseXml);
				}else if(MessageUtils.REQ_MESSAGE_TYPE_IMAGE.equals(msgType)){
					    //图片消息媒体id，可以调用多媒体文件下载接口拉取数据。
					    String mediaId = requestMap.get("MediaId");
					    //图片链接(PicUrl:http://mmbiz.qpic.cn/mmbiz/aKSlSgaQFZkVGOyVMflV1I2l3hRIsh4Ada7OjZekfichAI5HyibzTB3tSdLtyXhGsTu7kK38guXMweicoU6Xdh45Q/0)
					    String picUrl = requestMap.get("PicUrl");
					    
					    System.out.println("MediaId:"+mediaId);
					    System.out.println("PicUrl:"+picUrl);
					    
					    //xml格式的消息数据
						String responseXml = null;
					    
						ImageMessageP im = new ImageMessageP();
						im.setFromUserName(toUserName);
						im.setToUserName(fromUserName);
						im.setCreateTime(new Date().getTime());
						im.setMsgType(MessageUtils.RESP_MESSAGE_TYPE_IMAGE);
						Image i = new Image();
						i.setMediaId("aJZ_e1niD2S4VUdpCaVB5wbTiMC9ewr4W4gciF3RZOIRSsAN0btvs6HLg2EwAnYD");
						im.setImage(i);
						responseXml = MessageUtils.imageMessageToXml(im);
						out.print(responseXml);
						System.out.println("responseXml:"+responseXml);
				}else if(MessageUtils.REQ_MESSAGE_TYPE_VIDEO.equals(msgType)){
					    //视频消息媒体id，可以调用多媒体文件下载接口拉取数据。
				        String mediaId = requestMap.get("MediaId");
				        //视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据。
				        String thumbMediaId = requestMap.get("ThumbMediaId");
				        
				        System.out.println("MediaId:"+mediaId);
				        System.out.println("ThumbMediaId:"+thumbMediaId);
				        	
				        //xml格式的消息数据
						String responseXml = null;
						
						VideoMessageP vi = new VideoMessageP();
						vi.setToUserName(fromUserName);
						vi.setFromUserName(toUserName);
						vi.setCreateTime(new Date().getTime());
						vi.setMsgType(MessageUtils.RESP_MESSAGE_TYPE_VIDEO);
						Video v = new Video();
						v.setTitle("测试视频");
						v.setDescription("这是测试视频的描述");
						v.setMediaId("AmARP4jPPPZeIdQ6HxhgUnU66cxV0oG1cYOW9pKjBAFRuBynrzdl5XNntXrpsbTA");
						vi.setVideo(v);
						responseXml = MessageUtils.videoMessageToXml(vi);
						out.print(responseXml);
						System.out.println("responseXml:"+responseXml);
				}
				//信息类型(消息或者事件)
				String event =requestMap.get("Event");
				System.out.println("Event:"+event);
				System.out.println("-------------------");
				//xml格式的消息数据
				String responseXml = null;
				if(event != null && MessageUtils.EVENT_TYPE_SUBSCRIBE.equals(event)){
					TextMessageP tx = new TextMessageP();
					tx.setFromUserName(toUserName);
					tx.setToUserName(fromUserName);
					tx.setCreateTime(new Date().getTime());
					tx.setMsgType(MessageUtils.RESP_MESSAGE_TYPE_TEXT);
					tx.setContent("欢迎关注我的微信公众号，欢欢I MISS　YOU!");
					responseXml = MessageUtils.textMessageToXml(tx);
					out.print(responseXml);
					System.out.println("responseXml:"+responseXml);
					System.out.println("-------------------");
				}else if(event != null && MessageUtils.EVENT_TYPE_UNSUBSCRIBE.equals(event)){
					TextMessageP tx = new TextMessageP();
					tx.setFromUserName(toUserName);
					tx.setToUserName(fromUserName);
					tx.setCreateTime(new Date().getTime());
					tx.setMsgType(MessageUtils.RESP_MESSAGE_TYPE_TEXT);
					tx.setContent("你已经成功取消了关注，期待下次再关注！");
					responseXml = MessageUtils.textMessageToXml(tx);
					out.print(responseXml);
					System.out.println("responseXml:"+responseXml);
					System.out.println("-------------------");
				}
			} catch (DocumentException e) {
				e.printStackTrace();
			}finally{
				out.close();
				out = null;
			}
	   }
   }
}
