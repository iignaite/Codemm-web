package com.csit321g1.nightingailles.codemm.goal.entity;

import com.csit321g1.nightingailles.codemm.track.entity.TrackEntity;
import com.csit321g1.nightingailles.codemm.user.entity.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.Instant;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/** A learning goal set by a learner for one track (ERD: GOAL). */
@Entity
@Table(name = "tblgoal")
public class GoalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long goalId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "track_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private TrackEntity track;

    /** What the learner wants to achieve, in their own words. */
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String priorKnowledge;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Depth depth;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    public GoalEntity() {
        super();
    }

    @PrePersist
    void onCreate() {
        createdAt = Instant.now();
    }

    public Long getGoalId() {
        return goalId;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public TrackEntity getTrack() {
        return track;
    }

    public void setTrack(TrackEntity track) {
        this.track = track;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriorKnowledge() {
        return priorKnowledge;
    }

    public void setPriorKnowledge(String priorKnowledge) {
        this.priorKnowledge = priorKnowledge;
    }

    public Depth getDepth() {
        return depth;
    }

    public void setDepth(Depth depth) {
        this.depth = depth;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
