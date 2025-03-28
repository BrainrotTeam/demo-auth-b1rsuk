package com.codecon.backend.service;

import com.codecon.backend.exception.ClientNotFoundException;
import com.codecon.backend.exception.InvalidJwtTokenException;
import com.codecon.backend.model.Client;
import com.codecon.backend.model.Role;
import com.codecon.backend.repository.ClientRepository;
import com.codecon.backend.shared.service.BaseService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ClientService extends BaseService<Client> {

    ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        super(clientRepository);
        this.clientRepository = clientRepository;
    }

    public Client findClientByEmail(String email) {
        return clientRepository.findByEmail(email)
                .orElseThrow(() -> new ClientNotFoundException(email));
    }

    public Client findClientById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));
    }

    public Client getClientActualData(Client currentClient) {
        Long id = currentClient.getId();

        Client actualClient = findClientById(id);
        boolean isBanned = actualClient.isBanned();

        Role currentClientRole = currentClient.getRole();
        Role actualClientRole = actualClient.getRole();

        if (!Objects.equals(currentClientRole, actualClientRole) || isBanned) {
            throw new InvalidJwtTokenException("Jwt has expired data");
        }

        return actualClient;
    }

}
