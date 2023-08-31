package com.mindhub.homebanking.models;

import com.mindhub.homebanking.extras.ExtraMeth;
import com.mindhub.homebanking.repositories.CardRepository;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Random;


@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name="native", strategy="native")
    private long id;
    private String cardHolder;
    private CardType type;
    private CardColor color;
    private String number;
    private String cvv;
    private LocalDate thruDate;

    private LocalDate fromDate;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="client_id")
    private Client client;
    public Card(){}

    public Card(Client client, CardType type, CardColor color, String number, String cvv, LocalDate thruDate, LocalDate fromDate) {
        this.client = client;
        this.cardHolder = client.getFirstName() + " "+ client.getLastName();
        this.type = type;
        this.color = color;
        this.number = number;
        this.cvv = cvv;
        this.thruDate = thruDate;
        this.fromDate = fromDate;
    }

    public long getId() {
        return id;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public CardType getType() {
        return type;
    }

    public void setType(CardType type) {
        this.type = type;
    }

    public CardColor getColor() {
        return color;
    }

    public void setColor(CardColor color) {
        this.color = color;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }

    public void setThruDate(LocalDate thruDate) {
        this.thruDate = thruDate;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    // generador de números aleatorios

    public static String generateCardNumber(CardRepository cardRepository) {

        StringBuilder numberCard = new StringBuilder();

        boolean exists;
        do {
            numberCard.setLength(0);

            for (int i = 0; i < 4; i++) {
                if (i > 0) {
                    numberCard.append("-");
                }
                numberCard.append(String.format("%04d", ExtraMeth.getRandomNumber(1, 9999)));
            }

            exists = cardRepository.existsByNumber(numberCard.toString());
        } while (exists);

        return numberCard.toString();
    }

    //Crear CVV

    public static String generateCvv (CardRepository cardRepository){
        int numberCvv;
        String cvv;
        do{
            numberCvv = ExtraMeth.getRandomNumber(1, 999);
            cvv = String.format("%03d", numberCvv);} while (cardRepository.existsByCvv(cvv));
        return cvv;
    }

}
