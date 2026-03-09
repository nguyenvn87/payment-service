package com.uit.common.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class BusinessException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = 4432343431418216573L;

	private String errorCode;
	private Integer httpStatus;
	private Object content;

	public BusinessException(String errorCode) {
		this.errorCode = errorCode;
		content = new ArrayList<>();
	}

	public BusinessException(String errorCode, Object content) {
		this.errorCode = errorCode;
		this.content = content;
	}

	public BusinessException(String errorCode, Object content, Integer httpStatus) {
		this.errorCode = errorCode;
		this.content = content;
		this.httpStatus = httpStatus;
	}

	public BusinessException(HttpStatusResponse response) {
		this.errorCode = response.getErrorCode();
		this.httpStatus = response.getHttpStatus();
		this.content = response.getContent();
	}
}
