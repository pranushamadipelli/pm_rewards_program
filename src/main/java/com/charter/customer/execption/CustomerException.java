package com.charter.customer.execption;

import org.springframework.http.HttpStatus;

public class CustomerException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3571571450100243745L;

	private String errorMessage;

	private HttpStatus httpStatus;

	public CustomerException(String errorMessage, HttpStatus httpStatus) {
		this.errorMessage = errorMessage;
		this.httpStatus = httpStatus;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

}
