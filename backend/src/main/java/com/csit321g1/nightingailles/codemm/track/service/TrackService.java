package com.csit321g1.nightingailles.codemm.track.service;

import com.csit321g1.nightingailles.codemm.common.exception.ConflictException;
import com.csit321g1.nightingailles.codemm.common.exception.ResourceNotFoundException;
import com.csit321g1.nightingailles.codemm.track.dto.TrackRequest;
import com.csit321g1.nightingailles.codemm.track.dto.TrackResponse;
import com.csit321g1.nightingailles.codemm.track.entity.TrackEntity;
import com.csit321g1.nightingailles.codemm.track.repository.TrackRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TrackService {

    private final TrackRepository trackRepository;

    public TrackService(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    @Transactional(readOnly = true)
    public List<TrackResponse> getAll() {
        return trackRepository.findAll().stream().map(TrackResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public TrackResponse getById(Long id) {
        return TrackResponse.from(findEntity(id));
    }

    public TrackResponse create(TrackRequest request) {
        if (trackRepository.existsByTitleIgnoreCase(request.title().trim())) {
            throw new ConflictException("Track " + request.title() + " already exists");
        }
        TrackEntity e = new TrackEntity();
        apply(e, request);
        return TrackResponse.from(trackRepository.save(e));
    }

    public TrackResponse update(Long id, TrackRequest request) {
        TrackEntity e = findEntity(id);
        if (trackRepository.existsByTitleIgnoreCaseAndTrackIdNot(request.title().trim(), id)) {
            throw new ConflictException("Track " + request.title() + " already exists");
        }
        apply(e, request);
        return TrackResponse.from(trackRepository.save(e));
    }

    public void delete(Long id) {
        trackRepository.delete(findEntity(id));
    }

    @Transactional(readOnly = true)
    public TrackEntity findEntity(Long id) {
        return trackRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Track", id));
    }

    private void apply(TrackEntity e, TrackRequest request) {
        e.setTitle(request.title().trim());
        e.setLanguage(request.language().trim().toLowerCase());
        e.setDescription(request.description());
    }
}
