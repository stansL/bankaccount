package com.bank.service.impl;

import com.bank.model.Account;
import com.bank.model.Transaction;
import com.bank.service.AccountService;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * 
 * @author Stanslaus Odhiambo
 *
 *Service layer class for implementing {@link AccountService}
 */

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

	public static final int MAX_DEPOSIT_PER_TRANSACTION = 40000;
	public static final int MAX_DEPOSIT_PER_DAY = 150000;
	public static final int MAX_WITHDRAWAL_PER_TRANSACTION = 20000;
	public static final int MAX_WITHDRAWAL_PER_DAY = 50000;
	public static final int MAX_WITHDRAWAL_FREQUENCY = 3;
	public static final int MAX_DEPOSIT_FREQUENCY = 4;
	@Autowired
	private SessionFactory sessionFactory;

	/* (non-Javadoc)
	 * @see com.bank.service.AccountService#getBalance(java.lang.String)
	 */
	@Override
	public double getBalance(String number) {
		List<Account> accounts = sessionFactory.getCurrentSession()
				.createQuery("from Account where account_number = :number").setParameter("number", number).list();
		if (accounts == null || accounts.size() == 0) {
			throw new NullPointerException("No account with number: " + number + " found.");
		}
		return accounts.get(0).getBalance();
	}

	/* (non-Javadoc)
	 * @see com.bank.service.AccountService#deposit(java.lang.String, double)
	 */
	@Override
	public double deposit(String number, double amount) {
		if (amount > MAX_DEPOSIT_PER_TRANSACTION) {
			throw new IllegalArgumentException("Exceeded Maximum deposit for transaction.");
		}
		if (getTransactionFrequenciesToday(Transaction.Type.DEPOSIT) >= MAX_DEPOSIT_FREQUENCY) {
			throw new RuntimeException("Exceeded Maximum withdrawal for the day.");
		}
		if (getAmountTransactedTodayByType(Transaction.Type.DEPOSIT) > MAX_DEPOSIT_PER_DAY) {
			throw new RuntimeException("Exceeded Maximum deposit for the day.");
		}
		sessionFactory.getCurrentSession()
				.createQuery("update Account set balance = balance + :amount where account_number = :number")
				.setParameter("amount", amount).setParameter("number", number).executeUpdate();
		Account account = getAccount(number);
		Transaction.Type transactionType = Transaction.Type.DEPOSIT;
		generateTransaction(amount, account, transactionType);
		return account.getBalance();
	}

	/* (non-Javadoc)
	 * @see com.bank.service.AccountService#withdraw(java.lang.String, double)
	 */
	@Override
	public double withdraw(String number, double amount) {
		if (amount > MAX_WITHDRAWAL_PER_TRANSACTION) {
			throw new IllegalArgumentException("Exceeded Maximum withdrawal for transaction.");
		}
		if (getTransactionFrequenciesToday(Transaction.Type.WITHDRAWAL) >= MAX_WITHDRAWAL_FREQUENCY) {
			throw new RuntimeException("Exceeded Maximum withdrawal for the day.");
		}
		
		if (getAmountTransactedTodayByType(Transaction.Type.WITHDRAWAL) > MAX_WITHDRAWAL_PER_DAY) {
			throw new RuntimeException("Exceeded Maximum withdrawal for the day.");
		}
		
		sessionFactory.getCurrentSession()
				.createQuery("update Account set balance = balance - :amount where account_number = :number")
				.setParameter("amount", amount).setParameter("number", number).executeUpdate();
		Account account = getAccount(number);
		Transaction.Type transactionType = Transaction.Type.WITHDRAWAL;
		generateTransaction(amount, account, transactionType);
		return account.getBalance();
	}

	/* (non-Javadoc)
	 * @see com.bank.service.AccountService#getAmountTransactedTodayByType(com.bank.model.Transaction.Type)
	 */
	@Override
	public double getAmountTransactedTodayByType(Transaction.Type transactionType) {
		LocalTime midnight = LocalTime.MIDNIGHT;
		LocalDateTime today = LocalDateTime.of(LocalDate.now(), midnight);
		Object result = sessionFactory.getCurrentSession()
				.createQuery("select sum(amount) from Transaction where type =:transactionType and date > :today")
				.setParameter("transactionType", transactionType)
				.setParameter("today", today).uniqueResult();
		if (result == null) {
			return 0;
		}
		return (double) result;
	}


	/* (non-Javadoc)
	 * @see com.bank.service.AccountService#getTransactionFrequenciesToday(com.bank.model.Transaction.Type)
	 */
	@Override
	public long getTransactionFrequenciesToday(Transaction.Type transactionType) {
		LocalTime midnight = LocalTime.MIDNIGHT;
		LocalDateTime today = LocalDateTime.of(LocalDate.now(), midnight);
		Object result = sessionFactory.getCurrentSession()
				.createQuery("select count(id) from Transaction where type =:transactionType  and date > :today")
				.setParameter("transactionType", transactionType).setParameter("today", today).uniqueResult();
		if (result == null) {
			return 0;
		}
		return (long) result;
	}

	/**
	 * getst ehe account given the id
	 * @param number - id number for the account
	 * @return the account with given id
	 */
	private Account getAccount(String number) {
		List<Account> accounts = sessionFactory.getCurrentSession()
				.createQuery("from Account where account_number = :number").setParameter("number", number).list();
		if (accounts == null || accounts.size() == 0) {
			throw new NullPointerException("No account with number: " + number + " found.");
		}
		return accounts.get(0);
	}

	/**
	 * Generates the transaction
	 * @param amount - amount to be transacted
	 * @param account - account 
	 * @param transactionType - type of transaction
	 */
	private void generateTransaction(double amount, Account account, Transaction.Type transactionType) {
		Transaction transaction = new Transaction();
		transaction.setAccount(account);
		transaction.setAmount(amount);
		transaction.setType(transactionType);
		transaction.setDate(LocalDateTime.now());
		sessionFactory.getCurrentSession().save(transaction);
	}
}
