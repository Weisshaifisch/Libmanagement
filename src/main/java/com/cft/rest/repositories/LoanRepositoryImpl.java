package com.cft.rest.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.cft.rest.entities.Book;
import com.cft.rest.entities.Loan;
import com.cft.rest.entities.User;
import com.cft.rest.exceptions.NoMoreBooksAvailableException;
import com.cft.rest.utils.HibernateSessionFactoryUtil;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class LoanRepositoryImpl implements LoanRepository {

	private final BookRepository bookRepository;

	@Inject
	public LoanRepositoryImpl(BookRepository bookRepository) {
		this.bookRepository = bookRepository;		
	}

	@Override
	public List<Loan> findAllLoans() {
		try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
			return session.createQuery("from Loan", Loan.class).list();
		}		
	}

	@Override
	public Loan loanBook(User user, Book book) {
		if (book.getNumberOfCopies() == 0) {
			throw new NoMoreBooksAvailableException("All copies of the book '" + book.getTitle() + "' have been distributed to readers");
		}
		
		Transaction transaction = null;
		try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
			Loan loan = new Loan(user, book);
			transaction = session.beginTransaction();
			book.loanBook();
			bookRepository.updateBook(book);
			user.addLoan(loan);
			session.persist(loan);
			transaction.commit();
			return loan;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			
			throw new IllegalStateException(e);
		}		
	}

	@Override
	public void returnBook(User user, Book book) {
		findLoansByUser(user)
			.stream()
			.filter(loan -> loan.getBook().getId() == book.getId())
			.findFirst()
			.ifPresent(loan -> {
				Transaction transaction = null;
				try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
					transaction = session.beginTransaction();
					book.returnBook();
					bookRepository.updateBook(book);
					session.remove(loan);							
					transaction.commit();
				} catch (Exception e) {
					if (transaction != null) {
						transaction.rollback();
					}
				
					throw new IllegalStateException(e);
				}				
		});	
	}

	@Override
	public List<Loan> findLoansByUser(User user) {
		try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
			Query<Loan> query = session.createQuery("from Loan where user=:user", Loan.class);
			query.setParameter("user", user);
			return query.list(); 			
		}
	}

	@Override
	public Optional<Loan> findById(long id) {
		try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
			return Optional.ofNullable(session.get(Loan.class, id));
		}	
	}

	@Override
	public List<Loan> findLoansByUser(User user, LocalDate start, LocalDate end) {
		try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
			Query<Loan> query = session.createQuery("from Loan where user=:user and borrowDate between :start and :end", Loan.class);
			query.setParameter("user", user);
			query.setParameter("start", start);
			query.setParameter("end", end);
			return query.list(); 			
		}

	}
	
	
}
