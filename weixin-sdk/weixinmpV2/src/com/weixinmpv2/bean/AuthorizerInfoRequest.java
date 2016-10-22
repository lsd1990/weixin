package com.weixinmpv2.bean;

import java.io.Serializable;

public class AuthorizerInfoRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6407686027301093301L;
	
	private String component_appid;
	private String authorizer_appid;
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
	
	

}
