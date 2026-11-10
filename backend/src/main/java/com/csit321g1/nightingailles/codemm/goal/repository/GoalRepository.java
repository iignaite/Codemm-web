package com.csit321g1.nightingailles.codemm.goal.repository;

import com.csit321g1.nightingailles.codemm.goal.entity.GoalEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoalRepository extends JpaRepository<GoalEntity, Long> {

    List<GoalEntity> findByUserUserId(Long userId);
}
