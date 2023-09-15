package com.mindhub.homebanking;

import com.mindhub.homebanking.extras.ExtraMeth;
import com.mindhub.homebanking.models.Loan;
import com.mindhub.homebanking.repositories.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
class ExtraMethTest {
    @Autowired
    CardRepository cardRepository;

    @Test
    public void generateCardNumber() {
        String number = ExtraMeth.generateCardNumber(cardRepository);
        assertThat(number, is(not(emptyOrNullString())));

    }
}