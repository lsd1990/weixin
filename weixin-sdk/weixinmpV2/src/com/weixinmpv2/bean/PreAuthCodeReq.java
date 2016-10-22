package com.weixinmpv2.bean;

import java.io.Serializable;

public class PreAuthCodeReq implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5161559104475368678L;

	public String component_appid;

	public String getComponent_appid() {
		return component_appid;
	}

	public void setComponent_appid(String component_appid) {
		this.component_appid = component_appid;
	}
	
	
}
