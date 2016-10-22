package com.weixinmpv2.bean;

public class ApiComponentTokenResponse extends BaseResp {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3008806057187204912L;
	
	private String component_access_token; 	//第三方平台access_token
	private String expires_in; 			 	//有效期
	
	public String getComponent_access_token() {
		return component_access_token;
	}
	public void setComponent_access_token(String component_access_token) {
		this.component_access_token = component_access_token;
	}
	public String getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(String expires_in) {
		this.expires_in = expires_in;
	}
	@Override
	public String toString() {
		return "ApiComponentTokenResp [component_access_token=" + component_access_token + ", expires_in=" + expires_in
				+ "]";
	}

}
