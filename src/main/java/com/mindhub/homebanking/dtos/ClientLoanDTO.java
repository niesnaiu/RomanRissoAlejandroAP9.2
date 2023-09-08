package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.ClientLoan;

public class ClientLoanDTO {
    private long id;
    private long loanId;
    private Double amount;
    private int payments;
    private String loanName;
    public ClientLoanDTO(){

    }
    public ClientLoanDTO(ClientLoan clientLoan) {
        this.id = clientLoan.getId();
        this.loanId = clientLoan.getLoan().getId();
        this.amount = clientLoan.getAmount();
        this.payments = clientLoan.getPayments();
        this.loanName = clientLoan.getLoan().getName();


    }

    public long getId() {
        return id;
    }

    public long getLoanId() {
        return loanId;
    }

    public Double getAmount() {
        return amount;
    }

    public int getPayments() {
        return payments;
    }

    public String getLoanName() {
        return loanName;
    }


}
