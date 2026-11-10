package com.csit321g1.nightingailles.codemm.roadmap.dto;

import com.csit321g1.nightingailles.codemm.roadmap.entity.RoadmapEntity;
import com.csit321g1.nightingailles.codemm.roadmap.entity.RoadmapStatus;
import java.time.Instant;

public record RoadmapResponse(
        Long roadmapId,
        Long goalId,
        RoadmapStatus status,
        Instant createdAt) {

    public static RoadmapResponse from(RoadmapEntity e) {
        return new RoadmapResponse(
                e.getRoadmapId(),
                e.getGoal().getGoalId(),
                e.getStatus(),
                e.getCreatedAt());
    }
}
