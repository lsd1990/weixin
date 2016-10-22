package com.weixinmpv2.bean;

import java.io.Serializable;

public class AuthorizerInfoResponse implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4968793466465002226L;
	private AuthorizerInfo authorizer_info;
	public AuthorizerInfo getAuthorizer_info() {
		return authorizer_info;
	}
	public void setAuthorizer_info(AuthorizerInfo authorizer_info) {
		this.authorizer_info = authorizer_info;
	}
	
	

}
