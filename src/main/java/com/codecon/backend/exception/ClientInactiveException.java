package com.codecon.backend.exception;

import com.codecon.backend.model.Client;
import com.codecon.backend.shared.exception.GrpcException;
import io.grpc.Status;
import io.jsonwebtoken.lang.Strings;

public class ClientInactiveException extends GrpcException {

    public ClientInactiveException(Client client) {
        this(String.format("Client with email: %s is not activated", client.getEmail()));
    }

    public ClientInactiveException(String message) {
        super(message, Status.UNAUTHENTICATED);
    }

}
