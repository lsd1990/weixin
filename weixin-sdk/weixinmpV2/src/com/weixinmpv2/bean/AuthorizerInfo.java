package com.weixinmpv2.bean;

import java.io.Serializable;


public class AuthorizerInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4265125728997923481L;
	
	private String nick_name;
	private String head_img;
	private String user_name;
	
	public String getNick_name() {
		return nick_name;
	}

	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}

	public String getHead_img() {
		return head_img;
	}

	public void setHead_img(String head_img) {
		this.head_img = head_img;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	
}
