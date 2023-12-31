package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}
/*
@Autowired
PasswordEncoder passwordEncoder;

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, CardRepository cardRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, LoanRepository loanRepository, ClientLoanRepository clientLoanRepository) {
		return (args) -> {
			Client client = new Client("Melba", "Morel", "melbamorel@hotmail.com", passwordEncoder.encode("123"));
			clientRepository.save(client);
			Client client2 = new Client ("Juana", "Perez", "JuanaPerez@hotmail.com", passwordEncoder.encode("abc"));
			clientRepository.save(client2);
			Client  admin = new Client ("admin","admin","admin@admin.com", passwordEncoder.encode("123"));
			clientRepository.save(admin);

			Account account1 = new Account("VIN001", LocalDate.now(), 5000.00);
			client.addAccount(account1);
			accountRepository.save(account1);
			Account account2 = new Account("VIN002", LocalDate.now().plusDays(1), 7500.00);
			client.addAccount(account2);
			accountRepository.save(account2);
			Account account3 = new Account("ALD932", LocalDate.now().plusDays(30), 27500.00);
			client2.addAccount(account3);
			accountRepository.save(account3);

			Transaction trans1 = new Transaction(TransactionType.CREDIT, 1000.00,"cuenta 1 credito" );
			account1.addTransaction(trans1);
			transactionRepository.save(trans1);
			Transaction trans2 = new Transaction(TransactionType.DEBIT,-2000.00,"cuenta1 debito");
			account1.addTransaction(trans2);
			transactionRepository.save(trans2);
			Transaction trans3 = new Transaction(TransactionType.CREDIT, 1000.00,"cuenta3 credito");
			account3.addTransaction(trans3);
			transactionRepository.save(trans3);

            Loan lon1 = new Loan("Hipotecario", 500000, List.of(12,24,36,48,60));
            loanRepository.save(lon1);
            Loan lon2 = new Loan("Personal", 10000, List.of(6,12,24));
            loanRepository.save(lon2);
            Loan lon3 = new Loan("Automotriz", 300000, List.of(6,12,24));
            loanRepository.save(lon3);

			ClientLoan clonan = new ClientLoan(400000.00,60);
			client.addClientLoan(clonan);
			lon1.addClientLoan(clonan);
			clientLoanRepository.save(clonan);
			ClientLoan clonan2 = new ClientLoan(50000.00,12);
			client.addClientLoan(clonan2);
			lon2.addClientLoan(clonan2);
			clientLoanRepository.save(clonan2);
			ClientLoan clonan3 = new ClientLoan(100000.00,24);
			client2.addClientLoan(clonan3);
			lon3.addClientLoan(clonan3);
			clientLoanRepository.save(clonan3);
			ClientLoan clonan4 = new ClientLoan(200000.00, 36);
			client2.addClientLoan(clonan4);
			lon1.addClientLoan(clonan4);
			clientLoanRepository.save(clonan4);

			Card card1 = new Card(client, CardType.DEBIT, CardColor.GOLD, "123412341234", "433", LocalDate.now().plusYears(5), LocalDate.now());
			cardRepository.save(card1);
			Card card2 = new Card(client, CardType.CREDIT, CardColor.TITANIUM, "894939483948", "322", LocalDate.now().plusYears(5), LocalDate.now());
			cardRepository.save(card2);
			Card card3 = new Card(client2, CardType.DEBIT, CardColor.SILVER, "89562873656", "877", LocalDate.now().plusYears(5), LocalDate.now());
			cardRepository.save(card3);

		};
	}*/
}
