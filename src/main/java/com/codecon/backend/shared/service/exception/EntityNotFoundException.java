package com.codecon.backend.shared.service.exception;

import com.codecon.backend.shared.exception.GrpcException;
import io.grpc.Status;

public class EntityNotFoundException  extends GrpcException {

    public EntityNotFoundException(String message) {
        super(message, Status.FAILED_PRECONDITION);
    }

}
