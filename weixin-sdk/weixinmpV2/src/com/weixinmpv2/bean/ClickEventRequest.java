package com.weixinmpv2.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 自定义菜单点击事件的请求<br>
 * MsgId 字段无效
 * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/
 */
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class ClickEventRequest extends AbstractEventRequest {

    private static final long serialVersionUID = 1L;

    private String EventKey;

	public String getEventKey() {
		return EventKey;
	}

	public void setEventKey(String eventKey) {
		EventKey = eventKey;
	}

    
}
