package com.guard.service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.guard.utils.ValidationUtil;

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
		super.doPost(request, response);
	}

	
}
