package com.guard.WeChat.responseMessage;

/**
 * 回复图片消息类
 * @author guard
 * @version 2015年12月28日15:35:28
 */
public class ImageMessageP extends BaseMessageP {
	private String MediaId; // 通过素材管理接口上传多媒体文件，得到的id

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}

}
