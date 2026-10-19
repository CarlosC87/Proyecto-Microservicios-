package com.fleet.composite.exception;

public class DownstreamUnavailableException extends RuntimeException {
    public DownstreamUnavailableException(String serviceName, Throwable cause) {
        super(serviceName + " is unavailable", cause);
    }
}
