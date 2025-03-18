# Запуск
```
./gradlew bootRun -q --console=plain
```

# Тестирование
```
./gradlew test
```

# Покрытие тестами
![image](https://github.com/user-attachments/assets/ae23df44-60c8-4bd3-bdef-d4e34c3c9d34)

# Использованные паттерны:
- Factory - см. папку project/factories.
- Builder - использовался, например, для конструирования поля ввода пользователя (файл project/cli/input/IntField).
- Command - см. папку project/commands.
- Decorator - использовался в качестве обертки команды для получения времени выполнения (файл project/cli/commands/CommandWithTimer).
- Strategy - использовалась для сериализации. В сериализатор встраивается конвертер,
  который может определенный класс перевести в удобный для сериализации формат и обратно.
  Все это можно найти в папке project/serializers.
- Facade - см. папку project/storages.
- Template - project/cli/input/base/InputField является базовым классом для всех полей ввода.
  Он позволяет наследникам определить метод validate, а остальную работу берет на себя.
