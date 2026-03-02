# Autoflex Project

Hands-on backend technical project developed as part of a company assessment, focused on product and raw material management using Quarkus, React and Oracle.

## Requirements

Before running the project, make sure you have installed:

* Docker
* Java 21 (or the version required by Quarkus)
* Maven 3.9.0+ (if not using the wrapper)
* Node.js (recommended LTS version)

## How to run the project

### Database (Docker)

The database and required services run inside Docker.

Start the containers:

```
docker-compose up -d
```

If the container is already running, this command will have no effect.

### Backend (Quarkus)

In the project root, run:

```
./mvnw quarkus:dev
```

This starts Quarkus in development mode (hot reload).

API usually available at:

```
http://localhost:8081
```

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

Frontend usually available at:

```
http://localhost:5173
```

## Environment variables

The project uses `.env` for API configuration and credentials.
Environment variables are not stored in the repository. They must be provided separately (for example via email or secure sharing).

Create the files in each module:

```
autoflex-front/.env
autoflex-back/.env
```

Example (replace with provided values):

```
DB_URL=...
DB_USER=...
DB_PASSWORD=...
```

## Database configuration (Oracle in Docker)

After starting Docker, you may need to create the database user.
The database user variables are not stored in the repository. They also must be provided separately (for example via email or secure sharing).

## Quick test

1. Database running in Docker
2. Backend running on `localhost:8081`
3. Frontend running on `localhost:5173`
4. Open the browser and test the application

If you encounter errors, provide:

* Stack trace
* Node version
* Database logs
* Error screenshots

We will assist in resolving the issue.
