# Architecture

## Overview

```
Next.js (localhost:3000)  ──fetch JSON──▶  Spring Boot (localhost:8080)  ──JPA──▶  MySQL dbcodemm
   src/app/<entity>/page.js                   /api/<entity>                         tbl<entity>
```

## Back-end layers

Each request passes through four layers. Each layer has one job.

| Layer | Class | Job | Must not |
|---|---|---|---|
| Controller | `XxxController` | Map the URL, validate the body with `@Valid`, return a DTO and status code | Call a repository, contain rules |
| Service | `XxxService` | Business rules, foreign-key lookups, transactions | Know about HTTP |
| Repository | `XxxRepository` | Database queries (Spring Data derived methods) | Contain rules |
| Entity | `XxxEntity` | Map one table | Be returned by the API |

DTOs (`XxxRequest`, `XxxResponse`) sit between the controller and the service so the API shape can
change without changing tables, and so secrets such as password hashes never leave the server.

### Request lifecycle (POST /api/goals)

1. `GoalController.create` receives `GoalRequest`; `@Valid` rejects bad input with 400.
2. `GoalService.create` loads the user and track by ID (404 if missing), copies fields into a new
   `GoalEntity`, and saves it.
3. `GoalRepository.save` inserts the row; `@PrePersist` sets `createdAt`.
4. The service returns `GoalResponse.from(entity)`; the controller replies 201 with a `Location` header.

### Errors

`GlobalExceptionHandler` turns every exception into the same JSON:

```json
{
  "timestamp": "2026-11-10T08:00:00Z",
  "status": 404,
  "error": "Not Found",
  "message": "Track 9 was not found",
  "path": "/api/goals",
  "fieldErrors": null
}
```

| Exception | Status |
|---|---|
| `MethodArgumentNotValidException` (failed `@Valid`) | 400, with `fieldErrors` |
| `IllegalArgumentException`, unreadable JSON | 400 |
| `ResourceNotFoundException` | 404 |
| `ConflictException`, `DataIntegrityViolationException` | 409 |

### Deleting

Child foreign keys use `@OnDelete(action = CASCADE)`, so deleting a parent removes its children at
the database level (for example, deleting an activity removes its test cases, attempts and test
results). Keep this in mind before calling DELETE on a track, lesson or user.

## Folder layout

```
backend/src/main/java/com/csit321g1/nightingailles/codemm/
  CodemmApplication.java
  common/      config/ (CorsConfig, PasswordConfig)   exception/ (ApiError, handlers)
  user/        UserEntity, Role                        reference feature
  track/       TrackEntity
  lesson/      LessonEntity
  goal/        GoalEntity, Depth
  roadmap/     RoadmapEntity, RoadmapItemEntity, RoadmapStatus, ItemStatus
  activity/    ActivityEntity, TestCaseEntity, Difficulty, ActivityStatus
  attempt/     AttemptEntity, TestResultEntity
backend/src/test/java/...  CoreCrudFlowTests (MockMvc + H2)
```

Later increments add `reflection/`, `milestone/`, `consultation/`, `community/` (post, peer review,
review comment), `notification/` and `assistant/` (AI interaction), each with the same five
sub-packages.

## How to add a feature (checklist)

1. Create `<feature>/entity/XxxEntity.java` following `user/entity/UserEntity.java`.
2. Create `<feature>/repository/XxxRepository.java` (`extends JpaRepository<XxxEntity, Long>`), adding
   derived finders for filters, e.g. `findByTrackTrackIdOrderByPositionAsc`.
3. Create `XxxRequest` and `XxxResponse` records in `<feature>/dto/`.
4. Create `XxxService` with `getAll`, `getById`, `create`, `update`, `delete`, `findEntity`.
5. Create `XxxController` at `/api/<plural>` with the five endpoints.
6. Add a MockMvc test, run `./mvnw test`.
7. Add the endpoints to `docs/API.md`.
8. Front end: add `src/features/<entity>/` and `src/app/<entity>/page.js`, plus a NavBar link.

## Configuration

`backend/src/main/resources/application.properties` reads environment variables with defaults:

| Variable | Default |
|---|---|
| `DB_URL` | `jdbc:mysql://localhost:3306/dbcodemm?createDatabaseIfNotExist=true` |
| `DB_USERNAME` | `root` |
| `DB_PASSWORD` | empty (XAMPP default) |
| `CORS_ALLOWED_ORIGINS` | `http://localhost:3000` |

`spring.jpa.hibernate.ddl-auto=update` creates and updates tables automatically during development.
Tests use H2 in MySQL mode (`src/test/resources/application.properties`), so they need no database.
