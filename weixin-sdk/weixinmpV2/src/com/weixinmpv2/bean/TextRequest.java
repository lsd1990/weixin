package com.weixinmpv2.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 文本消息的请求
 * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/
 */
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class TextRequest extends AbstractRequest {

    private static final long serialVersionUID = 1L;

    /** 文本消息内容 */
    private String Content;

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}


}
