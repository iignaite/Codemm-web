package com.csit321g1.nightingailles.codemm.attempt.dto;

import com.csit321g1.nightingailles.codemm.attempt.entity.AttemptEntity;
import java.time.Instant;

public record AttemptResponse(
        Long attemptId,
        Long userId,
        Long activityId,
        String code,
        Integer score,
        Integer attemptNo,
        Instant submittedAt) {

    public static AttemptResponse from(AttemptEntity e) {
        return new AttemptResponse(
                e.getAttemptId(),
                e.getUser().getUserId(),
                e.getActivity().getActivityId(),
                e.getCode(),
                e.getScore(),
                e.getAttemptNo(),
                e.getSubmittedAt());
    }
}
