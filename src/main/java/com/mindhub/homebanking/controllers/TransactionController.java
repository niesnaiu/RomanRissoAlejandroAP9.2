package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TransactionController {
    @Autowired
    ClientService clientService;
    @Autowired
    AccountService accountService;
    @Autowired
    TransactionService transactionService;

    @Transactional
    @PostMapping("/transactions")
    public ResponseEntity<Object> transactionGeneration(
            @RequestParam String fromAccountNumber,
            @RequestParam String toAccountNumber,
            @RequestParam Double amount,
            @RequestParam String description,
            Authentication authentication){

        if (authentication == null) { return new ResponseEntity<>("please, log in", HttpStatus.FORBIDDEN);}
        else {
            if (fromAccountNumber.isBlank() || toAccountNumber.isBlank() || amount.isNaN() || description.isBlank()){
            return new ResponseEntity<>("No data enough", HttpStatus.FORBIDDEN);}
            if (amount <= 0) {
                return new ResponseEntity<>("Amount cannot be 0", HttpStatus.FORBIDDEN);}
            if (fromAccountNumber.equals(toAccountNumber)){
                return new ResponseEntity<>("Same account selected", HttpStatus.FORBIDDEN);}

            Client client = clientService.findByEmail(authentication.getName());
            Account toAccount = accountService.findAccountsByNum(toAccountNumber);
            Account fromAccount = accountService.findAccountsByNum(fromAccountNumber);

            //Verificar que la cuenta de origen exista
            if (fromAccount == null){
                return new ResponseEntity<>("Account does not exist", HttpStatus.FORBIDDEN);}
            //Verificar que la cuenta de origen pertenezca al cliente autenticado
            if ((fromAccount.getClient().getId()) != client.getId()){
                return new ResponseEntity<>("Error, this account is not yours", HttpStatus.FORBIDDEN);
            }
            //Verificar que exista la cuenta destino
            if (toAccount == null){
                return new ResponseEntity<>("Account does not exist", HttpStatus.FORBIDDEN);}
            //Verificar que la cuenta de origen tenga el monto disponible

            if (fromAccount.getBalance() <= amount) {
                return new ResponseEntity<>("Error, not enough founds", HttpStatus.FORBIDDEN);}

            String descripcionOrigen = description + " " + fromAccountNumber;
            String descripcionDestino = description + " " + toAccountNumber;

            Transaction origin =  new Transaction(TransactionType.DEBIT, -amount, descripcionOrigen);
            fromAccount.addTransaction(origin);
            transactionService.saveTransaction(origin);

            Transaction aimAccount =  new Transaction(TransactionType.CREDIT, amount, descripcionDestino);
            toAccount.addTransaction(aimAccount);
            transactionService.saveTransaction(aimAccount);

            accountService.saveAccount(toAccount);
            accountService.saveAccount(fromAccount);

            return new ResponseEntity<>("Success", HttpStatus.CREATED);

        }


        }
}
