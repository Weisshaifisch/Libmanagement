package com.cft.rest.api.loan;

import java.time.LocalDate;

import com.cft.rest.entities.Loan;

import jakarta.validation.constraints.NotNull;

public class LoanResponse {

	private final long id;
	private final String userName;
	private final String bookTitle;
	private final LocalDate borrowDate;
	private final LocalDate returnDate;
	
	public LoanResponse(@NotNull Loan loan) {
		
		this.id = loan.getId();
		this.userName = loan.getUser().getName();
		this.bookTitle = loan.getBook().getTitle();
		this.borrowDate = loan.getBorrowDate();
		this.returnDate = loan.getReturnDate();
	}

	public long getId() {
		return id;
	}

	public String getUserName() {
		return userName;
	}

	public String getBookTitle() {
		return bookTitle;
	}

	public LocalDate getBorrowDate() {
		return borrowDate;
	}

	public LocalDate getReturnDate() {
		return returnDate;
	}	
	
}
