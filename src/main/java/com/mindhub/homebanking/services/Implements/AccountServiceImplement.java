package com.mindhub.homebanking.services.Implements;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImplement implements AccountService {
    @Autowired
    private AccountRepository accountRepository;
    @Override
    public List<AccountDTO> getAccounts(){
        return this.accountRepository.findAll().stream().map(AccountDTO::new).collect(Collectors.toList());
    }
    @Override
    public Account findAccountsById(Long id){
        return accountRepository.findById(id).orElse(null);
    }

    @Override
    public String generateAccountNumber(){
        return Account.generateAccountNumber(accountRepository);
    }

    @Override
    public Account findAccountsByNum(String number){
        return accountRepository.findByNumber(number);
    }
    @Override
    public void saveAccount(Account account){
        accountRepository.save(account);
    }


}
