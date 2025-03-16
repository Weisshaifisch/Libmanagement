package com.cft.rest.services;

import java.util.List;
import java.util.Optional;

import com.cft.rest.api.book.BookUpdate;
import com.cft.rest.entities.Book;
import com.cft.rest.exceptions.DuplicateEntityException;
import com.cft.rest.exceptions.EntityNotFoundException;
import com.cft.rest.repositories.BookRepository;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class BookService {

    private final BookRepository bookRepository;

    @Inject
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAllBooks() {
        return bookRepository.findAllBooks();
    }
    
    public Optional<Book> findBookByUniqueId(long id) {
        return bookRepository.findBookByUniqueId(id);
    }
    
    public Book addBook(Book book) {
		String title = book.getTitle();
		Book existingBook= findBookByTitle(title).orElse(null);
		if (existingBook != null) {
			throw new DuplicateEntityException("The book with the title '" + title + "' already exists");
		}
    	
    	
        return bookRepository.addBook(book);
    }
    
    public Book updateBook(BookUpdate bookUpdate) {
		String title = bookUpdate.getTitle();
		long bookId = bookUpdate.getId();
		if (title != null) {
			Book existingBook = findBookByTitle(title).orElse(null);
			if (existingBook != null && bookId != existingBook.getId()) {
				throw new DuplicateEntityException("The book with the title '" + title + "' already exists"); 
			}			
		}
    	
    	
    	Book oldBook = bookRepository.findBookByUniqueId(bookId).orElseThrow(() -> new EntityNotFoundException("Book with id " + bookId + " not found"));
    	
    	Book newBook = new Book(); 
    	newBook.setId(bookId);

    	String newTitle = bookUpdate.getTitle();
    	newBook.setTitle(newTitle == null ? oldBook.getTitle() : newTitle);

    	Integer newYearIssued = bookUpdate.getYearIssued();
    	newBook.setYearIssued(newYearIssued== null ? oldBook.getYearIssued() : newYearIssued);

    	Integer newNumberOfCopies = bookUpdate.getNumberOfCopies();
    	newBook.setNumberOfCopies(newNumberOfCopies== null ? oldBook.getNumberOfCopies(): newNumberOfCopies);
    	
    	List<String> newAuthors = bookUpdate.getAuthors();
    	newBook.setAuthors(newAuthors == null ? oldBook.getAuthors() : newAuthors);
    	
        return bookRepository.updateBook(newBook);
    }
    
    public void deleteBook(Book book) {
        bookRepository.deleteBook(book);
    }    
    
    public Optional<Book> findBookByTitle(String title) {
    	return bookRepository.findBookByTitle(title);
    }
}
