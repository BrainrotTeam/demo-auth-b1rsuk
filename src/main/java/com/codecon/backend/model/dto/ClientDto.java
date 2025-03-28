package com.codecon.backend.model.dto;

import com.codecon.backend.model.Role;
import com.codecon.infrastructure.grpc.authentication.ClientPermission;
import com.codecon.infrastructure.grpc.authentication.ClientResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto {
    Long id;
    String email;
    Role role;

    public ClientResponseDto toClientResponseDto() {
        ClientPermission clientPermission = ClientPermission.valueOf(role.name());

        return ClientResponseDto.newBuilder()
                .setId(this.id)
                .setEmail(this.email)
                .setClientPermission(clientPermission)
                .build();
    }

}
