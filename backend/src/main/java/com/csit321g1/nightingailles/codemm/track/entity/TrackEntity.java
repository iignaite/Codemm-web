package com.csit321g1.nightingailles.codemm.track.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/** A learning track such as Java, Python or Web Development (ERD: TRACK). */
@Entity
@Table(name = "tbltrack")
public class TrackEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long trackId;

    @Column(nullable = false, unique = true, length = 100)
    private String title;

    /** The language used for code in this track, e.g. "java", "python" or "javascript". */
    @Column(nullable = false, length = 30)
    private String language;

    @Column(columnDefinition = "TEXT")
    private String description;

    public TrackEntity() {
        super();
    }

    public Long getTrackId() {
        return trackId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
