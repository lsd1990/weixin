package com.weixinmpv2.bean;

public class PreAuthCodeResp extends BaseResp {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3634388795414198575L;
	
	private String pre_auth_code;
	private Integer expires_in;
	public String getPre_auth_code() {
		return pre_auth_code;
	}
	public void setPre_auth_code(String pre_auth_code) {
		this.pre_auth_code = pre_auth_code;
	}
	public Integer getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(Integer expires_in) {
		this.expires_in = expires_in;
	}
	
}
