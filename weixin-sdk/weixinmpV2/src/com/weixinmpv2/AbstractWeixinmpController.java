package com.weixinmpv2;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.weixinmpv2.bean.AuthorizationInfo;
import com.weixinmpv2.bean.AuthorizerInfo;
import com.weixinmpv2.bean.VerifyTicket;
import com.weixinmpv2.exception.WeixinmpException;
import com.weixinmpv2.uitl.AesException;
import com.weixinmpv2.uitl.Util;

public abstract class AbstractWeixinmpController {
	
	private Logger logger = LoggerFactory.getLogger(AbstractWeixinmpController.class);

	public CloseableHttpClient httpClient; // 使用连接池

	protected CardService cardService = new CardService(this);

	protected CustomerMenuService customerMenuService = new CustomerMenuService(this);

	protected MassMessageService massMessageService = new MassMessageService(this);

	protected MediaLibraryService mediaLibraryService = new MediaLibraryService(this);

	protected MessageService messageService = new MessageService(this);

	protected QRCodeService qrCodeService = new QRCodeService(this);

	protected UserManagerService userManagerService = new UserManagerService(this);

	protected MessageTemplateService messageTemplateService = new MessageTemplateService(this);
	
	protected TicketService ticketService = new TicketService(this);
	
	protected BaseSupportService baseSupportService = new BaseSupportService(this);
	
	protected AbstractUserOperate operate;
	
	public CardService getCardService() {
		return cardService;
	}

	public void setCardService(CardService cardService) {
		this.cardService = cardService;
	}

	public CustomerMenuService getCustomerMenuService() {
		return customerMenuService;
	}

	public void setCustomerMenuService(CustomerMenuService customerMenuService) {
		this.customerMenuService = customerMenuService;
	}

	public MassMessageService getMassMessageService() {
		return massMessageService;
	}

	public void setMassMessageService(MassMessageService massMessageService) {
		this.massMessageService = massMessageService;
	}

	public MediaLibraryService getMediaLibraryService() {
		return mediaLibraryService;
	}

	public void setMediaLibraryService(MediaLibraryService mediaLibraryService) {
		this.mediaLibraryService = mediaLibraryService;
	}

	public MessageService getMessageService() {
		return messageService;
	}

	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}

	public QRCodeService getQrCodeService() {
		return qrCodeService;
	}

	public void setQrCodeService(QRCodeService qrCodeService) {
		this.qrCodeService = qrCodeService;
	}

	public UserManagerService getUserManagerService() {
		return userManagerService;
	}

	public void setUserManagerService(UserManagerService userManagerService) {
		this.userManagerService = userManagerService;
	}

	public MessageTemplateService getMessageTemplateService() {
		return messageTemplateService;
	}

	public void setMessageTemplateService(MessageTemplateService messageTemplateService) {
		this.messageTemplateService = messageTemplateService;
	}

	public TicketService getTicketService() {
		return ticketService;
	}

	public void setTicketService(TicketService ticketService) {
		this.ticketService = ticketService;
	}

	public AbstractUserOperate getOperate() {
		return operate;
	}

	public void setOperate(AbstractUserOperate operate) {
		this.operate = operate;
	}

	public void setHttpClient(CloseableHttpClient httpClient) {
		this.httpClient = httpClient;
	}

	protected abstract <T> T post(String url, com.weixinmpv2.bean.Entity entity, Class<T> returnClazz)
			throws WeixinmpException;
	
	protected <T> T postWithJson(String url, Object params, Class<T> returnClazz) throws WeixinmpException{
		return null;
	}
	
	public abstract void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, JAXBException, AesException;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
	}
	
	public  File downLoadFile(String url, String fileName, String filenameSuffix){
		FileOutputStream fos = null;
		InputStream inputStream = null;
		try {
			HttpGet httpget = new HttpGet(url);
			HttpResponse response = httpClient.execute(httpget);
			HttpEntity entity = response.getEntity();
			if (entity != null) {

				inputStream = entity.getContent();

				File file = new File(getDataFileDir(), fileName + filenameSuffix);
				fos = new FileOutputStream(file);

				byte[] buf = new byte[1024];
				int len = 0;

				while ((len = inputStream.read(buf)) != -1) {
					if (len == 0) {
						Thread.sleep(10);// 如果读取到0字节，则留休息一会免得CPU走火入魔
						continue;
					} else {
						fos.write(buf, 0, len);
					}
				}
				fos.flush();
				return file;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}

		return null;
		
	}

	public  InputStream downLoadAsStream(String url) throws IOException{
		InputStream inputStream = null;
		try {

			HttpGet httpget = new HttpGet(url);
			HttpResponse response = httpClient.execute(httpget);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				inputStream = entity.getContent();
				return inputStream;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					logger.error(e.getMessage());
				}
			}
		}
		return null;
	}
	
	protected String createPreAuthcode() throws WeixinmpException{
		return null;
	}
	
	protected AuthorizationInfo getAuthorizerInfo() throws WeixinmpException{
		return null;
	}
	
	protected VerifyTicket refreshTicket(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		return null;
	}
	
	protected AuthorizationInfo handleComponentCallback(HttpServletRequest req, HttpServletResponse resp)throws IOException, WeixinmpException{
		return null;
	}
	
	protected AuthorizerInfo getAuthorizerInfo(String weixinAppid) throws WeixinmpException{
		return null; 
	}
	

	/**
	 * 返回数据目录的路径（根据年月日产生不同的目录）
	 * 
	 * @return
	 */
	protected final File getDataFileDir() {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(System.currentTimeMillis());
		int y = cal.get(Calendar.YEAR);
		int m = cal.get(Calendar.MONTH) + 1;
		int d = cal.get(Calendar.DAY_OF_MONTH);
		String year = String.valueOf(y);
		String month = m >= 10 ? String.valueOf(m) : "0" + m;
		String day = d >= 10 ? String.valueOf(d) : "0" + m;
		StringBuffer sb = new StringBuffer();
		sb.append(year).append('/');
		sb.append(year).append('-').append(month).append('/');
		sb.append(year).append('-').append(month).append('-').append(day).append('/');
		File dir = new File(Util.getProperty("dataDir"), sb.toString());
		dir.mkdirs();
		return dir;
	}
}
