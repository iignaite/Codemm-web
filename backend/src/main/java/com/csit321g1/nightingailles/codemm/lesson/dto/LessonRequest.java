package com.csit321g1.nightingailles.codemm.lesson.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/** Body for POST and PUT /api/lessons. */
public record LessonRequest(
        @NotNull Long trackId,
        @NotBlank @Size(max = 150) String title,
        String content,
        @NotNull @Min(1) Integer position) {
}
