package com.cft.rest.api.loan;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BookManageRequest {

	private final long userId;
	private final long bookId;
	
	public BookManageRequest(@JsonProperty(value = "userId", required = true) long userId, 
							 @JsonProperty(value = "bookId", required = true) long bookId) {
		this.userId = userId;
		this.bookId = bookId;
	}
	
	public long getUserId() {
		return userId;
	}
	public long getBookId() {
		return bookId;
	}
	
	
}
