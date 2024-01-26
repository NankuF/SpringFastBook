# Добавление бинов с использованием аннотации @Component

С помощью стереотипных аннотаций можно создавать бины только для классов, определенных в приложении.
#### Плюсы
Меньше кода
#### Минусы
Нельзя добавить в контекст классы, не определенные в приложении (внешние библиотеки, String, Integer, etc)

```java
//ProjectConfig.java
package ru.poltoranin.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration  // аннотация для указания класса-конфигуратора
@ComponentScan(basePackages = "ru.poltoranin")  // где искать бины
public class ProjectConfig {

}

//Parrot.java
package ru.poltoranin.main;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

@Component  // указывает Spring, что объект этого класса необходимо добавить в контекст (bean) и управлять им
public class Parrot {
    private String name;

    @PostConstruct  // для добавления логики после создания объекта.
    public void init() {
        this.name = "Abu";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

//Main.java
package ru.poltoranin.main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import ru.poltoranin.config.ProjectConfig;

public class Main {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(ProjectConfig.class);
        Parrot p = context.getBean(Parrot.class);
        System.out.println(p.getName()); //Abu
        context.close();
    }
}
```