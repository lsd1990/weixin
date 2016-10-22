package com.weixinmpv2;

import com.weixinmpv2.bean.MassNewsMessageAllRequest;
import com.weixinmpv2.bean.MassNewsMessageRequest;
import com.weixinmpv2.bean.MassTextMessageAllRequest;
import com.weixinmpv2.bean.MassTextMessageRequest;
import com.weixinmpv2.exception.WeixinmpException;
import com.weixinmpv2.uitl.Util;


public class MassMessageService {

    private final AbstractWeixinmpController controller;
	
    protected MassMessageService(AbstractWeixinmpController controller){
    	this.controller = controller;
    }
    
    
    /**
     *  发送多图文 分组或全部
     * @param request
     * @throws WeixinmpException 
     * @throws WeixinException
     */
    public void sendMassNewsAll(MassNewsMessageAllRequest request) throws WeixinmpException {
    	String url = Util.getProperty("mass_send_all");
    	controller.postWithJson(url, request, MassNewsMessageAllRequest.Response.class);
    }
    
    /**
     *  发送文本  分组或全部
     * @param request
     * @throws WeixinmpException 
     * @throws WeixinException
     */
    public void sendMassTextAll(MassTextMessageAllRequest request) throws WeixinmpException{
    	String url = Util.getProperty("mass_send_all");
    	controller.postWithJson(url, request, MassTextMessageAllRequest.Response.class);
    }
    
    /**
     *  指定用户发送
     * @param request
     * @throws WeixinmpException 
     * @throws WeixinException
     */
    public void sendMassNews(MassNewsMessageRequest request) throws WeixinmpException{
    	String url = Util.getProperty("mass_send");
    	controller.postWithJson(url, request, MassNewsMessageRequest.Response.class);
    }
    
    /**
     *  指定用户发送
     * @param request
     * @throws WeixinmpException 
     * @throws WeixinException
     */
    public void sendMassText(MassTextMessageRequest request) throws WeixinmpException{
    	String url = Util.getProperty("mass_send_all");
    	controller.postWithJson(url, request, MassTextMessageRequest.Response.class);
    }
}
