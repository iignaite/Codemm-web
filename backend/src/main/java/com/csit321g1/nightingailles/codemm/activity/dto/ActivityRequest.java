package com.csit321g1.nightingailles.codemm.activity.dto;

import com.csit321g1.nightingailles.codemm.activity.entity.ActivityStatus;
import com.csit321g1.nightingailles.codemm.activity.entity.Difficulty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/** Body for POST and PUT /api/activities. */
public record ActivityRequest(
        @NotNull Long lessonId,
        @NotBlank String statement,
        String starterCode,
        String referenceSolution,
        @NotNull Difficulty difficulty,
        @NotNull ActivityStatus status) {
}
