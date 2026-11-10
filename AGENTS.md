# AGENTS.md

Instructions for anyone, human or AI coding agent, who changes this repository.
Read this file fully before writing code. When it disagrees with your habits, this file wins.

## 1. What this project is

Codemm-web is the web version of Codemm (CSIT321 project, team Nightingailles, section G1).
The project documentation (ERD, class diagram, use cases) is the contract. Do not invent entities,
fields or features that are not in it without updating `docs/ERD.md` and telling the team.

- `backend/`: Spring Boot 3.5.x, Java 17, Maven wrapper (`./mvnw`), Spring Web, Spring Data JPA,
  Validation, MySQL (XAMPP), H2 for tests only. Group `com.csit321g1.nightingailles`, artifact `codemm`.
- `frontend/`: Next.js 15 App Router, **JavaScript only** (no TypeScript), React 19, plain CSS.

## 2. Back-end pattern (mandatory)

Package by feature, then by layer. Every feature lives in its own package under
`com.csit321g1.nightingailles.codemm`:

```
<feature>/
  entity/       XxxEntity.java (+ enums)      JPA mapping only, no business logic
  repository/   XxxRepository.java            extends JpaRepository<XxxEntity, Long>
  dto/          XxxRequest.java, XxxResponse.java   Java records; the API never exposes entities
  service/      XxxService.java               all business rules, @Transactional
  controller/   XxxController.java            HTTP only: map routes, @Valid, return DTOs
common/
  config/       CORS, password encoder
  exception/    ResourceNotFoundException (404), ConflictException (409), GlobalExceptionHandler, ApiError
```

Rules:

1. **Flow is one way:** controller → service → repository. Controllers never touch repositories.
   A service may read another feature's repository to resolve a foreign key; it must not modify
   another feature's data.
2. **Constructor injection only.** No `@Autowired` fields. Fields are `private final`.
3. **Entities:** class name `XxxEntity`, table name `tblxxx`, `@GeneratedValue(strategy = IDENTITY)`,
   `Long` IDs named `xxxId`, plain getters and setters (no Lombok), a public no-arg constructor that
   calls `super()`, no `setXxxId`. Relations are `@ManyToOne(fetch = LAZY, optional = false)` with
   `@JoinColumn(name = "xxx_id")` and `@OnDelete(action = CASCADE)` when the child cannot exist without
   the parent. Enums use `@Enumerated(EnumType.STRING)`. Timestamps are set in `@PrePersist`.
4. **DTOs:** `XxxRequest` (body for POST and PUT, with Jakarta Validation annotations; foreign keys are
   plain IDs like `trackId`) and `XxxResponse` (with a static `from(XxxEntity)`; foreign keys are
   returned as IDs, never nested entities). Never return a password or password hash.
5. **Services** throw `ResourceNotFoundException("Thing", id)` for missing records and
   `ConflictException` for duplicates. They expose `findEntity(Long id)` for other services.
   Class-level `@Transactional`; read methods use `@Transactional(readOnly = true)`.
6. **Controllers:** `@RestController @RequestMapping("/api/<plural-kebab-case>")`, five endpoints:
   `GET /`, `GET /{id}`, `POST /` (201 + Location), `PUT /{id}`, `DELETE /{id}` (204). Optional
   filters are `@RequestParam(required = false)`, e.g. `GET /api/lessons?trackId=1`.
7. Every new endpoint gets a row in `docs/API.md`. Every new table gets a row in `docs/ERD.md`.
8. Add or update a test in `backend/src/test` for every feature (MockMvc against H2, see
   `CoreCrudFlowTests`). `./mvnw test` must pass before you push.

The `user` feature is the reference implementation. Copy its shape for new features.

## 3. Front-end pattern

```
src/app/<entity>/page.js         one route per entity ("use client" CRUD page)
src/features/<entity>/<entity>Api.js   fetch calls via src/lib/api.js
src/features/<entity>/<Entity>Form.js  create/edit form
src/components/                  shared UI (NavBar: add your link there)
src/lib/api.js                   base URL from NEXT_PUBLIC_API_BASE_URL
```

`src/app/users/page.js` is the reference page. Use plain CSS classes from `globals.css`
(`page`, `card`, `form`, `actions`, `secondary`, `danger`, `error`). No UI library unless the team agrees.
`npm run lint` and `npm run build` must pass.

## 4. Git and GitHub

- Branch from `main`: `feat/<issue-number>-<short-name>`, `fix/...`, `docs/...`.
- Commit messages: imperative and specific, e.g. `Add lesson CRUD endpoints (#3)`.
- **No co-author lines and no AI attribution** in commits or pull requests.
- Open a pull request to `main` that says `Closes #<issue>`; another member reviews it.
- Never commit `.env*` files (except `.env.example`), `target/`, `node_modules/` or `.next/`.

## 5. Commands

```bash
cd backend && ./mvnw test              # all back-end tests (H2, no MySQL needed)
cd backend && ./mvnw spring-boot:run   # needs MySQL running (XAMPP)
cd frontend && npm run lint && npm run build
```

## 6. Where to look

- `docs/ARCHITECTURE.md`: request lifecycle, error format, how to add a feature step by step
- `docs/API.md`: endpoint list
- `docs/ERD.md`: table and relationship map
- `docs/HANDOFF.md`: current status and the next tasks
