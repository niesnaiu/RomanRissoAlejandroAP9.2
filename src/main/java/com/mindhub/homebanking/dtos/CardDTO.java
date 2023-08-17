package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardType;

import java.time.LocalDate;

public class CardDTO {
    private long id;
    private String cardHolder;
    private LocalDate fromDate;
    private LocalDate truDate;
    private CardType cardType;
    private String number;
    private int cvv;

    public CardDTO(){}

    public CardDTO(Card card) {
        this.id = card.getId();
        this.cardHolder = card.getCardHolder();
        this.fromDate = card.getFromDate();
        this.truDate = card.getThruDate();
        this.cardType = card.getType();
        this.number = card.getNumber();
        this.cvv = card.getCvv();
    }

    public long getId() {
        return id;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public LocalDate getTruDate() {
        return truDate;
    }

    public CardType getCardType() {
        return cardType;
    }

    public String getNumber() {
        return number;
    }

    public int getCvv() {
        return cvv;
    }
}
