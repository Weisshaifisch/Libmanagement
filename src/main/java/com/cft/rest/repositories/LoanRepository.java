package com.cft.rest.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.cft.rest.entities.Book;
import com.cft.rest.entities.Loan;
import com.cft.rest.entities.User;

public interface LoanRepository {

	List<Loan> findAllLoans();
	
	Optional<Loan> findById(long id);
	
	List<Loan> findLoansByUser(User user);
	
	List<Loan> findLoansByUser(User user, LocalDate start, LocalDate end);
	
	Loan loanBook(User user, Book book);
	
	void returnBook(User user, Book book);
}
