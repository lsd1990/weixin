package com.weixinmpv2;

import com.weixinmpv2.bean.AbstractMessage;
import com.weixinmpv2.bean.BaseResp;
import com.weixinmpv2.bean.ImageMessage;
import com.weixinmpv2.bean.MassUploadNewsRequest;
import com.weixinmpv2.bean.MpMaterialDelRequest;
import com.weixinmpv2.bean.MpNewsMessage;
import com.weixinmpv2.bean.MpUploadNewsRequest;
import com.weixinmpv2.bean.MusicMessage;
import com.weixinmpv2.bean.NewsMessage;
import com.weixinmpv2.bean.TextMessage;
import com.weixinmpv2.bean.VideoMessage;
import com.weixinmpv2.bean.VoiceMessage;
import com.weixinmpv2.exception.WeixinmpException;
import com.weixinmpv2.uitl.Util;


/**
 * 主动消息接口
 */
public class MessageService {

    private final AbstractWeixinmpController controller;

    protected MessageService(AbstractWeixinmpController controller) {
        this.controller = controller;
    }

    /**
     * 单个用户
     * 发送一个主动消息（文本、图片、语音、视频、音乐、图文）
     * @param message
     *            消息（文本、图片、语音、视频、音乐、图文）
     * @throws WeixinmpException
     *             如果发生错误
     */
    private void sendMessae(AbstractMessage message) throws WeixinmpException {
        String url = Util.getProperty("message_send_url");
        controller.postWithJson(url, message, BaseResp.class);
    }

    /**
     * 主动发送一个文本消息
     * @param toUser
     *            接收用户的OpenID
     * @param content
     *            发送内容
     * @throws WeixinException
     *             如果发送业务错误
     */
    public void sendText(String toUser, String content) throws WeixinmpException {
        TextMessage msg = new TextMessage();
        msg.touser = toUser;
        msg.addContent(content);
        sendMessae(msg);
    }
    
    /**
     * 主动发送一个文本消息
     * @param toUser
     *            接收用户的OpenID
     * @param content
     *            发送内容
     * @throws WeixinException
     *             如果发送业务错误
     */
    public void sendText(String toUser, String content, String kfAccount) throws WeixinmpException {
        TextMessage msg = new TextMessage();
        msg.touser = toUser;
        msg.addContent(content);
        if(kfAccount != null && !"".equals(kfAccount)){
        	msg.addCustomservice(kfAccount);
        }
        sendMessae(msg);
    }

    /**
     * 主动发送一个图片消息
     * @param toUser
     *            接收用户的OpenID
     * @param mediaId
     *            图片的资源ID
     * @throws WeixinException
     *             如果发送业务错误
     */
    public void sendImage(String toUser, String mediaId) throws WeixinmpException {
        ImageMessage msg = new ImageMessage();
        msg.touser = toUser;
        msg.image.media_id = mediaId;
        sendMessae(msg);
    }

    /**
     * 主动发送一个语音消息
     * @param toUser
     *            接收用户的OpenID
     * @param mediaId
     *            语音资源ID
     * @throws WeixinException
     *             如果发送业务错误
     */
    public void sendVoice(String toUser, String mediaId) throws WeixinmpException {
        VoiceMessage msg = new VoiceMessage();
        msg.touser = toUser;
        msg.media_id = mediaId;
        sendMessae(msg);
    }

    /**
     * 主动发送一个视频消息
     * @param toUser
     *            接收用户的OpenID
     * @param mediaId
     *            视频资源ID
     * @param title
     *            视频标题【可选】
     * @param description
     *            视频描述【可选】
     * @throws WeixinException
     *             如果发送业务错误
     */
    public void sendVideo(String toUser, String mediaId, String title, String description) throws WeixinmpException {
        VideoMessage msg = new VideoMessage();
        msg.touser = toUser;
        msg.media_id = mediaId;
        msg.title = title;
        msg.description = description;
        sendMessae(msg);
    }

    /**
     * 主动发送一个音乐消息消息
     * @param toUser
     *            接收用户的OpenID
     * @param musicUrl
     *            普通音质的音乐地址
     * @param hqMusicUrl
     *            高音质的音乐地址
     * @param thumbMediaId
     *            缩略图的资源ID
     * @param title
     *            音乐名称【可选】
     * @param description
     *            音乐描述【可选】
     * @throws WeixinmpException 
     */
    public void sendMusic(String toUser, String musicUrl, String hqMusicUrl, String thumbMediaId, String title, String description) throws WeixinmpException{
        MusicMessage msg = new MusicMessage();
        msg.touser = toUser;
        msg.musicurl = musicUrl;
        msg.hqmusicurl = hqMusicUrl;
        msg.title = title;
        msg.description = description;
        sendMessae(msg);
    }

    /**
     * 主动发送一组文章消息
     * @param toUser
     *            接收用户的OpenID
     * @param news
     *            文章消息，至少添加一个Article，最多10个
     * @throws WeixinmpException
     *             如果发送业务错误
     */
    public void sendNews(String toUser, NewsMessage.News news) throws WeixinmpException {
        NewsMessage msg = new NewsMessage();
        msg.touser = toUser;
        msg.news = news;
        sendMessae(msg);
    }
    
    /**
     * 根据media_id 发送信息
     * @param toUser
     * @param media_id
     * @throws WeixinException
     */
    public void sendMpNews(String toUser, String media_id)throws WeixinmpException {
    	MpNewsMessage mpNewsMessage = new MpNewsMessage();
    	mpNewsMessage.touser = toUser;
    	mpNewsMessage.mpnews.media_id = media_id;
    	sendMessae(mpNewsMessage);
    }
    
    
    /**
     * 上传多图文信息
     * @param request
     * @return mediaId
     * @throws WeixinException
     */
    public String uploadMpNews(MpUploadNewsRequest request) throws WeixinmpException{
		String url = Util.getProperty("mp_news_upload");
		MassUploadNewsRequest.Response result = controller.postWithJson(url, request, MassUploadNewsRequest.Response.class);
		return result.mediaId;
    }
    
    /**
     * 删除永久素材
     * @param request
     * @return mediaId
     * @throws WeixinException
     */
    public void delMaterial(String mediaId) throws WeixinmpException{
		String url = Util.getProperty("mp_del_material");
		MpMaterialDelRequest request = new MpMaterialDelRequest();
		request.setMediaId(mediaId);
		controller.postWithJson(url, request, MassUploadNewsRequest.Response.class);
    }

}
