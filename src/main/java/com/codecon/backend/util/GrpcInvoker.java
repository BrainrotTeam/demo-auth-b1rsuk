package com.codecon.backend.util;

import com.codecon.backend.shared.exception.GrpcException;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;

import java.util.function.Supplier;

public final class GrpcInvoker {

    private GrpcInvoker() {}

    public static  <T> void executeSingleGrpcCall(
            Supplier<T> callable,
            StreamObserver<T> responseObserver
    ) {
        try {
            T result = callable.get();
            responseObserver.onNext(result);
            responseObserver.onCompleted();
        } catch (GrpcException e) {
            responseObserver.onError(e.getGrpcStatus()
                    .withDescription(e.getMessage())
                    .asRuntimeException());
        } catch (RuntimeException e) {
            responseObserver.onError(Status.UNKNOWN
                    .withDescription(e.getMessage())
                    .asRuntimeException());
        }
    }

}
