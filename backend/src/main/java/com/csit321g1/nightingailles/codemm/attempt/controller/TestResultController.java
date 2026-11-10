package com.csit321g1.nightingailles.codemm.attempt.controller;

import com.csit321g1.nightingailles.codemm.attempt.dto.TestResultRequest;
import com.csit321g1.nightingailles.codemm.attempt.dto.TestResultResponse;
import com.csit321g1.nightingailles.codemm.attempt.service.TestResultService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test-results")
public class TestResultController {

    private final TestResultService testResultService;

    public TestResultController(TestResultService testResultService) {
        this.testResultService = testResultService;
    }

    @GetMapping
    public List<TestResultResponse> getAll(@RequestParam(required = false) Long attemptId) {
        return testResultService.getAll(attemptId);
    }

    @GetMapping("/{id}")
    public TestResultResponse getById(@PathVariable Long id) {
        return testResultService.getById(id);
    }

    @PostMapping
    public ResponseEntity<TestResultResponse> create(@Valid @RequestBody TestResultRequest request) {
        TestResultResponse created = testResultService.create(request);
        return ResponseEntity.created(URI.create("/api/test-results/" + created.resultId())).body(created);
    }

    @PutMapping("/{id}")
    public TestResultResponse update(@PathVariable Long id, @Valid @RequestBody TestResultRequest request) {
        return testResultService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        testResultService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
