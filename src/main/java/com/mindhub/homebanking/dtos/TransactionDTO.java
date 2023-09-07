package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;

import java.time.LocalDate;

public class TransactionDTO {
    private Long id;
    private Double amount;
    private LocalDate date = LocalDate.now();
    private String description;
    private TransactionType type;
    private double balance;

    public TransactionDTO (Transaction transaction){
        this.id = transaction.getId();
        this.type = transaction.getType();
        this.amount = transaction.getAmount();
        this.description = transaction.getDescription();
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

    public void setDate(LocalDate date) {
        this.date = date;
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

    public LocalDate getDate() {
        return date;
    }
}
