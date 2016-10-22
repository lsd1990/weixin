/**
 */
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
import javax.xml.bind.UnmarshalException;
import javax.xml.bind.Unmarshaller;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.weixinmpv2.bean.AbstractRequest;
import com.weixinmpv2.bean.AbstractResponse;
import com.weixinmpv2.bean.AccessToken;
import com.weixinmpv2.bean.BaseResp;
import com.weixinmpv2.bean.ClickEventRequest;
import com.weixinmpv2.bean.EventRequest;
import com.weixinmpv2.bean.ImageRequest;
import com.weixinmpv2.bean.LinkRequest;
import com.weixinmpv2.bean.LocationEventRequest;
import com.weixinmpv2.bean.LocationRequest;
import com.weixinmpv2.bean.MassSendJobFinishEventRequest;
import com.weixinmpv2.bean.RequestHead;
import com.weixinmpv2.bean.ScanEventRequest;
import com.weixinmpv2.bean.SignatureInfo;
import com.weixinmpv2.bean.SubscribeEventRequest;
import com.weixinmpv2.bean.TemplateSendJobFinishRequest;
import com.weixinmpv2.bean.TextRequest;
import com.weixinmpv2.bean.VideoRequest;
import com.weixinmpv2.bean.VoiceRequest;
import com.weixinmpv2.exception.WeixinmpException;
import com.weixinmpv2.uitl.Util;

public class DirectWeixinmpController extends AbstractWeixinmpController {

	private Logger logger = LoggerFactory.getLogger(DirectWeixinmpController.class);

	public final static String WEIXIN_PROPERTIES_FILENAME = "/weixinmp.properties";
	private String encoding = "UTF-8";
	private String token;
	private String username;
	private String appid;
	private String appsecret;

	public void setToken(String token) {
		this.token = token;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public void setAppsecret(String appsecret) {
		this.appsecret = appsecret;
	}

	public DirectWeixinmpController() {
		try {
			// 初始化SSL
			initSSLSocketFactory();
			// 基础XML解析
			headCoxtext = JAXBContext.newInstance(RequestHead.class);
			// 普通消息
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

	private SSLSocketFactory sslSocketFactory;

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

	/** 请求头解释器 */
	JAXBContext headCoxtext;

	/** XML解释器集合 */
	Map<String, JAXBContext> unMarshallerJAXBContexts = new HashMap<String, JAXBContext>();

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding(encoding);
		response.setContentType("text/html");
		response.setCharacterEncoding(encoding);
		// 收集参数
		SignatureInfo sign = getSignatureInfo(request);
		// 接入
		try {
			String result = baseSupportService.onAccess(sign, 60);
			// 返回接入结果
			PrintWriter out = response.getWriter();
			out.print(result);
			out.flush();
			out.close();
			// 保存记录
			StringBuffer sb = new StringBuffer();
			sb.append("signature=").append(sign.signature).append("\r\n");
			sb.append("timestamp=").append(sign.timestamp).append("\r\n");
			sb.append("nonce=").append(sign.nonce).append("\r\n");
			sb.append("echostr=").append(sign.echostr).append("\r\n");
		} catch (WeixinmpException e) {
			// 返回错误信息
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "GET OUT HERE");
			// 验证出错
			logger.warn(e.getMessage());
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 设置编码
		request.setCharacterEncoding(encoding);
		response.setContentType("text/html");
		response.setCharacterEncoding(encoding);
		StringReader reader = null;
		try {
			// 读取并保存xml内容
			ServletInputStream inputStream = request.getInputStream();

			String xml = Util.inputStream2String(inputStream);

			// 读取请求头
			reader = new StringReader(xml);
			Unmarshaller headUnMar = headCoxtext.createUnmarshaller();
			RequestHead head = (RequestHead) headUnMar.unmarshal(reader);
			// 解析为实际消息类型
			reader = new StringReader(xml);
			JAXBContext handlerCoxtext = unMarshallerJAXBContexts.get(head.getMsgType());
			Unmarshaller reqUnMar = handlerCoxtext.createUnmarshaller();
			AbstractRequest req = (AbstractRequest) reqUnMar.unmarshal(reader);

			// 准备响应结果
			AbstractResponse resp = null;
			// 检查开发者账号
			if (!username.equals(head.getToUserName())) {
				logger.error("开发者账号不正确: " + head.getToUserName() + "[" + username + "]");
			}
			// 鉴权
			if (resp == null) {
				SignatureInfo sign = getSignatureInfo(request);
				try {
					baseSupportService.checkSignature(sign, 60);
				} catch (WeixinmpException e) {
					// 签名不正确
					logger.error("签名不正确：" + e.getMessage());
				}
			}
			// 处理用户事件
			if (resp == null) {
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
							PrintWriter out = response.getWriter();
							out.write("success");
							out.flush();
							out.close();
							return;
						}
						switch (event.Event) {
						case TEMPLATESENDJOBFINISH: // 模板消息发送后回复
							TemplateSendJobFinishRequest templateSendJobFinishRequest = (TemplateSendJobFinishRequest) unMarshallerJAXBContexts
									.get("event_" + EventRequest.EventType.TEMPLATESENDJOBFINISH).createUnmarshaller()
									.unmarshal(reader);
							templateSendJobFinishRequest.setXml(xml);
							operate.onTemplateSendJobFinishEvent(templateSendJobFinishRequest);
							return;
						case MASSSENDJOBFINISH: // 群发后回复

							MassSendJobFinishEventRequest massSendJobFinishEventRequest = (MassSendJobFinishEventRequest) unMarshallerJAXBContexts
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
							LocationEventRequest locationEvent = (LocationEventRequest) unMarshallerJAXBContexts. //
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
				} catch (Throwable t) {
					logger.error(t.getMessage(), t);
				}
				// 没有返回值
				if (resp == null) {
					PrintWriter out = response.getWriter();
					out.write("success");
					out.flush();
					out.close();
					return;
				}
			}

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
			// 输出响应结果
			PrintWriter out = response.getWriter();
			out.write(result);
			out.flush();
			out.close();
		} catch (UnmarshalException e) {
			logger.error(e.getMessage(), e);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "XML PARSE ERROR");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "ENERGY DEFICIENCY");
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
	}

	// ///////////////////////////////////////////////////////

	/** 上次获取accessToken的时间 */
	private long lastAccessTokenTime = 0;

	/** 上次获取到的accessToken */
	private final AccessToken lastAccessToken = new AccessToken();

	/**
	 * 从缓存中获取AccessToken <br>
	 * 在超时有效期时自动重新获取
	 * 
	 * @param renew
	 *            是否更新token
	 * @return
	 */
	public final AccessToken getAccessToken(boolean renew) {
		synchronized (lastAccessToken) {
			// 如果上次获取到的token仍然在有效期则直接返回
			long now = new Date().getTime();
			if (renew || now / 1000 - lastAccessTokenTime >= 7000) {
				AccessToken token = baseSupportService.getAccessToken(appid, appsecret);
				lastAccessTokenTime = now;
				lastAccessToken.access_token = token.access_token;
				lastAccessToken.expires_in = token.expires_in;
			}
			return lastAccessToken;
		}
	}

	// ///////////////////////////////////////////////////////

	/**
	 * 从请求中收集并创建签名信息对象
	 * 
	 * @param request
	 * @return
	 */
	private SignatureInfo getSignatureInfo(HttpServletRequest request) {
		SignatureInfo sign = new SignatureInfo();
		sign.signature = request.getParameter("signature");
		sign.timestamp = request.getParameter("timestamp");
		sign.nonce = request.getParameter("nonce");
		sign.echostr = request.getParameter("echostr");
		sign.token = token;
		return sign;
	}

	// ///////////////////////////////////////////////////

	/**
	 * 打开一个URL连接
	 */
	private HttpURLConnection openConnection(String url) throws MalformedURLException, IOException {
		// 填充accessToken
		url = replaceAccessToken(url);
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
	protected final <T> T postWithJson(String url, Object params, Class<T> returnType) throws WeixinmpException {
		url = replaceAccessToken(url);

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
				return Util.toBuilderGson().fromJson(resp, returnType);
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

	@Override
	protected final <T> T post(String url, com.weixinmpv2.bean.Entity entity, Class<T> returnClazz)
			throws WeixinmpException {
		OutputStream out = null;
		InputStream is = null;
		DataInputStream dis = null;
		try {
			Gson gson = Util.toBuilderGson();
			// 打开连接
			url = replaceAccessToken(url);
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
			conn.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36");
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
				// 循环输出实体
				// 输出分割线
				sb.setLength(0);
				sb.append("--").append(BOUNDARY).append("\r\n");
				// 输出实体头和内容
				switch (entity.type) {
				case stream:
					InputStream stream = (InputStream) entity.obj;
					sb.append("Content-Disposition: form-data;name=\"media\";filename=\"" + entity.name + "\"\r\n");
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
		} finally {
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

	// ///////////////////////////XML解析器////////////////////////

	/** XML编组器集合 */
	Map<Class<? extends AbstractResponse>, JAXBContext> marshallerJAXBContexts = new HashMap<Class<? extends AbstractResponse>, JAXBContext>();

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

	// ///////////////////////////帮助信息////////////////////////

	/** 帮助信息文件名 */
	final String HELPS_FILENAME = "weixinmp_helps.xml";

	/** 向导文件解组解释器 */
	JAXBContext helpsCoxtext;

	/** 向导集合最后更新时间（标志位） */
	long guidesLastModifiedTime = 0;

	protected String replaceAccessToken(String url) {
		if (url.indexOf("ACCESS_TOKEN") != -1) {
			url = url.replaceFirst("ACCESS_TOKEN", getAccessToken(false).access_token);
		}
		return url;
	}
}
