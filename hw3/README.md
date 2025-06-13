# Запуск
```
docker compose --profile all up --build
```
- Фронтэнд: http://localhost:5173/
- Документация API: http://localhost:8080/swagger-ui/index.html#/

# Тесты
- Запуск
```
./gradlew test
```
- Посмотреть покрытие
```
./gradlew test && ./gradlew jacocoTestReport
```
Отчеты о покрытии сгенерируются в папках сервисов (api_gateway, order_service, payments_service). 
Найти их можно по пути {service_dir}/build/reports/jacoco/test/html/index.html
