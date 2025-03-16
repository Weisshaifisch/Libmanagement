package com.cft.rest.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.cft.rest.entities.Book;
import com.cft.rest.entities.Loan;
import com.cft.rest.entities.User;
import com.cft.rest.exceptions.DuplicateEntityException;
import com.cft.rest.repositories.LoanRepository;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class LoanService {

    private final LoanRepository loanRepository;

    @Inject
    public LoanService(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    } 
    
    public List<Loan> findAllLoans() {
        return loanRepository.findAllLoans();
    }
    
    public Optional<Loan> findLoanById(long id) {
    	return loanRepository.findById(id);
    }
    
    public List<Loan> findLoansByUser(User user, LocalDate start, LocalDate end) {
    	if (start != null && end != null) {
    		return loanRepository.findLoansByUser(user, start, end);
    	}
        return loanRepository.findLoansByUser(user);
    }    
    
    public Loan loanBook(User user, Book book) {
    	Loan foundLoan = loanRepository
    		.findLoansByUser(user)
    		.stream()
    		.filter(loan -> loan.getBook().getId() == book.getId())
    		.findFirst()
    		.orElse(null);
    	
    	if (foundLoan != null) {
    		throw new DuplicateEntityException("The user " + user.getName() + " already has the book " + book.getTitle());
    	}
    	
        return loanRepository.loanBook(user, book);
    }
    
    public void returnBook(User user, Book book) {
        loanRepository.returnBook(user, book);
    }
    
}
