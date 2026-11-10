package com.csit321g1.nightingailles.codemm.roadmap.dto;

import com.csit321g1.nightingailles.codemm.roadmap.entity.RoadmapStatus;
import jakarta.validation.constraints.NotNull;

/** Body for POST and PUT /api/roadmaps. */
public record RoadmapRequest(
        @NotNull Long goalId,
        @NotNull RoadmapStatus status) {
}
