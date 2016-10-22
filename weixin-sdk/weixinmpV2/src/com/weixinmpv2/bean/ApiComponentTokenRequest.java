package com.weixinmpv2.bean;

import java.io.Serializable;

public class ApiComponentTokenRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7837475419705674297L;
	
	private String component_appid; 		//第三方APPID
	private String component_appsecret;		//第三方AppSecret
	private String component_verify_ticket;	//VerifyTicket
	
	
	public String getComponent_appid() {
		return component_appid;
	}
	public void setComponent_appid(String component_appid) {
		this.component_appid = component_appid;
	}
	public String getComponent_appsecret() {
		return component_appsecret;
	}
	public void setComponent_appsecret(String component_appsecret) {
		this.component_appsecret = component_appsecret;
	}
	public String getComponent_verify_ticket() {
		return component_verify_ticket;
	}
	public void setComponent_verify_ticket(String component_verify_ticket) {
		this.component_verify_ticket = component_verify_ticket;
	}


	
	

}
