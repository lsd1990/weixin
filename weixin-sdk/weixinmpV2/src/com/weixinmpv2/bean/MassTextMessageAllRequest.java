package com.weixinmpv2.bean;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

/**
 * 群发文本信息
 * @author nhsoft.lsd
 *
 */
public class MassTextMessageAllRequest implements Serializable {

	/**
	 * 
	 */
    private static final long serialVersionUID = -268862540037763130L;

    public Filter filter;
    public Text text;
    public String msgtype = "text";
    
    
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
     * 添加文本内容
     * @param content
     */
    public void addContent(String content){
    	text = new Text();
    	text.content = content;
    }
    
    public static class Filter implements Serializable{

    	
        /**
		 * 
		 */
        private static final long serialVersionUID = -2143015847744311754L;

		@SerializedName("is_to_all")
        public boolean isToAll; //是否群发所有用户
        
        @SerializedName("group_id")
        public String groupId;   //用户分组好
    }
    
    public static class Text implements Serializable{

		/**
		 * 
		 */
        private static final long serialVersionUID = 2049375175001511285L;
    	
        public String content;
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
