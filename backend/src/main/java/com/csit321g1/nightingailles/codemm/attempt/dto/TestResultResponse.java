package com.csit321g1.nightingailles.codemm.attempt.dto;

import com.csit321g1.nightingailles.codemm.attempt.entity.TestResultEntity;

public record TestResultResponse(
        Long resultId,
        Long attemptId,
        Long testId,
        Boolean passed,
        String actualOutput) {

    public static TestResultResponse from(TestResultEntity e) {
        return new TestResultResponse(
                e.getResultId(),
                e.getAttempt().getAttemptId(),
                e.getTestCase().getTestId(),
                e.getPassed(),
                e.getActualOutput());
    }
}
