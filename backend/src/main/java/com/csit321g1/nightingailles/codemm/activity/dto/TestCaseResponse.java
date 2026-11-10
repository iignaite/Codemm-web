package com.csit321g1.nightingailles.codemm.activity.dto;

import com.csit321g1.nightingailles.codemm.activity.entity.TestCaseEntity;

public record TestCaseResponse(
        Long testId,
        Long activityId,
        String input,
        String expectedOutput,
        Boolean hidden) {

    public static TestCaseResponse from(TestCaseEntity e) {
        return new TestCaseResponse(
                e.getTestId(),
                e.getActivity().getActivityId(),
                e.getInput(),
                e.getExpectedOutput(),
                e.getHidden());
    }
}
