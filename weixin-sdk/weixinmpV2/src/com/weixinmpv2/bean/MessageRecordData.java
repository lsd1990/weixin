package com.weixinmpv2.bean;

import java.io.Serializable;
import java.util.List;

public class MessageRecordData  extends  BaseResp{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6652459327217106669L;
	public List<Record> recordlist;
	
	class Record implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = -8987552720783601725L;
		public String worker;
		public String openid;
		public String opercode;
		public String time;
		public String text;
	}
	
}
