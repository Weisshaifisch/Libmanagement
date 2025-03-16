package com.cft.rest.exceptions;

public class EntityNotFoundException extends IllegalArgumentException {

	private static final long serialVersionUID = 1L;

	public EntityNotFoundException(String message) {
		super(message);
	}

	
}
