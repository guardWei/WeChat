package com.guard.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;

import com.guard.WeChat.responseMessage.TextMessageP;
import com.guard.WeChat.utils.MessageUtils;
import com.guard.WeChat.utils.ValidationUtil;

/**
 * 接受来自微信服务器的请求
 * @author guard
 * @version 2015年12月25日13:43:05
 */
public class LoginServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	/**
	 * 微信服务器将验证信息转发到自有公网IP地址的服务器
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		
		try {
			Map<String,String> requestMap = MessageUtils.parseXml(request);
			//发送方账号(open_id)
			String fromUserName = requestMap.get("FromUserName");
			// 公众帐号
		    String toUserName = requestMap.get("ToUserName");
			// 消息类型
			String msgType = requestMap.get("MsgType");
			//消息时间
			String CreateTime = requestMap.get("CreateTime");
			if("text".equals(msgType)){
				//消息内容
				String Content = requestMap.get("Content");
				//消息id
				String MsgId = requestMap.get("MsgId");
				System.out.println("fromUserName:"+fromUserName);
				System.out.println("toUserName:"+toUserName);
				System.out.println("msgType："+msgType);
				System.out.println("Content:"+Content);
				System.out.println("CreateTime:"+CreateTime);
				System.out.println("MsgId:"+MsgId);
				if("1".equals(Content)){
					//xml格式的消息数据
					String responseXml = null;
					
					TextMessageP tx = new TextMessageP();
					tx.setFromUserName(toUserName);
					tx.setToUserName(fromUserName);
					tx.setCreateTime(new Date().getTime());
					tx.setMsgType("text");
					tx.setContent("欢迎你，你输入的是1"+"\n"+"换行了");
					responseXml = MessageUtils.textMessageToXml(tx);
					out.print(responseXml);
					System.out.println("responseXml:"+responseXml);
				}
			}
			//信息类型(消息或者事件)
			String Event =requestMap.get("Event");
			System.out.println("Event:"+Event);
			System.out.println("-------------------");
			//xml格式的消息数据
			String responseXml = null;
			if(Event != null && Event.equals("subscribe")){
				TextMessageP tx = new TextMessageP();
				tx.setFromUserName(toUserName);
				tx.setToUserName(fromUserName);
				tx.setCreateTime(new Date().getTime());
				tx.setMsgType("text");
				tx.setContent("欢迎关注我的微信，这里是爱为一个人~~。。。。");
				responseXml = MessageUtils.textMessageToXml(tx);
				out.print(responseXml);
				System.out.println("responseXml:"+responseXml);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}finally{
			out.close();
			out = null;
		}
	}

	
}
