package com.weixinmpv2.bean;

import java.io.Serializable;

public class OauthData implements Serializable {


    /**
	 * 
	 */
    private static final long serialVersionUID = 5653655620997329163L;

	/** 获取到的凭证 */
    public String access_token;

    /** 凭证有效时间，单位：秒 */
    public String expires_in;
    
    public String refresh_token;
    
    public String openid;
    
    public String scope;
}
