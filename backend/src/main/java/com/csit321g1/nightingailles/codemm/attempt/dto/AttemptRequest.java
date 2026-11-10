package com.csit321g1.nightingailles.codemm.attempt.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/** Body for POST and PUT /api/attempts. attemptNo is assigned by the server. */
public record AttemptRequest(
        @NotNull Long userId,
        @NotNull Long activityId,
        @NotNull String code,
        @NotNull @Min(0) @Max(100) Integer score) {
}
