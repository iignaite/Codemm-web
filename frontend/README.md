# Codemm front end (Next.js, JavaScript)

## Run

```bash
cp .env.example .env.local   # points the app at http://localhost:8080
npm install
npm run dev                  # http://localhost:3000
```

Start the back end first (see `../backend/README.md`).

## Structure

```
src/
  app/                 # routes (one folder per page)
    users/page.js      # reference CRUD page; copy it for new entities
  components/          # shared UI (NavBar, ...)
  features/<entity>/   # API calls + forms for one entity
    <entity>Api.js
    <Entity>Form.js
  lib/api.js           # fetch wrapper; throws Error(message) from the back end's ApiError
```

See `../docs/ARCHITECTURE.md` for the full conventions.
