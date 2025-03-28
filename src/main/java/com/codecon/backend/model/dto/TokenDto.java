package com.codecon.backend.model.dto;

import com.codecon.backend.service.JwtService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenDto {
    String token;

    public TokenDto(ClientDto clientDto) {
        token = "Bearer: " + JwtService.generateToken(clientDto);
    }
}
