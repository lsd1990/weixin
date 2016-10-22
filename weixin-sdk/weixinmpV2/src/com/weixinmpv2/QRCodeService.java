package com.weixinmpv2;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.weixinmpv2.bean.QrCodeRequest;
import com.weixinmpv2.bean.QrCodeResponse;
import com.weixinmpv2.exception.WeixinmpException;
import com.weixinmpv2.uitl.Util;

/**
 * 推广二维码接口
 */
public class QRCodeService {
	
	private Logger logger = LoggerFactory.getLogger(QRCodeService.class);

    /** 微信公众平台API控制器实例 */
    private final AbstractWeixinmpController controller;

    protected QRCodeService(AbstractWeixinmpController controller) {
        this.controller = controller;
    }

    /**
     * 申请带参数二维码
     * @param type 类型，永久或者临时
     * @param sceneId 场景值ID，临时二维码时为32位非0整型，永久二维码时最大值为100000（目前参数只支持1--100000）
     * @param expreSeconds 该二维码有效时间，以秒为单位。 最大不超过1800。申请临时二维码时使用，永久二维码不使用。
     * @return 微信服务器返回ticket和有效时间
     * @throws WeixinException 如果发生错误
     */
    public QrCodeResponse createQrcode(QrCodeRequest.TYPE type, Integer sceneId, Integer expreSeconds) throws WeixinmpException {
        QrCodeRequest qrcode = new QrCodeRequest(type, sceneId, expreSeconds);
        String url =  Util.getProperty("qrcode_create_url");
        QrCodeResponse result = controller.postWithJson(url, qrcode, QrCodeResponse.class);
        return result;
    }
    
    
    /**
     * 申请带参数二维码
     * @param type 类型，永久或者临时
     * @param sceneId 场景值ID，临时二维码时为32位非0整型，永久二维码时最大值为100000（目前参数只支持1--100000）
     * @param expreSeconds 该二维码有效时间，以秒为单位。 最大不超过1800。申请临时二维码时使用，永久二维码不使用。
     * @return 微信服务器返回ticket和有效时间
     * @throws WeixinException 如果发生错误
     */
    public QrCodeResponse createQrcode(QrCodeRequest.TYPE type, String sceneStr, Integer expreSeconds) throws WeixinmpException {
        QrCodeRequest qrcode = new QrCodeRequest(type, sceneStr, expreSeconds);
        String url =  Util.getProperty("qrcode_create_url");
        QrCodeResponse result = controller.postWithJson(url, qrcode, QrCodeResponse.class);
        return result;
    }


    /**
     * 拉取二维码
     * @param ticket二维码凭证
     * @return 返回一个数据流
     * @throws IOException 
     * @throws WeixinException
     *             如果发生错误
     */
    public InputStream getQrCodeAsStream(String ticket) throws IOException {
    	 String url =  Util.getProperty("qrcode_get_url");
         try {
			url = url.replaceFirst("TICKET", URLEncoder.encode(ticket, "UTF-8"));
         } catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage());
			return null;
         }
         return controller.downLoadAsStream(url);
    }

    /**
     * 拉取二维码
     * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/, 2014-2-24 下午2:08:20
     * @param ticket
     *            二维码凭证
     * @return 返回一个文件
     * @throws WeixinException
     *             如果发生错误
     */
    public File getQrCodeAsFile(String ticket, String fileName, String filenameSuffix)  {
    	 String url =  Util.getProperty("qrcode_get_url");
         try {
			url = url.replaceFirst("TICKET", URLEncoder.encode(ticket, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage());
			return null;
		}
         File file = controller.downLoadFile(url, fileName, filenameSuffix);
         return file;
    }

}
