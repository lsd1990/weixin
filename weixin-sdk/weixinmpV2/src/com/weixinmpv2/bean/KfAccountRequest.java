package com.weixinmpv2.bean;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class KfAccountRequest implements Serializable{

	/**
	 * 
	 */
    private static final long serialVersionUID = -6475939687304428390L;
    @SerializedName("kf_account")
	public String kfaccount;  //账号
	public String nickname;   //昵称
	public String password;   //密码
}
