package com.weixinmpv2.bean;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class MassNewsRequest implements Serializable {

	
	/**
	 * 
	 */
    private static final long serialVersionUID = -252347816564763042L;
    
    public List<Article> articles; //图文消息，一个图文消息支持1到10条图文
    /** 文章 */
    public static class Article implements Serializable {


        /**
		 * 
		 */
        private static final long serialVersionUID = -3551816547384515193L;
        
        @SerializedName("thumb_media_id")
		public String thumbMediaId; //图文消息缩略图的media_id，可以在基础支持-上传多媒体文件接口中获得
       
        @SerializedName("author")
        public String author;  //图文消息的作者
        
        @SerializedName("title")
        public String title; //图文消息的标题
        
        @SerializedName("content_source_url")
        public String contentSourceUrl; //在图文消息页面点击“阅读原文”后的页面
        
        @SerializedName("content")
        public String content;  //图文消息页面的内容，支持HTML标签
        
        @SerializedName("digest")
        public String digest; //图文消息的描述
        
        @SerializedName("show_cover_pic")
        public String showCoverPic; //是否显示封面，1为显示，0为不显示
        
		@Override
        public String toString() {
	        return super.toString();
        }


    }
	
	public static class Response extends  BaseResp{

		/**
		 * 
		 */
		private static final long serialVersionUID = -3836279276863458026L;
		
		@SerializedName("type")
		public String type; //媒体文件类型，分别有图片（image）、语音（voice）、视频（video）和缩略图（thumb），次数为news，即图文消息
		
		@SerializedName("mediaId")
		public String mediaId; //媒体文件/图文消息上传后获取的唯一标识
		
		@SerializedName("created_at")
		public long createdAt; //媒体文件上传时间
	}
}
