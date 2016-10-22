package com.weixinmpv2.bean;

public class UpdateAuthorizedVerifyTicket extends VerifyTicket {

	/**
	 * 
	 */
	private static final long serialVersionUID = 963715424022048068L;

	private String AuthorizerAppid;
	private String AuthorizationCode;
	private String AuthorizationCodeExpiredTime;
	public String getAuthorizerAppid() {
		return AuthorizerAppid;
	}
	public void setAuthorizerAppid(String authorizerAppid) {
		AuthorizerAppid = authorizerAppid;
	}
	public String getAuthorizationCode() {
		return AuthorizationCode;
	}
	public void setAuthorizationCode(String authorizationCode) {
		AuthorizationCode = authorizationCode;
	}
	public String getAuthorizationCodeExpiredTime() {
		return AuthorizationCodeExpiredTime;
	}
	public void setAuthorizationCodeExpiredTime(String authorizationCodeExpiredTime) {
		AuthorizationCodeExpiredTime = authorizationCodeExpiredTime;
	}
	
	
}
