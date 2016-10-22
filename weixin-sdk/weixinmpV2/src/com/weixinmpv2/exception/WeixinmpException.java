package com.weixinmpv2.exception;

public class WeixinmpException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1717059335951157203L;
	
	private Integer errorCode;
	private String errorMsg;
	
	public WeixinmpException(String msg){
		super(msg);
	}
	
	public WeixinmpException(Integer errorCode, String msg){
		super(errorCode + ": " + msg);
		this.errorCode = errorCode;
		this.errorMsg = msg;
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}
