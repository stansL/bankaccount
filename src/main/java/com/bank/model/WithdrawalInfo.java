package com.bank.model;

public class WithdrawalInfo {
    String account;
    double withdrawn;
    double balance;

    public WithdrawalInfo(String account, double withdrawn, double balance) {
        this.account = account;
        this.withdrawn = withdrawn;
        this.balance = balance;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public double getWithdrawn() {
        return withdrawn;
    }

    public void setWithdrawn(double withdrawn) {
        this.withdrawn = withdrawn;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}