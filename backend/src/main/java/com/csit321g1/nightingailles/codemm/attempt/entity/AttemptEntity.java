package com.csit321g1.nightingailles.codemm.attempt.entity;

import com.csit321g1.nightingailles.codemm.activity.entity.ActivityEntity;
import com.csit321g1.nightingailles.codemm.user.entity.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

/** A recorded Check submission (ERD: ATTEMPT). Try runs are never stored. */
@Entity
@Table(name = "tblattempt")
public class AttemptEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long attemptId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "activity_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ActivityEntity activity;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String code;

    /** Percentage of tests passed, 0 to 100. */
    @Column(nullable = false)
    private Integer score;

    /** 1 for the learner's first Check on this activity, 2 for the second, and so on. */
    @Column(nullable = false)
    private Integer attemptNo;

    @Column(nullable = false, updatable = false)
    private Instant submittedAt;

    public AttemptEntity() {
        super();
    }

    @PrePersist
    void onCreate() {
        submittedAt = Instant.now();
    }

    public Long getAttemptId() {
        return attemptId;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public ActivityEntity getActivity() {
        return activity;
    }

    public void setActivity(ActivityEntity activity) {
        this.activity = activity;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getAttemptNo() {
        return attemptNo;
    }

    public void setAttemptNo(Integer attemptNo) {
        this.attemptNo = attemptNo;
    }

    public Instant getSubmittedAt() {
        return submittedAt;
    }
}
