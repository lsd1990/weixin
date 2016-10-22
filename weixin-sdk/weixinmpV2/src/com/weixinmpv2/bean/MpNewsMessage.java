package com.weixinmpv2.bean;

public class MpNewsMessage extends AbstractMessage{

	/**
	 * 
	 */
    private static final long serialVersionUID = -1162433823482955857L;

    public MpNews mpnews = new MpNews();
    
    public MpNewsMessage() {
    	 msgtype = "mpnews";
    }
    
    public class MpNews{
    	public String media_id;
    }
}


