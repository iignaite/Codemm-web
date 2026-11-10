package com.csit321g1.nightingailles.codemm.roadmap.controller;

import com.csit321g1.nightingailles.codemm.roadmap.dto.RoadmapRequest;
import com.csit321g1.nightingailles.codemm.roadmap.dto.RoadmapResponse;
import com.csit321g1.nightingailles.codemm.roadmap.service.RoadmapService;
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
@RequestMapping("/api/roadmaps")
public class RoadmapController {

    private final RoadmapService roadmapService;

    public RoadmapController(RoadmapService roadmapService) {
        this.roadmapService = roadmapService;
    }

    @GetMapping
    public List<RoadmapResponse> getAll(@RequestParam(required = false) Long goalId) {
        return roadmapService.getAll(goalId);
    }

    @GetMapping("/{id}")
    public RoadmapResponse getById(@PathVariable Long id) {
        return roadmapService.getById(id);
    }

    @PostMapping
    public ResponseEntity<RoadmapResponse> create(@Valid @RequestBody RoadmapRequest request) {
        RoadmapResponse created = roadmapService.create(request);
        return ResponseEntity.created(URI.create("/api/roadmaps/" + created.roadmapId())).body(created);
    }

    @PutMapping("/{id}")
    public RoadmapResponse update(@PathVariable Long id, @Valid @RequestBody RoadmapRequest request) {
        return roadmapService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        roadmapService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
