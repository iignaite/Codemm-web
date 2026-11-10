package com.csit321g1.nightingailles.codemm.lesson.controller;

import com.csit321g1.nightingailles.codemm.lesson.dto.LessonRequest;
import com.csit321g1.nightingailles.codemm.lesson.dto.LessonResponse;
import com.csit321g1.nightingailles.codemm.lesson.service.LessonService;
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
@RequestMapping("/api/lessons")
public class LessonController {

    private final LessonService lessonService;

    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @GetMapping
    public List<LessonResponse> getAll(@RequestParam(required = false) Long trackId) {
        return lessonService.getAll(trackId);
    }

    @GetMapping("/{id}")
    public LessonResponse getById(@PathVariable Long id) {
        return lessonService.getById(id);
    }

    @PostMapping
    public ResponseEntity<LessonResponse> create(@Valid @RequestBody LessonRequest request) {
        LessonResponse created = lessonService.create(request);
        return ResponseEntity.created(URI.create("/api/lessons/" + created.lessonId())).body(created);
    }

    @PutMapping("/{id}")
    public LessonResponse update(@PathVariable Long id, @Valid @RequestBody LessonRequest request) {
        return lessonService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        lessonService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
