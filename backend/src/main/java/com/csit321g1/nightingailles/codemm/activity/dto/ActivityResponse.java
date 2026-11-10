package com.csit321g1.nightingailles.codemm.activity.dto;

import com.csit321g1.nightingailles.codemm.activity.entity.ActivityEntity;
import com.csit321g1.nightingailles.codemm.activity.entity.ActivityStatus;
import com.csit321g1.nightingailles.codemm.activity.entity.Difficulty;

public record ActivityResponse(
        Long activityId,
        Long lessonId,
        String statement,
        String starterCode,
        String referenceSolution,
        Difficulty difficulty,
        ActivityStatus status) {

    public static ActivityResponse from(ActivityEntity e) {
        return new ActivityResponse(
                e.getActivityId(),
                e.getLesson().getLessonId(),
                e.getStatement(),
                e.getStarterCode(),
                e.getReferenceSolution(),
                e.getDifficulty(),
                e.getStatus());
    }
}
