package com.weixinmpv2;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.weixinmpv2.bean.AuthorizationInfo;
import com.weixinmpv2.bean.AuthorizerInfo;
import com.weixinmpv2.bean.VerifyTicket;
import com.weixinmpv2.exception.WeixinmpException;

public class TicketService {

	
	private AbstractWeixinmpController weixinmpController;
	

	public TicketService(AbstractWeixinmpController weixinmpController) {
		this.weixinmpController = weixinmpController;
	}
	
	/**
	 * 获取预授权码pre_auth_code
	 * @return
	 * @throws WeixinmpException 
	 */
	public String createPreauthcode() throws WeixinmpException{
		return weixinmpController.createPreAuthcode();
	}
	
	
	/**
	 * 更具AuthCode获取信息
	 * @return
	 * @throws WeixinmpException
	 */
	public AuthorizationInfo getAuthorizerInfo() throws WeixinmpException{
		return weixinmpController.getAuthorizerInfo();
	}
	
	public VerifyTicket refreshTicket(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		return weixinmpController.refreshTicket(req, resp);
		
	}
	
	public AuthorizationInfo handleComponentCallback(HttpServletRequest req, HttpServletResponse resp)throws IOException, WeixinmpException{
		return weixinmpController.handleComponentCallback(req, resp);
	}
	
	public AuthorizerInfo getAuthorizerInfo(String appid) throws WeixinmpException{
		
		return weixinmpController.getAuthorizerInfo(appid);
	}
}
