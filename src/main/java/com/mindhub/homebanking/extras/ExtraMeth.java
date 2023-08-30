package com.mindhub.homebanking.extras;

import com.mindhub.homebanking.repositories.AccountRepository;

public class ExtraMeth {
    public static int getRandomNumber(int min, int max) {return (int) ((Math.random()*(max - min)) + min);
    }

}
