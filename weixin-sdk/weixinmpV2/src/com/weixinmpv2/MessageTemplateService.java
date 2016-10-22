package com.weixinmpv2;

import com.weixinmpv2.bean.BaseResp;
import com.weixinmpv2.bean.GetIndustryResponse;
import com.weixinmpv2.bean.MessageTemplate;
import com.weixinmpv2.bean.MessageTemplateRequest;
import com.weixinmpv2.bean.WeixinIndustry;
import com.weixinmpv2.exception.WeixinmpException;
import com.weixinmpv2.uitl.Util;


public class MessageTemplateService {

    /** 微信公众平台API控制器实例 */
    private final AbstractWeixinmpController controller;
	
    protected MessageTemplateService(AbstractWeixinmpController controller){
    	this.controller = controller;
    }
    
    //设置微信公众号 行业
    public void setWeixinIndustry(String industryId1, String industryId2) throws WeixinmpException{
    	 WeixinIndustry weixinIndustry = new WeixinIndustry();
    	 weixinIndustry.industryId1 = industryId1;
    	 weixinIndustry.industryId2 = industryId2;
    	 
    	 String url =  Util.getProperty("message_template_set_industry");
    	 controller.postWithJson(url, weixinIndustry, BaseResp.class);
    }
    
    //获取设置过的行业
    public GetIndustryResponse getIndustryResponse() throws WeixinmpException{
    	 String url =  Util.getProperty("message_template_get_industry");
    	 GetIndustryResponse result = controller.postWithJson(url, null, GetIndustryResponse.class);
		 return result;
    }
    
    //获取模板Id
	public String getMessageTemplateId(MessageTemplateRequest messageTemplateRequest) throws WeixinmpException {
		 String url =  Util.getProperty("message_template_get_id");
		 MessageTemplateRequest.Response result = controller.postWithJson(url, messageTemplateRequest, MessageTemplateRequest.Response.class);
		 return result.templateId;
	}
	
	public void sendMessageTemplate(MessageTemplate messageTemplate) throws WeixinmpException {
		String url =  Util.getProperty("message_template_send");
		controller.postWithJson(url, messageTemplate, BaseResp.class);
	} 
}

