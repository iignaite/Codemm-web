package com.csit321g1.nightingailles.codemm.goal.service;

import com.csit321g1.nightingailles.codemm.common.exception.ResourceNotFoundException;
import com.csit321g1.nightingailles.codemm.goal.dto.GoalRequest;
import com.csit321g1.nightingailles.codemm.goal.dto.GoalResponse;
import com.csit321g1.nightingailles.codemm.goal.entity.GoalEntity;
import com.csit321g1.nightingailles.codemm.goal.repository.GoalRepository;
import com.csit321g1.nightingailles.codemm.track.entity.TrackEntity;
import com.csit321g1.nightingailles.codemm.track.repository.TrackRepository;
import com.csit321g1.nightingailles.codemm.user.entity.UserEntity;
import com.csit321g1.nightingailles.codemm.user.repository.UserRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GoalService {

    private final GoalRepository goalRepository;
    private final UserRepository userRepository;
    private final TrackRepository trackRepository;

    public GoalService(GoalRepository goalRepository, UserRepository userRepository, TrackRepository trackRepository) {
        this.goalRepository = goalRepository;
        this.userRepository = userRepository;
        this.trackRepository = trackRepository;
    }

    @Transactional(readOnly = true)
    public List<GoalResponse> getAll(Long userId) {
        if (userId != null) {
            return goalRepository.findByUserUserId(userId).stream().map(GoalResponse::from).toList();
        }
        return goalRepository.findAll().stream().map(GoalResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public GoalResponse getById(Long id) {
        return GoalResponse.from(findEntity(id));
    }

    public GoalResponse create(GoalRequest request) {
        GoalEntity e = new GoalEntity();
        apply(e, request);
        return GoalResponse.from(goalRepository.save(e));
    }

    public GoalResponse update(Long id, GoalRequest request) {
        GoalEntity e = findEntity(id);
        apply(e, request);
        return GoalResponse.from(goalRepository.save(e));
    }

    public void delete(Long id) {
        goalRepository.delete(findEntity(id));
    }

    @Transactional(readOnly = true)
    public GoalEntity findEntity(Long id) {
        return goalRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Goal", id));
    }

    private void apply(GoalEntity e, GoalRequest request) {
        e.setUser(userRepository.findById(request.userId())
                .orElseThrow(() -> new ResourceNotFoundException("User", request.userId())));
        e.setTrack(trackRepository.findById(request.trackId())
                .orElseThrow(() -> new ResourceNotFoundException("Track", request.trackId())));
        e.setDescription(request.description());
        e.setPriorKnowledge(request.priorKnowledge());
        e.setDepth(request.depth());
    }
}
