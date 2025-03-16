package com.cft.rest.utils;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import com.cft.rest.entities.Book;
import com.cft.rest.entities.Loan;
import com.cft.rest.entities.User;

public class HibernateSessionFactoryUtil {
    private static SessionFactory sessionFactory;

    private HibernateSessionFactoryUtil() {
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            Configuration configuration = new Configuration();
            configuration.addAnnotatedClass(User.class);
            configuration.addAnnotatedClass(Book.class);
            configuration.addAnnotatedClass(Loan.class);

            configuration.setProperty("connection.driver_class", "h2.org.Driver");
            configuration.setProperty("hibernate.connection.url", "jdbc:h2:mem:test");
            configuration.setProperty("dialect", "org.hibernate.dialect.H2Dialect");
            configuration.setProperty("hibernate.hbm2ddl.auto", "update");
            configuration.setProperty("hibernate.show_sql", "true");
            configuration.setProperty("hibernate.connection.pool_size", "10");
            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties());
            sessionFactory = configuration.buildSessionFactory(builder.build());
        }
        
        return sessionFactory;
    }
}
