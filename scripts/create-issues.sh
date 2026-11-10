#!/usr/bin/env bash
# Creates the Software Increment 1 issues on GitHub. Run once from the repo root:
#   gh auth status && bash scripts/create-issues.sh
# Needs the GitHub CLI logged in as an account that can write to iignaite/Codemm-web.
set -euo pipefail
REPO="iignaite/Codemm-web"
KIMMY="ykm1216"
DIVINAH="kamisamdiv"
GAILLE="@me"

gh label create "increment-1" --repo "$REPO" --color "1f6feb" --description "Software Increment 1 (due Nov 13)" --force
gh label create "backend" --repo "$REPO" --color "0e8a16" --description "Spring Boot" --force
gh label create "frontend" --repo "$REPO" --color "fbca04" --description "Next.js" --force
gh label create "good first issue" --repo "$REPO" --color "7057ff" --force

# create TITLE ASSIGNEE LABELS BODY  -> prints the issue URL.
# If the assignee has not accepted the repo invite yet, the issue is created unassigned
# and the body mentions them; assign them later with: gh issue edit <n> --add-assignee <user>
create() {
  local title="$1" assignee="$2" labels="$3" body="$4"
  gh issue create --repo "$REPO" --title "$title" --label "$labels" --assignee "$assignee" --body "$body" 2>/dev/null \
    || gh issue create --repo "$REPO" --title "$title" --label "$labels" --body "$body"$'\n\n'"Owner: @${assignee#@} (assign once the repo invite is accepted)"
}

create "Tracks page (front end)" "$KIMMY" "increment-1,frontend,good first issue" "$(cat <<'MD'
**Owner:** Kimmy · **Due:** Nov 12 (one day before checking)

The back end is done: `GET/POST/PUT/DELETE /api/tracks` (see `docs/API.md`). Copy `frontend/src/app/users/page.js` and `frontend/src/features/users/` (see `AGENTS.md` §3).

**To do**
- [ ] `src/features/tracks/tracksApi.js` (GET/POST/PUT/DELETE `/api/tracks`)
- [ ] `src/features/tracks/TrackForm.js` (title, language select: java / python / javascript, description textarea)
- [ ] `src/app/tracks/page.js` – table + create/edit/delete
- [ ] Add "Tracks" to `src/components/NavBar.js` and the home page list

**Done when** `npm run lint && npm run build` pass and the page works against the running back end.
MD
)"

create "Lessons page (front end)" "$DIVINAH" "increment-1,frontend,good first issue" "$(cat <<'MD'
**Owner:** Divinah · **Due:** Nov 12 (one day before checking)

The back end is done: `/api/lessons` supports `?trackId=` (see `docs/API.md`). Copy the Users page pattern (`AGENTS.md` §3).

**To do**
- [ ] `src/features/lessons/lessonsApi.js` (supports `?trackId=`)
- [ ] `src/features/lessons/LessonForm.js` – track dropdown (load `/api/tracks`), title, position, content textarea
- [ ] `src/app/lessons/page.js` – track filter, table, create/edit/delete
- [ ] NavBar + home page link

**Done when** lint and build pass and lessons can be managed per track.
MD
)"

FOUNDATION=$(create "Back-end foundation and core CRUD for all Increment 1 entities" "$GAILLE" "increment-1,backend" "$(cat <<'MD'
**Owner:** Gaille

- Spring Boot 3.5 project `com.csit321g1.nightingailles:codemm`, Java 17, MySQL, H2 tests
- `common/` error handling (`ApiError`), CORS, BCrypt
- Full CRUD: users, tracks, lessons, goals, roadmaps, roadmap items, activities, test cases, attempts (server-assigned `attemptNo`), test results
- `AGENTS.md`, `docs/`, MockMvc tests
MD
)")
gh issue close "$FOUNDATION" --repo "$REPO" --comment "Done in the initial project commit on main." || true

create "CRUD pages: goals, roadmaps, activities, attempts" "$GAILLE" "increment-1,frontend" "$(cat <<'MD'
**Owner:** Gaille

Pages with foreign-key dropdowns for: goals (user, track, depth), roadmaps + roadmap items (goal, lesson, position, status), activities + test cases (lesson, difficulty, status, code fields), attempts + test results (user, activity, score; attemptNo read-only). Same pattern as `src/app/users`.
MD
)"

create "Increment 1 demo: seed data, Postman collection, MySQL run-through" "$GAILLE" "increment-1,backend" "$(cat <<'MD'
**Owner:** Gaille · Before Nov 13 checking

- [ ] Seed data on first run (3 tracks: Java, Python, Web Development; a few lessons, one activity with test cases) behind a `app.seed=true` property
- [ ] Postman collection in `docs/postman/` covering every endpoint
- [ ] Full run on XAMPP MySQL; screenshot the tables in phpMyAdmin
- [ ] Rehearse the CRUD demo for each entity
MD
)"

echo "All issues created."
