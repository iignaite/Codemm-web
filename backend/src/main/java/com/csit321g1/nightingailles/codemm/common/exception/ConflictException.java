package com.csit321g1.nightingailles.codemm.common.exception;

/** Thrown when a request clashes with existing data (for example, a duplicate email). Mapped to HTTP 409. */
public class ConflictException extends RuntimeException {

    public ConflictException(String message) {
        super(message);
    }
}
