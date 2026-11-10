package com.csit321g1.nightingailles.codemm.roadmap.repository;

import com.csit321g1.nightingailles.codemm.roadmap.entity.RoadmapEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoadmapRepository extends JpaRepository<RoadmapEntity, Long> {

    List<RoadmapEntity> findByGoalGoalIdOrderByCreatedAtDesc(Long goalId);
}
