package com.codecon.backend.shared.exception;


import io.grpc.Status;

public abstract class GrpcException  extends RuntimeException {

    private final Status status;

    public GrpcException(String message, Status status) {
        super(message);
        this.status = status;
    }

    public Status getGrpcStatus() {
        return status;
    }

}
