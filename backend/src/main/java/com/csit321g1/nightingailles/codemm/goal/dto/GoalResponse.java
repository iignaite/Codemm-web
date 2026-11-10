package com.csit321g1.nightingailles.codemm.goal.dto;

import com.csit321g1.nightingailles.codemm.goal.entity.Depth;
import com.csit321g1.nightingailles.codemm.goal.entity.GoalEntity;
import java.time.Instant;

public record GoalResponse(
        Long goalId,
        Long userId,
        Long trackId,
        String description,
        String priorKnowledge,
        Depth depth,
        Instant createdAt) {

    public static GoalResponse from(GoalEntity e) {
        return new GoalResponse(
                e.getGoalId(),
                e.getUser().getUserId(),
                e.getTrack().getTrackId(),
                e.getDescription(),
                e.getPriorKnowledge(),
                e.getDepth(),
                e.getCreatedAt());
    }
}
