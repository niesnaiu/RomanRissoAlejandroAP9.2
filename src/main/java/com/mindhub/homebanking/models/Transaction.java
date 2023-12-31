package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator( name = "nativa", strategy = "native")
    private long id;
    private Double amount;
    private LocalDate date = LocalDate.now();
    private String description;
    private TransactionType type;
    private double balance;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn (name ="account_Id")
    private Account account;

    public Transaction (){}

    public Transaction(TransactionType type, Double amount, String description) {
        this.amount = amount;
        this.description = description;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    /*public LocalDate getTransactionDate() {
        return transactionDate;
    }*/

    /*public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }*/

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
