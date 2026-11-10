package com.csit321g1.nightingailles.codemm.roadmap.entity;

import com.csit321g1.nightingailles.codemm.lesson.entity.LessonEntity;
import com.csit321g1.nightingailles.codemm.roadmap.entity.RoadmapEntity;
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
import jakarta.persistence.Table;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/** One topic (lesson) inside a roadmap (ERD: ROADMAP_ITEM). */
@Entity
@Table(name = "tblroadmapitem")
public class RoadmapItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long itemId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "roadmap_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private RoadmapEntity roadmap;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lesson_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private LessonEntity lesson;

    /** Order of the topic inside the roadmap, starting at 1. */
    @Column(nullable = false)
    private Integer position;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ItemStatus status;

    public RoadmapItemEntity() {
        super();
    }

    public Long getItemId() {
        return itemId;
    }

    public RoadmapEntity getRoadmap() {
        return roadmap;
    }

    public void setRoadmap(RoadmapEntity roadmap) {
        this.roadmap = roadmap;
    }

    public LessonEntity getLesson() {
        return lesson;
    }

    public void setLesson(LessonEntity lesson) {
        this.lesson = lesson;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public ItemStatus getStatus() {
        return status;
    }

    public void setStatus(ItemStatus status) {
        this.status = status;
    }
}
