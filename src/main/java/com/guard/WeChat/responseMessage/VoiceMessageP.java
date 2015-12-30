package com.guard.WeChat.responseMessage;

/**
 * 回复语音消息
 * @author guard
 * @version 2015年12月30日17:29:47
 */
public class VoiceMessageP extends BaseMessageP {
	// 通过上传多媒体文件，得到的id
	private String MediaId;

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}

}
