package com.weixinmpv2.bean;

public class WeixinAccount {

    /** token */
    private String token;

    /** 开发者账号 */
    private String username;

    /** appID */
    private String appid;

    /** appsecret */
    private String appsecret;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getAppsecret() {
		return appsecret;
	}

	public void setAppsecret(String appsecret) {
		this.appsecret = appsecret;
	}
    
}
