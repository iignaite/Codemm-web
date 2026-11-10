package com.csit321g1.nightingailles.codemm.common.exception;

/** Thrown when a record with the requested ID does not exist. Mapped to HTTP 404. */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String resource, Object id) {
        super(resource + " " + id + " was not found");
    }
}
