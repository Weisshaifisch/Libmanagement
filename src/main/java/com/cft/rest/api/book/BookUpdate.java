package com.cft.rest.api.book;

import java.util.List;

public class BookUpdate {

	private final long id;
	private final String title;
	private final Integer yearIssued;
	private final Integer numberOfCopies;
	private final List<String> authors;
	
	public BookUpdate(long id, String title, Integer yearIssued, Integer numberOfCopies, List<String> authors) {
		this.id = id;
		this.title = title;
		this.yearIssued = yearIssued;
		this.numberOfCopies = numberOfCopies;
		this.authors = authors;
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
