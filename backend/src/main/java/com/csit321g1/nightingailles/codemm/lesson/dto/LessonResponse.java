package com.csit321g1.nightingailles.codemm.lesson.dto;

import com.csit321g1.nightingailles.codemm.lesson.entity.LessonEntity;

public record LessonResponse(Long lessonId, Long trackId, String title, String content, Integer position) {

    public static LessonResponse from(LessonEntity e) {
        return new LessonResponse(e.getLessonId(), e.getTrack().getTrackId(), e.getTitle(), e.getContent(),
                e.getPosition());
    }
}
