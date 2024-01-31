# Внедрение бинов с помощью аннотации @Autowired

#### Варианты применения аннотации @Autowired
1. На конструктор (лучший вариант, удобен для тестирования).
2. На поле (лучше не применять, используется рефлексия, сложно писать тесты).
3. На сеттер (редко, не популярно).

### На конструктор
Бин parrot будет создан в момент создания бина person.<br>
**Плюсы:**
1. Поле parrot **можно** сделать final.
2. Удобно тестировать.
```java
package ru.poltoranin.beans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Person {
    private String name;
    private final Parrot parrot; // можно сделать final

    @Autowired // передача бина в конструкторе (это wiring in params)
    public Person(Parrot parrot) {
        this.parrot = parrot;
    }
    // getters and setters
}

```
### На поле
Бин parrot будет создан через рефлексию, в обход конструктора, во время создания бина person.<br>
**Минусы:**
1. Поле parrot **нельзя** сделать final.
2. Неудобно тестировать.
```java
package ru.poltoranin.beans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Person {
    private String name;
    @Autowired
    private Parrot parrot; // нельзя сделать final

    // создавать конструктор и передавать бин через конструктор не обязательно. Бин parrot добавится в поле через рефлексию.
    // getters and setters
}
```
### На сеттер
Бин parrot будет создан при передаче в параметр сеттера.<br>
**Минусы:**
1. Поле parrot **нельзя** сделать final.<br>
2. Устаревший, непопулярный метод.
```java
package ru.poltoranin.beans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Person {
    private String name;
    private Parrot parrot; // нельзя сделать final

    ...

    @Autowired
    public void setParrot(Parrot parrot) {
        this.parrot = parrot;
    }

}
```

## Циклические зависимости
Стр 97.<br>
Чтобы разрешить циклическую зависимость, необходимо удалить зависимость в одном из бинов.<br>

Циклическая зависимость: Spring должен создать бин типа Parrot.
Однако, поскольку у Parrot есть зависимость от Person, сначала нужно создать Person.
Но, чтобы создать Person, нужно, чтобы уже существовал Parrot.<br>
Spring в тупике: он не может создать Parrot, поскольку для этого нужен Person,
и не может создать Person, поскольку для этого нужен Parrot.

Циклической зависимости легко избежать.
Достаточно убедиться, что у вас нет объектов, при создании которых
возникает взаимная зависимость этих объектов друг от друга.
Наличие такой зависимости — пример плохой разработки классов.<br>
В этом случае нужно переписать код.
```java
@Component
public class Person {
    private String name;
    @Autowired
    private Parrot parrot; // Person зависит от Parrot
}

@Component
public class Parrot {
    private String name;
    @Autowired
    private Person person; // Parrot зависит от Person
}
```