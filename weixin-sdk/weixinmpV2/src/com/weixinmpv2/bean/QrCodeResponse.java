/**
 * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/, 2014-2-24 下午1:26:12
 */
package com.weixinmpv2.bean;

import java.io.Serializable;

/**
 * 申请带参数的二维码的响应结果
 * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/, 2014-2-24 下午1:26:12
 */
public class QrCodeResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 获取的二维码ticket，凭借此ticket可以在有效时间内换取二维码。 */
    public String ticket;

    /** 二维码的有效时间，以秒为单位。最大不超过1800 */
    public String expire_seconds;
    
    /**
     * 二维码图片解析后的地址，开发者可根据该地址自行生成需要的二维码图片
     */
    public String url;


	@Override
    public String toString() {
	    return "QrCodeResponse [ticket=" + ticket + ", expire_seconds=" + expire_seconds + ", url=" + url + "]";
    }
    
  

}
