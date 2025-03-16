package com.cft.rest.resources;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.cft.rest.api.book.BookResponse;
import com.cft.rest.api.book.BookUpdate;
import com.cft.rest.api.book.CreateBookRequest;
import com.cft.rest.api.book.UpdateBookRequest;
import com.cft.rest.entities.Book;
import com.cft.rest.services.BookService;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;

@RunWith(MockitoJUnitRunner.class)
public class BookResourceTest {

    private static BookService bookService;
    private static BookResource bookResource;
    
    @BeforeClass
    public static void init() {
        bookService = mock(BookService.class);
        bookResource = new BookResource(bookService);
    }
    
    @Mock
    ContainerRequestContext containerRequestContext;
    
    @Test
    public void createBook() {
        String title = "Spring in Action";
        int yearIssued = 2022;
        int numberOfCopies = 2;
        List<String> authors = Collections.singletonList("Craig Walls");
        
        CreateBookRequest request = new CreateBookRequest(title, yearIssued, numberOfCopies, authors);        
        when(bookService.addBook(any(Book.class))).thenReturn(new Book(title, yearIssued, authors, numberOfCopies));
        
        UriInfo uriInfo = mock(UriInfo.class);
        when(uriInfo.getAbsolutePathBuilder()).thenReturn(UriBuilder.fromUri(URI.create("http://localhost:8080/api/books")));
        
        Response response = bookResource.createBook(request, uriInfo); 
        assertEquals(201, response.getStatus());        
    }
    
    @Test
    public void createBookEmptyRequestBody() {
        Assert.assertThrows(NullPointerException.class, () -> bookResource.createBook(null, null));
    }
    
    @Test
    public void testFindBooks() {
        
        Book book = new Book("Spring in Action", 2022, Collections.singletonList("Craig Walls"), 2);
        Book anotherBook = new Book("Spring Security in Action", 2020, Collections.singletonList("Laurentiu Spilca"), 2);
        List<Book> books = new ArrayList<>();
        books.add(book);
        books.add(anotherBook);
        when(bookService.findAllBooks()).thenReturn(books);
        
        List<BookResponse> expectedBookResponses = new ArrayList<>();
        expectedBookResponses.add(new BookResponse(book));
        expectedBookResponses.add(new BookResponse(anotherBook));
        
        List<BookResponse> bookResponses = bookResource.findAllBooks();
        assertTrue(Objects.equals(bookResponses, bookResponses));
    }
    
    @Test
    public void testUpdateBook() {
        
    	Book book = new Book("Spring in Action", 2022, Collections.singletonList("Craig Walls"), 2);
        book.setId(1);
        UpdateBookRequest request = new UpdateBookRequest(null, 2023, 3, null);
        Book updatedBook = new Book("Spring in Action", 2023, Collections.singletonList("Craig Walls"), 3);
        updatedBook.setId(1);
        
        when(bookService.updateBook(any(BookUpdate.class))).thenReturn(updatedBook);
        
        Response response = bookResource.updateBook(1, request);
        assertEquals(200, response.getStatus());
    }
    
    @Test
    public void testUpdateBookEmptyRequestBody() {
        Assert.assertThrows(NullPointerException.class, () -> bookResource.updateBook(1, null));
    }
    
    @Test
    public void testFindBookById() {
    	Book book = new Book("Spring in Action", 2022, Collections.singletonList("Craig Walls"), 2);
        book.setId(1);
        
        when(bookService.findBookByUniqueId(1)).thenReturn(Optional.of(book));
        Response response = bookResource.findBookById(1);
        assertEquals(200, response.getStatus());        
    }
    
    @Test
    public void testDeleteBook() {
    	Book book = new Book("Spring in Action", 2022, Collections.singletonList("Craig Walls"), 2);
        book.setId(1);

        when(bookService.findBookByUniqueId(1)).thenReturn(Optional.of(book));
        Response response = bookResource.deleteBook(1);
        assertEquals(204, response.getStatus());
    }
}