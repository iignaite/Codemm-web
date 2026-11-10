package com.csit321g1.nightingailles.codemm.track.dto;

import com.csit321g1.nightingailles.codemm.track.entity.TrackEntity;

public record TrackResponse(Long trackId, String title, String language, String description) {

    public static TrackResponse from(TrackEntity e) {
        return new TrackResponse(e.getTrackId(), e.getTitle(), e.getLanguage(), e.getDescription());
    }
}
