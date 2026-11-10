package com.csit321g1.nightingailles.codemm.roadmap.controller;

import com.csit321g1.nightingailles.codemm.roadmap.dto.RoadmapItemRequest;
import com.csit321g1.nightingailles.codemm.roadmap.dto.RoadmapItemResponse;
import com.csit321g1.nightingailles.codemm.roadmap.service.RoadmapItemService;
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
@RequestMapping("/api/roadmap-items")
public class RoadmapItemController {

    private final RoadmapItemService roadmapItemService;

    public RoadmapItemController(RoadmapItemService roadmapItemService) {
        this.roadmapItemService = roadmapItemService;
    }

    @GetMapping
    public List<RoadmapItemResponse> getAll(@RequestParam(required = false) Long roadmapId) {
        return roadmapItemService.getAll(roadmapId);
    }

    @GetMapping("/{id}")
    public RoadmapItemResponse getById(@PathVariable Long id) {
        return roadmapItemService.getById(id);
    }

    @PostMapping
    public ResponseEntity<RoadmapItemResponse> create(@Valid @RequestBody RoadmapItemRequest request) {
        RoadmapItemResponse created = roadmapItemService.create(request);
        return ResponseEntity.created(URI.create("/api/roadmap-items/" + created.itemId())).body(created);
    }

    @PutMapping("/{id}")
    public RoadmapItemResponse update(@PathVariable Long id, @Valid @RequestBody RoadmapItemRequest request) {
        return roadmapItemService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        roadmapItemService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
