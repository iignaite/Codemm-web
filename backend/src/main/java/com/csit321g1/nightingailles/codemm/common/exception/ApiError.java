package com.csit321g1.nightingailles.codemm.common.exception;

import java.time.Instant;
import java.util.Map;

/** The JSON body returned for every error response. */
public record ApiError(
        Instant timestamp,
        int status,
        String error,
        String message,
        String path,
        Map<String, String> fieldErrors) {
}
