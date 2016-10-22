package com.weixinmpv2.bean;

import java.io.Serializable;
import java.util.List;

/** 
 * @author nhsoft.lsd
 * @version 2015年12月19日  上午10:50:36 
 * 
 * 查看卡券详情
 */
public class CardInfo implements Serializable{

	/**
	 * 
	 */
    private static final long serialVersionUID = 4169200626266608868L;

    public String card_type;
    
    public Card groupon;
    public Card discount;
    public Card gift;
    public Card cash;
    public Card general_coupon;
    public Card member_card;
    public Card scenic_ticket;
    public Card movie_ticket;
    public Card boarding_pass;
    public Card meeting_ticket;
    public Card bus_ticket;
    
   
    public static class Card{
    	public BaseInfo base_info;
    
    	public Integer least_cost;
    	public Integer reduce_cost;
    	public String deal_detail;
    	public Integer discount;
    }
    
    public static class BaseInfo{
    	
    	public String logo_url;
    	public String code_type;
    	public String brand_name;
    	public String title;
    	public String sub_title;
    	public String color;
    	public String notice;
    	public String description;
     	public DateInfo date_info;
     	
     	public String type;
     	
     	public String begin_timestamp;
     	public String end_timestamp;
     	
     	public Integer fixed_term;
     	public Integer fixed_begin_term;
    	public Sku sku;
    	
    	public boolean use_custom_code;
    	public boolean bind_openid;
    	public boolean can_share;
    	public String service_phone;
    	public String custom_url;
    	public String custom_url_sub_title;
    	public String custom_url_name;
      	public String promotion_url_name;
      	public String promotion_url_sub_title;
    	public String promotion_url;
    	public String status;
    	public List<Integer> location_id_list;
    	public String source;
    	public Integer use_limit;
    	public Integer get_limit;
    	public String id;
    	public String appid;
    	
    }
    
    public static class DateInfo{
    	
    	public String type;
    	public String begin_timestamp;
    	public String end_timestamp;
    }
    
    public static class Sku{
    	
    	public Integer quantity;
    	public Integer total_quantity;
    }
    
    public Card getCard(){
    	
    	if(groupon != null){
    		return groupon;
    	}else if(discount != null){
    		return discount;
    	}else if(gift != null){
    		return gift;
    	}else if(general_coupon != null){
    		return general_coupon;
    	}else if(member_card != null){
    		return member_card;
    	}else if(scenic_ticket != null){
    		return scenic_ticket;
    	}else if(movie_ticket != null){
    		return movie_ticket;
    	}else if(boarding_pass != null){
    		return boarding_pass;
    	}else if(meeting_ticket != null){
    		return meeting_ticket;
    	}else if(bus_ticket != null){
    		return bus_ticket;
    	}else if(cash != null){
    		return cash;
    	}
    	return null;
    	
    }
}
