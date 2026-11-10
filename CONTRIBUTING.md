# Contributing

1. Pick an issue assigned to you on GitHub. Comment if you are blocked.
2. `git checkout main && git pull`, then `git checkout -b feat/<issue-number>-<short-name>`.
3. Follow `AGENTS.md`: copy the `user` feature for the back end and `src/app/users` for the front end.
4. Run the checks:
   - `cd backend && ./mvnw test`
   - `cd frontend && npm run lint && npm run build`
5. Commit using [Conventional Commits](https://www.conventionalcommits.org), e.g.
   `feat(track): add tracks page (#1)` or `fix(lesson): sort lessons by position (#2)`. No co-author lines.
6. `git push -u origin <branch>` and open a pull request to `main` with `Closes #<issue>` in the body.
   Ask a teammate to review; Gaille merges.

## Setting up

- Java 17 (Temurin) and IntelliJ IDEA or VS Code with the Java extensions
- XAMPP with MySQL running on port 3306 (user `root`, empty password)
- Node.js 20 or newer
