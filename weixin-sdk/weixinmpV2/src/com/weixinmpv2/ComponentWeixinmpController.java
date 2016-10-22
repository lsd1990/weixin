package com.weixinmpv2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.weixinmpv2.bean.AbstractRequest;
import com.weixinmpv2.bean.AbstractResponse;
import com.weixinmpv2.bean.ApiAuthorizerTokenRequest;
import com.weixinmpv2.bean.ApiAuthorizerTokenResponse;
import com.weixinmpv2.bean.ApiComponentTokenRequest;
import com.weixinmpv2.bean.ApiComponentTokenResponse;
import com.weixinmpv2.bean.ApiQueryAuthRequest;
import com.weixinmpv2.bean.ApiQueryAuthResponse;
import com.weixinmpv2.bean.AuthorizationInfo;
import com.weixinmpv2.bean.AuthorizedVerifyTicket;
import com.weixinmpv2.bean.AuthorizerInfo;
import com.weixinmpv2.bean.AuthorizerInfoRequest;
import com.weixinmpv2.bean.AuthorizerInfoResponse;
import com.weixinmpv2.bean.BaseResp;
import com.weixinmpv2.bean.ClickEventRequest;
import com.weixinmpv2.bean.ComponentVerifyTicket;
import com.weixinmpv2.bean.EventRequest;
import com.weixinmpv2.bean.ImageRequest;
import com.weixinmpv2.bean.LinkRequest;
import com.weixinmpv2.bean.LocationEventRequest;
import com.weixinmpv2.bean.LocationRequest;
import com.weixinmpv2.bean.MassSendJobFinishEventRequest;
import com.weixinmpv2.bean.PreAuthCodeReq;
import com.weixinmpv2.bean.PreAuthCodeResp;
import com.weixinmpv2.bean.RequestHead;
import com.weixinmpv2.bean.ScanEventRequest;
import com.weixinmpv2.bean.SubscribeEventRequest;
import com.weixinmpv2.bean.TemplateSendJobFinishRequest;
import com.weixinmpv2.bean.TextRequest;
import com.weixinmpv2.bean.UnauthorizedVerifyTicket;
import com.weixinmpv2.bean.UpdateAuthorizedVerifyTicket;
import com.weixinmpv2.bean.VerifyTicket;
import com.weixinmpv2.bean.VideoRequest;
import com.weixinmpv2.bean.VoiceRequest;
import com.weixinmpv2.exception.WeixinmpException;
import com.weixinmpv2.uitl.AesException;
import com.weixinmpv2.uitl.DateUtil;
import com.weixinmpv2.uitl.Util;
import com.weixinmpv2.uitl.WXBizMsgCrypt;
import com.weixinmpv2.uitl.XMLParse;


public class ComponentWeixinmpController extends AbstractWeixinmpController{

	private Logger logger = LoggerFactory.getLogger(ComponentWeixinmpController.class);
	

	private CloseableHttpClient httpClient; // 使用连接池

	private String appid; // 第三方平台AppId
	private String appsecret; // 第三方平台appsecret
	private String token; // 第三方平台token
	private String key; // 第三方平台key
	private String authorizerRefreshToken; // 刷新令牌，貌似不会过去，没有说明
	private final String encoding = "utf-8";
	
	private String weixinAppid;

	private String componentVerifyTicket; // 授权ticket

	private String authCode; // 授权后返回的授权码

	private String lastComponentaccessToken = ""; // 调用第三方平台接口Token
	private Date lastComponentaccessTokenTime;

	private String lastAuthorizerAccessToken = ""; // 调用微信接口Token
	private Date lastAuthorizerAccessTokenTime;
	
	private String lastPreAuthCode = ""; //预授权码
	private Date lastPreAuthCodeTime;   


	public String getAppid() {
		return appid;
	}

	public String getAppsecret() {
		return appsecret;
	}

	public CloseableHttpClient getHttpClient() {
		return httpClient;
	}

	public void setHttpClient(CloseableHttpClient httpClient) {
		this.httpClient = httpClient;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public void setAppsecret(String appsecret) {
		this.appsecret = appsecret;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	protected String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	public void setAuthorizerRefreshToken(String authorizerRefreshToken) {
		this.authorizerRefreshToken = authorizerRefreshToken;
	}

	public String getComponentVerifyTicket() {
		return componentVerifyTicket;
	}

	public void setComponentVerifyTicket(String componentVerifyTicket) {
		this.componentVerifyTicket = componentVerifyTicket;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setWeixinAppid(String weixinAppid) {
		this.weixinAppid = weixinAppid;
	}


	public void setOperate(AbstractUserOperate operate) {
		this.operate = operate;
	}

	/** XML编组器集合 */
	Map<Class<? extends AbstractResponse>, JAXBContext> marshallerJAXBContexts = new HashMap<Class<? extends AbstractResponse>, JAXBContext>();
	
	/** SSL */
	private SSLSocketFactory sslSocketFactory;
	
	/** 请求头解释器 */
	JAXBContext headCoxtext;
	
	/** XML解释器集合 */
	Map<String, JAXBContext> unMarshallerJAXBContexts = new HashMap<String, JAXBContext>();

	/**
	 * 初始化SSL
	 */
	private void initSSLSocketFactory() {
		try {
			System.setProperty("jsse.enableSNIExtension", "false");
			System.setProperty("https.protocols", "TLSv1");
			SSLContext sslContext = SSLContext.getInstance("TLSv1");
			sslContext.init(null, null, null);
			sslSocketFactory = sslContext.getSocketFactory();
		} catch (NoSuchAlgorithmException e) {
			logger.error(e.getMessage(), e);
		} catch (KeyManagementException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public ComponentWeixinmpController() {
		initSSLSocketFactory();
		try {
			headCoxtext = JAXBContext.newInstance(RequestHead.class);
		
			unMarshallerJAXBContexts.put("text", JAXBContext.newInstance(TextRequest.class));
		
			unMarshallerJAXBContexts.put("image", JAXBContext.newInstance(ImageRequest.class));
			unMarshallerJAXBContexts.put("location", JAXBContext.newInstance(LocationRequest.class));
			unMarshallerJAXBContexts.put("link", JAXBContext.newInstance(LinkRequest.class));
			unMarshallerJAXBContexts.put("video", JAXBContext.newInstance(VideoRequest.class));
			unMarshallerJAXBContexts.put("voice", JAXBContext.newInstance(VoiceRequest.class));
			
			// 事件消息
			unMarshallerJAXBContexts.put("event", JAXBContext.newInstance(EventRequest.class));
			unMarshallerJAXBContexts.put("event_" + EventRequest.EventType.subscribe,
			        JAXBContext.newInstance(SubscribeEventRequest.class));
			unMarshallerJAXBContexts.put("event_" + EventRequest.EventType.unsubscribe,
			        JAXBContext.newInstance(SubscribeEventRequest.class));
			unMarshallerJAXBContexts.put("event_" + EventRequest.EventType.SCAN,
			        JAXBContext.newInstance(ScanEventRequest.class));
			unMarshallerJAXBContexts.put("event_" + EventRequest.EventType.LOCATION,
			        JAXBContext.newInstance(LocationEventRequest.class));
			unMarshallerJAXBContexts.put("event_" + EventRequest.EventType.CLICK,
			        JAXBContext.newInstance(ClickEventRequest.class));
			unMarshallerJAXBContexts.put("event_" + EventRequest.EventType.VIEW,
			        JAXBContext.newInstance(ClickEventRequest.class));
			unMarshallerJAXBContexts.put("event_" + EventRequest.EventType.MASSSENDJOBFINISH,
			        JAXBContext.newInstance(MassSendJobFinishEventRequest.class));
			unMarshallerJAXBContexts.put("event_" + EventRequest.EventType.TEMPLATESENDJOBFINISH,
			        JAXBContext.newInstance(TemplateSendJobFinishRequest.class));
		} catch (JAXBException e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 获取指定响应类型的编组器
	 * 
	 * @param respType
	 * @return
	 * @throws JAXBException
	 */
	private Marshaller getMarshaller(Class<? extends AbstractResponse> respType) throws JAXBException {
		JAXBContext context = marshallerJAXBContexts.get(respType);
		if (context == null) {
			context = JAXBContext.newInstance(respType);
			marshallerJAXBContexts.put(respType, context);
		}
		return context.createMarshaller();
	}
	
	
	@Override
	protected <T> T post(String url, com.weixinmpv2.bean.Entity entity, Class<T> returnClazz)
			throws WeixinmpException {

		OutputStream out = null;
		InputStream is = null;
		DataInputStream dis = null;
		try {
			Gson gson = Util.toBuilderGson();
			
			HttpURLConnection conn = openConnection(url);
			
			// 设置请求头
			String BOUNDARY = "----WebKitFormBoundaryEPoo0Ppd4sfHa2c5"; // 定义数据分隔线
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "keep-alive");
			conn.setRequestProperty("Keep-Alive", "max=50, timeout=5");
			conn.setReadTimeout(10000);
			conn.setConnectTimeout(5000);
			conn.setRequestProperty("Charset", "UTF-8");
			conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36");
			conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
			conn.setRequestProperty("Accept", "*/*");
			conn.setRequestProperty("Accept-Encoding", "gzip, deflate");
			conn.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");
			// 发送实体附件
			if (entity != null) {
				// 准备输出流
				out = new DataOutputStream(conn.getOutputStream());
				byte[] end_data = ("\r\n--" + BOUNDARY + "--\r\n").getBytes(encoding);// 定义最后数据分隔线
				StringBuilder sb = new StringBuilder();
				int bytes = 0;
				byte[] bufferOut = new byte[1024];
	
				// 输出分割线
				sb.setLength(0);
				sb.append("--").append(BOUNDARY).append("\r\n");
				// 输出实体头和内容
				switch (entity.type) {
				case stream:
					InputStream stream = (InputStream) entity.obj;
					sb.append("Content-Disposition: form-data;name=\"media\";filename=\""+entity.name+"\"\r\n");
					sb.append("Content-Type:application/octet-stream\r\n\r\n");
					
					out.write(sb.toString().getBytes(encoding));
					dis = new DataInputStream(stream);
					while ((bytes = dis.read(bufferOut)) != -1) {
						out.write(bufferOut, 0, bytes);
					}
					break;
				case binary:
					File file = (File) entity.obj;
					sb.append("Content-Disposition: form-data;name=\"" + entity.name + "\";filename=\""
					        + file.getAbsolutePath() + "\"\r\n");
					sb.append("Content-Type:application/octet-stream\r\n\r\n");
					out.write(sb.toString().getBytes(encoding));
					dis = new DataInputStream(new FileInputStream(file));
					while ((bytes = dis.read(bufferOut)) != -1) {
						out.write(bufferOut, 0, bytes);
					}
					break;
				case json:
					sb.append("Content-Disposition: form-data;name=\"" + entity.name + "\"\r\n");
					sb.append("Content-Type:text/plain\r\n\r\n");
					out.write(sb.toString().getBytes(encoding));
					String json = gson.toJson(entity.obj);
					out.write(json.getBytes(encoding));
					break;
				case text:
					sb.append("Content-Disposition: form-data;name=\"" + entity.name + "\"\r\n");
					sb.append("Content-Type:text/plain\r\n\r\n");
					out.write(sb.toString().getBytes(encoding));
					out.write(entity.obj.toString().getBytes(encoding));
					break;
				}
				out.write("\r\n".getBytes(encoding)); // 多个文件时，二个文件之间加入这个
				if (dis != null) {
					dis.close();
				}

				out.flush();
				
				// 输出结束符
				out.write(end_data);
				out.flush();
				out.close();
			}
			// 接收响应
			is = conn.getInputStream();
			String json = Util.inputStream2String(is);
			// 检查是否出现错误
			BaseResp error = gson.fromJson(json, BaseResp.class);
			if (error != null && error.errcode != null) {
				if (error.errcode != 0) {
					throw new WeixinmpException(error.errcode, error.errmsg);
				}
			}
		
			T obj = gson.fromJson(json, returnClazz);
			if (obj == null) {
				throw new WeixinmpException("微信服务器无响应");
			}
			return obj;
		} catch (JsonSyntaxException e) {
			throw new WeixinmpException(e.getMessage());
		} catch (IOException e) {
			throw new WeixinmpException(e.getMessage());
		}finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e2) {
					logger.error(e2.getMessage(), e2);
				}
			}
			if (is != null) {
				try {
					is.close();
				} catch (IOException e2) {
					logger.error(e2.getMessage(), e2);
				}
			}
			if (dis != null) {
				try {
					dis.close();
				} catch (IOException e2) {
					logger.error(e2.getMessage(), e2);
				}
			}
		}
	}
	
	private HttpURLConnection openConnection(String url) throws MalformedURLException, IOException, WeixinmpException {
	
		// 填充accessToken
		// 打开连接
		url = replaceToken(url);
		// 创建connection
		HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
		// 配置SSL
		if (conn instanceof HttpsURLConnection) {
			HttpsURLConnection https = (HttpsURLConnection) conn;
			https.setSSLSocketFactory(sslSocketFactory);
		}
		return conn;
	}

	@Override
	protected <T> T postWithJson(String url, Object params, Class<T> returnClazz) throws WeixinmpException {

		url = replaceToken(url);

		HttpPost httppost = new HttpPost(url);

		try {
			if (params != null) {
				StringEntity stringEntity = new StringEntity(Util.toBuilderGson().toJson(params), "UTF-8");// 解决中文乱码问题
				httppost.setEntity(stringEntity);
			}

			CloseableHttpResponse response = httpClient.execute(httppost);

			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String resp = EntityUtils.toString(entity, "UTF-8");

				BaseResp error = Util.toBuilderGson().fromJson(resp, BaseResp.class);
				if (error.getErrcode() != null && error.getErrcode().intValue() != 0) {
					throw new WeixinmpException(error.toString());
				}
				return Util.toBuilderGson().fromJson(resp, returnClazz);
			}

		} catch (ClientProtocolException e) {
			logger.error(e.getMessage(), e);
		} catch (UnsupportedEncodingException e1) {
			logger.error(e1.getMessage(), e1);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}

		return null;
	}

	private String replaceToken(String url) throws WeixinmpException {
		if (url.contains("COMPONENT_TOKEN")) {
			url = url.replaceFirst("COMPONENT_TOKEN", getComponentAccessToken());
		}

		if (url.contains("ACCESS_TOKEN")) {
			url = url.replaceFirst("ACCESS_TOKEN", getAuthorizerAccessToken());
		}
		return url;
	}

	private String getAuthorizerAccessToken() throws WeixinmpException {

		synchronized (lastAuthorizerAccessToken) {

			Date currentAuthorizerAccessTokenTime = Calendar.getInstance().getTime();

			if (lastAuthorizerAccessTokenTime == null
					|| DateUtil.diffSecond(lastAuthorizerAccessTokenTime, currentAuthorizerAccessTokenTime) > 7000) {

				ApiAuthorizerTokenRequest req = new ApiAuthorizerTokenRequest();
				req.setComponent_appid(appid);
				req.setAuthorizer_appid(weixinAppid);
				req.setAuthorizer_refresh_token(authorizerRefreshToken);

				ApiAuthorizerTokenResponse resp = postWithJson(Util.getProperty("api_authorizer_token"), req,
						ApiAuthorizerTokenResponse.class);

				lastAuthorizerAccessTokenTime = currentAuthorizerAccessTokenTime;
				lastAuthorizerAccessToken = resp.getAuthorizer_access_token();
				authorizerRefreshToken = resp.getAuthorizer_refresh_token();

			}
			return lastAuthorizerAccessToken;
		}
	}

	private String getComponentAccessToken() throws WeixinmpException {

		if (StringUtils.isEmpty(componentVerifyTicket)) {
			logger.warn("ComponentVerifyTicket为空，请稍后再试");
			return null;
		}

		synchronized (lastComponentaccessToken) {

			Date currentComponentaccessTokenTime = Calendar.getInstance().getTime();

			if (lastComponentaccessTokenTime == null
					|| DateUtil.diffSecond(lastComponentaccessTokenTime, currentComponentaccessTokenTime) > 7000) {

				ApiComponentTokenRequest apiComponentTokenReq = new ApiComponentTokenRequest();
				apiComponentTokenReq.setComponent_appid(appid);
				apiComponentTokenReq.setComponent_appsecret(appsecret);
				apiComponentTokenReq.setComponent_verify_ticket(componentVerifyTicket);
				ApiComponentTokenResponse apiComponentTokenResp = postWithJson(Util.getProperty("api_component_token"),
						apiComponentTokenReq, ApiComponentTokenResponse.class);

				lastComponentaccessTokenTime = currentComponentaccessTokenTime;
				lastComponentaccessToken = apiComponentTokenResp.getComponent_access_token();

			}

			return lastComponentaccessToken;
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, JAXBException, AesException {

		ServletInputStream inputStream = request.getInputStream();
		String xml = Util.inputStream2String(inputStream);
		
		WXBizMsgCrypt wxBizMsgCrypt = new WXBizMsgCrypt(token, key, appid);
		
		String msgSignature = request.getParameter("msg_signature");
		String timeStamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		
		xml = wxBizMsgCrypt.decryptMsg(msgSignature, timeStamp, nonce, xml);
		
		// 读取请求头
		StringReader reader = new StringReader(xml);
		Unmarshaller headUnMar = headCoxtext.createUnmarshaller();
		RequestHead head = (RequestHead) headUnMar.unmarshal(reader);
	
		// 解析为实际消息类型
		reader = new StringReader(xml);
		JAXBContext handlerCoxtext = unMarshallerJAXBContexts.get(head.getMsgType());
		Unmarshaller reqUnMar = handlerCoxtext.createUnmarshaller();
		AbstractRequest req = (AbstractRequest) reqUnMar.unmarshal(reader);
		
		AbstractResponse resp = null;
		PrintWriter out = response.getWriter();
		// 调用处理器
		try {
			if (req instanceof TextRequest) { // 用户发送文本信息事件
				resp = operate.onTextMessage((TextRequest) req);
			} else if (req instanceof ImageRequest) { // 用户发送图片信息事件
				resp = operate.onImageMessage((ImageRequest) req);
			} else if (req instanceof LocationRequest) { // 用户发送地理位置事件
				resp = operate.onLocationMessage((LocationRequest) req);
			} else if (req instanceof LinkRequest) { // 用户发送链接信息事件
				resp = operate.onLinkMessage((LinkRequest) req);
			} else if (req instanceof VideoRequest) { // 用户发送视频事件
				resp = operate.onVideoMessage((VideoRequest) req);
			} else if (req instanceof VoiceRequest) { // 用户发送语音事件
				resp = operate.onVoiceMessage((VoiceRequest) req);
			} else if (req instanceof EventRequest) { // 事件类型
				reader = new StringReader(xml);
				EventRequest event = (EventRequest) req;
				if (event == null || event.Event == null) {
					out.write("success");
					out.flush();
					out.close();
					return;
				}
				switch (event.Event) {
					case TEMPLATESENDJOBFINISH: // 模板消息发送后回复
						TemplateSendJobFinishRequest templateSendJobFinishRequest = (TemplateSendJobFinishRequest)unMarshallerJAXBContexts
								.get("event_" + EventRequest.EventType.TEMPLATESENDJOBFINISH).createUnmarshaller()
								.unmarshal(reader);
						templateSendJobFinishRequest.setXml(xml);
						operate.onTemplateSendJobFinishEvent(templateSendJobFinishRequest);
						return;
					case MASSSENDJOBFINISH: // 群发后回复
					
						MassSendJobFinishEventRequest massSendJobFinishEventRequest = (MassSendJobFinishEventRequest)unMarshallerJAXBContexts
								.get("event_" + EventRequest.EventType.MASSSENDJOBFINISH).createUnmarshaller()
						        .unmarshal(reader);
						massSendJobFinishEventRequest.setXml(xml);
						operate.onMassSendJobFinishEvent(massSendJobFinishEventRequest);
						return;
					case subscribe: // 用户订阅
						SubscribeEventRequest subscribeEvent = (SubscribeEventRequest) unMarshallerJAXBContexts
						        .get("event_" + EventRequest.EventType.subscribe).createUnmarshaller()
						        .unmarshal(reader);
						subscribeEvent.setXml(xml);
						resp = operate.onSubscribeEvent(subscribeEvent);
						break;
					case unsubscribe: // 用户退订
						SubscribeEventRequest unsubscribeEvent = (SubscribeEventRequest) unMarshallerJAXBContexts
						        .get("event_" + EventRequest.EventType.unsubscribe).createUnmarshaller()
						        .unmarshal(reader);
						unsubscribeEvent.setXml(xml);
						resp = operate.onUnsubscribeEvent(unsubscribeEvent);
						break;
					case SCAN: // 用户已关注时的事件推送
						ScanEventRequest scanEvent = (ScanEventRequest) unMarshallerJAXBContexts. //
						        get("event_" + EventRequest.EventType.SCAN).createUnmarshaller().unmarshal(reader);
						scanEvent.setXml(xml);
						resp = operate.onScanEvent(scanEvent);
						break;
					case LOCATION: // 上报地理位置事件
						LocationEventRequest locationEvent = (LocationEventRequest) unMarshallerJAXBContexts
						        . //
						        get("event_" + EventRequest.EventType.LOCATION).createUnmarshaller()
						        .unmarshal(reader);
						locationEvent.setXml(xml);
						resp = operate.onLocationEvent(locationEvent);
						break;
					case CLICK: // 自定义菜单点击按钮事件
						ClickEventRequest clickEvent = (ClickEventRequest) unMarshallerJAXBContexts. //
						        get("event_" + EventRequest.EventType.CLICK).createUnmarshaller().unmarshal(reader);
						clickEvent.setXml(xml);
						resp = operate.onClickEvent(clickEvent);
						break;
					case VIEW: // 点击菜单跳转链接时的事件推送
						ClickEventRequest viewEvent = (ClickEventRequest) unMarshallerJAXBContexts. //
						        get("event_" + EventRequest.EventType.VIEW).createUnmarshaller().unmarshal(reader);
						viewEvent.setXml(xml);
						resp = operate.onViewEvent(viewEvent);
						break;
						
					default:
						break;

				}

			} else {
				// 无效的请求
				logger.error("无效的事件：" + req);
			}
			// 没有返回值
			if (resp == null) {
				out.write("success");
				out.flush();
				out.close();
				return;
			} else {
				
				// 设置固定参数
				resp.ToUserName = req.getFromUserName();
				resp.FromUserName = req.getToUserName();
				resp.CreateTime = String.valueOf(new Date().getTime());
				
				// 转换为xml
				Class<? extends AbstractResponse> respType = resp.getClass();
				Marshaller respMar = getMarshaller(respType);
				StringWriter sw = new StringWriter();
				respMar.marshal(resp, sw);
				// 保存响应结果
				String result = sw.toString();

				String timestamp = Calendar.getInstance().getTimeInMillis() + "";

				result = wxBizMsgCrypt.encryptMsg(result, timestamp, "nhsoft");

				// 输出响应结果
				out.write(result);
				out.flush();
				out.close();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {

			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (Exception e2) {
			}

			try {
				if (out != null) {
					out.close();
				}
			} catch (Exception e2) {
			}
		}
	}

	@Override
	public String createPreAuthcode() throws WeixinmpException {
		
		synchronized (lastPreAuthCode) {
			
			Date currentPreAuthCodeTime = Calendar.getInstance().getTime();
			
			if (lastPreAuthCodeTime == null
					|| DateUtil.diffSecond(lastPreAuthCodeTime, currentPreAuthCodeTime) > 500) {
				
				PreAuthCodeReq req = new PreAuthCodeReq();
				req.setComponent_appid(appid);
				PreAuthCodeResp resp = postWithJson(Util.getProperty("api_create_preauthcode"), req, PreAuthCodeResp.class);
			
				lastPreAuthCode = resp.getPre_auth_code();
				lastPreAuthCodeTime = currentPreAuthCodeTime;
			}
		}
		return lastPreAuthCode;
	}
	
	/**
	 * 更具AuthCode获取信息
	 * @return
	 * @throws WeixinmpException
	 */
	@Override
	public AuthorizationInfo getAuthorizerInfo() throws WeixinmpException{
		
		ApiQueryAuthRequest req = new ApiQueryAuthRequest();
		req.setAuthorization_code(authCode);
		req.setComponent_appid(appid);
		
		ApiQueryAuthResponse apiQueryAuthResp = postWithJson(Util.getProperty("api_query_auth"), req, ApiQueryAuthResponse.class);
		return apiQueryAuthResp.getAuthorization_info();
	}
	
	@Override
	public VerifyTicket refreshTicket(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		
		ServletInputStream inputStream = req.getInputStream();
		String resut = Util.inputStream2String(inputStream);
		try {
			WXBizMsgCrypt wxBizMsgCrypt = new WXBizMsgCrypt(token, key, appid);
			String afterDecrpt = wxBizMsgCrypt.decryptTicketMsg(resut);
			VerifyTicket ticket = (VerifyTicket)XMLParse.getObjectFromXML(afterDecrpt, VerifyTicket.class);
		
			if (ticket.getInfoType().equals("component_verify_ticket")) {
				ticket = (ComponentVerifyTicket)XMLParse.getObjectFromXML(afterDecrpt, ComponentVerifyTicket.class);
				ComponentVerifyTicket componentVerifyTicket = (ComponentVerifyTicket)ticket;
				String componentVerifyTicketStr = componentVerifyTicket.getComponentVerifyTicket();
				setComponentVerifyTicket(componentVerifyTicketStr);
			}else if (ticket.getInfoType().equals("unauthorized")) {
				ticket = (UnauthorizedVerifyTicket)XMLParse.getObjectFromXML(afterDecrpt, UnauthorizedVerifyTicket.class);
			}else if(ticket.getInfoType().equals("authorized")){
				ticket = (AuthorizedVerifyTicket)XMLParse.getObjectFromXML(afterDecrpt, AuthorizedVerifyTicket.class);
			}else if(ticket.getInfoType().equals("updateauthorized")){
				ticket = (UpdateAuthorizedVerifyTicket)XMLParse.getObjectFromXML(afterDecrpt, UpdateAuthorizedVerifyTicket.class);
			}
			
			logger.info(ticket.toString());
			
			resp.getWriter().print("success");
			
			return ticket;
			
		} catch (AesException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
		
	}
	
	@Override
	public AuthorizationInfo handleComponentCallback(HttpServletRequest req, HttpServletResponse resp)throws IOException, WeixinmpException{
		
		String authCode = req.getParameter("auth_code");
		setAuthCode(authCode);
		AuthorizationInfo authorizationInfo = getAuthorizerInfo();
		setAuthorizerRefreshToken(authorizationInfo.getAuthorizer_refresh_token());
		
		return authorizationInfo;
	}
	
	@Override
	public AuthorizerInfo getAuthorizerInfo(String weixinAppid) throws WeixinmpException{
		
		AuthorizerInfoRequest req = new AuthorizerInfoRequest();
		req.setAuthorizer_appid(weixinAppid);
		req.setComponent_appid(appid);
		
		AuthorizerInfoResponse authorizerInfoResponse = postWithJson(Util.getProperty("api_get_authorizer_info"), req, AuthorizerInfoResponse.class);
		if (authorizerInfoResponse == null) {
			return null;
		}
		return  authorizerInfoResponse.getAuthorizer_info();
	}
}
