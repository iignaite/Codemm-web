package com.csit321g1.nightingailles.codemm.track.repository;

import com.csit321g1.nightingailles.codemm.track.entity.TrackEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackRepository extends JpaRepository<TrackEntity, Long> {

    boolean existsByTitleIgnoreCase(String title);

    boolean existsByTitleIgnoreCaseAndTrackIdNot(String title, Long trackId);
}
