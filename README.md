# Book Service

Простой REST-сервис на Spring Boot для управления книгами (CRUD).

## Стек
- Java 21 / Spring Boot 3 (Web, Validation, Data JPA, Actuator, Security)
- PostgreSQL
- Flyway (миграции БД)
- Maven

## Быстрый старт

### 1) Настрой БД и приложение
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
      ddl-auto: validate      # рекомендуем validate + миграции Flyway
    show-sql: true
    properties:
      hibernate:
        format_sql: true
        dialect: org.hibernate.dialect.PostgreSQLDialect
  flyway:
    enabled: true
server:
  port: 8080
