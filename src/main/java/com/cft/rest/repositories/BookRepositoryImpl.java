package com.cft.rest.repositories;

import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.cft.rest.entities.Book;
import com.cft.rest.utils.HibernateSessionFactoryUtil;
import com.google.inject.Singleton;

@Singleton
public class BookRepositoryImpl implements BookRepository {

	@Override
	public List<Book> findAllBooks() {
		try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
			return session.createQuery("from Book", Book.class).list();
		}		
	}

	@Override
	public Optional<Book> findBookByUniqueId(long id) {
		try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
			return Optional.ofNullable(session.get(Book.class, id));
		}				
	}

	@Override
	public Book addBook(Book book) {		
		Transaction transaction = null;
		try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.persist(book);
			transaction.commit();
			return book;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			
			throw new IllegalStateException(e);
		}
	}

	@Override
	public Book updateBook(Book book) {
		Transaction transaction = null;
		try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.merge(book);
			transaction.commit();
			return book;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			
			throw new IllegalStateException(e);
		}
	}

	@Override
	public void deleteBook(Book book) {
		Transaction transaction = null;
		try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.remove(book);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			
			throw new IllegalStateException(e);
		}		
	}

	@Override
	public Optional<Book> findBookByTitle(String title) {
		try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
			Query<Book> query = session.createQuery("from Book where title=:title", Book.class);
			query.setParameter("title", title);
			return query.uniqueResultOptional();
		}				
		
	}
	
	
}
