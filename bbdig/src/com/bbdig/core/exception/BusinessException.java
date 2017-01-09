package com.bbdig.core.exception;

public class BusinessException extends Exception {

	public BusinessException(Object source, String msg) {
		super(msg);
	}

	public BusinessException(Object source, String msg, Exception e) {
		super(msg, e);
	}

}
