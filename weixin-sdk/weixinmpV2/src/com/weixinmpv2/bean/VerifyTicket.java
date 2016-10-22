package com.weixinmpv2.bean;

import java.io.Serializable;

public class VerifyTicket implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -246134748644301829L;
	private String AppId;
	private String CreateTime;
	private String InfoType;
	public String getAppId() {
		return AppId;
	}
	public void setAppId(String appId) {
		AppId = appId;
	}
	public String getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(String createTime) {
		CreateTime = createTime;
	}
	public String getInfoType() {
		return InfoType;
	}
	public void setInfoType(String infoType) {
		InfoType = infoType;
	}
	
	
}
