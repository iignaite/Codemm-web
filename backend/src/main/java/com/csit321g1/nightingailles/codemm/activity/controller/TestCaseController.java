package com.csit321g1.nightingailles.codemm.activity.controller;

import com.csit321g1.nightingailles.codemm.activity.dto.TestCaseRequest;
import com.csit321g1.nightingailles.codemm.activity.dto.TestCaseResponse;
import com.csit321g1.nightingailles.codemm.activity.service.TestCaseService;
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
@RequestMapping("/api/test-cases")
public class TestCaseController {

    private final TestCaseService testCaseService;

    public TestCaseController(TestCaseService testCaseService) {
        this.testCaseService = testCaseService;
    }

    @GetMapping
    public List<TestCaseResponse> getAll(@RequestParam(required = false) Long activityId) {
        return testCaseService.getAll(activityId);
    }

    @GetMapping("/{id}")
    public TestCaseResponse getById(@PathVariable Long id) {
        return testCaseService.getById(id);
    }

    @PostMapping
    public ResponseEntity<TestCaseResponse> create(@Valid @RequestBody TestCaseRequest request) {
        TestCaseResponse created = testCaseService.create(request);
        return ResponseEntity.created(URI.create("/api/test-cases/" + created.testId())).body(created);
    }

    @PutMapping("/{id}")
    public TestCaseResponse update(@PathVariable Long id, @Valid @RequestBody TestCaseRequest request) {
        return testCaseService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        testCaseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
