package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}


	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository) {
		return (args) -> {
			Client client = new Client("Melba", "Morel", "melbamorel@hotmail.com");
			clientRepository.save(client);
			Client client2 = new Client ("Juana", "Perez", "JuanaPerez@hotmail.com");
			clientRepository.save(client2);

			Account account1 = new Account("VIN001", LocalDate.now(), 5000.00);
			client.addAccount(account1);
			accountRepository.save(account1);
			Account account2 = new Account("VIN002", LocalDate.now().plusDays(1), 7500.00);
			client.addAccount(account2);
			accountRepository.save(account2);
			Account account3 = new Account("ALD932", LocalDate.now().plusDays(30), 27500.00);
			client2.addAccount(account3);
			accountRepository.save(account3);

			Transaction trans1 = new Transaction(1000.00,LocalDate.now(),"cuenta1 credito", TransactionType.CREDITO, 8500);
			account1.addTransaction(trans1);
			transactionRepository.save(trans1);
			Transaction trans2 = new Transaction(-2000.00,LocalDate.now(),"cuenta1 debito", TransactionType.DEBITO, 6500);
			account1.addTransaction(trans2);
			transactionRepository.save(trans2);
			Transaction trans3 = new Transaction(1000.00,LocalDate.now(),"cuenta3 credito", TransactionType.CREDITO, 28500);
			account3.addTransaction(trans3);
			transactionRepository.save(trans3);
		};
	}
}
