package com.codecon.backend.repository;

import com.codecon.backend.model.Client;
import com.codecon.backend.shared.repository.BaseRepository;

import java.util.Optional;

public interface ClientRepository extends BaseRepository<Client> {

    Optional<Client> findByEmail(String email);

}
