package com.codecon.backend.shared.service.exception;

import com.codecon.backend.shared.exception.GrpcException;
import io.grpc.Status;

public class EntityIdCannotBeNullException extends GrpcException {


    public EntityIdCannotBeNullException() {
        super("Error: Update requires Id", Status.FAILED_PRECONDITION);
    }

}
