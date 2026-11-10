package com.csit321g1.nightingailles.codemm.attempt.repository;

import com.csit321g1.nightingailles.codemm.attempt.entity.TestResultEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestResultRepository extends JpaRepository<TestResultEntity, Long> {

    List<TestResultEntity> findByAttemptAttemptId(Long attemptId);
}
