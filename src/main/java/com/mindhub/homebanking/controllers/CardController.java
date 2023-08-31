package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CardController {
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    CardRepository cardRepository;

    @RequestMapping(path = "/clients/current/cards", method = RequestMethod.POST)
    public ResponseEntity<Object> createCard(Authentication authentication, CardColor cardColor, CardType cardType){

        if (authentication != null){

            Client client = clientRepository.findByEmail(authentication.getName());

            Set<Card> cards = client.getCards();
            Set<Card> creditCards = cards.stream().filter(card -> card.getType() == CardType.CREDIT).collect(Collectors.toSet());
            Set<Card> debitCards = cards.stream().filter(card -> card.getType() == CardType.DEBIT).collect(Collectors.toSet());

            if (cardType == CardType.CREDIT){
                if (creditCards.size() < 3){
                    if (creditCards.stream().anyMatch(card -> card.getColor().equals(cardColor))){
                        return new ResponseEntity<>("Not possible. Already have that color", HttpStatus.FORBIDDEN);
                    }
                } else{
                    return new ResponseEntity<>("Maximum reached", HttpStatus.FORBIDDEN);
                }
            }

            if (cardType == CardType.DEBIT){
                if (debitCards.size() < 3){
                    if (debitCards.stream().anyMatch(card -> card.getColor().equals(cardColor))){
                        return new ResponseEntity<>("Not possible. Already have that Color", HttpStatus.FORBIDDEN);
                    }
                } else{
                    return new ResponseEntity<>("Maximum reached", HttpStatus.FORBIDDEN);
                }
            };


            String number = Card.generateCardNumber(cardRepository);
            String  cvv = Card.generateCvv(cardRepository);
            LocalDate thruDate = LocalDate.now().plusYears(5);
            LocalDate fromDate = LocalDate.now();

            Card card = new Card(client, cardType, cardColor, number, cvv, thruDate, fromDate);
            client.addCard(card);
            cardRepository.save(card);

            return new ResponseEntity<>(HttpStatus.CREATED);
        }

        return new ResponseEntity<>("Please, Log in.", HttpStatus.FORBIDDEN);
    }
}
