# Добавление бинов программно - context.registerBean()

#### Плюсы
Позволяет реализовать собственную логику добавления бинов в контекст Spring.
Этот метод можно использовать только в Spring 5 или в более поздних версиях.

```java
//ProjectConfig.java
package ru.poltoranin.config;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProjectConfig {}

//Parrot.java
package ru.poltoranin.main;

public class Parrot {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
//Main.java
package ru.poltoranin.main;
import java.util.function.Supplier;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.poltoranin.config.ProjectConfig;

public class Main {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(ProjectConfig.class);
        // настраиваем объект для добавления в контекст
        Parrot x = new Parrot();
        x.setName("Kiki");
        Supplier<Parrot> parrotSupplier = () -> x;

        // добавляем в контекст
        // context.registerBean("parrot1", Parrot.class, parrotSupplier);
        context.registerBean("parrot1",
                Parrot.class,
                parrotSupplier,
                bc -> bc.setPrimary(true));

        //используем бин из контекста
        Parrot p = context.getBean(Parrot.class);
        System.out.println(p.getName());
        context.close();
    }
}
```