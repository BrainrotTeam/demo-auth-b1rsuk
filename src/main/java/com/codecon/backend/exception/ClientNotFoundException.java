package com.codecon.backend.exception;

import com.codecon.backend.shared.exception.GrpcException;
import io.grpc.Status;

public class ClientNotFoundException extends GrpcException {

    public ClientNotFoundException(String message) {
        super(message, Status.NOT_FOUND);
    }
    public ClientNotFoundException(Long id) {
        this("Client with id " + id + " not found");
    }

}
