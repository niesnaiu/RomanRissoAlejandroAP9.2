package com.mindhub.homebanking.extras;

import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.CardRepository;

public class ExtraMeth {
    public static int getRandomNumber(int min, int max) {return (int) ((Math.random()*(max - min)) + min);
    }

    //Credit Card number generator
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
    //Creat credit Card CVV
    public static String generateCvv (CardRepository cardRepository){
        int numberCvv;
        String cvv;
        do{
            numberCvv = ExtraMeth.getRandomNumber(1, 999);
            cvv = String.format("%03d", numberCvv);} while (cardRepository.existsByCvv(cvv));
        return cvv;
    }

}
