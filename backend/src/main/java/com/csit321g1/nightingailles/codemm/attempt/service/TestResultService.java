package com.csit321g1.nightingailles.codemm.attempt.service;

import com.csit321g1.nightingailles.codemm.activity.entity.TestCaseEntity;
import com.csit321g1.nightingailles.codemm.activity.repository.TestCaseRepository;
import com.csit321g1.nightingailles.codemm.attempt.dto.TestResultRequest;
import com.csit321g1.nightingailles.codemm.attempt.dto.TestResultResponse;
import com.csit321g1.nightingailles.codemm.attempt.entity.AttemptEntity;
import com.csit321g1.nightingailles.codemm.attempt.entity.TestResultEntity;
import com.csit321g1.nightingailles.codemm.attempt.repository.AttemptRepository;
import com.csit321g1.nightingailles.codemm.attempt.repository.TestResultRepository;
import com.csit321g1.nightingailles.codemm.common.exception.ResourceNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TestResultService {

    private final TestResultRepository testResultRepository;
    private final AttemptRepository attemptRepository;
    private final TestCaseRepository testCaseRepository;

    public TestResultService(TestResultRepository testResultRepository, AttemptRepository attemptRepository, TestCaseRepository testCaseRepository) {
        this.testResultRepository = testResultRepository;
        this.attemptRepository = attemptRepository;
        this.testCaseRepository = testCaseRepository;
    }

    @Transactional(readOnly = true)
    public List<TestResultResponse> getAll(Long attemptId) {
        if (attemptId != null) {
            return testResultRepository.findByAttemptAttemptId(attemptId).stream().map(TestResultResponse::from).toList();
        }
        return testResultRepository.findAll().stream().map(TestResultResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public TestResultResponse getById(Long id) {
        return TestResultResponse.from(findEntity(id));
    }

    public TestResultResponse create(TestResultRequest request) {
        TestResultEntity e = new TestResultEntity();
        apply(e, request);
        return TestResultResponse.from(testResultRepository.save(e));
    }

    public TestResultResponse update(Long id, TestResultRequest request) {
        TestResultEntity e = findEntity(id);
        apply(e, request);
        return TestResultResponse.from(testResultRepository.save(e));
    }

    public void delete(Long id) {
        testResultRepository.delete(findEntity(id));
    }

    @Transactional(readOnly = true)
    public TestResultEntity findEntity(Long id) {
        return testResultRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Test result", id));
    }

    private void apply(TestResultEntity e, TestResultRequest request) {
        e.setAttempt(attemptRepository.findById(request.attemptId())
                .orElseThrow(() -> new ResourceNotFoundException("Attempt", request.attemptId())));
        e.setTestCase(testCaseRepository.findById(request.testId())
                .orElseThrow(() -> new ResourceNotFoundException("Test case", request.testId())));
        e.setPassed(request.passed());
        e.setActualOutput(request.actualOutput());
    }
}
