package com.guard.WeChat.responseMessage;

/**
 * 回复视频消息类
 * @author guard
 * @version 2015年12月31日17:32:28
 */
public class VideoMessageP extends BaseMessageP {
	private Video Video;

	public Video getVideo() {
		return Video;
	}

	public void setVideo(Video video) {
		Video = video;
	}

}
