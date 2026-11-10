package com.csit321g1.nightingailles.codemm.roadmap.dto;

import com.csit321g1.nightingailles.codemm.roadmap.entity.ItemStatus;
import com.csit321g1.nightingailles.codemm.roadmap.entity.RoadmapItemEntity;

public record RoadmapItemResponse(
        Long itemId,
        Long roadmapId,
        Long lessonId,
        Integer position,
        ItemStatus status) {

    public static RoadmapItemResponse from(RoadmapItemEntity e) {
        return new RoadmapItemResponse(
                e.getItemId(),
                e.getRoadmap().getRoadmapId(),
                e.getLesson().getLessonId(),
                e.getPosition(),
                e.getStatus());
    }
}
