package com.weixinmpv2.bean;

/**
 * 数据实体
 */
public class Entity {

	public TYPE type;

	public String name;

	public Object obj;

	public Entity(TYPE type, String name, Object obj) {
		this.type = type;
		this.name = name;
		this.obj = obj;
	}
	
	/**
	 * 数据类型
	 * 
	 * @author jianqing.cai@qq.com,
	 *         https://github.com/caijianqing/weixinmp4java/
	 */
	public static enum TYPE {
		/** 普通文本，直接调用key=value.toString */
		text,
		/** 需要转换为json格式 */
		json,
		/** 二进制数据，对象必须为文件对象 */
		binary,
		/** 二进制数据数据流，对象必须为InputStream的子类 */
		stream;
	}
}