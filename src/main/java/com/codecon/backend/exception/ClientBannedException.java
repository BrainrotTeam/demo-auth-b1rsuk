package com.codecon.backend.exception;

import com.codecon.backend.model.Client;
import com.codecon.backend.shared.exception.GrpcException;
import io.grpc.Status;

public class ClientBannedException extends GrpcException {

    public ClientBannedException(Client client) {
        this(String.format("Client with id: %s is banned", client.getId()));
    }

    public ClientBannedException(String message) {
        super(message, Status.PERMISSION_DENIED);
    }

}
