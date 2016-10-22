package com.weixinmpv2.bean;

import com.google.gson.annotations.SerializedName;

/** 
 * @author nhsoft.lsd
 * @version 2016年1月23日  下午4:01:58 
 */
public class MpMaterialDelRequest {

	@SerializedName("media_id")
	private String mediaId;

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	
	
}
