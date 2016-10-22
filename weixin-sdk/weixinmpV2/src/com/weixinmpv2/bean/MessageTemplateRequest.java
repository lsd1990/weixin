package com.weixinmpv2.bean;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class MessageTemplateRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8283832608023021876L;
	
	@SerializedName("template_id_short")
	public String templateIdshort;
	
	public static class Response extends  BaseResp{

		/**
		 * 
		 */
		private static final long serialVersionUID = -3836279276863458026L;
		/** 消息模板ID */
		@SerializedName("template_id")
		public String templateId;
	}
}
