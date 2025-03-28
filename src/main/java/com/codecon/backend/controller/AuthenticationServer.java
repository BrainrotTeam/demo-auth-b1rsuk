package com.codecon.backend.controller;

import com.codecon.backend.model.Client;
import com.codecon.backend.model.dto.ClientDto;
import com.codecon.backend.model.dto.SignInDto;
import com.codecon.backend.model.dto.SignUpDto;
import com.codecon.backend.model.dto.TokenDto;
import com.codecon.backend.service.AuthenticationService;
import com.codecon.backend.service.ClientService;
import com.codecon.backend.service.JwtService;
import com.codecon.backend.util.GrpcInvoker;
import com.codecon.infrastructure.grpc.authentication.*;
import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationServer extends AuthenticationServiceGrpc.AuthenticationServiceImplBase {

    AuthenticationService authenticationService;
    ClientService clientService;

    @Override
    public void signUp(SignUpRequest signUpRequest, StreamObserver<Empty> emptyStreamObserver) {
        GrpcInvoker.executeSingleGrpcCall(() -> {
            SignUpDto signUpDto = new SignUpDto(signUpRequest);
            authenticationService.signUp(signUpDto);

            return Empty.newBuilder().build();
        }, emptyStreamObserver);
    }

    @Override
    public void signIn(SignInRequest signInRequest, StreamObserver<TokenResponse> tokenResponseObserver) {
        GrpcInvoker.executeSingleGrpcCall(() -> {
            SignInDto signInDto = new SignInDto(signInRequest);
            TokenDto tokenDto = authenticationService.signIn(signInDto);
            String token = tokenDto.getToken();

            return TokenResponse
                    .newBuilder()
                    .setToken(token)
                    .build();
        }, tokenResponseObserver);
    }

    @Override
    public void getActualDataByToken(TokenRequest tokenRequest, StreamObserver<ClientResponseDto> responseObserver) {
        GrpcInvoker.executeSingleGrpcCall(() -> {
            String bearerToken = tokenRequest.getToken();
            String token = JwtService.extractToken(bearerToken);
            Client client = JwtService.extractClient(token);

            Client actualClient = clientService.getClientActualData(client);
            ClientDto clientDto = actualClient.toDto();

            return clientDto.toClientResponseDto();
        }, responseObserver);
    }

}
