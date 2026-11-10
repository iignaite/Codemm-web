package com.csit321g1.nightingailles.codemm.activity.service;

import com.csit321g1.nightingailles.codemm.activity.dto.TestCaseRequest;
import com.csit321g1.nightingailles.codemm.activity.dto.TestCaseResponse;
import com.csit321g1.nightingailles.codemm.activity.entity.ActivityEntity;
import com.csit321g1.nightingailles.codemm.activity.entity.TestCaseEntity;
import com.csit321g1.nightingailles.codemm.activity.repository.ActivityRepository;
import com.csit321g1.nightingailles.codemm.activity.repository.TestCaseRepository;
import com.csit321g1.nightingailles.codemm.common.exception.ResourceNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TestCaseService {

    private final TestCaseRepository testCaseRepository;
    private final ActivityRepository activityRepository;

    public TestCaseService(TestCaseRepository testCaseRepository, ActivityRepository activityRepository) {
        this.testCaseRepository = testCaseRepository;
        this.activityRepository = activityRepository;
    }

    @Transactional(readOnly = true)
    public List<TestCaseResponse> getAll(Long activityId) {
        if (activityId != null) {
            return testCaseRepository.findByActivityActivityId(activityId).stream().map(TestCaseResponse::from).toList();
        }
        return testCaseRepository.findAll().stream().map(TestCaseResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public TestCaseResponse getById(Long id) {
        return TestCaseResponse.from(findEntity(id));
    }

    public TestCaseResponse create(TestCaseRequest request) {
        TestCaseEntity e = new TestCaseEntity();
        apply(e, request);
        return TestCaseResponse.from(testCaseRepository.save(e));
    }

    public TestCaseResponse update(Long id, TestCaseRequest request) {
        TestCaseEntity e = findEntity(id);
        apply(e, request);
        return TestCaseResponse.from(testCaseRepository.save(e));
    }

    public void delete(Long id) {
        testCaseRepository.delete(findEntity(id));
    }

    @Transactional(readOnly = true)
    public TestCaseEntity findEntity(Long id) {
        return testCaseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Test case", id));
    }

    private void apply(TestCaseEntity e, TestCaseRequest request) {
        e.setActivity(activityRepository.findById(request.activityId())
                .orElseThrow(() -> new ResourceNotFoundException("Activity", request.activityId())));
        e.setInput(request.input());
        e.setExpectedOutput(request.expectedOutput());
        e.setHidden(request.hidden());
    }
}
