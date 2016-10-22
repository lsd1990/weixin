package com.weixinmpv2.bean;

import java.io.Serializable;

/**
 * 客服聊天记录查询条件
 */
public class MessageRecordRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1191325566671595618L;
	public long starttime;
	public long endtime;
	public String openid;
	public int pagesize;
	public int pageindex;
   
}
