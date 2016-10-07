package com.bank.model;

/**
 * 
 * @author Stanslaus Odhiambo
 * Model class for the account balance infrmation
 *
 */
public class BalanceInfo {
    String account;
    double balance;

    public BalanceInfo(String account, double balance) {
        this.account = account;
        this.balance = balance;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}