package com.csit321g1.nightingailles.codemm.goal.dto;

import com.csit321g1.nightingailles.codemm.goal.entity.Depth;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/** Body for POST and PUT /api/goals. */
public record GoalRequest(
        @NotNull Long userId,
        @NotNull Long trackId,
        @NotBlank String description,
        @Size(max = 2000) String priorKnowledge,
        @NotNull Depth depth) {
}
