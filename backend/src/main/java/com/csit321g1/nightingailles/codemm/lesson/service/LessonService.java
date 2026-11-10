package com.csit321g1.nightingailles.codemm.lesson.service;

import com.csit321g1.nightingailles.codemm.common.exception.ResourceNotFoundException;
import com.csit321g1.nightingailles.codemm.lesson.dto.LessonRequest;
import com.csit321g1.nightingailles.codemm.lesson.dto.LessonResponse;
import com.csit321g1.nightingailles.codemm.lesson.entity.LessonEntity;
import com.csit321g1.nightingailles.codemm.lesson.repository.LessonRepository;
import com.csit321g1.nightingailles.codemm.track.repository.TrackRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LessonService {

    private final LessonRepository lessonRepository;
    private final TrackRepository trackRepository;

    public LessonService(LessonRepository lessonRepository, TrackRepository trackRepository) {
        this.lessonRepository = lessonRepository;
        this.trackRepository = trackRepository;
    }

    @Transactional(readOnly = true)
    public List<LessonResponse> getAll(Long trackId) {
        if (trackId != null) {
            return lessonRepository.findByTrackTrackIdOrderByPositionAsc(trackId).stream()
                    .map(LessonResponse::from).toList();
        }
        return lessonRepository.findAll().stream().map(LessonResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public LessonResponse getById(Long id) {
        return LessonResponse.from(findEntity(id));
    }

    public LessonResponse create(LessonRequest request) {
        LessonEntity e = new LessonEntity();
        apply(e, request);
        return LessonResponse.from(lessonRepository.save(e));
    }

    public LessonResponse update(Long id, LessonRequest request) {
        LessonEntity e = findEntity(id);
        apply(e, request);
        return LessonResponse.from(lessonRepository.save(e));
    }

    public void delete(Long id) {
        lessonRepository.delete(findEntity(id));
    }

    @Transactional(readOnly = true)
    public LessonEntity findEntity(Long id) {
        return lessonRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Lesson", id));
    }

    private void apply(LessonEntity e, LessonRequest request) {
        e.setTrack(trackRepository.findById(request.trackId())
                .orElseThrow(() -> new ResourceNotFoundException("Track", request.trackId())));
        e.setTitle(request.title().trim());
        e.setContent(request.content());
        e.setPosition(request.position());
    }
}
