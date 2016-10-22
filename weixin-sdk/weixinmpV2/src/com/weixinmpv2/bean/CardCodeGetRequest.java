package com.weixinmpv2.bean;

import com.google.gson.annotations.SerializedName;

/** 
 * @author nhsoft.lsd
 * @version 2015年12月19日  上午9:59:14 
 */
public class CardCodeGetRequest {

    @SerializedName("code")
    public String code;
   
    @SerializedName("card_id")
    public String cardId;
    
    @SerializedName("check_consume")
    public boolean checkConsume = true;

}
