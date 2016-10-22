package com.weixinmpv2.bean;

import java.io.Serializable;

public class ApiAuthorizerTokenRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3723189650083199021L;

	private String component_appid; //第三方平台appid
	private String authorizer_appid; //授权方appid，也就是公众平台appid
	private String authorizer_refresh_token;
	public String getComponent_appid() {
		return component_appid;
	}
	public void setComponent_appid(String component_appid) {
		this.component_appid = component_appid;
	}
	public String getAuthorizer_appid() {
		return authorizer_appid;
	}
	public void setAuthorizer_appid(String authorizer_appid) {
		this.authorizer_appid = authorizer_appid;
	}
	public String getAuthorizer_refresh_token() {
		return authorizer_refresh_token;
	}
	public void setAuthorizer_refresh_token(String authorizer_refresh_token) {
		this.authorizer_refresh_token = authorizer_refresh_token;
	}
	
	
}
