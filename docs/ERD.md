# ERD to table map

Source: Figure 1 (Codemm ERD) in the project documentation. Column names are snake_case versions
of the Java fields (Spring Boot's default naming).

| ERD entity | Table | Java entity | Feature package | Increment 1 status |
|---|---|---|---|---|
| USER | `tbluser` | `UserEntity` | `user` | Done |
| TRACK | `tbltrack` | `TrackEntity` | `track` | Done |
| LESSON | `tbllesson` | `LessonEntity` | `lesson` | Done |
| GOAL | `tblgoal` | `GoalEntity` | `goal` | Done |
| ROADMAP | `tblroadmap` | `RoadmapEntity` | `roadmap` | Done |
| ROADMAP_ITEM | `tblroadmapitem` | `RoadmapItemEntity` | `roadmap` | Done |
| ACTIVITY | `tblactivity` | `ActivityEntity` | `activity` | Done |
| TEST_CASE | `tbltestcase` | `TestCaseEntity` | `activity` | Done |
| ATTEMPT | `tblattempt` | `AttemptEntity` | `attempt` | Done |
| TEST_RESULT | `tbltestresult` | `TestResultEntity` | `attempt` | Done |
| AI_INTERACTION, REFLECTION, MILESTONE, CONSULTATION, CONSULTATION_MESSAGE, POST, PEER_REVIEW, REVIEW_COMMENT, NOTIFICATION | n/a | n/a | n/a | Later increments |

## Relationships

```
USER 1──* GOAL *──1 TRACK 1──* LESSON 1──* ACTIVITY 1──* TEST_CASE
            │                    │              │              │
            1                    1              1              1
            *                    *              *              *
         ROADMAP 1──* ROADMAP_ITEM          ATTEMPT 1──* TEST_RESULT
                                              *
USER 1─────────────────────────────────────────┘
```

## Columns

| Table | Columns |
|---|---|
| `tbluser` | user_id PK, name, email (unique), password_hash (BCrypt), role (LEARNER, INSTRUCTOR, ADMIN), created_at |
| `tbltrack` | track_id PK, title (unique), language, description |
| `tbllesson` | lesson_id PK, track_id FK, title, content, position |
| `tblgoal` | goal_id PK, user_id FK, track_id FK, description, prior_knowledge, depth (OVERVIEW, STANDARD, IN_DEPTH), created_at |
| `tblroadmap` | roadmap_id PK, goal_id FK, status (ACTIVE, ARCHIVED), created_at |
| `tblroadmapitem` | item_id PK, roadmap_id FK, lesson_id FK, position, status (LOCKED, CURRENT, DONE) |
| `tblactivity` | activity_id PK, lesson_id FK, statement, starter_code, reference_solution, difficulty (EASY, MEDIUM, HARD), status (DRAFT, VALIDATED, REJECTED) |
| `tbltestcase` | test_id PK, activity_id FK, input, expected_output, is_hidden |
| `tblattempt` | attempt_id PK, user_id FK, activity_id FK, code, score (0–100), attempt_no, submitted_at |
| `tbltestresult` | result_id PK, attempt_id FK, test_id FK, passed, actual_output |
