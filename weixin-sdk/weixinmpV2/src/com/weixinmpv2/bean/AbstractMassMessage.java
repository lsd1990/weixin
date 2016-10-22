package com.weixinmpv2.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 多OPEN_ID发送信息
 * @author nhsoft.lsd
 *
 */
public class AbstractMassMessage implements Serializable {

	/**
	 * 
	 */
    private static final long serialVersionUID = 275385245199621326L;
    public List<String> touser;
    public String msgtype;

}
