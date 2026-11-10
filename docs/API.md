# REST API

Base URL: `http://localhost:8080`. All bodies are JSON. Every resource has the same five endpoints:

| Method | Path | Success | Notes |
|---|---|---|---|
| GET | `/api/<resource>` | 200 + list | optional filter query parameter (below) |
| GET | `/api/<resource>/{id}` | 200 + object | 404 if missing |
| POST | `/api/<resource>` | 201 + object + `Location` | 400 on validation errors, 404 if a referenced ID is missing |
| PUT | `/api/<resource>/{id}` | 200 + object | full replace; same body as POST |
| DELETE | `/api/<resource>/{id}` | 204 | cascades to child records |

Errors use the `ApiError` format in `docs/ARCHITECTURE.md`.

## Resources

| Resource | Filter | Request body (POST/PUT) | Response adds |
|---|---|---|---|
| `/api/users` | none | `name`, `email`, `password` (min 8; required on POST, optional on PUT), `role` (`LEARNER`/`INSTRUCTOR`/`ADMIN`) | `userId`, `createdAt` (no password) |
| `/api/tracks` | none | `title`, `language`, `description` | `trackId` |
| `/api/lessons` | `?trackId=` | `trackId`, `title`, `content`, `position` (≥1) | `lessonId` |
| `/api/goals` | `?userId=` | `userId`, `trackId`, `description`, `priorKnowledge`, `depth` (`OVERVIEW`/`STANDARD`/`IN_DEPTH`) | `goalId`, `createdAt` |
| `/api/roadmaps` | `?goalId=` | `goalId`, `status` (`ACTIVE`/`ARCHIVED`) | `roadmapId`, `createdAt` |
| `/api/roadmap-items` | `?roadmapId=` | `roadmapId`, `lessonId`, `position` (≥1), `status` (`LOCKED`/`CURRENT`/`DONE`) | `itemId` |
| `/api/activities` | `?lessonId=` | `lessonId`, `statement`, `starterCode`, `referenceSolution`, `difficulty` (`EASY`/`MEDIUM`/`HARD`), `status` (`DRAFT`/`VALIDATED`/`REJECTED`) | `activityId` |
| `/api/test-cases` | `?activityId=` | `activityId`, `input`, `expectedOutput`, `hidden` | `testId` |
| `/api/attempts` | `?userId=` or `?activityId=` | `userId`, `activityId`, `code`, `score` (0–100) | `attemptId`, `attemptNo` (assigned by server), `submittedAt` |
| `/api/test-results` | `?attemptId=` | `attemptId`, `testId`, `passed`, `actualOutput` | `resultId` |

## Example

```bash
curl -X POST localhost:8080/api/users -H 'Content-Type: application/json' \
  -d '{"name":"Ada","email":"ada@cit.edu","password":"password123","role":"LEARNER"}'
# 201 {"userId":1,"name":"Ada","email":"ada@cit.edu","role":"LEARNER","createdAt":"..."}
```
