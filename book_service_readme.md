# Book Service

Простой REST-сервис на Spring Boot для управления книгами (CRUD).

---

## Стек технологий

- Java 21 / Spring Boot 3
  - Web, Validation, Data JPA, Actuator, Security
- PostgreSQL
- Flyway (миграции БД)
- Maven
- Docker (опционально для БД)

---

## Быстрый старт

### 1️⃣ Настройка базы данных

В `src/main/resources/application.yml` укажи параметры подключения к Postgres:

```yaml
spring:
  datasource:
    driver-class-name: org.postgresql.Driver
    url: jdbc:postgresql://localhost:5433/book-service
    username: postgres
    password: postgres
  jpa:
    hibernate:
      ddl-auto: validate      # validate + миграции Flyway рекомендуется
    show-sql: true
    properties:
      hibernate:
        format_sql: true
        dialect: org.hibernate.dialect.PostgreSQLDialect
  flyway:
    enabled: true

server:
  port: 8080
```

---

### 2️⃣ Docker (опционально)

Если хочешь поднять Postgres через Docker:

```yaml
version: "3.8"
services:
  database:
    container_name: book-database
    image: postgres:latest
    environment:
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: postgres
      POSTGRES_DB: book-service
    ports:
      - "5433:5432"
```

Запуск:

```bash
docker-compose up -d
```

---

### 3️⃣ Миграции Flyway

Создай папку `src/main/resources/db/migration` и добавь миграцию, например:

**V1__create_books_table.sql**
```sql
CREATE TABLE IF NOT EXISTS books (
    id BIGSERIAL PRIMARY KEY,
    book_name VARCHAR(200) NOT NULL,
    author_name VARCHAR(120) NOT NULL,
    version BIGINT NOT NULL DEFAULT 0,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE UNIQUE INDEX IF NOT EXISTS uk_books_name_author
ON books(book_name, author_name);
```

> ⚠️ Если миграция изменилась после применения, используй `flyway repair`.

---

### 4️⃣ Сборка и запуск

```bash
mvn clean install
mvn spring-boot:run
```

Приложение доступно на `http://localhost:8080`.

---

## API

### 1️⃣ Создание книги

`POST /books`

**Request JSON:**
```json
{
  "bookName": "Clean Code",
  "authorName": "Robert C. Martin"
}
```

**Response JSON:**
```json
{
  "id": 1,
  "bookName": "Clean Code",
  "authorName": "Robert C. Martin"
}
```

---

### 2️⃣ Получение книги по ID

`GET /books/{id}`

**Response JSON:**
```json
{
  "id": 1,
  "bookName": "Clean Code",
  "authorName": "Robert C. Martin"
}
```

---

### 3️⃣ Получение списка книг (с пагинацией)

`GET /books?page=0&size=10`

**Response JSON:**
```json
{
  "content": [
    {
      "id": 1,
      "bookName": "Clean Code",
      "authorName": "Robert C. Martin"
    }
  ],
  "pageable": { "pageNumber": 0, "pageSize": 10 },
  "totalPages": 1,
  "totalElements": 1,
  "last": true,
  "first": true
}
```

---

### 4️⃣ Обновление книги

`PUT /books/{id}`

**Request JSON:**
```json
{
  "bookName": "Clean Code (2nd Edition)",
  "authorName": "Robert C. Martin"
}
```

**Response JSON:**
```json
{
  "id": 1,
  "bookName": "Clean Code (2nd Edition)",
  "authorName": "Robert C. Martin"
}
```

---

### 5️⃣ Удаление книги

`DELETE /books/{id}`

**Response:** HTTP 204 No Content или:
```json
{
  "message": "Book with ID 1 deleted successfully."
}
```

