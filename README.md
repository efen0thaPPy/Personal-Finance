### Personal Finance API

Spring Boot 3 (Java 21) REST API for managing personal finance: users, accounts, categories, transactions, and transfers. JWT-based authentication with role-based authorization, OpenAPI/Swagger UI, and PostgreSQL persistence.

### Project Context & Thoughts
This section contains my personal insights and thoughts about the project:

I have built this project for learning purposes and to demonstrate my skills
in developing a secure and well-structured REST API using Spring Boot.
The project showcases my practices in authentication, authorization, and data management,
while also providing a user-friendly interface through Swagger UI for easy exploration of the API endpoints.
I am well aware that DTO's can be further refined, such as creating a Mapper class and map between Model and Dto objects with the methods like ToEntity and ToDto.
I hope my project is to your liking.

---

### Tech Stack
- **Runtime**: Java 21, Spring Boot 3.5.6
- **Modules**: Spring Web, Spring Security, Spring Data JPA
- **Auth**: JWT (`jjwt` 0.11.5)
- **Docs**: springdoc-openapi UI
- **DB**: PostgreSQL (with `data.sql` seeding categories)

---

### Prerequisites
- Java 21+
- Maven 3.9+ (or use `mvnw` wrapper provided)
- PostgreSQL (local or remote)

---

### Configuration

**Environment Variables**:
- `JWT_SECRET` — Required. Secret key for signing JWTs.

**Database Configuration** (in `application.properties`):
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/personal_finance
spring.datasource.username=postgres
spring.datasource.password=postgres

```

On startup, `src/main/resources/data.sql` seeds default income/expense categories.

---

### Build & Run
- Build:
```
./mvnw clean package
```

- Run (dev):
```
./mvnw spring-boot:run
```

App defaults to port 8080. OpenAPI docs:
- Swagger UI: `/swagger-ui/index.html`
- OpenAPI JSON: `/v3/api-docs`

---

### Authentication & Authorization
- **Public endpoints**: `POST /register`, `POST /login`, Swagger/OpenAPI assets
- **JWT**: `Authorization: Bearer <token>`
- **Roles**: `ROLE_USER`, `ROLE_ADMIN`
- Method-level security via `@PreAuthorize` restricts access (see each endpoint below)

Login flow:
1) `POST /register` with username/password to create user.
2) `POST /login` with credentials, receive JWT token.
3) Use `Authorization: Bearer <token>` for protected endpoints.

Token contains username and roles; expiration is enforced by the JWT filter.

---

### DTOs (request/response shapes)
- EntryDto (auth):
```
{
  "username": "string",
  "password": "string"
}
```

- TransactionReceiveDto (create):
```
{
  "money": 123.45,
  "description": "string",
  "date": "2025-10-20",
  "category": { "id": number, "name": "string", "type": "INCOME|EXPENSE" },
  "type": "INCOME|EXPENSE"
}
```

- TransactionUpdateReceiverDto (update):
```
{
  "id": number,
  "money": 123.45,
  "description": "string",
  "date": "2025-10-20",
  "category": { "id": number, "name": "string", "type": "INCOME|EXPENSE" },
  "type": "INCOME|EXPENSE"
}
```

- TransactionDto (response):
```
{
  "id": number,
  "money": 123.45,
  "description": "string",
  "date": "2025-10-20",
  "categoryName": "string",
  "username": "string",
  "type": "INCOME|EXPENSE"
}
```

- TransferDto:
```
{
  "receiver": "username",
  "amount": 50.00
}
```

---

### Endpoints

Auth & Users
- POST `/register` — public. Body: EntryDto. Returns `{ username, rolesSet }`.
- POST `/login` — public. Body: EntryDto. Returns `{ result, message, token }`.
- GET `/api/users` — `ROLE_ADMIN` only. Returns list of users.

Transactions (`/api`)
- GET `/api/transactions` — `ROLE_ADMIN` only. Returns `TransactionDto[]`.
- GET `/api/transaction/{username}` — same user or `ROLE_ADMIN`. Returns `TransactionDto[]`.
- GET `/api/transactions/{id}` — `ROLE_ADMIN` only. Returns `TransactionDto` or 404.
- POST `/api/transaction` — `ROLE_ADMIN` only. Body: TransactionReceiveDto. Returns `TransactionDto`.
- PUT `/api/transaction` — `ROLE_ADMIN` only. Body: TransactionUpdateReceiverDto. Returns `TransactionDto`.
- DELETE `/api/transaction/{id}` — `ROLE_ADMIN` only. Returns 204.

Transfers (`/api`)
- POST `/api/transfer` — authenticated. Body: TransferDto. Returns 202 with sent body if accepted.
- GET `/api/transfer/{username}` — same user or `ROLE_ADMIN`. Returns `AccountDto` for user.

Categories (`/web`)
- GET `/web/categories` — authenticated. Returns all categories.
- GET `/web/category/{type}` — authenticated. Path `type` in `INCOME|EXPENSE`. Returns categories by type.
- GET `/web/transactionTypes` — authenticated. Returns `INCOME|EXPENSE` values.

Notes
- Swagger UI is pre-configured with bearerAuth. Click “Authorize” and paste `Bearer <token>`.
- Category endpoints use `TransactionType` enum: `INCOME`, `EXPENSE`.

---

### Example Usage
Register
```
POST /register
{
  "username": "alice",
  "password": "StrongPass!23"
}
```

Login
```
POST /login
{
  "username": "alice",
  "password": "StrongPass!23"
}
=> { "token": "<JWT>" }
```

Authorized request
```
GET /api/transaction/alice
Authorization: Bearer <JWT>
```

---

### Development
- Run tests: `./mvnw test`
- Common env via `.env` for local; use real env vars in production
- Ensure `JWT_SECRET` is strong and kept secret
