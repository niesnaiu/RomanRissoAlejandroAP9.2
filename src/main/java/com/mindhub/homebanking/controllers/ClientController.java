package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ClientController {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    private ClientService clientService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/clients")
    public List<ClientDTO> getClients(){
        return clientService.getClientsDTOformat();

    }
    @GetMapping("/clients/{id}")
    public ClientDTO getClientById(@PathVariable Long id){
        return clientService.getClientById(id);
    }

    @PostMapping ("/clients")
    public ResponseEntity<Object> register(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String email,
            @RequestParam String password ) {
        if (firstName.isBlank() || lastName.isBlank() || email.isBlank() || password.isBlank()){
            return new ResponseEntity<>("Not enough data", HttpStatus.FORBIDDEN);
        }
        if ( clientService.findByEmail(email)!= null){
            return new ResponseEntity<>("email already taken", HttpStatus.FORBIDDEN);
        }
        Client newclient = new Client(firstName, lastName, email, passwordEncoder.encode(password));
        clientService.saveClient(newclient);

        LocalDate date = LocalDate.now();
        String numberAccount = Account.generateAccountNumber(accountRepository);
        double balance = 0;
        Account account = new Account(numberAccount, date, balance);
        newclient.addAccount(account);
        accountRepository.save(account);
        return new ResponseEntity<>("Creado", HttpStatus.CREATED);

    }
    @GetMapping ("/clients/current")
    public ClientDTO getCurrent (Authentication authentication){
        return new ClientDTO( clientService.findByEmail(authentication.getName()));
    }


}
