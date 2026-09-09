# EventPulse

A modern event operations management application built with Java, Spring Boot, Spring Security, PostgreSQL, Flyway, Thymeleaf, and Bootstrap.

Designed as a professional portfolio project demonstrating enterprise Java development, secure authentication, role-based access control, database migrations, responsive UI, analytics, and reporting.

**Live Demo:** https://eventpulse-q8lb.onrender.com

**GitHub Repository:** https://github.com/LiliyaCrush/EventPulse

---

## Project Overview

EventPulse helps event coordinators and operations managers track network events, plan Wi‑Fi capacity, monitor attendance, and review operational analytics from one secure workspace.

---

## Features

### Security
- Database-backed authentication
- BCrypt password hashing
- Remember Me
- Role-based authorization (`ADMIN` / `VIEWER`)
- Custom Access Denied page

### Event Management
- Multi-day event scheduling
- Attendance tracking
- Wi‑Fi capacity planning
- Network usage monitoring
- Attendance reporting
- Search by title or notes

### Analytics
- Operational dashboard
- KPI cards powered by live database data
- Attendance by event
- Monthly event trends
- Attendance vs. Wi-Fi capacity planning

### User Experience
- Responsive design
- Professional dashboard
- Modern UI with EventPulse design system
- Bootstrap Icons

---

## Technology Stack

### Backend
- Java 17
- Spring Boot
- Spring MVC
- Spring Data JPA
- Bean Validation

### Frontend
- Thymeleaf
- Bootstrap 5
- Chart.js
- Bootstrap Icons

### Database
- PostgreSQL
- Flyway migrations

### Security
- Spring Security
- BCrypt
- Thymeleaf Spring Security dialect

### Deployment
- Docker
- Render (live demo)

### Build Tools
- Maven Wrapper

---

## Configuration

The application requires the following environment variables:

- `SPRING_DATASOURCE_URL`
- `SPRING_DATASOURCE_USERNAME`
- `SPRING_DATASOURCE_PASSWORD`
- `EVENTPULSE_REMEMBER_ME_KEY`

These values should be configured in your local environment or deployment platform (for example, Render) before running the application.

---

## Running Locally

1. Ensure **Java 17**, **Maven** (or the included wrapper), and **PostgreSQL** are available.
2. Create a PostgreSQL database for EventPulse.
3. Export the environment variables listed above.
4. From the `EventPulse` application directory:

```bash
./mvnw spring-boot:run
```

5. Open `http://localhost:8080` and sign in with a demo account.

---

## Demo Accounts

Passwords are stored only as BCrypt hashes in PostgreSQL. Use these accounts for local or demo testing:

| Username | Role | Password |
|----------|------|----------|
| `admin` | ADMIN | `EventPulse-Admin!` |
| `viewer` | VIEWER | `EventPulse-Viewer!` |

| Capability | ADMIN | VIEWER |
|------------|-------|--------|
| View dashboard, analytics, search, and reports | ✓ | ✓ |
| Add, edit, and delete events | ✓ | ✗ (Access Denied) |

---

## Deployment

The application is containerized with Docker and deployed on Render using PostgreSQL.

Live Demo:
https://eventpulse-q8lb.onrender.com

---

## Author

Liliya (Lily) Piskunova
