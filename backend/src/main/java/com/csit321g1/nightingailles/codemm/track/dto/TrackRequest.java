package com.csit321g1.nightingailles.codemm.track.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/** Body for POST and PUT /api/tracks. */
public record TrackRequest(
        @NotBlank @Size(max = 100) String title,
        @NotBlank @Size(max = 30) String language,
        String description) {
}
