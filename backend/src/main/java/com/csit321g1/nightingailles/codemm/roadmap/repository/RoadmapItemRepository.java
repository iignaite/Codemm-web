package com.csit321g1.nightingailles.codemm.roadmap.repository;

import com.csit321g1.nightingailles.codemm.roadmap.entity.RoadmapItemEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoadmapItemRepository extends JpaRepository<RoadmapItemEntity, Long> {

    List<RoadmapItemEntity> findByRoadmapRoadmapIdOrderByPositionAsc(Long roadmapId);
}
