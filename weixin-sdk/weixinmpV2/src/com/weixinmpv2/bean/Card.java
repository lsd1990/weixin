package com.weixinmpv2.bean;

import java.io.Serializable;


/** 
 * @author nhsoft.lsd
 * @version 2015年12月19日  上午10:02:09 
 */
public class Card implements Serializable {

	/**
	 * 
	 */
    private static final long serialVersionUID = -2865512903700854464L;

    public String card_id;
    public String begin_time;
    public String end_time;
	@Override
    public String toString() {
	    return "Card [card_id=" + card_id + ", begin_time=" + begin_time + ", end_time=" + end_time + "]";
    }


    
}
