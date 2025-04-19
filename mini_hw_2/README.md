# Запуск
```
./gradlew bootRun
```
# Swagger
```
http://localhost:8080/swagger-ui/index.html#/
```
# Отчет

Слои Clean Architecture:
- Domain Layer
  -  Сущности: Animal, Enclosure, FeedingSchedule
  - Репозитории (интерфейсы): AnimalRepo, EnclosureRepo, ScheduleRepo
- Application Layer
  - Сервисы (интерфейсы и их реализации): AnimalService, EnclosureService, FeedingService
- Infrastructure Layer
  - Реализация репозиториев: InMemoryAnimalRepo, InMemoryEnclosureRepo, InMemoryScheduleRepo
  - Конфигурации (DI и Swagger)
- Presentation Layer
  - REST контроллеры: AnimalController, EnclosureController, FeedingController, StatController

Применённые концепции DDD:
- Ограниченный контекст:
  - Зоопарк как цельный контекст с управлением животными, вольерами и расписаниями
- Сущности (Entities) и объекты-значения (Value Objects):
  - Сущности: Animal, Enclosure, FeedingSchedule со своими идентификаторами
  - Объекты-значения: Species, HealthStatus, Gender и т.д.
- Репозитории:
  - Интерфейсы для доступа к данным в домене
  - Реализации в инфраструктурном слое
