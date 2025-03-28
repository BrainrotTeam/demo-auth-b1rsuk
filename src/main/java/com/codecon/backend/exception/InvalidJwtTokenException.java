package com.codecon.backend.exception;

import com.codecon.backend.shared.exception.GrpcException;
import io.grpc.Status;

public class InvalidJwtTokenException extends GrpcException {

    public InvalidJwtTokenException(String message) {
        super(message, Status.UNAUTHENTICATED);
    }

}
