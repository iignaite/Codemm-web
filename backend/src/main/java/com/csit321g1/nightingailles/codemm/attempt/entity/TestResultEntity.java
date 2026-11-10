package com.csit321g1.nightingailles.codemm.attempt.entity;

import com.csit321g1.nightingailles.codemm.activity.entity.TestCaseEntity;
import com.csit321g1.nightingailles.codemm.attempt.entity.AttemptEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/** The outcome of one test case within an attempt (ERD: TEST_RESULT). */
@Entity
@Table(name = "tbltestresult")
public class TestResultEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long resultId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "attempt_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private AttemptEntity attempt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "test_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private TestCaseEntity testCase;

    @Column(nullable = false)
    private Boolean passed;

    @Column(columnDefinition = "TEXT")
    private String actualOutput;

    public TestResultEntity() {
        super();
    }

    public Long getResultId() {
        return resultId;
    }

    public AttemptEntity getAttempt() {
        return attempt;
    }

    public void setAttempt(AttemptEntity attempt) {
        this.attempt = attempt;
    }

    public TestCaseEntity getTestCase() {
        return testCase;
    }

    public void setTestCase(TestCaseEntity testCase) {
        this.testCase = testCase;
    }

    public Boolean getPassed() {
        return passed;
    }

    public void setPassed(Boolean passed) {
        this.passed = passed;
    }

    public String getActualOutput() {
        return actualOutput;
    }

    public void setActualOutput(String actualOutput) {
        this.actualOutput = actualOutput;
    }
}
