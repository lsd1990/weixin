package com.weixinmpv2.bean;
/** 
 * @author nhsoft.lsd
 * @version 2015年12月19日  上午10:06:11 
 */
public class CardCodeGetResponse extends BaseResp{

	/**
	 * 
	 */
    private static final long serialVersionUID = 514853154316346402L;

    public String openid;
    public Card card;
    public boolean can_consume;
    public String  user_card_status;
	
    @Override
    public String toString() {
	    return "CardCodeGetResponse [openid=" + openid + ", card=" + card + ", can_consume=" + can_consume
	            + ", user_card_status=" + user_card_status + "]";
    }
    
}
