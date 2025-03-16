package com.cft.rest.api.book;

import java.util.List;

import com.cft.rest.entities.Book;

public class BookResponse {

	private final long id;
	private final String title;
	private final Integer yearIssued;
	private final Integer numberOfCopies;
	private final List<String> authors;
	
	public BookResponse(Book book) {
		this.id = book.getId();
		this.title = book.getTitle();
		this.yearIssued = book.getYearIssued();
		this.numberOfCopies = book.getNumberOfCopies();
		this.authors = book.getAuthors();
	}

	public long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public Integer getYearIssued() {
		return yearIssued;
	}

	public Integer getNumberOfCopies() {
		return numberOfCopies;
	}

	public List<String> getAuthors() {
		return authors;
	}
 
}
