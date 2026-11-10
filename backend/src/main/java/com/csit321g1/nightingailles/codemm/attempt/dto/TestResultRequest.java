package com.csit321g1.nightingailles.codemm.attempt.dto;

import jakarta.validation.constraints.NotNull;

/** Body for POST and PUT /api/test-results. */
public record TestResultRequest(
        @NotNull Long attemptId,
        @NotNull Long testId,
        @NotNull Boolean passed,
        String actualOutput) {
}
