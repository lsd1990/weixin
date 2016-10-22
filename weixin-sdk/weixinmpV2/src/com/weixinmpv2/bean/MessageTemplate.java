package com.weixinmpv2.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.annotations.SerializedName;


public class MessageTemplate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2543130061946126168L;
	  /** 普通用户openid */
    public String touser;
    
    @SerializedName("template_id")
    public String templateid;
    
    public String url;
    
    public String topcolor;
    
    private Map<String,Item> data = new HashMap<String, MessageTemplate.Item>(); 
    
    public void addItem(String key, Item item) {
    	data.put(key,item);
    }
    /** 文章 */
    public static class Item implements Serializable {

        /**
		 * 
		 */
		private static final long serialVersionUID = -1308022074323607134L;

		public String value;

        public String color;


    }
}
