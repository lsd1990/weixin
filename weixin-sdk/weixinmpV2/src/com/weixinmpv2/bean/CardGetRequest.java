package com.weixinmpv2.bean;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

/** 
 * @author nhsoft.lsd
 * @version 2015年12月19日  上午11:04:14 
 */
public class CardGetRequest implements Serializable{

	 /**
	 * 
	 */
    private static final long serialVersionUID = 460339336530385150L;
	@SerializedName("card_id")
	public String cardId;
}
