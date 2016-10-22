package com.weixinmpv2.bean;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

/**
 * 群发多图文信息
 * @author nhsoft.lsd
 *
 */
public class MassNewsMessageAllRequest implements Serializable {

	/**
	 * 
	 */
    private static final long serialVersionUID = 4900335933345097977L;
    
    public Filter filter;
    public Mpnews mpnews;
    public String msgtype = "mpnews";
    
    /**
     * 添加过滤
     * @param isToAll 是否群发所有用户
     * @param groupId 用户分组好
     */
    public void addFilter(boolean isToAll, String groupId){
    	filter = new Filter();
    	filter.isToAll = isToAll;
    	filter.groupId = groupId;
    }
    
    /**
     * 添加多图文
     * @param mediaId
     */
    public void addMpnews(String mediaId){
    	mpnews = new Mpnews();
    	mpnews.mediaId = mediaId;
    }
    
    public static class Filter implements Serializable{

		/**
		 * 
		 */
        private static final long serialVersionUID = -5479058203782534294L;
    	
        @SerializedName("is_to_all")
        public boolean isToAll; //是否群发所有用户
        
        @SerializedName("group_id")
        public String groupId;   //用户分组好
    }
    
    public static class Mpnews implements Serializable{

		/**
		 * 
		 */
        private static final long serialVersionUID = 6072412516689296577L;
    	
        @SerializedName("media_id")
        public String mediaId;   //多图文上传后返回的media_id
    }

    /**
     * 响应信息
     * @author nhsoft.lsd
     *
     */
    public static class Response extends BaseResp{

		/**
		 * 
		 */
        private static final long serialVersionUID = 5307598862540774196L;
    	
        public String msg_id; //消息ID
        public String type; //媒体文件类型，分别有图片（image）、语音（voice）、视频（video）和缩略图（thumb），图文消息为news
    }
}
