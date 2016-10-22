package com.weixinmpv2.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * 群发多图文信息
 * @author nhsoft.lsd
 *
 */
public class MassNewsMessageRequest implements Serializable {

	/**
	 * 
	 */
    private static final long serialVersionUID = 4900335933345097977L;
    
    public ToUser touser;
    public Mpnews mpnews;
    public String msgtype = "mpnews";
    
    /**
     * 添加用户
     * @param isToAll 
     * @param groupId 
     */
    public void addToUsers(List<String> toUsers){
    	touser = new ToUser();
    	touser.touser = toUsers;
    }
    
    /**
     * 添加多图文
     * @param mediaId
     */
    public void addMpnews(String mediaId){
    	mpnews = new Mpnews();
    	mpnews.mediaId = mediaId;
    }
    
    public static class ToUser implements Serializable{

		/**
		 * 
		 */
        private static final long serialVersionUID = -6541160995470951620L;
        public List<String> touser = new ArrayList<String>();
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
