package com.weixinmpv2.bean;
/** 
 * @author nhsoft.lsd
 * @version 2015年12月19日  上午10:14:42 
 */
public class CardCodeConsumeResponse extends BaseResp{

	/**
	 * 
	 */
    private static final long serialVersionUID = -1558562700898070452L;
    public String openid;
    public Card card;
	
    @Override
    public String toString() {
	    return "CardCodeGetResponse [openid=" + openid + ", card=" + card + "]";
    }
}
