package com.guard.WeChat.responseMessage;

/**
 * 语音基类
 * @author guard
 * @version 2015年12月31日11:11:48
 */
public class Voice {
	// 通过上传多媒体文件，得到的id
	private String MediaId;

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}
}
