package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientLoanDTO;
import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class LoanController {
    @Autowired
    LoanRepository loanRepository;
    @Autowired
    ClientLoanRepository clientLoanRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    TransactionRepository transactionRepository;


    @GetMapping("/loans")
    public List<LoanDTO> getLoans(){
        return loanRepository.findAll().stream().map(LoanDTO::new).collect(Collectors.toList());
    }

    @Transactional
    @RequestMapping(value="/loans", method=RequestMethod.POST)
    public ResponseEntity<Object> loanApplication(
            @RequestBody LoanApplicationDTO loanApplicationDTO,
            Authentication authentication){
        if (authentication == null) {return new ResponseEntity<>("please, log in.", HttpStatus.FORBIDDEN);
    } else {
            //determinar variables

            Client clientAuth = clientRepository.findByEmail(authentication.getName());
            Account toLoanAccount = accountRepository.findByNumber(loanApplicationDTO.getToAccountNumber());
            Loan loanApp = loanRepository.findById(loanApplicationDTO.getId()).orElse(null);

            //data completa
            if(loanApplicationDTO.getId() == null || loanApplicationDTO.getToAccountNumber().isBlank() || loanApplicationDTO.getAmount() == 0 || loanApplicationDTO.getPayments() == 0 ){
                return new ResponseEntity<>("not enough data.", HttpStatus.FORBIDDEN);}
            //Valor mayor a 0
            if (loanApplicationDTO.getAmount() <= 0){
                return new ResponseEntity<>("Amount Error. Please review.", HttpStatus.FORBIDDEN);}
            //Verificar que el préstamo exista
            if (loanApp == null){return new ResponseEntity<>("Loan does not exists", HttpStatus.FORBIDDEN);}
            //Verificar que no exceda el máximo
            if (loanApplicationDTO.getAmount() > loanApp.getMaxAmount()){return new ResponseEntity<>("Maximum already reached", HttpStatus.FORBIDDEN);}
            //Disponibilidad de cuotas
            if (!loanApp.getPayments().contains(loanApplicationDTO.getPayments())){return new ResponseEntity<>("Payments not available", HttpStatus.FORBIDDEN);}

            //Chequear si existe cuenta de destino
            if (toLoanAccount == null){
                return new ResponseEntity<>("Amount Error. Please review.", HttpStatus.FORBIDDEN);}
            //Verificar que no sea una cuenta de terceros
            if (toLoanAccount.getClient().getId() != clientAuth.getId()){
                return new ResponseEntity<>("Not same person", HttpStatus.FORBIDDEN);}

            double loanAmount = loanApplicationDTO.getAmount() + (loanApplicationDTO.getAmount() * 0.20);
            ClientLoan currentLoan = new ClientLoan(loanAmount, loanApplicationDTO.getPayments());
            clientAuth.addClientLoans(currentLoan);
            loanApp.addClientLoan(currentLoan);

            String description = "Loan for:" + loanApp.getName() ;
            Transaction transaction = new Transaction(TransactionType.CREDIT, loanApplicationDTO.getAmount(), description);
            toLoanAccount.addTransaction(transaction);

            transactionRepository.save(transaction);
            accountRepository.save(toLoanAccount);
            clientRepository.save(clientAuth);

            return new ResponseEntity<>("Loan Successfully Done", HttpStatus.CREATED);



    //else
         }
//RequestBody
    }
//el más grande
}
