package com.cft.rest.repositories;

import java.util.List;
import java.util.Optional;

import com.cft.rest.entities.Book;

public interface BookRepository {

	List<Book> findAllBooks();
	
	Optional<Book> findBookByUniqueId(long id);
	
	Book addBook(Book book);
	
	Book updateBook(Book book);
	
	void deleteBook(Book book);	

	Optional<Book> findBookByTitle(String title);
}
