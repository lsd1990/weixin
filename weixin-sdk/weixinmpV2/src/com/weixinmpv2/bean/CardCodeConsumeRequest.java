package com.weixinmpv2.bean;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

/** 
 * @author nhsoft.lsd
 * @version 2015年12月19日  上午10:12:41 
 */
public class CardCodeConsumeRequest implements Serializable{

	/**
	 * 
	 */
    private static final long serialVersionUID = 1699402833042557207L;

    @SerializedName("code")
    public String code; //需核销的Code码。
    
    @SerializedName("card_id")
    public String cardId; //卡券ID。创建卡券时use_custom_code填写true时必填。非自定义Code不必填写。
}
