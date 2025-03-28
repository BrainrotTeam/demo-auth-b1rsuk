package com.codecon.backend.shared.service.exception;

import com.codecon.backend.shared.exception.GrpcException;
import io.grpc.Status;

public class EntityIdUpdateNotAllowedException extends GrpcException {

    public EntityIdUpdateNotAllowedException() {
        super("Error: Creating an entity must not include an ID", Status.FAILED_PRECONDITION);
    }
}