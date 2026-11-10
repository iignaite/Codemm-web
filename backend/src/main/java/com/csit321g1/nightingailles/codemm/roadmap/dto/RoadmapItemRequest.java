package com.csit321g1.nightingailles.codemm.roadmap.dto;

import com.csit321g1.nightingailles.codemm.roadmap.entity.ItemStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/** Body for POST and PUT /api/roadmap-items. */
public record RoadmapItemRequest(
        @NotNull Long roadmapId,
        @NotNull Long lessonId,
        @NotNull @Min(1) Integer position,
        @NotNull ItemStatus status) {
}
