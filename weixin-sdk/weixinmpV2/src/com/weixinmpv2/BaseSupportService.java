package com.weixinmpv2;

import java.util.Arrays;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.weixinmpv2.bean.AccessToken;
import com.weixinmpv2.bean.SignatureInfo;
import com.weixinmpv2.exception.WeixinmpException;
import com.weixinmpv2.uitl.Util;

public class BaseSupportService {

	private Logger logger = LoggerFactory.getLogger(BaseSupportService.class);
    private final AbstractWeixinmpController controller;

    protected BaseSupportService(AbstractWeixinmpController controller) {
        this.controller = controller;
    }

    protected String onAccess(SignatureInfo sign, int timestampLimit) throws WeixinmpException {
    	logger.debug("微信服务器请求接入：" + sign);
        checkSignature(sign, timestampLimit);
        return sign.echostr;
    }

    protected AccessToken getAccessToken(String appid, String secert){
        if (appid == null || secert == null) {
            logger.warn("调用高级服务需要提供appid和secert");
        }
        String url = Util.getProperty("accessToken_url");
        url = url.replaceFirst("APPID", appid).replaceFirst("APPSECRET", secert);
        try {
            AccessToken token = controller.post(url, null, AccessToken.class);
            return token;
        } catch (WeixinmpException e) {
            logger.error(e.getMessage(), e);
        }
		return null;
    }
    
    /**
     * 检查签名
     * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/, 2014-2-25 下午4:02:59
     * @param sign
     * @param timestampLimit
     * @throws WeixinException
     */
    protected void checkSignature(SignatureInfo sign, int timestampLimit) throws WeixinmpException {
        // 预期签名
        String expectSignature = null;
        long expectTimestamp = new Date().getTime() / 1000;
        String err = null;
        // 验证数字签名
        if (sign.signature != null && sign.timestamp != null && sign.nonce != null) {
            // 0. 鉴定时间差
            try {
                long timestamp = Long.valueOf(sign.timestamp);
                if (Math.abs(expectTimestamp - timestamp) <= timestampLimit) {
                    // 1. sort
                    String[] tmpArr = new String[] { sign.token, sign.timestamp, sign.nonce };
                    Arrays.sort(tmpArr);
                    // 2. implode
                    StringBuffer sb = new StringBuffer();
                    for (String s : tmpArr) {
                        sb.append(s);
                    }
                    // 3. sha1
                    expectSignature = Util.sha1(sb.toString());
                    // 4. equals
                    if (expectSignature != null && expectSignature.equals(sign.signature)) {
                        return; // 验证通过
                    }
                }
            } catch (NumberFormatException e) {
                err = "时间格式不正确：" + e.getMessage();
            }
        }
        // 验证出错，抛出异常
        StringBuffer sb = new StringBuffer();
        sb.append("signature=").append(sign.signature).append("\r\n");
        sb.append("timestamp=").append(sign.timestamp).append("\r\n");
        sb.append("nonce=").append(sign.nonce).append("\r\n");
        sb.append("echostr=").append(sign.echostr).append("\r\n");
        sb.append("expectSignature=").append(expectSignature).append("\r\n");
        sb.append("expectTimestamp=").append(expectTimestamp).append("\r\n");
        sb.append("err=").append(err).append("\r\n");
        throw new WeixinmpException(sb.toString());
    }

}
