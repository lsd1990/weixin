package com.weixinmpv2.bean;

public class ApiQueryAuthResponse extends BaseResp{

	/**
	 * 
	 */
	private static final long serialVersionUID = -680128391990896371L;
	
	public AuthorizationInfo authorization_info;

	public AuthorizationInfo getAuthorization_info() {
		return authorization_info;
	}

	public void setAuthorization_info(AuthorizationInfo authorization_info) {
		this.authorization_info = authorization_info;
	}

	
}
