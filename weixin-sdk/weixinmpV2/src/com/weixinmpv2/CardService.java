package com.weixinmpv2;

import com.weixinmpv2.bean.CardCodeConsumeRequest;
import com.weixinmpv2.bean.CardCodeConsumeResponse;
import com.weixinmpv2.bean.CardCodeDecryptRequest;
import com.weixinmpv2.bean.CardCodeDecryptResponse;
import com.weixinmpv2.bean.CardCodeGetRequest;
import com.weixinmpv2.bean.CardCodeGetResponse;
import com.weixinmpv2.bean.CardGetRequest;
import com.weixinmpv2.bean.CardResponse;
import com.weixinmpv2.exception.WeixinmpException;
import com.weixinmpv2.uitl.Util;


public class CardService {

    private final AbstractWeixinmpController controller;
	
    protected CardService(AbstractWeixinmpController controller){
    	this.controller = controller;
    }
    
    /**
     * 查询Code
     * @param req
     * @return
     * @throws WeixinmpException 
     * @throws WeixinException
     */
    public CardCodeGetResponse getCardCode(CardCodeGetRequest req) throws WeixinmpException{
		String url = Util.getProperty("card_code_get");
		CardCodeGetResponse result = controller.postWithJson(url, req, CardCodeGetResponse.class);
		CardCodeGetResponse response = (CardCodeGetResponse) result;
		return response;
    }
    
    /**
     * 核销卡券接口
     * @param req
     * @return
     * @throws WeixinmpException 
     * @throws WeixinException
     */
    public CardCodeConsumeResponse consumeCardCode(CardCodeConsumeRequest req) throws WeixinmpException{
		String url = Util.getProperty("card_code_consume");
		CardCodeConsumeResponse result = controller.postWithJson(url, req, CardCodeConsumeResponse.class);
		CardCodeConsumeResponse response = (CardCodeConsumeResponse) result;
		return response;
    }
    
    /**
     * 解密卡券Code
     * @param req
     * @return
     * @throws WeixinmpException 
     * @throws WeixinException
     */
    public CardCodeDecryptResponse decryptCardCode(CardCodeDecryptRequest req) throws WeixinmpException{
		String url = Util.getProperty("card_code_decrypt");
		CardCodeDecryptResponse result = controller.postWithJson(url, req, CardCodeDecryptResponse.class);
		CardCodeDecryptResponse response = (CardCodeDecryptResponse) result;
		return response;
    }
    
    /**
     * 查询卡券详情
     * @param req
     * @return
     * @throws WeixinmpException 
     * @throws WeixinException
     */
    public CardResponse getCard(CardGetRequest req) throws WeixinmpException{
		String url = Util.getProperty("card_get");
		CardResponse result = controller.postWithJson(url, req, CardResponse.class);
		CardResponse response = (CardResponse) result;
		return response;
    }
}
