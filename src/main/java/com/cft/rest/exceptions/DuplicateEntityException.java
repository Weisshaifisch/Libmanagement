package com.cft.rest.exceptions;

public class DuplicateEntityException extends IllegalArgumentException {

	private static final long serialVersionUID = 1L;

	public DuplicateEntityException(String message) {
		super(message);
	}

	
}
