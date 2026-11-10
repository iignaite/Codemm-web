package com.csit321g1.nightingailles.codemm.activity.repository;

import com.csit321g1.nightingailles.codemm.activity.entity.ActivityEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<ActivityEntity, Long> {

    List<ActivityEntity> findByLessonLessonId(Long lessonId);
}
