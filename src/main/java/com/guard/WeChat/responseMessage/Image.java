package com.guard.WeChat.responseMessage;

/**
 * 图片基类
 * @author guard
 * @version 2015年12月31日16:53:18
 */
public class Image {
	private String MediaId; // 通过素材管理接口上传多媒体文件，得到的id

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}
}
