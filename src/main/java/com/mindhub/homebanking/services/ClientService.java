package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Client;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public interface ClientService {
    List<ClientDTO> getClientsDTOformat();
    ClientDTO getClientById(@PathVariable Long id);

    Client findByEmail(String email);

    void saveClient(Client client);
}
