package com.weixinmpv2;

import com.weixinmpv2.bean.AbstractResponse;
import com.weixinmpv2.bean.ClickEventRequest;
import com.weixinmpv2.bean.ImageRequest;
import com.weixinmpv2.bean.LinkRequest;
import com.weixinmpv2.bean.LocationEventRequest;
import com.weixinmpv2.bean.LocationRequest;
import com.weixinmpv2.bean.MassSendJobFinishEventRequest;
import com.weixinmpv2.bean.ScanEventRequest;
import com.weixinmpv2.bean.SubscribeEventRequest;
import com.weixinmpv2.bean.TemplateSendJobFinishRequest;
import com.weixinmpv2.bean.TextRequest;
import com.weixinmpv2.bean.VideoRequest;
import com.weixinmpv2.bean.VoiceRequest;

/**
 * 用户主动操作出发的事件的抽象接口<br>
 * 例如：用户向公众号发送消息、点击菜单事件、关注事件等等<br>
 */
public interface  AbstractUserOperate {


	 public AbstractResponse onTextMessage(TextRequest text);

	 public AbstractResponse onImageMessage(ImageRequest image);

	 public AbstractResponse onVoiceMessage(VoiceRequest voice);

	 public AbstractResponse onVideoMessage(VideoRequest video);

	 public AbstractResponse onLocationMessage(LocationRequest location);

	 public AbstractResponse onLinkMessage(LinkRequest link);

	 public AbstractResponse onSubscribeEvent(SubscribeEventRequest event);

	 public AbstractResponse onUnsubscribeEvent(SubscribeEventRequest event);

	 public AbstractResponse onScanEvent(ScanEventRequest event);

	 public AbstractResponse onLocationEvent(LocationEventRequest event) ;

	 public AbstractResponse onClickEvent(ClickEventRequest click) ;
    
	 public AbstractResponse onViewEvent(ClickEventRequest click) ;

	 public void onMassSendJobFinishEvent(MassSendJobFinishEventRequest massSendJobFinishEventRequest) ;
	
	 public void onTemplateSendJobFinishEvent(TemplateSendJobFinishRequest  templateSendJobFinishRequest);
}
