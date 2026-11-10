package com.csit321g1.nightingailles.codemm.goal.controller;

import com.csit321g1.nightingailles.codemm.goal.dto.GoalRequest;
import com.csit321g1.nightingailles.codemm.goal.dto.GoalResponse;
import com.csit321g1.nightingailles.codemm.goal.service.GoalService;
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
@RequestMapping("/api/goals")
public class GoalController {

    private final GoalService goalService;

    public GoalController(GoalService goalService) {
        this.goalService = goalService;
    }

    @GetMapping
    public List<GoalResponse> getAll(@RequestParam(required = false) Long userId) {
        return goalService.getAll(userId);
    }

    @GetMapping("/{id}")
    public GoalResponse getById(@PathVariable Long id) {
        return goalService.getById(id);
    }

    @PostMapping
    public ResponseEntity<GoalResponse> create(@Valid @RequestBody GoalRequest request) {
        GoalResponse created = goalService.create(request);
        return ResponseEntity.created(URI.create("/api/goals/" + created.goalId())).body(created);
    }

    @PutMapping("/{id}")
    public GoalResponse update(@PathVariable Long id, @Valid @RequestBody GoalRequest request) {
        return goalService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        goalService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
