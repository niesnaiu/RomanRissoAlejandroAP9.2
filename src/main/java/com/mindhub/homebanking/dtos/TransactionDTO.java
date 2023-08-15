package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;

import java.time.LocalDate;

public class TransactionDTO {
    private Long id;
    private Double amount;
    private LocalDate transactionDate;
    private String description;
    private TransactionType type;
    private double balance;

    public TransactionDTO (Transaction transaction){
        this.id = transaction.getId();
        this.amount = transaction.getAmount();
        this.transactionDate = transaction.getTransactionDate();
        this.description = transaction.getDescription();
        this.type = transaction.getType();
        this.balance = transaction.getBalance();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }


    public LocalDate getTransactionDate() {
        return transactionDate;
    }


    public String getDescription() {
        return description;
    }


    public TransactionType getType() {
        return type;
    }


    public double getBalance() {
        return balance;
    }

}
