package com.weixinmpv2.bean;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * 被动请求消息的公用部分
 */
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class AbstractRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 开发者微信号 */
    private String ToUserName;

    /** 发送方帐号（一个OpenID） */
    private String FromUserName;

    /** 消息创建时间 （整型） */
    private String CreateTime;

    /** 消息类型 */
    private String MsgType;

    /** 消息id，64位整型（事件消息{@link SubscribeEventRequest}没有该属性） */
    private String MsgId;

    private String xml;
    
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

	public String getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(String createTime) {
		CreateTime = createTime;
	}

	public String getMsgType() {
		return MsgType;
	}

	public void setMsgType(String msgType) {
		MsgType = msgType;
	}

	public String getMsgId() {
		return MsgId;
	}

	public void setMsgId(String msgId) {
		MsgId = msgId;
	}

	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

	
}
