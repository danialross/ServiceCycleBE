# ServiceCycle

A REST API for tracking vehicle maintenance — log service records, manage replaceable parts, and monitor mileage over time. Built with Spring Boot and PostgreSQL (via Supabase).

## Features

### Vehicles
- Register and manage multiple vehicles per user (make, model, type, license plate)
- Ownership-based access control — users can only view/edit their own vehicles

### Maintenance Records
- Log full service history including date, mileage at time of service, and description
- Attach one or more parts to a single maintenance record
- Deleting a record automatically reactivates the previously active part for each affected slot

### Parts Tracking
- Track parts by type, and where relevant, by position (e.g. front-left, rear-right) or index (for multi-unit parts like spark plugs)
- Define part lifespan by distance (km) and/or duration (months)
- Automatic status categorization: **Good**, **Expiring Soon**, or **Expired**, based on current vehicle mileage and time since install
- Tracks how far overdue a part is, by both kilometers and months
- Enforces a single active part per type/position (or type/index) slot — replacing a part automatically deactivates the old one
- Duplicate-part validation prevents submitting conflicting parts in the same request

### Mileage Records
- Chronological mileage logging per vehicle
- Validation ensures new entries are consistent with existing mileage history (no backdated entries with lower mileage than prior records)
- Calculates average monthly mileage usage based on first and latest records

### Authentication & Access Control
- User signup/login handled by Supabase Auth
- JWT-based authentication via Supabase Auth (OAuth2 Resource Server) — the API validates tokens issued by Supabase, it does not issue its own
- All vehicle-scoped resources (maintenance records, parts, mileage records) are access-checked against the authenticated user's ownership

### API Documentation
- Interactive Swagger UI with full endpoint, schema, and response documentation
- Structured global error handling with consistent `ApiError` response shape across validation, access, and not-found errors

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot 4.0.6 |
| Security | Spring Security, OAuth2 Resource Server, JWT (Supabase Auth) |
| Persistence | Spring Data JPA / Hibernate |
| Database | PostgreSQL (Supabase) |
| API Docs | SpringDoc OpenAPI (Swagger UI) |
| Build Tool | Maven |
| Other | Lombok, Jakarta Bean Validation |

## Getting Started

### Prerequisites

- Java 21+
- Maven 3.9+
- A PostgreSQL database (e.g. a [Supabase](https://supabase.com) project)

### Environment Variables

The application is configured via environment variables:

| Variable | Description |
|---|---|
| `DB_URL` | JDBC connection string for your PostgreSQL database |
| `DB_USER` | Database username |
| `DB_PASSWORD` | Database password |
| `SUPABASE_URL` | Base URL of your Supabase project (used to validate JWTs) |

Create a `.env` file or export these in your shell before running the app.

### Running Locally

```bash
# Clone the repository
git clone https://github.com/<your-username>/ServiceCycle.git
cd ServiceCycle

# Build
./mvnw clean install

# Run
./mvnw spring-boot:run
```

The API will start on `http://localhost:8080` by default.

### API Documentation

Once running, interactive Swagger UI is available at:

```
http://localhost:8080/swagger-ui.html
```

## Authentication

ServiceCycle delegates user signup and login to [Supabase Auth](https://supabase.com/docs/guides/auth) — the API itself does not handle credentials or issue tokens. Clients authenticate directly against Supabase (email/password, OAuth providers, etc.) to obtain a JWT, then include that token on requests to ServiceCycle:

```
Authorization: Bearer <token>
```

The API validates incoming JWTs against your Supabase project's JWKS endpoint and identifies users by the JWT `sub` claim (the Supabase user ID), which is also used as the `ownerId` for vehicle-scoped resources.

## Project Structure

The codebase follows a feature-based module structure, with each domain (vehicles, maintenance records, parts, mileage records) organized into its own package containing controllers, services, repositories, entities, and DTOs.

```
src/main/java/com/danialross/ServiceCycle/
├── modules/
│   ├── vehicles/
│   ├── maintenanceRecord/
│   ├── parts/
│   └── mileageRecord/
└── exception/          # Global exception handling
```

## License

This project is currently unlicensed. Add a license here if you intend to open source it.
