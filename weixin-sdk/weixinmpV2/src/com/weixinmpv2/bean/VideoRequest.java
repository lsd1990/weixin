package com.weixinmpv2.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 视频消息请求<br>
 * MsgType 语音为video
 * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/
 */
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class VideoRequest extends AbstractRequest {

    private static final long serialVersionUID = 1L;

    /** 语音消息媒体id，可以调用多媒体文件下载接口拉取数据。 */
    private String MediaId;

    /** 视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据。 */
    private String ThumbMediaId;

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}

	public String getThumbMediaId() {
		return ThumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		ThumbMediaId = thumbMediaId;
	}

    

}
