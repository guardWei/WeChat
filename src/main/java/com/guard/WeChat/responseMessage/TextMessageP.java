package com.guard.WeChat.responseMessage;

/**
 * 回复文本消息类
 * @author guard
 * @version 2015年12月26日16:07:44
 */
public class TextMessageP extends BaseMessageP {
	// 消息内容
	private String Content;

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}
}
