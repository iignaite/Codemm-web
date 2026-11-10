package com.csit321g1.nightingailles.codemm.activity.dto;

import jakarta.validation.constraints.NotNull;

/** Body for POST and PUT /api/test-cases. */
public record TestCaseRequest(
        @NotNull Long activityId,
        String input,
        @NotNull String expectedOutput,
        @NotNull Boolean hidden) {
}
