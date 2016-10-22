package com.weixinmpv2;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import com.weixinmpv2.bean.Entity;
import com.weixinmpv2.bean.Material;
import com.weixinmpv2.bean.Media;
import com.weixinmpv2.exception.WeixinmpException;
import com.weixinmpv2.uitl.Util;

/**
 * 多媒体内容库接口
 */
public class MediaLibraryService {

    private final AbstractWeixinmpController controller;

    protected MediaLibraryService(AbstractWeixinmpController controller) {
        this.controller = controller;
    }

    /**
     * 上传一个文件
     * @param type 文件类型
     * @param file 文件对象
     * @return 上传成功后微信服务器返回的信息
     * @return WeixinException 如发生错误
     * @throws Exception 
     */
    public Media uploadMedia(MediaLibraryService.TYPE type, File file) throws Exception{
        
        String url = Util.getProperty("file_upload_url");
        url = url.replaceFirst("TYPE", type.name());
        
        // 检查文件大小
        String fileLimitVarname = "file_upload_limit_" + type; 
        long fileLimit = Long.valueOf(Util.getProperty(fileLimitVarname));
        if (file.length() > fileLimit) {
            throw new WeixinmpException(String.format("超出文件大小限制,上限为[%s]", fileLimit));
        }
        // 发送文件
        Entity entity = new Entity(Entity.TYPE.binary, "media", file);
        Media media = controller.post(url, entity, Media.class);
        return media;
    }
    
    
    /**
     * 下载一个文件
     * @param mediaId 媒体ID
     * @param filenameSuffix 文件名后缀
     * @return 以文件的形式返回
     * @return WeixinException 如发生错误
     * @throws IOException 
     * @throws UnsupportedOperationException 
     */
    public File getMedia(String mediaId, String filename, String filenameSuffix) throws UnsupportedOperationException, IOException{
    	
    	 String url = Util.getProperty("file_download_url");
         url = url.replaceFirst("MEDIA_ID", mediaId);
         
         File file = controller.downLoadFile(url, filename, filenameSuffix);
         
         return file;
    }

    public Material uploadMaterial(MediaLibraryService.TYPE type, File file) throws Exception {
    	 
    	 String url = Util.getProperty("material_upload_url");
         url = url.replaceFirst("TYPE", type.name());
      // 检查文件大小
         String fileLimitVarname = "file_upload_limit_" + type;
         long fileLimit = Long.valueOf( Util.getProperty(fileLimitVarname));
         if (file.length() > fileLimit) {
        	 throw new WeixinmpException(String.format("超出文件大小限制,上限为[%s]", fileLimit));
         }
         // 发送文件
         Entity entity= new Entity(Entity.TYPE.binary, "media", file);
         Material media = controller.post(url, entity, Material.class);
         return media;
    }
    
    public Material uploadMaterial(MediaLibraryService.TYPE type, String fileName, InputStream inputStream) throws Exception {
   	 	
    	String url = Util.getProperty("material_upload_url");
        url = url.replaceFirst("TYPE", type.name());
        Entity entity= new Entity(Entity.TYPE.stream, fileName, inputStream);
        Material media = controller.post(url, entity, Material.class);
      
        return media;
   }
    
    /**
     * 媒体文件类型
     * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/
     */
    public enum TYPE {
        /** 图片文件，限制128k */
        image,
        /** 语音文件，限制256k */
        voice,
        /** 视频文件，限制1M */
        video,
        /** 缩略图文件，限制64k */
        thumb;
    }

}
