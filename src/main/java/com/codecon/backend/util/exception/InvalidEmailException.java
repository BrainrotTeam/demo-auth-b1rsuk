package com.codecon.backend.util.exception;

import com.codecon.backend.shared.exception.GrpcException;
import io.grpc.Status;

public class InvalidEmailException extends GrpcException {

    public InvalidEmailException(String email) {
        super("Email is not valid: " + email, Status.FAILED_PRECONDITION);
    }

}
