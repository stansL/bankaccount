package com.bank.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 
 * @author Stanslaus Odhiambo
 * Entity model class for the transaction table
 *
 */
@Entity
@Table(name = "account_transaction")
public class Transaction {
    public static enum Type { DEPOSIT, WITHDRAWAL }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Id @Column @GeneratedValue
    private int id;
    @Column(name = "transaction_date")
    private LocalDateTime date;
    @Column
    private double amount;
    @Column(name = "transaction_type"/*, columnDefinition = "enum('DEPOSIT','WITHDRAWAL')"*/)
    @Enumerated(EnumType.STRING)
    private Type type;
    @ManyToOne
    @JoinColumn(name = "account_number", nullable = false)
    private Account account;
}
