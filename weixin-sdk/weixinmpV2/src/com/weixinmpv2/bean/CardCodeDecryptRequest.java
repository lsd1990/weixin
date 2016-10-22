package com.weixinmpv2.bean;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

/** 
 * @author nhsoft.lsd
 * @version 2015年12月19日  上午10:18:34 
 */
public class CardCodeDecryptRequest implements Serializable{

	/**
	 * 
	 */
    private static final long serialVersionUID = -1253071497172594848L;

    @SerializedName("encrypt_code")
    public String encryptCode;
}
