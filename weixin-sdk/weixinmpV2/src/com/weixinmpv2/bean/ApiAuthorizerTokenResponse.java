package com.weixinmpv2.bean;

public class ApiAuthorizerTokenResponse extends BaseResp{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8943247825706414287L;

	private String authorizer_access_token;
	private String expires_in;
	private String authorizer_refresh_token;
	public String getAuthorizer_access_token() {
		return authorizer_access_token;
	}
	public void setAuthorizer_access_token(String authorizer_access_token) {
		this.authorizer_access_token = authorizer_access_token;
	}
	public String getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(String expires_in) {
		this.expires_in = expires_in;
	}
	public String getAuthorizer_refresh_token() {
		return authorizer_refresh_token;
	}
	public void setAuthorizer_refresh_token(String authorizer_refresh_token) {
		this.authorizer_refresh_token = authorizer_refresh_token;
	}
	
	
}
