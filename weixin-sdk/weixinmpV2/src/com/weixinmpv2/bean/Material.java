package com.weixinmpv2.bean;

import java.io.Serializable;

public class Material implements Serializable {

	/**
	 * 
	 */
    private static final long serialVersionUID = 7854682580227419908L;
    public String media_id;
    public String url;
    
	public String getMedia_id() {
		return media_id;
	}
	public void setMedia_id(String media_id) {
		this.media_id = media_id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
    public String toString() {
	    return "Material [media_id=" + media_id + ", url=" + url + "]";
    }
    
    
}
