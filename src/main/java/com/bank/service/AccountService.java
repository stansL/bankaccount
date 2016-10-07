package com.bank.service;

import com.bank.model.Transaction;

/**
 * 
 * @author Stanslaus Odhiambo Interface declaring service methods to be
 *         implemented by a contract with the service impl
 *
 */
public interface AccountService {

	/**
	 * gets the balance for an account with the given id
	 * 
	 * @param number
	 *            - id number of the account
	 * @return account balanc, throws an error if given account does not exist
	 */
	double getBalance(String number);

	/**
	 * defines the logic for the deposit of an amount into a given account, tracks that the amounts are within the allowed
	 * range and that the maximum deposit frequency has not been surpassed
	 * @param number - account id
	 * @param amount - amount to be deposited
	 * @return balance after deposit
	 */
	double deposit(String number, double amount);

	/**
	 * defines the logic for the withdrawal of an amount into a given account, tracks that the amounts are within the allowed
	 * range and that the maximum withdrawal frequency has not been surpassed
	 * @param number - account id
	 * @param amount - amount to be deposited
	 * @return balance after withdrawal
	 */
	double withdraw(String number, double amount);

	/**
	 * Finds the cumulative amounts already transacted to day by TransactionType
	 * @param transactionType - type of transaction
	 * @return - amount transacted thus far
	 */
	double getAmountTransactedTodayByType(Transaction.Type transactionType);

	/**
	 * Gets the transaction frequency for the day by type
	 * @param transactionType - type oof transaction type to investigate(withdraw/deposit)
	 * @return
	 */
	long getTransactionFrequenciesToday(Transaction.Type transactionType);

}