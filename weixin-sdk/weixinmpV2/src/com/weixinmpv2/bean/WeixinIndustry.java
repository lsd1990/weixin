package com.weixinmpv2.bean;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class WeixinIndustry implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4325518179967229898L;

	@SerializedName("industry_id1")
	public String industryId1;
	
	@SerializedName("industry_id2")
	public String industryId2;
}
