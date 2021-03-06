package com.bank.model;

/**
 * 
 * @author Stanslaus Odhiambo
 * Model class for the Deposit transaction information
 *
 */
public class DepositInfo {
    String account;
    double deposit;
    double balance;

    public DepositInfo(String account, double deposit, double balance) {
        this.account = account;
        this.deposit = deposit;
        this.balance = balance;
    }
    
    public DepositInfo(String account, double deposit) {
        this.account = account;
        this.deposit = deposit;
    }
    
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public double getDeposit() {
        return deposit;
    }

    public void setDeposit(double deposit) {
        this.deposit = deposit;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
