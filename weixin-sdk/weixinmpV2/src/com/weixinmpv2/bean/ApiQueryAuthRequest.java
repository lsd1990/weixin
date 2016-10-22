package com.weixinmpv2.bean;

import java.io.Serializable;

public class ApiQueryAuthRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4496952119996684847L;
	
	private String component_appid;
	private String authorization_code;
	public String getComponent_appid() {
		return component_appid;
	}
	public void setComponent_appid(String component_appid) {
		this.component_appid = component_appid;
	}
	public String getAuthorization_code() {
		return authorization_code;
	}
	public void setAuthorization_code(String authorization_code) {
		this.authorization_code = authorization_code;
	}
	
	

}
