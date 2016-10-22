package com.weixinmpv2.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 *  群发接口 指定 openIds
 * @author nhsoft.lsd
 *
 */
public class MassTextMessageRequest implements Serializable {

	/**
	 * 
	 */
    private static final long serialVersionUID = -268862540037763130L;
    public String msgtype = "text";
    public Text text;
    public ToUser touser;
    
    /**
     * 添加内容
     * @param conent
     */
    public void addContent(String conent){
    	text = new Text();
    	text.content = conent;
    }
    
    public void addToUsers(List<String> toUsers){
    	touser = new ToUser();
    	touser.touser = toUsers;
    }
    
    public static class ToUser implements Serializable{

		/**
		 * 
		 */
        private static final long serialVersionUID = -6541160995470951620L;
        public List<String> touser = new ArrayList<String>();
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
