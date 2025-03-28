package com.codecon.backend.util.exception;

import com.codecon.backend.shared.exception.GrpcException;
import io.grpc.Status;

public class InvalidPasswordException extends GrpcException {

    public InvalidPasswordException(String message) {
        super(message, Status.FAILED_PRECONDITION);
    }

}
