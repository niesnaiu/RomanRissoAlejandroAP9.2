package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping ("/api")
public class AccountController {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @GetMapping("/accounts")
    public List<AccountDTO> getAccounts(){
        return this.accountRepository.findAll().stream().map(AccountDTO::new).collect(Collectors.toList());
    }

    @RequestMapping("/accounts/{id}")
    public ResponseEntity<Object> getAccount (@PathVariable Long id, Authentication authentication){
        if(authentication != null){

            Client client = clientRepository.findByEmail(authentication.getName());
            Account account = accountRepository.findById(id).orElse(null);

            if (account == null){
                return new ResponseEntity<>("No Account found", HttpStatus.NOT_FOUND);
            }

            if (account.getClient().equals(client)){

                AccountDTO accountDTO = new AccountDTO(account);
                return new ResponseEntity<>(accountDTO, HttpStatus.ACCEPTED);
            } else{
                return new ResponseEntity<>("Access denied.", HttpStatus.FORBIDDEN);
            }
        }
        return new ResponseEntity<>("Please log in", HttpStatus.FORBIDDEN);

    }

    @PostMapping("/clients/current/accounts")
    public ResponseEntity<Object> createAccount(Authentication authentication){
        if (authentication != null) {
            Client newclient = clientRepository.findByEmail(authentication.getName());

            Set<Account> accounts = newclient.getAccounts();
            if (accounts.size() > 3) {
                return new ResponseEntity<>("Maximum reached", HttpStatus.FORBIDDEN);
            }
            LocalDate date = LocalDate.now();
            String numberAccount = Account.generateAccountNumber(accountRepository);
            double balance = 0;
            Account account = new Account(numberAccount, date, balance);
            newclient.addAccount(account);
            accountRepository.save(account);
            return new ResponseEntity<>("Creado", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Not logged in", HttpStatus.FORBIDDEN);
    }

    @GetMapping("/clients/current/accounts")
    public List<AccountDTO> getCurrentAccounts(Authentication authentication){
        Client client = clientRepository.findByEmail(authentication.getName());
        return client.getAccounts().stream().map(AccountDTO::new).collect(Collectors.toList());
    }




}
