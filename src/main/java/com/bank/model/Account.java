package com.bank.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 
 * @author Stanslaus Odhiambo
 * Model Entity class for the account table holding the default Account
 *
 */
@Entity
@Table
public class Account {
    @Id @Column(name = "account_number")
    private String number;

    @Column
    private double balance;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
