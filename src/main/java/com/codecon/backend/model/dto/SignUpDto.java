package com.codecon.backend.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.codecon.infrastructure.grpc.authentication.SignUpRequest;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDto implements Authentication {

    @NotBlank
    String firstName;

    @NotBlank
    String lastName;

    @NotBlank
    String password;

    @Email
    String email;

    public SignUpDto(SignUpRequest signUpRequest) {
        this.firstName = signUpRequest.getFirstName();
        this.lastName = signUpRequest.getLastName();
        this.password = signUpRequest.getPassword();
        this.email = signUpRequest.getEmail();
    }

}
