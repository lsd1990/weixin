package com.weixinmpv2.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class MassSendJobFinishEventRequest extends AbstractEventRequest {

	/**
	 * 
	 */
    private static final long serialVersionUID = -8112342205788481016L;

    public String Status;
    public int TotalCount;
    public int FilterCount;
    public int SentCount;
    public int ErrorCount;
	
    @Override
    public String toString() {
	    return "[ Status = "+Status+", TotalCount = "+TotalCount+", FilterCount="+FilterCount+" SentCount="+SentCount+", ErrorCount="+ErrorCount+" ]";
    }
    
    public String getDesc(){
    	String desc = "[ Status = "+Status+" 发送粉丝数："+TotalCount+", 过滤粉丝数："+FilterCount+", 发送成功粉丝数："+SentCount + ", 发送失败粉丝数："+ ErrorCount + "]";
    	return desc;
    }
}
