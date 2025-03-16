package com.cft.rest.repositories;

import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.cft.rest.entities.Book;
import com.cft.rest.entities.User;
import com.cft.rest.utils.HibernateSessionFactoryUtil;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class UserRepositoryImpl implements UserRepository {

	private final LoanRepository loanRepository;
	private final BookRepository bookRepository;
	
	@Inject
	public UserRepositoryImpl(LoanRepository loanRepository, BookRepository bookRepository) {
		this.loanRepository = loanRepository;
		this.bookRepository = bookRepository;
	}

	@Override
	public Optional<User> findUserByUniqueId(long id) {
		try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
			return Optional.ofNullable(session.get(User.class, id));
		}		
	}

	@Override
	public User addUser(User user) {
		Transaction transaction = null;
		try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.persist(user);
			transaction.commit();
			return user; 
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			
			throw new IllegalStateException(e);
		}
	}

	@Override
	public User updateUser(User user) {
		Transaction transaction = null;
		try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.merge(user);
			transaction.commit();
			return user;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			
			throw new IllegalStateException(e);
		}
	}

	@Override
	public void deleteUser(User user) {
		Transaction transaction = null;
		try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();			
			loanRepository.findLoansByUser(user).forEach(loan -> {
				Book book = loan.getBook();
				book.returnBook();
				bookRepository.updateBook(book);
			});
			
			session.remove(user);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			
			throw new IllegalStateException(e);
		}		
	}

	@Override
	public List<User> findAllUsers() {
		try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
			return session.createQuery("from User", User.class).list();
		}
	}

	@Override
	public Optional<User> findUserByName(String name) {
		try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
			Query<User> query = session.createQuery("from User where name=:name", User.class);
			query.setParameter("name", name);
			return query.uniqueResultOptional();
		}				

	}
	
	
}
