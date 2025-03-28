package com.codecon.backend.service;


import com.codecon.backend.exception.ClientBannedException;
import com.codecon.backend.exception.ClientInactiveException;
import com.codecon.backend.model.Client;
import com.codecon.backend.model.dto.*;
import com.codecon.backend.util.EmailRulesValidator;
import com.codecon.backend.util.PasswordRulesValidator;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthenticationService {

    ClientService clientService;

    public void signUp(SignUpDto signUpDto) {
        validateAuthenticationData(signUpDto);

        String password = signUpDto.getPassword();
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));

        Client client = new Client();
        client.setEmail(signUpDto.getEmail());
        client.setFirstName(signUpDto.getFirstName());
        client.setLastName(signUpDto.getLastName());
        client.setHashedPassword(hashedPassword);

        clientService.create(client);
    }

    public TokenDto signIn(SignInDto signInDto) {
        String email = signInDto.getEmail();
        Client clientByEmail = clientService.findClientByEmail(email);

        ensureClientActivated(clientByEmail);
        ensureClientIsNotBanned(clientByEmail);

        String hashedPassword = clientByEmail.getHashedPassword();
        PasswordRulesValidator.validatePassword(signInDto, hashedPassword);

        ClientDto clientDto = clientByEmail.toDto();
        return new TokenDto(clientDto);
    }

    private void ensureClientActivated(Client clientByEmail) {
        if (!clientByEmail.isActivated()) {
            throw new ClientInactiveException(clientByEmail);
        }
    }

    private void ensureClientIsNotBanned(Client clientByEmail) {
        if (clientByEmail.isBanned()) {
            throw new ClientBannedException(clientByEmail);
        }
    }

    private void validateAuthenticationData(Authentication authentication) {
        String password = authentication.getPassword();
        PasswordRulesValidator.validatePassword(password);

        String email = authentication.getEmail();
        EmailRulesValidator.validateEmail(email);
    }

}
