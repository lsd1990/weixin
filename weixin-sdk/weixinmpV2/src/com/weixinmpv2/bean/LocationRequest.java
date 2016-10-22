package com.weixinmpv2.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 位置消息的请求
 * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/
 */
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class LocationRequest extends AbstractRequest {

    private static final long serialVersionUID = 1L;

    /** 地理位置纬度 */
    private float Location_X;

    /** 地理位置经度 */
    private float Location_Y;

    /** 地图缩放大小 */
    private float Scale;

    /** 地理位置信息 */
    private String Label;

	public float getLocation_X() {
		return Location_X;
	}

	public void setLocation_X(float location_X) {
		Location_X = location_X;
	}

	public float getLocation_Y() {
		return Location_Y;
	}

	public void setLocation_Y(float location_Y) {
		Location_Y = location_Y;
	}

	public float getScale() {
		return Scale;
	}

	public void setScale(float scale) {
		Scale = scale;
	}

	public String getLabel() {
		return Label;
	}

	public void setLabel(String label) {
		Label = label;
	}

   
}
