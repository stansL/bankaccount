package com.bank.service;

import com.bank.model.Account;
import com.bank.model.Transaction;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@Transactional
public class AccountService {

    public static final int MAX_DEPOSIT_PER_TRANSACTION = 40000;
    public static final int MAX_DEPOSIT_PER_DAY = 150000;
    public static final int MAX_WITHDRAWAL_PER_TRANSACTION = 20000;
    public static final int MAX_WITHDRAWAL_PER_DAY = 50000;
    @Autowired
    private SessionFactory sessionFactory;

    public double getBalance(String number) {
        List<Account> accounts = sessionFactory.getCurrentSession().createQuery("from Account where account_number = :number")
                .setParameter("number", number).list();
        if (accounts == null || accounts.size() == 0) {
            throw new NullPointerException("No account with number: " + number + " found.");
        }
        return accounts.get(0).getBalance();
    }

    public double deposit(String number, double amount) {
        if (amount > MAX_DEPOSIT_PER_TRANSACTION) {
            throw new IllegalArgumentException("Exceeded Maximum deposit for transaction.");
        }
        if (getAmountDepositedToday(number) > MAX_DEPOSIT_PER_DAY) {
            throw new RuntimeException("Exceeded Maximum deposit for the day.");
        }
        sessionFactory.getCurrentSession().createQuery("update Account set balance = balance + :amount where account_number = :number")
                .setParameter("amount", amount)
                .setParameter("number", number)
                .executeUpdate();
        Account account = getAccount(number);
        Transaction.Type transactionType = Transaction.Type.DEPOSIT;
        generateTransaction(amount, account, transactionType);
        return account.getBalance();
    }

    public double withdraw(String number, double amount) {
        if (amount > MAX_WITHDRAWAL_PER_TRANSACTION) {
            throw new IllegalArgumentException("Exceeded Maximum withdrawal for transaction.");
        }
        if (getAmountWithdrawnToday(number) > MAX_WITHDRAWAL_PER_DAY) {
            throw new RuntimeException("Exceeded Maximum withdrawal for the day.");
        }
        sessionFactory.getCurrentSession().createQuery("update Account set balance = balance - :amount where account_number = :number")
                .setParameter("amount", amount)
                .setParameter("number", number)
                .executeUpdate();
        Account account = getAccount(number);
        Transaction.Type transactionType = Transaction.Type.WITHDRAWAL;
        generateTransaction(amount, account, transactionType);
        return account.getBalance();
    }

    public double getAmountDepositedToday(String number) {
        LocalTime midnight = LocalTime.MIDNIGHT;
        LocalDateTime today = LocalDateTime.of(LocalDate.now(), midnight);
        Object result = sessionFactory.getCurrentSession()
                .createQuery("select sum(amount) from Transaction where type = 'DEPOSIT' and date > :today")
                .setParameter("today", today).uniqueResult();
        if (result == null) {
            return 0;
        }
        return (double) result;
    }

    public double getAmountWithdrawnToday(String number) {
        LocalTime midnight = LocalTime.MIDNIGHT;
        LocalDateTime today = LocalDateTime.of(LocalDate.now(), midnight);
        Object result = sessionFactory.getCurrentSession()
                .createQuery("select sum(amount) from Transaction where type = 'WITHDRAWAL' and date > :today")
                .setParameter("today", today).uniqueResult();
        if (result == null) {
            return 0;
        }
        return (double) result;
    }

    private Account getAccount(String number) {
        List<Account> accounts = sessionFactory.getCurrentSession().createQuery("from Account where account_number = :number")
                .setParameter("number", number).list();
        if (accounts == null || accounts.size() == 0) {
            throw new NullPointerException("No account with number: " + number + " found.");
        }
        return accounts.get(0);
    }

    private void generateTransaction(double amount, Account account, Transaction.Type transactionType) {
        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(amount);
        transaction.setType(transactionType);
        transaction.setDate(LocalDateTime.now());
        sessionFactory.getCurrentSession().save(transaction);
    }
}
