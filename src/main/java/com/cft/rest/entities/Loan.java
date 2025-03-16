package com.cft.rest.entities;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

@Entity
@Table(name = "loans")
public class Loan {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "book_id", referencedColumnName = "id")
	private Book book;
	
	private LocalDate borrowDate = LocalDate.now();
	
	private LocalDate returnDate;
	
	public Loan() {
	}

	public Loan(@Valid User user, @Valid Book book) {
		this.user = user;
		this.book = book;
	}

	public long getId() {
		return id;
	}

	public LocalDate getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(@NotNull @PastOrPresent LocalDate returnDate) {
		this.returnDate = returnDate;
	}

	public LocalDate getBorrowDate() {
		return borrowDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(@Valid User user) {
		this.user = user;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(@Valid Book book) {
		this.book = book;
	}

	public void setBorrowDate(@NotNull @PastOrPresent LocalDate borrowDate) {
		this.borrowDate = borrowDate;
	}

	@Override
	public String toString() {
		return "Loan [id=" + id + ", user=" + user.getName() +  ", book=" + book + ", borrowDate=" + borrowDate + ", returnDate="
				+ (returnDate == null ? " not set " : returnDate) + "]";
	}

	
}
