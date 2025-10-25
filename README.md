# 📚 Book Service

Простой REST-сервис на **Spring Boot**, предоставляющий CRUD-операции для управления книгами.

---

## 🚀 Стек технологий

- **Java 21**, **Spring Boot 3**
  - Spring Web
  - Spring Validation
  - Spring Data JPA
  - Spring Security (базовая конфигурация)
  - Spring Actuator
- **PostgreSQL**
- **Flyway** — миграции БД
- **Maven**
- **Docker** (опционально для БД)

---

## ⚙️ Установка и запуск

### 1️⃣ Настройка базы данных

В файле `src/main/resources/application.yml` укажи параметры подключения к PostgreSQL:

```yaml
spring:
  datasource:
    driver-class-name: org.postgresql.Driver
    url: jdbc:postgresql://localhost:5433/book-service
    username: postgres
    password: postgres
  jpa:
    hibernate:
      ddl-auto: validate
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

### 2️⃣ Поднять PostgreSQL через Docker

Создай файл `docker-compose.yml` и запусти:

```yaml
version: "3.8"
services:
  postgres:
    container_name: book-database
    image: postgres:15
    restart: always
    environment:
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: postgres
      POSTGRES_DB: book-service
    ports:
      - "5433:5432"
    volumes:
      - pgdata:/var/lib/postgresql/data

volumes:
  pgdata:
```

Запуск контейнера:
```bash
docker-compose up -d
```

---

### 3️⃣ Применить миграции Flyway

Добавь файл `src/main/resources/db/migration/V1__create_books_table.sql`:

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

---

### 4️⃣ Сборка и запуск приложения

```bash
mvn clean install
mvn spring-boot:run
```

Приложение доступно по адресу:  
👉 [http://localhost:8080](http://localhost:8080)

---

## 🔗 REST API

Все эндпоинты начинаются с префикса `/api/v1/books`

### ➕ Создать книгу
`POST /api/v1/books`

**Request:**
```json
{
  "bookName": "Clean Code",
  "authorName": "Robert C. Martin"
}
```

**Response (201):**
```json
{
  "id": 1,
  "bookName": "Clean Code",
  "authorName": "Robert C. Martin"
}
```

---

### 📖 Получить книгу по ID
`GET /api/v1/books/{id}`

**Response (200):**
```json
{
  "id": 1,
  "bookName": "Clean Code",
  "authorName": "Robert C. Martin"
}
```

---

### 📚 Получить все книги (с пагинацией)
`GET /api/v1/books?page=0&size=10`

**Response:**
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
  "totalElements": 1
}
```

---

### ✏️ Обновить книгу
`PUT /api/v1/books/{id}`

**Request:**
```json
{
  "bookName": "Clean Code (2nd Edition)",
  "authorName": "Robert C. Martin"
}
```

**Response:**
```json
{
  "id": 1,
  "bookName": "Clean Code (2nd Edition)",
  "authorName": "Robert C. Martin"
}
```

---

### ❌ Удалить книгу
`DELETE /api/v1/books/{id}`

**Response:** `204 No Content`

---

## 🧩 Архитектура проекта

```
com.funtara.book_service
├── api
│   ├── dto        # DTO для входных/выходных данных
│   └── mapper     # MapStruct-мапперы (DTO ↔ Entity)
├── controller      # REST-контроллеры
├── service         # Интерфейсы бизнес-логики
│   └── impl        # Реализация сервисов (транзакции)
├── repository      # Spring Data JPA репозитории
├── model           # JPA-сущности (Book и т.п.)
├── exception       # Кастомные исключения и обработчик ошибок
└── config          # Конфигурации (Security, CORS, и др.)
```

---

## 🧠 Валидация и ошибки

| Ошибка | HTTP | Описание |
|--------|------|----------|
| BookNotFoundException | 404 | Книга с указанным ID не найдена |
| MethodArgumentNotValidException | 400 | Ошибка валидации DTO |
| Exception | 500 | Внутренняя ошибка сервера |

Пример ошибки:
```json
{
  "timestamp": "2025-10-25T10:15:00",
  "status": 400,
  "error": "Validation Failed",
  "message": ["bookName: must not be blank"]
}
```

---

## 🧪 Пример запроса (curl)

```bash
curl -X POST http://localhost:8080/api/v1/books   -H "Content-Type: application/json"   -d '{"bookName": "Refactoring", "authorName": "Martin Fowler"}'
```

---

## 🧰 Используемые инструменты

| Инструмент | Назначение |
|-------------|------------|
| Spring Boot | REST-фреймворк |
| Flyway | Миграции БД |
| MapStruct | Маппинг DTO ↔ Entity |
| Lombok | Уменьшение бойлерплейта |
| PostgreSQL | База данных |
| Docker Compose | Поднятие инфраструктуры |