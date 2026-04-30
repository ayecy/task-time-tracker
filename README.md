# Task Time Tracker API

Backend REST-сервис для учета времени, которое сотрудники тратят на задачи.

---

## Функциональность

### Сущности

#### Task (Задача)
|     Поле      |   Тип  |                 Описание                     |
|---------------|--------|----------------------------------------------|
| `id`          | Long   | Уникальный идентификатор (автоинкремент)     |
| `title`       | String | Название задачи (обязательно, 3-50 символов) |
| `description` | String | Описание задачи (опционально)                |
| `status`      | Enum   | Статус: `NEW`, `IN_PROGRESS`, `DONE`         |

#### TimeRecord (Запись времени)
|       Поле    |         Тип          |                  Описание                    |
|---------------|----------------------|----------------------------------------------|
| `id`          |        Long          |         Уникальный идентификатор             |
| `employeeId`  |        Long          |               ID сотрудника                  |
| `taskId`      |        Long          |         Ссылка на задачу (внешний ключ)      |
| `startTime`   |     LocalDateTime    |                 Начало работы                |
| `endTime`     |     LocalDateTime    |         Окончание работы (опционально)       |
| `description` |        String        |          Описание выполненной работы         |

---

## Эндпоинты API

### Задачи

| Метод   |        Путь          |       Описание         |                 Статусы                      |
|---------|----------------------|------------------------|----------------------------------------------|
| `POST`  | `/tasks`             | Создать задачу         |      `201 Created`, `400 Bad Request`        |
| `GET`   | `/tasks/{id}`        | Получить задачу по ID  |           `200 OK`, `404 Not Found`          |
| `PATCH` | `/tasks/{id}/status` | Изменить статус задачи | `200 OK`, `400 Bad Request`, `404 Not Found` |


#### Postman-коллекция 

Присутствует в task-time-tracker/postman/TaskTrackerAPI.postman_collection.json
Запуск через импорт в POSTMAN

## Стек проекта

Язык: Java 25.0.1
Фреймворк: Spring Boot 3.x
ORM: MyBatis 3.0.5 (SQL mapping через аннотации)
База данных: H2 (in-memory)
Сборка: Maven 3.9.11
Тестирование: JUnit 5 + Mockito
Валидация: Bean Validation (@Valid, @NotBlank, @Size)
Обработка ошибок: @RestControllerAdvice
Lombok: Для сокращения шаблонного кода

## Сборка проекта

После клонирования репозитория:
1) cd task-time-tracker
2) mvn clean compile spring-boot::run
3) http://localhost:8080/ping (для проверки работоспособности приложения)


## Unit-тесты

mvn test


## API документация

Интерактивная документация доступна через Swagger UI (после сборки):  
http://localhost:8080/swagger-ui.html

Спецификация OpenAPI 3.1.0:  
http://localhost:8080/v3/api-docs


## ИИ-артефакты

Приложены в виде скриншотов и в виде комментариев в коде
