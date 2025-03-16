package com.cft.rest.exceptions;

public class NoMoreBooksAvailableException extends IllegalStateException {

	private static final long serialVersionUID = 1L;

	public NoMoreBooksAvailableException(String message) {
		super(message);
	}

	
}
