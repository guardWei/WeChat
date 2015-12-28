package com.guard.WeChat.responseMessage;

/**
 * 回复基本消息类(公众账号 -> 普通用户)
 * @author guard
 * @version 2015年12月26日15:58:20
 */
public class BaseMessageP {
	// 开发者微信号
	private String ToUserName;
	// 发送方账号(一个open_id)
	private String FromUserName;
	// 消息创建时间(一个整型)
	private long CreateTime;
	// 消息类型（text/image/location/link）
	private String MsgType;

	
	public String getToUserName() {
		return ToUserName;
	}

	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}

	public String getFromUserName() {
		return FromUserName;
	}

	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}

	public long getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(long createTime) {
		CreateTime = createTime;
	}

	public String getMsgType() {
		return MsgType;
	}

	public void setMsgType(String msgType) {
		MsgType = msgType;
	}

}
