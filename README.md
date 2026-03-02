# Autoflex Project
Hands-on backend technical project developed as part of a company assessment, focused on product and raw material management using Quarkus and Oracle.

## How to run the project

### Backend (Quarkus)

In the project root, run:

```
docker-compose up -d
./mvnw quarkus:dev
```

This starts Quarkus in development mode (hot reload).

API usually available at:

```
http://localhost:8081
```

---

### Frontend (React)

Go to the frontend folder:

```
cd autoflex-front
```

Install dependencies:

```
npm install
```

Run the project:

```
npm run dev
```

Frontend usually at:

```
http://localhost:5173
```

---

## Environment variables

The project uses `.env` for API configuration and credentials.
Environment variables are not stored in the repository. Please provide them separately (for example via email or secure sharing).

Once you receive the variables, create the file in the frontend and backend folder:

```
autoflex-front/.env
autoflex-back/.env
```

---

## Database (Docker)

The database runs inside Docker. Before first use, you may need to create the database user in your Oracle container.

Run the container (if not already running):

```
docker-compose up -d
```

Then access the database and create the user (adjust according to your setup):

```sql
CREATE USER <db_user> IDENTIFIED BY <secure_password>;
GRANT ALL PRIVILEGES TO <db_user>;
```

Use secure credentials and do not commit them to the repository.

---

## Quick test

1. Database running in Docker with user configured
2. Backend running on `localhost:8081`
3. Frontend running on `localhost:5173`
4. Open the browser and test the application

If you encounter errors, send me a message.
We’ll fix it.
