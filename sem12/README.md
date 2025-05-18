1. Docker — это платформа для контейнеризации приложений. Она позволяет упаковывать приложения и их зависимости в изолированные контейнеры, которые работают одинаково на любой системе с Docker.

2. Как поднять БД в Docker?
```
docker run --name my-postgres -e POSTGRES_PASSWORD=mysecretpassword -p 5432:5432 -d postgres
```

3. Repository (репозиторий) — это паттерн доступа к данным, который абстрагирует работу с БД. В Spring Data JPA репозиторий — это интерфейс, расширяющий JpaRepository, который автоматически генерирует SQL-запросы.

4. Что такое Hibernate?
Hibernate — это ORM (Object-Relational Mapping) фреймворк для Java, который:
- Преобразует Java-объекты в таблицы БД и наоборот.
- Управляет жизненным циклом объектов.
- Генерирует SQL-запросы автоматически.

5. Связи в Hibernate (One-to-One, One-to-Many, Many-to-Many)

- One-to-One
    ```
    @Entity
    public class User {
        @Id
        private Long id;
        
        @OneToOne
        private Passport passport;
    }
    ```
- One-to-Many
    ```
        @Entity
    public class Author {
        @Id
        private Long id;
        
        @OneToMany(mappedBy = "author")
        private List<Book> books;
    }

    @Entity
    public class Book {
        @Id
        private Long id;
        
        @ManyToOne
        private Author author;
    }
    ```
- Many-to-Many
    ```
    @Entity
    public class Student {
        @Id
        private Long id;
        
        @ManyToMany
        private List<Course> courses;
    }

    @Entity
    public class Course {
        @Id
        private Long id;
        
        @ManyToMany(mappedBy = "courses")
        private List<Student> students;
    }
    ```
6. Общая таблица для разных объектов (Наследование в Hibernate)
    ```
    @Entity
    @Inheritance(strategy = InheritanceType.SINGLE_TABLE)
    @DiscriminatorColumn(name = "type")
    public abstract class Vehicle {
        @Id
        private Long id;
        private String model;
    }

    @Entity
    @DiscriminatorValue("CAR")
    public class Car extends Vehicle {
        private int seats;
    }

    @Entity
    @DiscriminatorValue("BIKE")
    public class Bike extends Vehicle {
        private int maxSpeed;
    }
    ```

7. Первый релиз Docker состоялся в 2013 году, а уже в 2017 году более 50% компаний из Fortune 100 использовали его в production!