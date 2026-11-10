package com.csit321g1.nightingailles.codemm.track.controller;

import com.csit321g1.nightingailles.codemm.track.dto.TrackRequest;
import com.csit321g1.nightingailles.codemm.track.dto.TrackResponse;
import com.csit321g1.nightingailles.codemm.track.service.TrackService;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tracks")
public class TrackController {

    private final TrackService trackService;

    public TrackController(TrackService trackService) {
        this.trackService = trackService;
    }

    @GetMapping
    public List<TrackResponse> getAll() {
        return trackService.getAll();
    }

    @GetMapping("/{id}")
    public TrackResponse getById(@PathVariable Long id) {
        return trackService.getById(id);
    }

    @PostMapping
    public ResponseEntity<TrackResponse> create(@Valid @RequestBody TrackRequest request) {
        TrackResponse created = trackService.create(request);
        return ResponseEntity.created(URI.create("/api/tracks/" + created.trackId())).body(created);
    }

    @PutMapping("/{id}")
    public TrackResponse update(@PathVariable Long id, @Valid @RequestBody TrackRequest request) {
        return trackService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        trackService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
