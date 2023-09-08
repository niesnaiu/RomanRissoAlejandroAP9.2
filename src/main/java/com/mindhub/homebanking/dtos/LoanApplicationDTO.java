package com.mindhub.homebanking.dtos;

public class LoanApplicationDTO {
    private Long id;
    private Double amount;

    private int payments;

    private String toAccountNumber;

    public LoanApplicationDTO(Double amount, int payments, String toAccountNumber) {
        this.amount = amount;
        this.payments = payments;
        this.toAccountNumber = toAccountNumber;
    }

    public Long getId() {
        return id;
    }

    public Double getAmount() {
        return amount;
    }

    public int getPayments() {
        return payments;
    }

    public String getToAccountNumber() {
        return toAccountNumber;
    }
}
