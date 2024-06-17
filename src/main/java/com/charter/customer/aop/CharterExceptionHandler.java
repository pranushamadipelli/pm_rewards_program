package com.charter.customer.aop;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.charter.customer.execption.CustomerException;
import com.charter.customer.utils.LogUtils;

@RestControllerAdvice
public class CharterExceptionHandler extends ResponseEntityExceptionHandler {

	
	@ExceptionHandler({ CustomerException.class, Exception.class })
	public ResponseEntity<CustomerException> handleAccessDeniedException(Exception ex, WebRequest request) {
		CustomerException customerException = null;
		if (ex instanceof CustomerException) {
			customerException = ((CustomerException) ex);
		} else {
			LogUtils.errorLog(ex);
		}
		return new ResponseEntity<CustomerException>(customerException, customerException.getHttpStatus());
	}

}
