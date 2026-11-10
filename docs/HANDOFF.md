# Hand-off: status and next steps

Written Nov 10, 2026 when the project moved from a Claude (Cowork) session to Claude Code on Gaille's Mac.
Read `AGENTS.md` first, then this file.

## Context

- Course: CSIT321 Applications Development and Emerging Technology (CIT-U). Team **Nightingailles**:
  Gaille Amolong (G2, lead, GitHub owner of this repo), Kimmy Swain Alontaga (G1, GitHub `ykm1216`),
  Lady Divinah M. Sinay (G1, GitHub `kamisamdiv`, invite pending).
- Instructor's required metadata: Group `com.csit321section.groupname`, Artifact `projectname`.
  Majority section is G1, so we use **`com.csit321g1.nightingailles`** / **`codemm`**.
- **Software Increment 1: Nov 13, 2026 (G1/G2 checking).** Output = CRUD of the core entities in the
  ERD, plus the finished Project Documentation PDF (already done).
- Stack decided by Gaille: Spring Boot back end + **Next.js with JavaScript** (React) front end.
- The documentation's ERD/class diagram is the contract; see `docs/ERD.md`.
- **Increment 1 scope = the must-have core entities only:** User, Track, Goal, Lesson, Activity,
  TestCase, Attempt, TestResult, Roadmap, RoadmapItem. Should-have and nice-to-have entities
  (AI interaction, reflection, milestone, consultation, forum, peer review, notification) wait for later increments.
- **Ownership: Gaille does about 90% of the project.** Teammates own only small, pattern-following
  front-end pages: Kimmy = Tracks page; Divinah = Lessons page. Everything else (the whole back end,
  all other front-end pages, integration, demo) is Gaille's.

## What exists (not yet committed)

- `backend/`: complete package-by-feature Spring Boot project. Full CRUD for all ten Increment 1 entities:
  users, tracks, lessons, goals, roadmaps, roadmap items, activities, test cases, attempts, test results.
  `CoreCrudFlowTests` covers the flow.
- `frontend/`: Next.js 15 (JavaScript, App Router). `npm run lint` and `npm run build` pass. Users page is
  the reference CRUD page.
- Docs: `README.md`, `AGENTS.md`, `CLAUDE.md`, `CONTRIBUTING.md`, `docs/ARCHITECTURE.md`, `docs/API.md`, `docs/ERD.md`.
- `scripts/create-issues.sh`: creates labels and the Increment 1 issues with assignees.

The Maven wrapper is in `backend/` (Maven 3.9.11, Spring Boot 3.5.16). `./mvnw test` passes (7 tests).

## Tasks for this session (in order)

1. **Java and Maven wrapper.** Check `java -version` (need 17+; if missing: `brew install --cask temurin@17`).
   Add the wrapper in `backend/`: if `mvn` exists run `mvn -N wrapper:wrapper -Dmaven=3.9.11`; otherwise
   download a Spring Initializr project (`curl -s https://start.spring.io/starter.zip -d type=maven-project
   -d javaVersion=17 -d bootVersion=3.5.6 -o /tmp/init.zip`) and copy `mvnw`, `mvnw.cmd` and `.mvn/` from it.
   If 3.5.6 is not offered, use the newest 3.5.x and update `pom.xml`.
2. **Build and test:** `cd backend && ./mvnw test`. Fix any compile or test failures while keeping the
   pattern in `AGENTS.md`. Do not add Lombok. Do not change table or column names without updating `docs/ERD.md`.
3. **Front end:** `cd frontend && npm install && npm run lint && npm run build`.
4. **Smoke test with MySQL** if XAMPP is running: `./mvnw spring-boot:run`, then
   `curl -X POST localhost:8080/api/users -H 'Content-Type: application/json' -d '{"name":"Ada","email":"ada@cit.edu","password":"password123","role":"LEARNER"}'`.
   Skip if MySQL is not running; tests use H2.
5. **Commit and push to `main`** (the remote only has the initial README commit). Use Gaille's own git
   identity. **No `Co-Authored-By` lines and no "Generated with" footers** in any commit or PR.
   Suggested commits: `Set up Spring Boot back end with core CRUD modules`, `Set up Next.js front end`,
   `Add AGENTS.md, architecture and API docs`. Then `git push origin main`.
6. **Issues:** `gh auth status`, then `bash scripts/create-issues.sh`. If Divinah's invite is still pending,
   her issues are created with an @mention; assign her later with `gh issue edit <n> --add-assignee kamisamdiv`.
7. **Report** to Gaille: what was fixed, test results, commit hashes, issue links.

## After that (Gaille's own issues)

- CRUD pages for goals, roadmaps + items, activities + test cases, attempts + results (branch per issue).
- Increment 1 demo prep: seed data behind `app.seed=true`, Postman collection in `docs/postman/`,
  run-through on XAMPP MySQL.
- Do not start Increment 2 work (sandbox runner, AI assistant) until Increment 1 is checked on Nov 13.
