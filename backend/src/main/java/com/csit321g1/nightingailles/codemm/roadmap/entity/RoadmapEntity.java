package com.csit321g1.nightingailles.codemm.roadmap.entity;

import com.csit321g1.nightingailles.codemm.goal.entity.GoalEntity;
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

/** An ordered list of topics built for a goal (ERD: ROADMAP). */
@Entity
@Table(name = "tblroadmap")
public class RoadmapEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roadmapId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "goal_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private GoalEntity goal;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private RoadmapStatus status;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    public RoadmapEntity() {
        super();
    }

    @PrePersist
    void onCreate() {
        createdAt = Instant.now();
    }

    public Long getRoadmapId() {
        return roadmapId;
    }

    public GoalEntity getGoal() {
        return goal;
    }

    public void setGoal(GoalEntity goal) {
        this.goal = goal;
    }

    public RoadmapStatus getStatus() {
        return status;
    }

    public void setStatus(RoadmapStatus status) {
        this.status = status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
