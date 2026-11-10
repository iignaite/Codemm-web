package com.csit321g1.nightingailles.codemm.lesson.repository;

import com.csit321g1.nightingailles.codemm.lesson.entity.LessonEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository extends JpaRepository<LessonEntity, Long> {

    List<LessonEntity> findByTrackTrackIdOrderByPositionAsc(Long trackId);
}
