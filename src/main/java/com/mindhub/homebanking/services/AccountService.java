package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AccountService {
    List<AccountDTO> getAccounts();
    Account findAccountsById(Long id);
    Account findAccountsByNum(String number);
    String generateAccountNumber();

    void saveAccount(Account account);
}
