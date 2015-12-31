package com.guard.WeChat.responseMessage;

/**
 * 回复语音消息
 * @author guard
 * @version 2015年12月30日17:29:47
 */
public class VoiceMessageP extends BaseMessageP {
	private Voice Voice;

	public Voice getVoice() {
		return Voice;
	}

	public void setVoice(Voice voice) {
		Voice = voice;
	}

}
