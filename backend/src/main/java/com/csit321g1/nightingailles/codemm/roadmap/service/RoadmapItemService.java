package com.csit321g1.nightingailles.codemm.roadmap.service;

import com.csit321g1.nightingailles.codemm.common.exception.ResourceNotFoundException;
import com.csit321g1.nightingailles.codemm.lesson.entity.LessonEntity;
import com.csit321g1.nightingailles.codemm.lesson.repository.LessonRepository;
import com.csit321g1.nightingailles.codemm.roadmap.dto.RoadmapItemRequest;
import com.csit321g1.nightingailles.codemm.roadmap.dto.RoadmapItemResponse;
import com.csit321g1.nightingailles.codemm.roadmap.entity.RoadmapEntity;
import com.csit321g1.nightingailles.codemm.roadmap.entity.RoadmapItemEntity;
import com.csit321g1.nightingailles.codemm.roadmap.repository.RoadmapItemRepository;
import com.csit321g1.nightingailles.codemm.roadmap.repository.RoadmapRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoadmapItemService {

    private final RoadmapItemRepository roadmapItemRepository;
    private final RoadmapRepository roadmapRepository;
    private final LessonRepository lessonRepository;

    public RoadmapItemService(RoadmapItemRepository roadmapItemRepository, RoadmapRepository roadmapRepository, LessonRepository lessonRepository) {
        this.roadmapItemRepository = roadmapItemRepository;
        this.roadmapRepository = roadmapRepository;
        this.lessonRepository = lessonRepository;
    }

    @Transactional(readOnly = true)
    public List<RoadmapItemResponse> getAll(Long roadmapId) {
        if (roadmapId != null) {
            return roadmapItemRepository.findByRoadmapRoadmapIdOrderByPositionAsc(roadmapId).stream().map(RoadmapItemResponse::from).toList();
        }
        return roadmapItemRepository.findAll().stream().map(RoadmapItemResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public RoadmapItemResponse getById(Long id) {
        return RoadmapItemResponse.from(findEntity(id));
    }

    public RoadmapItemResponse create(RoadmapItemRequest request) {
        RoadmapItemEntity e = new RoadmapItemEntity();
        apply(e, request);
        return RoadmapItemResponse.from(roadmapItemRepository.save(e));
    }

    public RoadmapItemResponse update(Long id, RoadmapItemRequest request) {
        RoadmapItemEntity e = findEntity(id);
        apply(e, request);
        return RoadmapItemResponse.from(roadmapItemRepository.save(e));
    }

    public void delete(Long id) {
        roadmapItemRepository.delete(findEntity(id));
    }

    @Transactional(readOnly = true)
    public RoadmapItemEntity findEntity(Long id) {
        return roadmapItemRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Roadmap item", id));
    }

    private void apply(RoadmapItemEntity e, RoadmapItemRequest request) {
        e.setRoadmap(roadmapRepository.findById(request.roadmapId())
                .orElseThrow(() -> new ResourceNotFoundException("Roadmap", request.roadmapId())));
        e.setLesson(lessonRepository.findById(request.lessonId())
                .orElseThrow(() -> new ResourceNotFoundException("Lesson", request.lessonId())));
        e.setPosition(request.position());
        e.setStatus(request.status());
    }
}
