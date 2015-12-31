package com.guard.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.guard.WeChat.responseMessage.TextMessageP;
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
