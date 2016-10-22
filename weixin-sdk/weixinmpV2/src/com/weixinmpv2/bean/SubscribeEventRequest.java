package com.weixinmpv2.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 关注/取消关注事件的请求 <br>
 * MsgId 字段无效
 * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/
 */
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class SubscribeEventRequest extends AbstractEventRequest {

    private static final long serialVersionUID = 1L;

    /** 事件KEY值，qrscene_为前缀，后面为二维码的参数值[扫描了带参数的二维码时使用] */
    private String EventKey;

    /** 二维码的ticket，可用来换取二维码图片[扫描了带参数的二维码时使用] */
    private String Ticket;

	public String getEventKey() {
		return EventKey;
	}

	public void setEventKey(String eventKey) {
		EventKey = eventKey;
	}

	public String getTicket() {
		return Ticket;
	}

	public void setTicket(String ticket) {
		Ticket = ticket;
	}

   

}
