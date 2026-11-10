package com.csit321g1.nightingailles.codemm.attempt.controller;

import com.csit321g1.nightingailles.codemm.attempt.dto.AttemptRequest;
import com.csit321g1.nightingailles.codemm.attempt.dto.AttemptResponse;
import com.csit321g1.nightingailles.codemm.attempt.service.AttemptService;
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
@RequestMapping("/api/attempts")
public class AttemptController {

    private final AttemptService attemptService;

    public AttemptController(AttemptService attemptService) {
        this.attemptService = attemptService;
    }

    @GetMapping
    public List<AttemptResponse> getAll(@RequestParam(required = false) Long userId, @RequestParam(required = false) Long activityId) {
        return attemptService.getAll(userId, activityId);
    }

    @GetMapping("/{id}")
    public AttemptResponse getById(@PathVariable Long id) {
        return attemptService.getById(id);
    }

    @PostMapping
    public ResponseEntity<AttemptResponse> create(@Valid @RequestBody AttemptRequest request) {
        AttemptResponse created = attemptService.create(request);
        return ResponseEntity.created(URI.create("/api/attempts/" + created.attemptId())).body(created);
    }

    @PutMapping("/{id}")
    public AttemptResponse update(@PathVariable Long id, @Valid @RequestBody AttemptRequest request) {
        return attemptService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        attemptService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
