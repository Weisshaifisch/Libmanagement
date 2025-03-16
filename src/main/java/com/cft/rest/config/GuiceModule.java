package com.cft.rest.config;

import com.cft.rest.repositories.BookRepository;
import com.cft.rest.repositories.BookRepositoryImpl;
import com.cft.rest.repositories.LoanRepository;
import com.cft.rest.repositories.LoanRepositoryImpl;
import com.cft.rest.repositories.UserRepository;
import com.cft.rest.repositories.UserRepositoryImpl;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class GuiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(BookRepository.class).to(BookRepositoryImpl.class).in(Singleton.class);
        bind(UserRepository.class).to(UserRepositoryImpl.class).in(Singleton.class);
        bind(LoanRepository.class).to(LoanRepositoryImpl.class).in(Singleton.class);
    }    
}
