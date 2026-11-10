# Codemm back end (Spring Boot)

- Group: `com.csit321g1.nightingailles` · Artifact: `codemm` · Java 17 · Spring Boot 3.5.x · Maven
- Dependencies: Spring Web, Spring Data JPA, Validation, MySQL Driver, DevTools, spring-security-crypto (BCrypt), H2 (tests)

## Run

1. Start MySQL in XAMPP. The database `dbcodemm` is created on first run.
2. `./mvnw spring-boot:run` (Windows: `mvnw.cmd spring-boot:run`)
3. Try it: `curl localhost:8080/api/users`

Different database settings: set `DB_URL`, `DB_USERNAME`, `DB_PASSWORD` before running.

## Test

`./mvnw test` runs MockMvc tests against an in-memory H2 database; MySQL is not needed.

See `../AGENTS.md`, `../docs/ARCHITECTURE.md` and `../docs/API.md`.
