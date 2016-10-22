package com.weixinmpv2.uitl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class Util {

	private static Logger logger = LoggerFactory.getLogger(Util.class);
	
	private static Properties properties = null;
	
	   /** 十六进制数字序列 */
    private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	
	static {
    	if (properties == null) {
    		URL url = Util.class.getResource("/weixinmp.properties");
    		if (url == null) {
    			throw new RuntimeException("缺少配置文件");
    		}
    		InputStream in = null;
    		try {
    			in = url.openStream();
    			properties = new Properties();
    			properties.load(in);
    			in.close();
    		} catch (IOException e) {
    			logger.error(e.getMessage(), e);
    		}
		}

		
    }
    
    public static String getProperty(String key){
    	
    	return properties.getProperty(key);
    }
    
	public static Gson toBuilderGson() {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			@Override
			public Date deserialize(final JsonElement json, final java.lang.reflect.Type typeOfT, final JsonDeserializationContext context)
					throws JsonParseException {
				try {
					return df.parse(json.getAsString());
				} catch (ParseException e) {
					return null;
				}
			}
		});
		gsonBuilder.registerTypeAdapter(Integer.class, new JsonDeserializer<Integer>() {

			@Override
			public Integer deserialize(JsonElement json, java.lang.reflect.Type typeOfT, JsonDeserializationContext context)
					throws JsonParseException {
				try {
					return Integer.parseInt(json.getAsString());
				} catch (Exception e) {
					return null;
				}
			}
		});
		gsonBuilder.disableHtmlEscaping();
		
		return gsonBuilder.create();
	} 
	
	public static String inputStream2String(InputStream is){
	    BufferedReader in = new BufferedReader(new InputStreamReader(is));
	    StringBuffer buffer = new StringBuffer();
	    String line = "";
	    try {
			while ((line = in.readLine()) != null){
			  buffer.append(line);
			 
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	    return buffer.toString();
	}
	
    /**
     * sha1加密
     * @param str
     *            数据原文
     * @return sha1密文，如果失败将返回null
     */
    public static String sha1(String str) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("sha1");
            md.update(str.getBytes());
            byte[] hash = md.digest();
            return toHexString(hash);
        } catch (Exception e) {
        	logger.error(e.getMessage(), e);
            return null;
        }

    }
    
    /**
     * 转换为十六进制字符串
     * @param bytes
     *            二进制数组
     * @return 十六进制的字符串
     */
    public static String toHexString(byte[] bytes) {
        int len = bytes.length;
        StringBuilder buf = new StringBuilder(len * 2);
        for (int j = 0; j < len; j++) {
            buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
            buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
        }
        return buf.toString();
    }

}
