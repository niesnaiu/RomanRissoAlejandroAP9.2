package com.mindhub.homebanking.services.Implements;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientServiceImplement implements ClientService {
    @Autowired
    private ClientRepository clientRepository;

    @Override
    public List<ClientDTO> getClientsDTOformat(){
        return clientRepository.findAll().stream().map(ClientDTO::new).collect(Collectors.toList());
    }

    @Override
    public ClientDTO getClientById(@PathVariable Long id){
        Optional<Client > client = clientRepository.findById(id);
        return new ClientDTO(client.get());
    }
    @Override
    public Client findByEmail(String email){
        return clientRepository.findByEmail(email);
    }
    @Override
    public void saveClient(Client client){
        clientRepository.save(client);
    }
}
