package com.codecon.backend.model.dto;

import com.codecon.infrastructure.grpc.authentication.SignInRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignInDto implements Authentication {

    String password;
    String email;

    public SignInDto(SignInRequest signInRequest) {
        this.password = signInRequest.getPassword();
        this.email = signInRequest.getEmail();
    }

}
