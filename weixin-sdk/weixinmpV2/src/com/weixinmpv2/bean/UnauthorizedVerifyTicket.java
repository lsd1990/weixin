package com.weixinmpv2.bean;

public class UnauthorizedVerifyTicket extends VerifyTicket{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2422501282559840781L;

	private String AuthorizerAppid;

	public String getAuthorizerAppid() {
		return AuthorizerAppid;
	}

	public void setAuthorizerAppid(String authorizerAppid) {
		AuthorizerAppid = authorizerAppid;
	}
	
	
}
