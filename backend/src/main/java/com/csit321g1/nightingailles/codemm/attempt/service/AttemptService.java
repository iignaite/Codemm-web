package com.csit321g1.nightingailles.codemm.attempt.service;

import com.csit321g1.nightingailles.codemm.activity.entity.ActivityEntity;
import com.csit321g1.nightingailles.codemm.activity.repository.ActivityRepository;
import com.csit321g1.nightingailles.codemm.attempt.dto.AttemptRequest;
import com.csit321g1.nightingailles.codemm.attempt.dto.AttemptResponse;
import com.csit321g1.nightingailles.codemm.attempt.entity.AttemptEntity;
import com.csit321g1.nightingailles.codemm.attempt.repository.AttemptRepository;
import com.csit321g1.nightingailles.codemm.common.exception.ResourceNotFoundException;
import com.csit321g1.nightingailles.codemm.user.entity.UserEntity;
import com.csit321g1.nightingailles.codemm.user.repository.UserRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AttemptService {

    private final AttemptRepository attemptRepository;
    private final UserRepository userRepository;
    private final ActivityRepository activityRepository;

    public AttemptService(AttemptRepository attemptRepository, UserRepository userRepository, ActivityRepository activityRepository) {
        this.attemptRepository = attemptRepository;
        this.userRepository = userRepository;
        this.activityRepository = activityRepository;
    }

    @Transactional(readOnly = true)
    public List<AttemptResponse> getAll(Long userId, Long activityId) {
        if (userId != null) {
            return attemptRepository.findByUserUserIdOrderBySubmittedAtDesc(userId).stream().map(AttemptResponse::from).toList();
        }
        if (activityId != null) {
            return attemptRepository.findByActivityActivityIdOrderBySubmittedAtDesc(activityId).stream().map(AttemptResponse::from).toList();
        }
        return attemptRepository.findAll().stream().map(AttemptResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public AttemptResponse getById(Long id) {
        return AttemptResponse.from(findEntity(id));
    }

    public AttemptResponse create(AttemptRequest request) {
        AttemptEntity e = new AttemptEntity();
        apply(e, request);
        // Next number after the learner's highest one, so a deleted attempt's number is never reused.
        e.setAttemptNo(attemptRepository
                .findTopByUserUserIdAndActivityActivityIdOrderByAttemptNoDesc(request.userId(), request.activityId())
                .map(last -> last.getAttemptNo() + 1)
                .orElse(1));
        return AttemptResponse.from(attemptRepository.save(e));
    }

    public AttemptResponse update(Long id, AttemptRequest request) {
        AttemptEntity e = findEntity(id);
        apply(e, request);
        return AttemptResponse.from(attemptRepository.save(e));
    }

    public void delete(Long id) {
        attemptRepository.delete(findEntity(id));
    }

    @Transactional(readOnly = true)
    public AttemptEntity findEntity(Long id) {
        return attemptRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Attempt", id));
    }

    private void apply(AttemptEntity e, AttemptRequest request) {
        e.setUser(userRepository.findById(request.userId())
                .orElseThrow(() -> new ResourceNotFoundException("User", request.userId())));
        e.setActivity(activityRepository.findById(request.activityId())
                .orElseThrow(() -> new ResourceNotFoundException("Activity", request.activityId())));
        e.setCode(request.code());
        e.setScore(request.score());
    }
}
