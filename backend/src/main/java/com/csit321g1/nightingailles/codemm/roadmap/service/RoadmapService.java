package com.csit321g1.nightingailles.codemm.roadmap.service;

import com.csit321g1.nightingailles.codemm.common.exception.ResourceNotFoundException;
import com.csit321g1.nightingailles.codemm.goal.entity.GoalEntity;
import com.csit321g1.nightingailles.codemm.goal.repository.GoalRepository;
import com.csit321g1.nightingailles.codemm.roadmap.dto.RoadmapRequest;
import com.csit321g1.nightingailles.codemm.roadmap.dto.RoadmapResponse;
import com.csit321g1.nightingailles.codemm.roadmap.entity.RoadmapEntity;
import com.csit321g1.nightingailles.codemm.roadmap.repository.RoadmapRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoadmapService {

    private final RoadmapRepository roadmapRepository;
    private final GoalRepository goalRepository;

    public RoadmapService(RoadmapRepository roadmapRepository, GoalRepository goalRepository) {
        this.roadmapRepository = roadmapRepository;
        this.goalRepository = goalRepository;
    }

    @Transactional(readOnly = true)
    public List<RoadmapResponse> getAll(Long goalId) {
        if (goalId != null) {
            return roadmapRepository.findByGoalGoalIdOrderByCreatedAtDesc(goalId).stream().map(RoadmapResponse::from).toList();
        }
        return roadmapRepository.findAll().stream().map(RoadmapResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public RoadmapResponse getById(Long id) {
        return RoadmapResponse.from(findEntity(id));
    }

    public RoadmapResponse create(RoadmapRequest request) {
        RoadmapEntity e = new RoadmapEntity();
        apply(e, request);
        return RoadmapResponse.from(roadmapRepository.save(e));
    }

    public RoadmapResponse update(Long id, RoadmapRequest request) {
        RoadmapEntity e = findEntity(id);
        apply(e, request);
        return RoadmapResponse.from(roadmapRepository.save(e));
    }

    public void delete(Long id) {
        roadmapRepository.delete(findEntity(id));
    }

    @Transactional(readOnly = true)
    public RoadmapEntity findEntity(Long id) {
        return roadmapRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Roadmap", id));
    }

    private void apply(RoadmapEntity e, RoadmapRequest request) {
        e.setGoal(goalRepository.findById(request.goalId())
                .orElseThrow(() -> new ResourceNotFoundException("Goal", request.goalId())));
        e.setStatus(request.status());
    }
}
