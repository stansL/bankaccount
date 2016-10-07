package com.bank;

import org.hibernate.SessionFactory;
import org.hibernate.jpa.HibernateEntityManagerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * 
 * @author Stanslaus Odhiambo
 * Entry point to the Spring Boot REST Application
 *
 */
@SpringBootApplication
public class BankApplication {
    @Bean
    public SessionFactory sessionFactory(HibernateEntityManagerFactory hibernateEntityManagerFactory) {
        return hibernateEntityManagerFactory.getSessionFactory();
    }

    public static void main(String[] args) {
        SpringApplication.run(BankApplication.class, args);
    }
}
