package com.cft.rest.entities;

import java.util.Arrays;
import java.util.List;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "books")
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private long id;
	
	@Size(min = 5, max = 100)
	@Column(unique = true, nullable = false)
	private String title;
	
	@Digits(integer = 4, fraction = 0)
	@Column(name = "year_issued", nullable = false)
	private Integer yearIssued;
		
	@Column(name = "number_of_copies")
	private Integer numberOfCopies = 1; 
	
	@JdbcTypeCode(SqlTypes.ARRAY)
	private List<String> authors;
	
	public Book() {		
	}

	public Book(@NotNull String title, 
			@NotNull Integer yearIssued, 
			@NotNull List<String> authors, 
			@NotNull Integer numberOfCopies) {
		
		this.title = title;
		this.yearIssued = yearIssued;
		this.authors = authors;
		this.numberOfCopies = numberOfCopies;
	}

	public void setId(long id) {
		this.id = id;
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

	public List<String> getAuthors() {
		return authors;
	}

	public Integer getNumberOfCopies() {
		return numberOfCopies;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setYearIssued(Integer yearIssued) {
		this.yearIssued = yearIssued;
	}

	public void setNumberOfCopies(Integer numberOfCopies) {
		this.numberOfCopies = numberOfCopies;
	}

	public void setAuthors(List<String> authors) {
		this.authors = authors;
	}
	
	public void loanBook() {
		numberOfCopies--;
	}
	
	public void returnBook() {
		numberOfCopies++;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", title=" + title + ", yearIssued=" + yearIssued + ", numberOfCopies="
				+ numberOfCopies + ", authors=" + Arrays.toString(authors.toArray())+ "]";
		
	}
	
}
