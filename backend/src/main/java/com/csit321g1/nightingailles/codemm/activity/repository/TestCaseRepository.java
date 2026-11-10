package com.csit321g1.nightingailles.codemm.activity.repository;

import com.csit321g1.nightingailles.codemm.activity.entity.TestCaseEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestCaseRepository extends JpaRepository<TestCaseEntity, Long> {

    List<TestCaseEntity> findByActivityActivityId(Long activityId);
}
