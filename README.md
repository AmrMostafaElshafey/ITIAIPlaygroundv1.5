# ITI AI Playground

Spring Boot web application that models the ITI AI Playground governance platform described in the SRS. It includes:

- Home page setup wizard for foundational content displayed to guest users.
- Role-based dashboards (Admin, Service Owner, Approver) with tailored data sets.
- Admin CRUD for AI services using soft deletes (no physical removal).
- Responsive UI with ITI-inspired default color theme plus two additional professional themes and a switcher.

## Tech Stack

- Spring Boot 3.3
- Thymeleaf
- Spring Data JPA (MySQL)

## Configuration

Update `src/main/resources/application.properties` to match your MySQL connection:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/iti_ai_playground?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=iti_user
spring.datasource.password=iti_password
```

## Run Locally

```bash
mvn spring-boot:run
```

Then visit:

- `http://localhost:8080/` (home page)
- `http://localhost:8080/setup` (setup wizard)
- `http://localhost:8080/admin/services` (admin CRUD)
- `http://localhost:8080/dashboard/admin`
- `http://localhost:8080/dashboard/service-owner`
- `http://localhost:8080/dashboard/approver`

## Soft Delete Behavior

Entities implement soft delete using Hibernate `@SQLDelete` and `@Where` so deleted records are retained for audit. Use the Admin CRUD page to soft delete services.
