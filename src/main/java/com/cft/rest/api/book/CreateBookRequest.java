package com.cft.rest.api.book;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateBookRequest {

	private final String title;
	private final Integer yearIssued;
	private final Integer numberOfCopies;
	private final List<String> authors;
	
	public CreateBookRequest(@JsonProperty(value = "title", required = true) String title, 
							 @JsonProperty(value = "yearIssued", required = true) Integer yearIssued, 
							 @JsonProperty(value = "numberOfCopies", required = true) Integer numberOfCopies, 
							 @JsonProperty(value = "authors", required = true) List<String> authors) {
		
		this.title = title;
		this.yearIssued = yearIssued;
		this.numberOfCopies = numberOfCopies;
		this.authors = authors;
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
