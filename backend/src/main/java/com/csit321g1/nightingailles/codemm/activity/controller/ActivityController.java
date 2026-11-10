package com.csit321g1.nightingailles.codemm.activity.controller;

import com.csit321g1.nightingailles.codemm.activity.dto.ActivityRequest;
import com.csit321g1.nightingailles.codemm.activity.dto.ActivityResponse;
import com.csit321g1.nightingailles.codemm.activity.service.ActivityService;
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
@RequestMapping("/api/activities")
public class ActivityController {

    private final ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @GetMapping
    public List<ActivityResponse> getAll(@RequestParam(required = false) Long lessonId) {
        return activityService.getAll(lessonId);
    }

    @GetMapping("/{id}")
    public ActivityResponse getById(@PathVariable Long id) {
        return activityService.getById(id);
    }

    @PostMapping
    public ResponseEntity<ActivityResponse> create(@Valid @RequestBody ActivityRequest request) {
        ActivityResponse created = activityService.create(request);
        return ResponseEntity.created(URI.create("/api/activities/" + created.activityId())).body(created);
    }

    @PutMapping("/{id}")
    public ActivityResponse update(@PathVariable Long id, @Valid @RequestBody ActivityRequest request) {
        return activityService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        activityService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
