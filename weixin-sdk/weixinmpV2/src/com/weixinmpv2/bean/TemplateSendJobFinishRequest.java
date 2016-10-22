package com.weixinmpv2.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class TemplateSendJobFinishRequest extends AbstractEventRequest {

	/**
	 * 
	 */
    private static final long serialVersionUID = 7472674313920806643L;
    public String Status;

	@Override
    public String toString() {
	    return "TemplateSendJobFinishRequest [Status=" + Status + "]";
    }
	
	
}
