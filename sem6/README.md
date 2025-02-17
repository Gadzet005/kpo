# 1. Singleton (Одиночка)
Паттерн гарантирует, что класс имеет только один экземпляр и предоставляет глобальную точку доступа к этому экземпляру.
```
public class Singleton {
    private static Singleton instance;

    private Singleton() {}

    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }
}
```

# 2. Factory (Фабрика)
Паттерн предоставляет интерфейс для создания объектов, но позволяет подклассам изменять тип создаваемых объектов.   
```
interface Product {
    void use();
}

class ConcreteProductA implements Product {
    public void use() {
        System.out.println("Using Product A");
    }
}

class ConcreteProductB implements Product {
    public void use() {
        System.out.println("Using Product B");
    }
}

class Factory {
    public static Product createProduct(String type) {
        if (type.equals("A")) {
            return new ConcreteProductA();
        } else if (type.equals("B")) {
            return new ConcreteProductB();
        }
        return null;
    }
}
```

# 3. Factory Method (Фабричный метод)
Паттерн определяет интерфейс для создания объекта, но позволяет подклассам изменять тип создаваемого объекта.
```
abstract class Creator {
    public abstract Product factoryMethod();
}

class ConcreteCreatorA extends Creator {
    public Product factoryMethod() {
        return new ConcreteProductA();
    }
}

class ConcreteCreatorB extends Creator {
    public Product factoryMethod() {
        return new ConcreteProductB();
    }
}
```

# 4. Abstract Factory (Абстрактная фабрика)
Паттерн предоставляет интерфейс для создания семейств связанных или зависимых объектов без указания их конкретных классов.
```
interface AbstractFactory {
    Product createProductA();
    Product createProductB();
}

class ConcreteFactory1 implements AbstractFactory {
    public Product createProductA() {
        return new ConcreteProductA();
    }
    public Product createProductB() {
        return new ConcreteProductB();
    }
}

class ConcreteFactory2 implements AbstractFactory {
    public Product createProductA() {
        return new ConcreteProductA();
    }
    public Product createProductB() {
        return new ConcreteProductB();
    }
}
```

# 5. Builder (Строитель)
Паттерн отделяет конструирование сложного объекта от его представления, так что один и тот же процесс строительства может создавать разные представления.
```
class Product {
    private String partA;
    private String partB;

    public void setPartA(String partA) { this.partA = partA; }
    public void setPartB(String partB) { this.partB = partB; }
}

class Builder {
    private Product product = new Product();

    public void buildPartA() { product.setPartA("Part A"); }
    public void buildPartB() { product.setPartB("Part B"); }
    public Product getResult() { return product; }
}
```

# 6. Prototype (Прототип)
Паттерн позволяет копировать объекты, не вдаваясь в подробности их реализации.
```
interface Prototype {
    Prototype clone();
}

class ConcretePrototype implements Prototype {
    public Prototype clone() {
        return new ConcretePrototype();
    }
}
```

# 7. Уникальный факт
Паттерны проектирования, известные как "Gang of Four" (GoF), не только предоставляют решения для часто встречающихся проблем в объектно-ориентированном дизайне, но и отражают философию событийного программирования. В книге "Design Patterns: Elements of Reusable Object-Oriented Software" (1994), авторы не только описали 23 паттерна, но и указали на значение абстракции и инкапсуляции в дизайне программного обеспечения. Именно поэтому эти паттерны стали краеугольным камнем для многих языков программирования, включая Java, C#, и Python. В некоторых современных языках программирования уже внедрены концепции и большие библиотеки, основанные на этих паттернах, что свидетельствует о их вечной актуальности.