package com.csit321g1.nightingailles.codemm.activity.service;

import com.csit321g1.nightingailles.codemm.activity.dto.ActivityRequest;
import com.csit321g1.nightingailles.codemm.activity.dto.ActivityResponse;
import com.csit321g1.nightingailles.codemm.activity.entity.ActivityEntity;
import com.csit321g1.nightingailles.codemm.activity.repository.ActivityRepository;
import com.csit321g1.nightingailles.codemm.common.exception.ResourceNotFoundException;
import com.csit321g1.nightingailles.codemm.lesson.entity.LessonEntity;
import com.csit321g1.nightingailles.codemm.lesson.repository.LessonRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final LessonRepository lessonRepository;

    public ActivityService(ActivityRepository activityRepository, LessonRepository lessonRepository) {
        this.activityRepository = activityRepository;
        this.lessonRepository = lessonRepository;
    }

    @Transactional(readOnly = true)
    public List<ActivityResponse> getAll(Long lessonId) {
        if (lessonId != null) {
            return activityRepository.findByLessonLessonId(lessonId).stream().map(ActivityResponse::from).toList();
        }
        return activityRepository.findAll().stream().map(ActivityResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public ActivityResponse getById(Long id) {
        return ActivityResponse.from(findEntity(id));
    }

    public ActivityResponse create(ActivityRequest request) {
        ActivityEntity e = new ActivityEntity();
        apply(e, request);
        return ActivityResponse.from(activityRepository.save(e));
    }

    public ActivityResponse update(Long id, ActivityRequest request) {
        ActivityEntity e = findEntity(id);
        apply(e, request);
        return ActivityResponse.from(activityRepository.save(e));
    }

    public void delete(Long id) {
        activityRepository.delete(findEntity(id));
    }

    @Transactional(readOnly = true)
    public ActivityEntity findEntity(Long id) {
        return activityRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Activity", id));
    }

    private void apply(ActivityEntity e, ActivityRequest request) {
        e.setLesson(lessonRepository.findById(request.lessonId())
                .orElseThrow(() -> new ResourceNotFoundException("Lesson", request.lessonId())));
        e.setStatement(request.statement());
        e.setStarterCode(request.starterCode());
        e.setReferenceSolution(request.referenceSolution());
        e.setDifficulty(request.difficulty());
        e.setStatus(request.status());
    }
}
