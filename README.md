# Codemm-web

Web version of **Codemm**, a programming practice platform for CIT-U BSIT and BSCS students.
Learners set a goal, follow a roadmap of lessons and validated coding activities, get their code
checked against tests in a sandbox, and receive short AI hints inside the workspace instead of full
answers. A desktop version already exists (https://codemm.vercel.app); this repository is the web
version built for CSIT321 (Applications Development and Emerging Technology).

**Team Nightingailles:** Gaille Amolong (G2), Kimmy Swain Alontaga (G1), Lady Divinah M. Sinay (G1)

| Part | Stack | Folder |
|---|---|---|
| Back end | Spring Boot 3.5, Java 17, Spring Data JPA, MySQL (XAMPP) | [`backend/`](backend/) |
| Front end | Next.js 15 (React 19), JavaScript | [`frontend/`](frontend/) |

## Quick start

1. Start MySQL in XAMPP (the database `dbcodemm` is created automatically).
2. Back end: `cd backend && ./mvnw spring-boot:run` → http://localhost:8080
3. Front end: `cd frontend && cp .env.example .env.local && npm install && npm run dev` → http://localhost:3000

## Documentation

- [`AGENTS.md`](AGENTS.md): rules for anyone (human or AI agent) changing this code
- [`docs/ARCHITECTURE.md`](docs/ARCHITECTURE.md): the entity → repository → service → controller pattern and folder layout
- [`docs/API.md`](docs/API.md): every endpoint and request body
- [`docs/ERD.md`](docs/ERD.md): how the tables map to the documentation's ERD
- [`CONTRIBUTING.md`](CONTRIBUTING.md): branches, commits, pull requests and issues

## Software Increment 1

Due **Nov 13, 2026** (G1/G2 checking). Output: CRUD of the core entities in the ERD. See the
`increment-1` issues on GitHub for who owns what.
